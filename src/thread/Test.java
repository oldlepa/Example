package thread;

public class Test {
    public static void main(String args[]){
    	
    	Test t = new Test();
    	t.method();
    }

    void method() {
        long endTimeMillis = System.currentTimeMillis() + 1000;
        while (true) {
            // method logic
            System.out.println("Le debut");
        	int a = 1, b = 10;
        	while (a < b)
        	{
        	  System.out.println("coucou " +a+ " fois !!");
        	  a++;
        	  
        	  if (System.currentTimeMillis() > endTimeMillis) {
                  System.out.println("le temps est depasse");
                  return;
              }
        	}
        	
        	
        	
            
        }
    }
}