package servlets;

public class MinJob implements Runnable {

@Override
public void run() {
    // Do your hourly job here.
    System.out.println("MinJob trigged by scheduler");
  }
}