import java.util.Scanner;


public class Main 
{
	public static void main(String args[])
	{	
		String host = IO();
		@SuppressWarnings("unused")
		Spiel spiel;
				
		spiel = new Spiel(host);
	}

	private static String IO()
	{
		String host = null;
		
		System.out.print("Host? ");
		
		Scanner scan = new Scanner(System.in);
		
		host = scan.nextLine();
		
		if (host.length()  < 2) host = null;
		
		scan.close();
		
		return host;
	}
}