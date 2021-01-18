package servlets;

import java.io.InputStream;
import java.util.Calendar;

import resources.Configuracion;

public class HourlyJob implements Runnable {

	@Override
	public void run() {
	    // Do your hourly job here.
		Calendar oFecha = Calendar.getInstance();
	    System.out.println(oFecha.getTime().toLocaleString() + " HourlyJob trigged by scheduler");
	    
	    // Inicio el proceso
	    
	    /** 1º Realizo el backup de la base de datos */
	    int horaBACKUP = 10;
	    if (oFecha.get(Calendar.HOUR_OF_DAY) == horaBACKUP)	    
	    	realizarBackup();
	    
	}

	/**
	 * Realiza el backup de la base de datos
	 * @return true si el proceso se realiza con éxito
	 */
	public static boolean realizarBackup() {
		String username = "dba_inmobiliaria";
		String database = "inmobiliaria";
		String file = System.getProperty("catalina.base") + "/temp/" + database + ".sql";
		//String file = "/tmp/" + database + ".sql";
		String password = "dba"; // dba en MD5
		
		String path = Configuracion.getInstance().getProperty("pathPGDUMP");
				
		Process runtimeProcess;
		int success=-1;
		try {
			Runtime.getRuntime();
			ProcessBuilder pb = new ProcessBuilder(path + "pg_dump", "-U", username, "-v", "-f", file, database);
			pb.environment().put("PGPASSWORD", password);
			pb.redirectErrorStream(true);
			
			runtimeProcess = pb.start();
			success = runtimeProcess.waitFor();
			
			if (runtimeProcess.exitValue() != 0) {
				System.out.println("Backup ERROR");
				InputStream errorStream = runtimeProcess.getErrorStream();
			    int c = 0;
			    while ((c = errorStream.read()) != -1) {
			    	System.out.print((char)c);
			    }
			} else System.out.println("Backup CORRECTO!!!!"); 
		    
		    
		    System.out.println("--------------------------------------------");
		    
		    /* Para ver la ejecución del proceso
			try {
				InputStream is = runtimeProcess.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String ll;
				while ((ll = br.readLine()) != null) {
					System.out.println(ll);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} */
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return (success == 0);
		
	}
}