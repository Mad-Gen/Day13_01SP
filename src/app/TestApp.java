package app;

import java.util.Timer;
import java.util.TimerTask;

public class TestApp{

  TestApp(){
    
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        TestApp.this.updateTimer();
      }
    };
    
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(task, 0L, 1000L);
  }
  
  /**
   * 
   */
  public void updateTimer() {
    System.out.println("タイマーで更新");
  }
  
  public static void main(String[] args) {
    new TestApp();
  }
}
