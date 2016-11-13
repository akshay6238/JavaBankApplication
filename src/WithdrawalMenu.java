
public class WithdrawalMenu extends Menu
{ private String[] menuText = 
	{"Select withdrawal amount option",
	 "$20 - press 1",
	 "$40 - press 2",
	 "$60 - press 3",
	 "$100 - press 4",
	 "$200 - press 5",
	 "To cancel transaction - press 6"};


	@Override
	public int displayMenu(Screen atmScreen) 
	{ int selection=0;
		atmScreen.Display(menuText);
			while (selection < 1 || selection > 6)
			{
				selection=super.input.nextInt();
				if (selection < 1 || selection > 6)
				{
					atmScreen.Display("Invalid selection");
				}
			}
		return selection;
	}

}
