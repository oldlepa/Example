package testDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestDate {
	
	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		
		System.out.println(dateFormat.format(cal.getTime()));
		
		Date date1 = cal.getTime(); 
		
		long milliseconds1 = date1.getTime();
		
		System.out.println("la date de debut en long :" + milliseconds1);

		//Add one day to current date.
		cal.add(Calendar.DATE, -20);
		System.out.println(dateFormat.format(cal.getTime()));
		
		Date date2 = cal.getTime(); 
		
		long milliseconds2 = date2.getTime();
		
		System.out.println("la date en long apres l'extraction de 20 jour :" + milliseconds2);

		//Substract one day to current date.
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5);
		System.out.println(dateFormat.format(cal.getTime()));
		
		Date date = cal.getTime(); 
		
		long milliseconds = date.getTime();
		
		System.out.println("la date en long apres l'extraction de 5 jour:" + milliseconds);
		
		
		int diffInDays = (int) ((date.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
		
		System.out.println("la difference entre les dates est : " + diffInDays);
		
		if(date2.before(date)){
			System.out.println("le compte est valide");
		}else{
			System.out.println("le compte expire dans 5");
		}
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(milliseconds);
		System.out.println(dateFormat.format(cal2.getTime()));
		
		
	}

}
