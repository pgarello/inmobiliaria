package servlets;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import resources.Configuracion;

public class MinJob implements Runnable {

@Override
public void run() {
    // Do your hourly job here.
	Calendar oFecha = Calendar.getInstance();
    
	int minuto = 25;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	System.out.println(
			sdf.format(oFecha.getTime()) + 
			" MinJob trigged by scheduler - " + minuto );
	
	try {
		System.out.println(Configuracion.getInstance().getProperty("clave") );
	} catch(Exception e) {
		e.printStackTrace();
	}

// Por ahora hago el backup en función de la HORA	
//	if (oFecha.get(Calendar.MINUTE) == minuto)	
//		HourlyJob.realizarBackup();
    
  }
}