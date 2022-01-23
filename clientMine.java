//Programmer: Adam A
//Date Created: 5/29/2019
//Date Revised: 1/22/2022
//Description: Play Mine Cleaner (click on tiles while avoiding bombs)

import java.awt.*; // Code that has to be imported
import javax.swing.*;
import java.awt.event.*;
import java.lang.*;
import java.util.ArrayList;
import javax.swing.AbstractButton;

public class clientMine implements ActionListener
{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Allows us to get the X and Y Values of the users screen size (used to center user interfaces)
	JFrame frame; // The main JFrame that contains the various JPanels
	int theNum; // This will store the grid size the user selects (for example it will store 7 if the player wants a 7x7 grid)
	int dif = 0;
	int secondsDone = 0; // Tracks game time
	boolean youWon = false; // Enabled when the player wins
	boolean threadStarted = false; // Enabled when the thread initally starts (the thread tracks time elapsed)

	boolean loweredByOne = false; // Enabled when the bomb count is lowered by one (only possible if the safety setting is on)


	Thread myThread = new MyThread(); // Thread to track time taken

	ArrayList<ArrayList<Integer>> retState; // ArrayList of ArrayLists (Integer values), incase the arraylist in minecleaner gets deleted.

	int thNum; // De-bugging purposes, to re-call the constructor for the check bomb code to work
	int theDif;

	boolean flagEnabled = false; // If flag mode is enabled
	boolean youLost = false; // Enabled if the player loses
	int bombsInGame = 0; // Amount of bombs in-game

	Color c1 = new Color(128,0,128); // Colour value of the flagged spaces

	Color l1 = new Color(238,238,238); // Almost White
	Color d1 = new Color(45,45,45); // Dark mode equivelant

	Color l2 = new Color(255,255,255); // Full white
	Color d2 = new Color(0,0,0); 

	Color ltext = new Color(0,0,0); // Black text for light backgrounds
	Color dtext = new Color(255,255,255); // White text for dark backgrounds

	ArrayList<JButton> yourTiles = new ArrayList<JButton>(); // Arraylist of the tiles for mine cleaner
	ArrayList<Boolean> wasClicked = new ArrayList<Boolean>(); // Was the tile clicked

	///// Settings that can be toggled by the user in the settings menu ///////
	boolean darkTheme = false; // Makes menus appear in a dark theme when enabled
	boolean firstSafeOp = false; // Makes the first click always safe (if a bomb, it will make it not a bomb)
	//////////////////////////////////////////////////////////////////////////
	boolean clickedAlready = false; // Enabled upon user clicking their first spot


	//////////////// The main menu that appears when the program is ran
	JPanel mainMenu; // For all of these, the JPanel is listed first and the contents of that JPanel is listed below
	JButton pickCleaner;
	JButton pickSettings;
	////////////////

	//////////////// The settings menu, features such as dark theme may be included here
	JPanel settings;

	JPanel actualSet; // Two JPanels for proper spacing
	JPanel retSet; 

	JButton darktoggle; // Toggle between light and dark mode
	JButton firstsafe; // Make first click safe (may lower bomb count by 1)

	JButton retBut; // Return button
	////////////////

	//////////////// User can select the game they want to see the Computer play (basically a demo of how sorting algorithims can be used to win more easily
	JPanel pickMode;
	////////////////

	//////////////// Menu for the Mine Cleaner minigame that lets you choose the grid's size
	JPanel pickGridSize;
	JPanel extra; // Extra JPanel used to fix an issue with text alignment
	JPanel one; // Using JPanel inside of JPanel so that I can have one with a BoxLayout on X and one on Y-axis
	JPanel two;
	JPanel difficulty;
	JLabel topText;
	JTextField theSize;
	JButton submit;
	JButton mainReturn;
	JButton easy;
	JButton medium;
	JButton hard;
	///////////////

	/////////////// The actual game of mine cleaner
	JPanel actualGame;
	JPanel theTopInfo;
	JPanel theGrid;

	JLabel bombsLeft;
	JLabel time;
	JButton flagMode;
	JButton mainMode;
	///////////////

	////////////// WIN SCREEN
	JPanel winScreen;
	JLabel winMsg;
	JLabel timeel;
	JButton backtoMain;

	//////////////

	///////////// Lose button
	JButton lostReturn;
	/////////////


	public clientMine()
	{
		frame = new JFrame("Main Menu"); // Inital title of the menu
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		settings = new JPanel();
		settings.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Border
		settings.setBackground(Color.white);
		settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));

		actualSet = new JPanel();
		actualSet.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Border
		actualSet.setBackground(Color.white);
		actualSet.setLayout(new BoxLayout(actualSet, BoxLayout.Y_AXIS));

		retSet = new JPanel();
		retSet.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Border
		retSet.setBackground(Color.white);
		retSet.setLayout(new BoxLayout(retSet, BoxLayout.Y_AXIS));

		retBut = new JButton();
		retBut.setText("       Go Back        "); // This and the next 2 buttons are the buttons in the settings menu
		retBut.setAlignmentX(Component.CENTER_ALIGNMENT);
		retBut.setActionCommand("Go back");
		retBut.addActionListener(this);

		darktoggle = new JButton();
		darktoggle.setText("    Dark Theme    ");
		darktoggle.setAlignmentX(Component.CENTER_ALIGNMENT);
		darktoggle.setActionCommand("Theme");
		darktoggle.addActionListener(this);

		firstsafe = new JButton();
		firstsafe.setText("Safety: Disabled");
		firstsafe.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstsafe.setActionCommand("FirstSafe");
		firstsafe.addActionListener(this);


		actualSet.add(darktoggle);
		actualSet.add(firstsafe);
		retSet.add(retBut);


		settings.add(actualSet);
		settings.add(retSet);





		frame.add(settings);

		////////////////////////

				
		////////////////////////	Main Menu set up	
		mainMenu = new JPanel();
		mainMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Border
		mainMenu.setBackground(Color.white);
		mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));



		pickCleaner = new JButton("Mine Cleaner"); // Mine Cleaner Option Button
		pickCleaner.setAlignmentX(JButton.CENTER_ALIGNMENT);

		pickCleaner.addActionListener(this);
		pickCleaner.setMinimumSize(new Dimension(120,30)); // Forcing a size
		pickCleaner.setMaximumSize(new Dimension(120,30));
		mainMenu.add(pickCleaner);

		pickSettings = new JButton("Settings"); // Settings Option Button
		pickSettings.setAlignmentX(JButton.CENTER_ALIGNMENT);
		pickSettings.setActionCommand("Settings");
		pickSettings.addActionListener(this);
		pickSettings.setMinimumSize(new Dimension(120,30)); // Forcing a size
		pickSettings.setMaximumSize(new Dimension(120,30));
		mainMenu.add(pickSettings);


		mainMenu.add(pickCleaner);
		mainMenu.add(pickSettings);



		mainMenu.setBackground(Color.white); // By default, the menu is white


		// Prepare win screen
		winScreen = new JPanel();
		winScreen.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 5)); // Border
		winScreen.setBackground(Color.white);
		winScreen.setLayout(new BoxLayout(winScreen, BoxLayout.Y_AXIS));
			
		winMsg = new JLabel();
		winMsg.setText("Congrats! You Won!");
		winScreen.add(winMsg);
			
		timeel = new JLabel();
		timeel.setText("Won in [loading]");
		winScreen.add(timeel);
			
			
			
		backtoMain = new JButton();
		backtoMain.setText("Back to Main Menu");
		backtoMain.setActionCommand("New Game");
		backtoMain.addActionListener(this);
		winScreen.add(backtoMain);
			
		frame.add(winScreen);
		winScreen.setVisible(false);
		frame.setContentPane(mainMenu);
		/////////////////////////


		//////////////////////// Menu for picking grid size of the mine cleaner game
		pickGridSize = new JPanel();
		pickGridSize.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Border
		pickGridSize.setBackground(Color.white);
		pickGridSize.setLayout(new BoxLayout(pickGridSize, BoxLayout.Y_AXIS));

		extra = new JPanel();
		extra.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Border
		extra.setBackground(Color.white);
		extra.setLayout(new BoxLayout(extra, BoxLayout.X_AXIS));

		one = new JPanel();
		one.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 5)); // Border
		one.setBackground(Color.white);
		one.setLayout(new BoxLayout(one, BoxLayout.Y_AXIS));

		difficulty = new JPanel();
		difficulty.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 5)); // Border
		difficulty.setBackground(Color.white);
		difficulty.setLayout(new BoxLayout(difficulty, BoxLayout.X_AXIS));

		two = new JPanel();
		two.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5)); // Border
		two.setBackground(Color.white);
		two.setLayout(new BoxLayout(two, BoxLayout.X_AXIS));




		pickGridSize.add(extra);
		pickGridSize.add(one);
		pickGridSize.add(difficulty);
		pickGridSize.add(two);

		topText = new JLabel("Please select your grid size:                                      "); // These go on the set-up menu
		topText.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		extra.add(topText);

		theSize = new JTextField(5); // SET-UP MENU (Allows user to pick grid size)
		theSize.setHorizontalAlignment(JTextField.LEFT);
		one.add(theSize);

		easy = new JButton("Easy"); // This and next 2 are difficulty option buttons
		easy.setAlignmentX(JButton.LEFT_ALIGNMENT);
		easy.addActionListener(this);
		difficulty.add(easy);

		medium = new JButton("Medium");
		medium.setAlignmentX(JButton.LEFT_ALIGNMENT);
		medium.addActionListener(this);
		difficulty.add(medium);

		hard = new JButton("Hard");
		hard.setAlignmentX(JButton.LEFT_ALIGNMENT);
		hard.addActionListener(this);
		difficulty.add(hard);

		submit = new JButton("Submit"); // Submit button
		submit.setAlignmentX(JButton.LEFT_ALIGNMENT);
		submit.addActionListener(this);
		two.add(submit);

		mainReturn = new JButton("Go back");
		mainReturn.setActionCommand("Go back");
		mainReturn.setAlignmentX(JButton.LEFT_ALIGNMENT);
		mainReturn.addActionListener(this);
		two.add(mainReturn); 
		///////////////////////

		////////////////////// ACTUAL GAME: MINE CLEANER

		actualGame = new JPanel(); // JPanel for the actual minecleaner game
		actualGame.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 5)); // Border
		actualGame.setBackground(Color.white);
		actualGame.setLayout(new BoxLayout(actualGame, BoxLayout.Y_AXIS));

		theTopInfo = new JPanel(); // JPanel for the actual minecleaner game (but for the info at the top)
		theTopInfo.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 5)); // Border
		theTopInfo.setBackground(Color.white);
		theTopInfo.setLayout(new GridLayout(2,2));

		theGrid = new JPanel(); // JPanel for the actual minecleaner game (but for the tiles)
		theGrid.setBorder(BorderFactory.createEmptyBorder(5, 6, 5, 5)); // Border
		theGrid.setBackground(Color.white);
		theGrid.setLayout(new GridLayout(2,0));

		//////////////////////

		frame.pack();
		frame.setSize(200,110);
		frame.setLocation((screenSize.width/2)-100, (screenSize.height/2)-55);


		frame.setVisible(true);
	}



	private static void runGUI()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		new clientMine();	
	}
		
	public class MyThread extends Thread // Threads can be ran in conjunction with other code, so this is used to track time
	{
		public void run()
		{
			while(true)
			{
				try{Thread.sleep(150);}catch(Exception e){}
				while(!youWon && !youLost)
				{
					secondsDone = secondsDone + 1;
					String hour = Integer.toString(((int)Math.floor(secondsDone/60))); // This is actually supposed to be minutes (60 seconds in a min, rounded down)
					String min = Integer.toString(((int)(secondsDone - (60*(Math.floor(secondsDone/60)))))); // This is actually seconds (count except minus minutes rounded down*60)
		
						if(hour.length() == 1) // If the min or second count are only 1 number long, add a 0 pre-fix.
						{
							hour = "0" + hour;
						}
						if(min.length() == 1)
						{
							min = "0" + min;
						}
		
					String timeText = (hour + ":" + min); // Prepare the text to show
					time.setText("Time: " + timeText);
					try{Thread.sleep(1000);}catch(Exception e){}
				}
		

		
			}
		}
	}
		

		
	public void diffChanged() // DIFFICULTY GOT CHANGED
	{
	
		if(!darkTheme) // if not dark theme, non-chosen ones should be white, else dark button colour
		{
			easy.setBackground(l2);
			medium.setBackground(l2);
			hard.setBackground(l2);
		}
		else
		{
			easy.setBackground(d1);
			medium.setBackground(d1);
			hard.setBackground(d1);
		}
		
		if(dif == 1) // Easy
		{
			if(!darkTheme)
			{
				easy.setBackground(new Color(180, 180, 180)); // Make button darker than the other difficulties (light mode)
			}
			else
			{
				easy.setBackground(new Color(90,90,90)); // Make button brighter than the other difficulties (dark mode)
			}
		}	
		else if(dif == 2) // Medium
		{
			if(!darkTheme)
			{
				medium.setBackground(new Color(180, 180, 180));
			}
			else
			{
				medium.setBackground(new Color(90,90,90));
			}
		}
		else if(dif == 3) // Hard
		{
			if(!darkTheme)
			{
				hard.setBackground(new Color(180, 180, 180));
			}
			else
			{
				hard.setBackground(new Color(90,90,90));
			}
		}
	}
		
	//Show win screen!
	public void theyWon()
	{
		frame.setTitle("Winner"); // Set frame title for win screen
		frame.setContentPane(winScreen);
		
		int mins = (int)(Math.floor(secondsDone/60)); // Calculate time elapsed
		int secs = (int)(secondsDone - ((Math.floor(secondsDone/60)*60)));
		
		String smins = Integer.toString(mins);
		String ssecs = Integer.toString(secs);
		
		if(smins.length() == 1) // If minutes or seconds is 1 in length, add a 0 before the number
		{
			smins = "0" + smins;
		}
		if(ssecs.length() == 1)
		{
			ssecs = "0" + ssecs;
		}
		

		
		timeel.setText("Won in " + smins + ":" + ssecs); // Display the time text
	
		frame.setSize(164,100);
		frame.setLocation((screenSize.width/2)-82, (screenSize.height/2)-50);
			
		if(darkTheme) // Dark theme setup
		{
			winScreen.setBackground(d2);
		}
		else
		{
			winScreen.setBackground(l2);
		}
		
		winScreen.setVisible(true);
		actualGame.setVisible(false);
		
		
		
		frame.setContentPane(winScreen);
	}
		
	//Pre: None
	//Sends: boolean (won or not yet)
	//Will let the program know if the user has met win conditions or not
	public void didTheyWin()
	{
		int estBombs = 0; // Amount of bombs flagged by user
		int nonBombs = 0; // Amount of spaces not interacted with by user
		
			for(int x = 0; x<=yourTiles.size()-1; x++) // Go through all the tiles
			{
				if(yourTiles.get(x).getBackground().equals(c1)) // FLAGGED SPACE
				{
					if(yourTiles.get(x).getText().equals("")) // As a safety measure this checks if the flagged space has a number indicating nearby bombs on it (it should not)
					{
						estBombs = estBombs + 1;
					}
					else // Flagged space has already been confirmed not to be a bomb (will correct the colour based on the player's chosen theme)
					{
						if(darkTheme)
						{
							yourTiles.get(x).setBackground(d1);
						}
						else
						{
							yourTiles.get(x).setBackground(l2);
						}
					}
				}
				else if( (yourTiles.get(x).getText().equals("")) && yourTiles.get(x).isVisible() && (yourTiles.get(x).getBackground().equals(l2) || yourTiles.get(x).getBackground().equals(d1)) )
				{
					nonBombs = nonBombs + 1; // Space not clicked or flagged
				}			
			}
			
		bombsLeft.setText("Bombs Left: " + (bombsInGame-estBombs) + " / " + bombsInGame); // Show bomb left count
		
		if(estBombs == bombsInGame && nonBombs == 0) // If no spaces untouched excluding flagged ones (which need to equal bomb count) then user won
		{ 
			youWon = true;
			theyWon();
		}
		// Not a win at the moment
	}
		
		
		
		
	public void actionPerformed(ActionEvent event) // Action performed
	{
		actionFunction(event.getActionCommand());
	}

	public void actionFunction(String eventName) // Action/Event Handler (actionPerformed is a seperate function to allow for manual requests)
	{
		int foundNum = -1; // Default
		try
		{
			foundNum = Integer.parseInt(eventName); // Event name is a number, therefore it is one of the squares on the grid of Mine Cleaner
		}
		catch(Exception e)
		{
			foundNum = -1; // Event name is not a number, therefore make it -1 so we know it is not that type of event
		}
	
		if(eventName == "Mine Cleaner") // MAIN MENU: MINE CLEANER SELECTED
		{
			mainMenu.setVisible(false); // Setting the JPanel mainMenu to not be visible
			pickGridSize.setVisible(true);
			frame.setContentPane(pickGridSize);
			frame.setTitle("Mine Cleaner: Size Select");
			frame.setSize(300,170);
			frame.setLocation((screenSize.width/2)-150, (screenSize.height/2)-85);
	
		}
		else if(eventName.equals("FirstSafe")) // Enable/Disable safety setting
		{
			if (firstSafeOp)
			{
				firstSafeOp = false;
				firstsafe.setText("Safety: Disabled");
			}
			else
			{
				firstSafeOp = true;
				firstsafe.setText(" Safety: Enabled ");
			}
		}
		else if(eventName.equals("New Game")) // Go back to main menu
		{
			winScreen.setVisible(false);
			mainMenu.setVisible(true);
			frame.setSize(200,110);
			frame.setLocation((screenSize.width/2)-100, (screenSize.height/2)-55);
			frame.setTitle("Main Menu");
			frame.setContentPane(mainMenu);

			for(int x = 0; x<yourTiles.size(); x++) // Clear the tile buttons
			{
				yourTiles.get(x).setVisible(false);
				theGrid.remove(yourTiles.get(x));
			}
	
			yourTiles.clear();
	
			theTopInfo.remove(bombsLeft); // Remove the top info
			theTopInfo.remove(time);
			theTopInfo.remove(flagMode);
			theTopInfo.remove(mainMode);
	
			try
			{
				theTopInfo.remove(lostReturn);
			}catch(Exception e){}
	
			wasClicked.clear();
	
			secondsDone = 0;
			youWon = false;

			flagEnabled = false;
			youLost = false;
			bombsInGame = 0;
		
		}
		else if(eventName.equals("Theme")) // Toggle theme between light and dark
		{
			if(darkTheme == false) // Switch to dark theme
			{
				darkTheme = true;
				darktoggle.setText("    Light Theme    ");
	
				darktoggle.setBackground(d1); // Changing buttons, menus, etc to pre-defined colours.
				darktoggle.setForeground(dtext);
				firstsafe.setBackground(d1);
				firstsafe.setForeground(dtext);
				settings.setBackground(d2);
				retBut.setBackground(d1);
				retBut.setForeground(dtext);
				retSet.setBackground(d2);
				actualSet.setBackground(d2);
				mainMenu.setBackground(d2);
				pickGridSize.setBackground(d2);
				extra.setBackground(d2);
				one.setBackground(d2);
				two.setBackground(d2);
				difficulty.setBackground(d2);
				topText.setBackground(d2);
				actualGame.setBackground(d2);
				theTopInfo.setBackground(d2);
				theGrid.setBackground(d2);
				submit.setBackground(d1);
				submit.setForeground(dtext);
				mainReturn.setBackground(d1);
				mainReturn.setForeground(dtext);
				easy.setBackground(d1);
				easy.setForeground(dtext);
				medium.setBackground(d1);
				medium.setForeground(dtext);
				hard.setBackground(d1);
				hard.setForeground(dtext);
				topText.setForeground(dtext);
				theSize.setBackground(d1);
				theSize.setForeground(dtext);
				pickCleaner.setBackground(d1);
				pickCleaner.setForeground(dtext);
				pickSettings.setBackground(d1);
				pickSettings.setForeground(dtext);
				winMsg.setForeground(dtext);
				timeel.setForeground(dtext);
				backtoMain.setBackground(d1);
				backtoMain.setForeground(dtext);
	
	
	
	
	
	
	
			}
			else // Switch from dark theme to light theme
			{
				darkTheme = false;
				darktoggle.setText("    Dark Theme    ");
	
				darktoggle.setBackground(l1);
				darktoggle.setForeground(ltext);
				firstsafe.setBackground(l1);
				firstsafe.setForeground(ltext);
				settings.setBackground(l2);
				retBut.setBackground(l1);
				retBut.setForeground(ltext);
				retSet.setBackground(l2);
				actualSet.setBackground(l2);
				mainMenu.setBackground(l2);
				pickGridSize.setBackground(l2);
				extra.setBackground(l2);
				one.setBackground(l2);
				two.setBackground(l2);
				difficulty.setBackground(l2);
				topText.setBackground(l2);
				actualGame.setBackground(l2);
				theTopInfo.setBackground(l2);
				theGrid.setBackground(l2);
				submit.setBackground(l1);
				submit.setForeground(ltext);
				mainReturn.setBackground(l1);
				mainReturn.setForeground(ltext);
				easy.setBackground(l1);
				easy.setForeground(ltext);
				medium.setBackground(l1);
				medium.setForeground(ltext);
				hard.setBackground(l1);
				hard.setForeground(ltext);
				topText.setForeground(ltext);
				theSize.setBackground(l2);
				theSize.setForeground(ltext);
				pickCleaner.setBackground(l1);
				pickCleaner.setForeground(ltext);
				pickSettings.setBackground(l1);
				pickSettings.setForeground(ltext);
				winMsg.setForeground(ltext);
				timeel.setForeground(ltext);
				backtoMain.setBackground(l1);
				backtoMain.setForeground(ltext);
			}
	
	
		}
		else if(eventName.equals("Settings")) // Open settings menu
		{
			mainMenu.setVisible(false);
			settings.setVisible(true);
			frame.setContentPane(settings);
			frame.setTitle("Settings");
			frame.setSize(180,140);
			frame.setLocation((screenSize.width/2)-90, (screenSize.height/2)-70);
		}
		else if(eventName == "Easy") // Change to easy mode
		{
			dif = 1;
			diffChanged();
		}
		else if(eventName == "Medium") // Change to medium mode
		{
			dif = 2;
			diffChanged();
		}
		else if(eventName == "Hard") // Change to hard mode
		{
			dif = 3;
			diffChanged();
		}
		else if(eventName.equals("Submit")) // MINECLEANER:: GRID SIZE MENU
		{
			clickedAlready = false;
			youWon = false;
			youLost = false;
			String num = theSize.getText();
			theNum = Integer.parseInt(num);
			if(theNum <= 20 && theNum >=3)
			{
				if(dif != 0)
				{
					mineCleaner thisGame = new mineCleaner(theNum, dif);
					thNum = theNum; // For the check bomb code to work, I have to re-call the constructor later, saving these variables for that reason.
					theDif = dif;
	
					pickGridSize.setVisible(false);
					theGrid.setLayout(new GridLayout(theNum,theNum));
					frame.setContentPane(actualGame);
					actualGame.setVisible(true);
					frame.setTitle("Mine Cleaner"); // Set title to mine cleaner
		
					if(420+(26*theNum) < (int)screenSize.getHeight() && 400+(26*theNum) < (int)screenSize.getWidth())
					{
						frame.setSize(400 + (26*theNum),420 + (26*theNum)); // Adjusting the frame size also accounts for size of grid
					}
					else
					{
						frame.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
					}
					frame.setLocation((screenSize.width/2)-(frame.getWidth()/2), (screenSize.height/2)-(frame.getHeight()/2));
					retState = thisGame.generateBombs();

					int whereWeAt = 0;
					for(int x= 0; x<=theNum-1; x++)
					{
						for(int y =0; y<=theNum-1; y++) // Add the space to the grid
						{
							JButton tempButton;
							tempButton = new JButton("");
							int tempInt = (x*theNum) + y; ////
							String nowString = Integer.toString(tempInt);
							tempButton.setActionCommand(nowString);
							tempButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
							tempButton.addActionListener(this);
							if(!darkTheme)
							{
								tempButton.setBackground(l2);
							}
							else
							{
								tempButton.setBackground(d1);
								tempButton.setForeground(dtext);
							}
							wasClicked.add(false);
							yourTiles.add(tempButton);
							theGrid.add(tempButton);
							whereWeAt = whereWeAt + 1;
						}
	
					}	
					bombsLeft = new JLabel("Bombs Left: Loading");                                    
					bombsLeft.setAlignmentX(JLabel.LEFT_ALIGNMENT);
					theTopInfo.add(bombsLeft);
		
					bombsLeft.setText("Bombs Left: " + thisGame.howManyBombs() + " / " + thisGame.howManyBombs()); // Show amount of bombs
					bombsInGame = thisGame.howManyBombs();
		
					time = new JLabel("Time: 00:00", SwingConstants.RIGHT);                                    
					time.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
					theTopInfo.add(time);
		
					secondsDone = 0;
		
					if(!threadStarted) // Start the thread, if it hasn't been started already (to track time)
					{
						threadStarted = true;
						myThread.start();
					}
		
					if(darkTheme)
					{
						time.setForeground(dtext); // Dark theme colour changes
						bombsLeft.setForeground(dtext);
					}
	
		
					flagMode = new JButton("Flag Mode"); // Make the top info buttons
					flagMode.setActionCommand("Flag Mode");
					flagMode.setAlignmentX(JButton.LEFT_ALIGNMENT);
					flagMode.addActionListener(this);
		
					mainMode = new JButton("Bomb Mode");
					mainMode.setActionCommand("Main Mode");
					mainMode.setAlignmentX(JButton.LEFT_ALIGNMENT);
					mainMode.addActionListener(this);
		
	
					if(darkTheme)
					{
						flagMode.setBackground(d1);
						flagMode.setForeground(dtext);
						mainMode.setForeground(dtext);
						mainMode.setBackground(new Color(25,159,25));
					}
					else
					{
						mainMode.setBackground(new Color(20,245,20));
					}
		
					theTopInfo.add(flagMode);
					theTopInfo.add(mainMode);
					theTopInfo.setMaximumSize(new Dimension(280,100));
		
					actualGame.add(theTopInfo);
					actualGame.add(theGrid);
				}
			}
		}
		else if(eventName.equals("Flag Mode")) // Enable flag mode
		{
			flagEnabled = true;

			if(!darkTheme)
			{
				flagMode.setBackground(new Color(20,245,20));
				mainMode.setBackground(l2);
			}
			else
			{
				flagMode.setBackground(new Color(25,159,25));
				mainMode.setBackground(d1);
			}
		
		}
		else if(eventName.equals("Main Mode")) // Enable main mode (click non-bombs)
		{
			flagEnabled = false;
		
			if(!darkTheme)
			{
				mainMode.setBackground(new Color(20,245,20));
				flagMode.setBackground(l2);
			}
			else
			{
			mainMode.setBackground(new Color(25,159,25));
			flagMode.setBackground(d1);
			}
	
		}
		else if(eventName == "Go back") // GO BACK (Various Menus)
		{
		
			if(pickGridSize.isVisible() || settings.isVisible())
			{
				pickGridSize.setVisible(false);
				settings.setVisible(false);
				mainMenu.setVisible(true);
				frame.setSize(200,108);
				frame.setTitle("Main Menu");
				frame.setLocation((screenSize.width/2)-100, (screenSize.height/2)-54);
				frame.setContentPane(mainMenu);
			}
		
		}
		else if(foundNum != -1) // Tile was clicked!
		{
			if((wasClicked.get(foundNum) == false && foundNum < yourTiles.size() && flagEnabled == false && youWon == false && youLost == false && (!yourTiles.get(foundNum).getBackground().equals(c1)))) // If button has text, it means it was already clicked, also checking to see if button exists
			{
				mineCleaner thisGame = new mineCleaner(thNum, theDif);

				// Prevents out of bound plays
				if(foundNum > (thisGame.getSize()*thisGame.getSize())-1)
				{
					return;
				}

				wasClicked.set(foundNum, true);
				int theReturn = thisGame.isBomb(foundNum, 0, 0, retState, firstSafeOp, clickedAlready); // 10 means bomb in that location; otherwise, that is amt of bombs touching. (20 is the default for de-bugging purposes).
	
				if(theReturn == 10) // Bomb was clicked
				{
					if(youLost == false)
					{
						mainMode.setVisible(false);
						flagMode.setVisible(false);
	
						lostReturn = new JButton(); // Make button appear on loss
						lostReturn.setText("Return");
						lostReturn.setActionCommand("New Game");
						bombsLeft.setText("You lost!");
						lostReturn.addActionListener(this);
	
						if(!darkTheme) 
						{
							lostReturn.setBackground(l1);
							lostReturn.setForeground(ltext);
						}
						else
						{
							lostReturn.setBackground(d1);
							lostReturn.setForeground(dtext);
						}
	
						theTopInfo.add(lostReturn);
	
					}
	
					youLost = true;
	
					for(int i = 0; i<yourTiles.size(); i++) // Go through all tiles
					{
						if((yourTiles.get(i).getBackground().equals(l2) || yourTiles.get(i).getBackground().equals(d1)) && yourTiles.get(i).getText().equals(""))
						{
							if(thisGame.isBomb(i, 0, 0, retState, false, clickedAlready) != 10)
							{
								yourTiles.get(i).setBackground(new Color(160,120,120)); // Make entire grid slightly red
								yourTiles.get(i).setForeground(l2);
							}
							else
							{
								yourTiles.get(i).setBackground(new Color(195,120,120)); // Is bomb (but not the one clicked to end the game), make it more red
								yourTiles.get(i).setForeground(l2);
							}
						}
						else
						{
							if(yourTiles.get(i).getBackground().equals(new Color(128,0,128)))
							{
								if(thisGame.isBomb(i, 0, 0, retState, false, clickedAlready) != 10)
								{
									yourTiles.get(i).setBackground(new Color(220,220,0)); // Flagged space was not bomb (yellow)
									yourTiles.get(i).setForeground(l2);
								}
								else
								{
									yourTiles.get(i).setBackground(new Color(0,220,0)); // Flagged space was a bomb (green)
									yourTiles.get(i).setForeground(l2);
								}
							}
						}
		
					}
					yourTiles.get(foundNum).setBackground(new Color(230,0,0)); // Make it really red for the bomb clicked that caused the game to end
		
		
				}
				else // Not a bomb
				{
					if(!youLost)
					{
						if(theReturn == 0 || (theReturn == 1000 && clickedAlready == true)) // No bombs touching tile clicked (1000 occurs in the case a bomb is clicked with safety mode on)
						{

							yourTiles.get(foundNum).setVisible(false); // Make the tile invisible (since no number has to be displayed since it would be 0)
		
							int theSize = thisGame.getSize(); // Length of grid (ex. length = 8)
							int maxSize = thisGame.getSize() * thisGame.getSize(); // Amount of tiles in grid (ex. with length 8, maxSize = 64)
		
							double d = foundNum;

							//////////// Below we will check all touching tiles, if any of them have 0 bombs touching them also, we will remove that tile. //////////////
								
							// position: directly left
							if((foundNum-1) >= 0 && ( ((d-1+1)/theSize) != Math.floor((d-1+1)/theSize)) )
							{
								int amt = thisGame.isBomb(foundNum-1,0,0,retState);

								// Remove nearby tiles labelled 0
								if(amt == 0)
								{
									actionFunction(Integer.toString(foundNum-1));
								}

								if(yourTiles.get(foundNum-1).getBackground().equals(c1)) // Tile is flagged (even though it is not a bomb)
								{
									if(darkTheme)
									{
										yourTiles.get(foundNum-1).setBackground(d1);
									}
									else
									{
										yourTiles.get(foundNum-1).setBackground(l2);
									}
								}


								if(amt < 1000) // Unintended issue where some of these get marked as 1000 more than they should, this is meant to only occur to bombs when the user has the safety mode enabled, so this is just a fix.
								{
									yourTiles.get(foundNum-1).setText(Integer.toString(amt));
								}
								else
								{
									yourTiles.get(foundNum-1).setText((Integer.toString(amt)).substring(3));
								}
	
							}

							// position: directly right
							if((foundNum+1) < maxSize && ( ((d+1)/theSize) != Math.floor((d+1)/theSize)))
							{
								int amt = thisGame.isBomb(foundNum+1,0,0,retState);
								// Remove nearby tiles labelled 0

								if(yourTiles.get(foundNum+1).getBackground().equals(c1)) // Tile is flagged (even though it is not a bomb)
								{
									if(darkTheme)
									{
										yourTiles.get(foundNum+1).setBackground(d1);
									}
									else
									{
										yourTiles.get(foundNum+1).setBackground(l2);
									}
								}

								if(amt == 0)
								{
									actionFunction(Integer.toString(foundNum+1));
								}
								if(amt < 1000)
								{

									yourTiles.get(foundNum+1).setText(Integer.toString(amt));
								}
								else
								{
									yourTiles.get(foundNum+1).setText((Integer.toString(amt)).substring(3));
								}
	
	
							}

							// position: directly down
							if((d+theSize) < maxSize)
							{
								int amt = thisGame.isBomb(foundNum+theSize,0,0,retState);

								if(yourTiles.get(foundNum+theSize).getBackground().equals(c1)) // Tile is flagged (even though it is not a bomb)
								{
									if(darkTheme)
									{
										yourTiles.get(foundNum+theSize).setBackground(d1);
									}
									else
									{
										yourTiles.get(foundNum+theSize).setBackground(l2);
									}
								}
									
								// Remove nearby tiles labelled 0
								if(amt == 0)
								{
									actionFunction(Integer.toString(foundNum+theSize));
								}
	
								if(amt < 1000)
								{
									yourTiles.get(foundNum+theSize).setText(Integer.toString(amt));
								}
								else
								{
									yourTiles.get(foundNum+theSize).setText((Integer.toString(amt)).substring(3));
								}
							}

							// position: directly up
							if((d-theSize) >= 0)
							{
								int amt = thisGame.isBomb(foundNum-theSize,0,0,retState);
								if(yourTiles.get(foundNum-theSize).getBackground().equals(c1))
								{
									if(darkTheme)
									{
										yourTiles.get(foundNum-theSize).setBackground(d1);
									}
									else
									{
										yourTiles.get(foundNum-theSize).setBackground(l2);
									}
								}
	
								// Remove nearby tiles labelled 0
								if(amt == 0){
									actionFunction(Integer.toString(foundNum-theSize));
								}

								if(amt < 1000)
								{
									yourTiles.get(foundNum-theSize).setText(Integer.toString(amt));
								}
								else
								{
								yourTiles.get(foundNum-theSize).setText((Integer.toString(amt)).substring(3));
								}
							}
		
							// position: up and to the left
							if((d-1-theSize) >= 0 && ( ((d-1-theSize+1)/theSize) != Math.floor((d-1-theSize+1)/theSize)) )
							{
								int amt = thisGame.isBomb(foundNum-1-theSize,0,0,retState);

								if(yourTiles.get(foundNum-1-theSize).getBackground().equals(c1))
								{
									if(darkTheme)
									{
										yourTiles.get(foundNum-1-theSize).setBackground(d1);
									}
									else
									{
										yourTiles.get(foundNum-1-theSize).setBackground(l2);
									}
								}

								// Remove nearby tiles labelled 0
								if(amt == 0){
									actionFunction(Integer.toString(foundNum-1-theSize));
								}

								if(amt < 1000)
								{
									yourTiles.get(foundNum-1-theSize).setText(Integer.toString(amt));
								}
								else
								{
									yourTiles.get(foundNum-1-theSize).setText((Integer.toString(amt)).substring(3));
								}
							}
		
							// position: up and to the right
							if((foundNum+1-theSize) >= 0 && ( ((d+1-theSize)/theSize) != Math.floor((d+1-theSize)/theSize)) )
							{
								int amt = thisGame.isBomb(foundNum+1-theSize,0,0,retState);

								if(yourTiles.get(foundNum+1-theSize).getBackground().equals(c1))
								{
									if(darkTheme)
									{
									yourTiles.get(foundNum+1-theSize).setBackground(d1);
									}
									else
									{
										yourTiles.get(foundNum+1-theSize).setBackground(l2);
									}
								}

								// Remove nearby tiles labelled 0
								if(amt == 0)
								{
									actionFunction(Integer.toString(foundNum+1-theSize));
								}

								if(amt < 1000)
								{
									yourTiles.get(foundNum+1-theSize).setText(Integer.toString(amt));
								}
								else
								{
									yourTiles.get(foundNum+1-theSize).setText((Integer.toString(amt)).substring(3));
								}
							}
		
							// position: down and to the right
							if((foundNum+1+theSize) < maxSize && ( ((d+1+theSize)/theSize) != Math.floor((d+1+theSize)/theSize)))
							{
								int amt = thisGame.isBomb(foundNum+1+theSize,0,0,retState);
								if(yourTiles.get(foundNum+1+theSize).getBackground().equals(c1))
								{
									if(darkTheme)
									{
										yourTiles.get(foundNum+1+theSize).setBackground(d1);
									}
									else
									{
										yourTiles.get(foundNum+1+theSize).setBackground(l2);
									}
								}

								// Remove nearby tiles labelled 0
								if(amt == 0)
								{
									actionFunction(Integer.toString(foundNum+1+theSize));
								}

								if(amt < 1000)
								{
									yourTiles.get(foundNum+1+theSize).setText(Integer.toString(amt));
								}
								else
								{
									yourTiles.get(foundNum+1+theSize).setText((Integer.toString(amt)).substring(3));
								}
							}
		
							// position: down and to the left
							if((foundNum-1+theSize) >= 0 && ( ((d-1+theSize+1)/theSize) != Math.floor((d-1+theSize+1)/theSize)) )
							{
								int amt = thisGame.isBomb(foundNum-1+theSize,0,0,retState);
								try
								{
									if(yourTiles.get(foundNum-1+theSize).getBackground().equals(c1))
									{
										if(darkTheme)
										{
											yourTiles.get(foundNum-1+theSize).setBackground(d1);
										}
										else
										{
											yourTiles.get(foundNum-1+theSize).setBackground(l2);
										}
									}
								}
								catch(Exception e){}

								// Remove nearby tiles labelled 0
								try
								{
									if(amt == 0)
									{
										actionFunction(Integer.toString(foundNum-1+theSize));
									}
								}catch(Exception e){}

								try
								{
									if(amt < 1000)	
									{
										yourTiles.get(foundNum-1+theSize).setText(Integer.toString(amt));
									}
									else
									{
										yourTiles.get(foundNum-1+theSize).setText((Integer.toString(amt)).substring(3));
									}
								}catch(Exception e){}
							}
							didTheyWin(); // Check if the player won
						}
						else
						{
	
							String gotBack = Integer.toString(theReturn);
							if (gotBack.length() == 4 && clickedAlready == false) // The space you clicked was a bomb but based on your safety setting, it is has been converted to a non-bomb
							{
								yourTiles.get(foundNum).setForeground(new Color(50,205,50)); // Green text for visibility
								gotBack = gotBack.substring(gotBack.length()-1);
								bombsInGame = bombsInGame - 1;
							}
							else if(gotBack.length() == 4)
							{
								gotBack = gotBack.substring(gotBack.length()-1);
							}
	
	
							yourTiles.get(foundNum).setText(gotBack);
							if(yourTiles.get(foundNum).getBackground().equals(c1))
							{
								if(darkTheme)
								{
									yourTiles.get(foundNum).setBackground(d1);
								}else
								{
									yourTiles.get(foundNum).setBackground(l2);
								}
							}
							didTheyWin(); // Check if the player won!
						}
		
					}
		
				}
	
	
			}
			else if(wasClicked.get(foundNum) == false && flagEnabled == true)
			{
				// FLAGGING
				// Return statement prevents user from flagging an already-clicked space with a number
				if (!yourTiles.get(foundNum).getText().equals(""))
				{
					return;
				}
		
				if (!yourTiles.get(foundNum).getBackground().equals(c1)) // FLAG THE SPACE
				{
					yourTiles.get(foundNum).setBackground(new Color(128,0,128));
				}
				else // UNFLAG THE SPACE
				{
					if(!darkTheme) // MAKE SPACE WHITE
					{
						yourTiles.get(foundNum).setBackground(l2);
					}
					else // DARK THEME: MAKE SPACE BLACK
					{
						yourTiles.get(foundNum).setBackground(d1);
					}
	
				}
		
				didTheyWin(); // Check if the player won!
			}
		clickedAlready = true; // Already clicked
		}
	
		else
		{
			System.out.println("This button has not been programmed yet!"); // If for any reason a button is pressed which is not programmed print this
		}
	}

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				runGUI();
			}
		});
	}
}
