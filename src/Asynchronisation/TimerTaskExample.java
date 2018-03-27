package Asynchronisation;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskExample extends TimerTask{

	static String  test = null;
	static Timer timer;
	static TimerTask timerTask;
	
	@Override
	public void run() {
		System.out.println("Start time:" + new Date());
		
		if(test!=null){
			System.out.println("TimerTask cancelled! in run function:" + new Date());
			timer.cancel();
			timer.purge();
		}
		
		 
		
		
		System.out.println("End time:" + new Date());
	}
	
	 
	 private static int test(){
		 int a = 0;
		 for(int i=0;i<10;i++){
			  a = 2 + i;
			  test();
		 }
		 test = "test";
		 return a;
	 }
	 
	 public static void autreMethode() throws InterruptedException{
		 timerTask = new TimerTaskExample();
		// running timer task as daemon thread
		timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, 5000);

		//timer.schedule(timerTask,5000);
		System.out.println("TimerTask begins! :" + new Date());
		test();
//		int a = 0;
//		for(int i=0;i<200000000;i++){
//			a = test();
//		}
//		if(a == 0){
//			 System.out.println("Dans le if de timerCancel:" + new Date());
//			 System.out.println("TimerTask cancelled! :" + new Date());
//			 
//		 }
		// cancel after sometime
		timer.cancel();
		
	 }
	 
	 public static void main(String args[]) {
		 try {
			autreMethode();
		} catch (InterruptedException e) {
			System.out.println("Echec");
		}	 
	 }

}
