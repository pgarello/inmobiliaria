package servlets;

public class HourlyJob implements Runnable {

@Override
public void run() {
    // Do your hourly job here.
    System.out.println("HourlyJob trigged by scheduler");
  }
}