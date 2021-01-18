package servlets;

import java.util.Calendar;

import resources.Configuracion;

public class MinJob implements Runnable {

@Override
public void run() {
    // Do your hourly job here.
	Calendar oFecha = Calendar.getInstance();
    
	int minuto = 25;
	
	System.out.println(
			oFecha.getTime().toLocaleString() + 
			" MinJob trigged by scheduler - " + minuto );
	
	try {
		System.out.println(Configuracion.getInstance().getProperty("clave") );
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	if (oFecha.get(Calendar.MINUTE) == minuto)	
		HourlyJob.realizarBackup();
    
  }
}