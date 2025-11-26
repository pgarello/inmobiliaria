package servlets;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import resources.Configuracion;

public class HourlyJob /*implements Runnable*/ {

	//@Override
	public void run() {
	    // Do your hourly job here.
		Calendar oFecha = Calendar.getInstance();
	    System.out.println(oFecha.getTime().toLocaleString() + " HourlyJob trigged by scheduler");
	    
	    // Inicio el proceso
	    
	    /** 1º Realizo el backup de la base de datos */
	    int horaBACKUP = 10;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    if (oFecha.get(Calendar.HOUR_OF_DAY) == horaBACKUP)	    
	    	realizarBackup(sdf.format(oFecha.getTime()));
	    
	}

	/**
	 * Realiza el backup de la base de datos
	 * @return true si el proceso se realiza con éxito
	 */
	public static boolean realizarBackup(String fecha) {
				
	    String username = "dba_inmobiliaria";
	    String database = "inmobiliaria";
	    String password = "dba";
	    String file = System.getProperty("catalina.base") + "/temp/" + database + fecha + ".sql";
	    String path = Configuracion.getInstance().getProperty("pathPGDUMP");

	    ProcessBuilder pb = new ProcessBuilder(
	        path + "pg_dump",
	        "-U", username,
	        "-h", "localhost",
	        database
	    );

	    // Configuramos la variable de entorno del password
	    pb.environment().put("PGPASSWORD", password);

	    // Redirigimos salida del comando al archivo
	    File outputFile = new File(file);
	    pb.redirectOutput(outputFile);
	    pb.redirectErrorStream(true);

	    int success = -1;
	    try {
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
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   
	    realizarBackup(sdf.format(oFecha.getTime()));
		
	}
	
}