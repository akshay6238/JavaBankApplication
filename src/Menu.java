import java.util.Scanner;
public abstract class Menu 
{
	protected Scanner input = new Scanner(System.in);
	
	public abstract int displayMenu(Screen atmScreen);

}


