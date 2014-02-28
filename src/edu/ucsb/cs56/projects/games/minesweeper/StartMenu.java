package edu.ucsb.cs56.projects.games.minesweeper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** StartMenu.java is a base that calls all GUI objects and handles tasks
    such as pausing the game, creating the GUI, making the escape key functional,
	and allowing for a new game.

     @author David Acevedo
     @version CS56, Winter 2014, UCSB
*/

public class StartMenu {
	JButton newGame; //Main Menu New Game Button
	JButton help;	//Main Menu Help Button
	JButton newGamePause;	//Pause Menu New Game Button
	JButton helpPause;	//Pause Menu Help Button
	JButton resume;	//Pause Menu Resume Button
	JFrame frame;	//The frame is where all the good stuff is displayed e.g. Everything
	JPanel menu;	//Menu Panel, initial panel at initial creation of the game e.g. Main Menu
	JPanel game; 	//Game Panel, where the game is played
	JPanel pause;	//Pause Menu JPanel
	boolean inUse; //if pause menu is started and in use
	MineComponent mc; //MineComponent is the actual layout of the game, and what makes the game function
	JLabel status;		//the game status label that is displayed during the game

	/** no-arg constructor which creates the GUI of the Main Menu for Minesweeper
	 *  This menu includes a start and help button
     */
	public StartMenu() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createPausePanel();
		createMainMenu();

		frame.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.setSize(650, 600);
		frame.setVisible(true);
	}

	/** 
	 *  Starts a new Minesweeper game from the main menu
	 */
	public void newGame() {
		game = new JPanel(new BorderLayout());			//our game panel e.g. where everything will be put in for this display
		escapeListener();								//listens for the esc button
		status= new JLabel("Press esc to pause game"); //Our good label
		Grid grid = new Grid(true);
		Messager m = new SOMessager();			
		mc = new MineComponent(grid, m, this);	//creates our game interface
		
		game.add(mc);							//puts the game in the jPanel
		game.add(status,BorderLayout.NORTH);	//puts the game status label at the top of the screen
		menu.setVisible(false);					//puts the menu away
		pause.setVisible(false);				//puts the pause menu away
		frame.getContentPane().add(game);
	}

	/**
	 *  Pauses the game and brings up the pause menu
	 */
	public void pause() {
		inUse=true;							//the pause menu is now in use
		frame.getContentPane().add(pause);	//frame now puts pause in its dear list of importance
		game.setVisible(false);				//put the game away for a bit
		pause.setVisible(true);				//show the good ol pause menu
	}
	
	/**
	 *  Resumes the game and takes you back to the game
	 */
	public void resume() {
		pause.setVisible(false);			//put the pause menu back in its box
		game.setVisible(true);				//bring the game back out of its box
	}
	
	/**
	 *  creates a Pause menu for when you want to pause the game
	 */
	public void createPausePanel(){
		pause = new JPanel(new GridLayout(3,0));	//our 3 section grid layout for our pause menu
		resume = new JButton("Resume");				//create Button#1
		newGamePause = new JButton("New Game");		//create Button#2
		helpPause = new JButton("Help");			//create Button#3
		addActionListener(resume, "Resume");		//Give Button#1 purpose
		addActionListener(newGamePause, "New Game");//Give Button#2 purpose
		addActionListener(helpPause, "Help");		//Give Button#3 purpose
		//adds from order of importance e.g. top==more important than bottom
		pause.add(resume);							//Add Button#1 to our top section
		pause.add(newGamePause);					//Add Button#2 to our middle section
		pause.add(helpPause);						//Add Button#3 to our bottom section
		frame.getContentPane().add(pause);
	}
	
	/**
	 *  Creates the main menu, the menu when you launch the application
	 */
	public void createMainMenu(){
		menu = new JPanel(new GridLayout(2,0));		//our 2 section grid layout for our main menu

		newGame = new JButton("New Game");			//create Button#1
		help = new JButton("Help");					//create Button#2

		addActionListener(newGame, "New Game");		//Give Button#1 something to do
		addActionListener(help, "Help");			//Give Button#2 something to do
		menu.add(newGame);							//Make Button#1 a game star for the menu panel
		menu.add(help);								//Make Button#2 a game star for the menu panel
		frame.getContentPane().add(menu);	
	}
	
	/**
	 *  Allows the escape button to function as a pause key
	 */
	public void escapeListener() {
		AbstractAction escapeAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mc.getStatus()==0)
					pause();
				else{
					game.setVisible(false);
					newGame();
				}
			}
		};
		String key = "ESCAPE";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
		game.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
		game.getActionMap().put(key, escapeAction);

	}
	
	/**
	 *  Sets a message on the status bar on the top of the GUI
	 *
	 *	@param status - message you wish the status bar to display
	 */
	public void setLabel(String status){
		this.status.setText(status);
	}
	
	/**
	 * Creates a specified task for the buttons
	 * @param button - The JButton that you want to assign a task to
	 * @param action - the action you would like to give the button
	 */
	public void addActionListener(JButton button, String action){
		if(action == "New Game")
			{
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Execute when button is pressed
						newGame();
			}
		});	
	}
		else if(action == "Help")
			{
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Execute when button is pressed
						if(inUse==false){
							HelpScreen helpScreen=new HelpScreen(frame, menu);
							}
						else{
							HelpScreen helpScreen = new HelpScreen(frame, pause);
							}
			}
		});	
	}
		else if(action == "Resume")
			{
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Execute when button is pressed
						resume();
			}
		});	
	}
}
}
