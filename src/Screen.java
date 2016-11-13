
public class Screen 
{
	//Print single line of text to "Screen"
	public void Display(String text)
	{
		System.out.println(text);
	}
	//Print an String array to the "screen"
	public void Display(String[] text)
	{
		for(String s: text)
		System.out.println(s);
	}
	
}
