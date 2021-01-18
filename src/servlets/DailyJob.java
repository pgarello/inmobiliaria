package servlets;

import java.util.Calendar;

public class DailyJob implements Runnable {

@Override
public void run() {
    // Do your hourly job here.
	Calendar oFecha = Calendar.getInstance();
    System.out.println(oFecha.getTime().toLocaleString() + " DailyJob trigged by scheduler");
  }
}