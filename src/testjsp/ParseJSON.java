package testjsp;

public class ParseJSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Parsing JSON");
		

		 
		 
		 	String s="if Outlook=sunny than play_tennis=no";
			String domaine="Outlook";
	 
			System.out.println(""+s);
			System.out.println(""+s.indexOf("Outlook")); 
	 
			int x =s.indexOf("Outlook")+domaine.length()+1;
			int y = s.indexOf(" ",x);
			System.out.println(""+s.substring(x,y));
			
			
			String paramApp = "http://www.google.com";
			
			String urlParam = null;
			 
			 if(paramApp.contains("http")){
				urlParam =  paramApp;
				System.out.println("Dans le if:"+urlParam);
			 }else{
				 urlParam = "https://" + paramApp;
				 System.out.println("Dans le else :"+urlParam);
			 }
			


	}

}
