package servlets;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import resources.Configuracion;

public class HourlyJob implements Runnable {

	//@Override
	public void run() {
	    // Do your hourly job here.
		Calendar oFecha = Calendar.getInstance();
	    System.out.println(oFecha.getTime().toLocaleString() + " HourlyJob trigged by scheduler");
	    
	    // Inicio el proceso
	    
	    /** 1º Realizo el backup de la base de datos */
	    int horaBACKUP = 6;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    if (oFecha.get(Calendar.HOUR_OF_DAY) == horaBACKUP)	    
	    	realizarBackup(sdf.format(oFecha.getTime()));
	    
	}

	/**
	 * Realiza el backup de la base de datos
	 * @return true si el proceso se realiza con éxito
	 */
	public static boolean realizarBackup(String fecha) {
				
		System.out.println("HourlyJob.realizarBackup:" + fecha);
			    
	    String database = "inmobiliaria";
	    
	    String username = Configuracion.getInstance().getProperty("bd_username");
	    String password = Configuracion.getInstance().getProperty("bd_password");
	    
	    // Construir la ruta del archivo
	    String file = "";
	    if (System.getProperty("catalina.base") != null) {
	        file = System.getProperty("catalina.base") + "/temp/" + database + fecha + ".sql";
	    } else {
	        file = "/tmp/" + database + fecha + ".sql";
	    }

	    // Verificar si el directorio existe y crearlo si es necesario
	    File outputDir = new File(file).getParentFile();
	    if (!outputDir.exists()) {
	        boolean dirsCreated = outputDir.mkdirs();
	        if (!dirsCreated) {
	            Logger.getLogger("Inmobiliaria").log(Level.SEVERE, "No se pudo crear el directorio de salida: " + outputDir.getPath());
	            return false;
	        }
	    }

	    // Obtener la ruta de pg_dump
	    String path = Configuracion.getInstance().getProperty("pathPGDUMP");
	    if (path == null || path.isEmpty()) {
	        Logger.getLogger("Inmobiliaria").log(Level.SEVERE, "La ruta de pg_dump no está configurada correctamente");
	        return false;
	    }

	    // Preparar el comando
	    ProcessBuilder pb = new ProcessBuilder(
	            path + "pg_dump",   // Asegúrate de que esta ruta sea correcta
	            "-U", username,
	            "-h", "localhost",
	            database
	    );

	    // Configurar la variable de entorno para la contraseña
	    pb.environment().put("PGPASSWORD", password);

	    // Redirigir la salida del comando al archivo
	    File outputFile = new File(file);
	    pb.redirectOutput(outputFile);
	    pb.redirectErrorStream(true);

	    int success = -1;
	    try {
	        // Ejecutar el proceso
	        Process process = pb.start();
	        success = process.waitFor();

	        if (success != 0) {
	            Logger.getLogger("Inmobiliaria").log(Level.SEVERE, "Backup ERROR - exit code: " + success);
	            try (InputStream errorStream = process.getErrorStream()) {
	                int c;
	                while ((c = errorStream.read()) != -1) {
	                    Logger.getLogger("Inmobiliaria").log(Level.SEVERE, String.valueOf((char) c));
	                }
	            }
	        } else {
	            Logger.getLogger("Inmobiliaria").log(Level.INFO, "Backup CORRECTO: " + file);
	        }

	    } catch (Exception e) {
	        Logger.getLogger("Inmobiliaria").log(Level.SEVERE, "Backup falló con excepción", e);
	    }

	    return (success == 0);
	}

	
	public static void main(String[] args) {
		
		Calendar oFecha = Calendar.getInstance();
	    System.out.println(oFecha.getTime().toLocaleString() + " HourlyJob trigged by scheduler");
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
   
	    realizarBackup(sdf.format(oFecha.getTime()));
		
	}
	
}