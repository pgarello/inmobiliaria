import java.text.SimpleDateFormat;
import java.util.Calendar;

import servlets.HourlyJob;

public class ejecutable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Calendar oFecha = Calendar.getInstance();
	    System.out.println(oFecha.getTime().toLocaleString() + " HourlyJob trigged by scheduler");
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   
	    HourlyJob.realizarBackup(sdf.format(oFecha.getTime()));
		
	}

}
