package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * GamePlayer Implementation - COSC322
 * @author Yong Gao (yong.gao@ubc.ca)
 * @author Team 05: Corey Bond, Kshitij Suri, Jun Kang, Alex Rogov, Jose Pena
 * June 9, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	private RecursiveAI player = null;
    
    private String userName = null;
    private String passwd = null;
    private Board myBoard = null;
    
    private boolean moving = true;
    private int myColour;
    private int oppColour;
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and password 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	System.out.println("Congratulations!!! "
    			+ "I am called because the server indicated that the login is successful");
    	System.out.println("The next step is to find a room and join it: "
    			+ "the gameClient instance created in my constructor knows how!"); 
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }
    
    /**
     * Takes in coordinates of players moves, and communicates it to the gui 
     * @param qr1 : Original queen row number
     * @param qc1 : Original queen column number
     * @param qr2 : New queen row number
     * @param qc2 : New queen column number
     * @param ar : Arrow row number
     * @param ac : Arrow column number
     */
    public void sendPlay(int qr1, int qc1, int qr2, int qc2, int ar, int ac) {
    	ArrayList<Integer> queenStart = new ArrayList<Integer>();
    	queenStart.add(qr1);
    	queenStart.add(qc1);
    	ArrayList<Integer> queenEnd = new ArrayList<Integer>();
    	queenEnd.add(qr2);
    	queenEnd.add(qc2);
    	ArrayList<Integer> arrow = new ArrayList<Integer>();
    	arrow.add(ar);
    	arrow.add(ac);
    	
    	this.gameClient.sendMoveMessage(queenStart, queenEnd, arrow);
    	this.gamegui.updateGameState(queenStart, queenEnd, arrow);
    }

   /**
    * This method will be called by the GameClient when it receives a game-related message
	* from the server.
	*
	* For a detailed description of the message types and format, 
	* see the method GamePlayer.handleGameMessage() in the game-client-api document. 
	*
	* @param messageType
	* @param msgDetails
    */
    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	if(messageType.compareTo(GameMessage.GAME_STATE_BOARD)==0) {
    		ArrayList<Integer> board = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		if(board==null) System.out.println("Board state is null");
    		myBoard = new Board();
    		System.out.println(myBoard.toString());
			this.gamegui.setGameState(board);
    	}
    	else if(messageType.compareTo(GameMessage.GAME_ACTION_START)==0) {
    		ArrayList<Integer> board = myBoard.getBoard();
    		this.gamegui.setGameState(board);
    		System.out.println("Action start message");
    		if(board == null)
    			System.out.println("No board information");
    		else {
    			System.out.println(myBoard.toString());
    			this.gamegui.setGameState(board);
    		}
    		
    		String black = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
    		String white = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
    		System.out.println("The Black Player's name is " + black + ", and the White player's name is " + white + ".");
    		
    		if(black.contains(userName)) {
    			this.myColour = myBoard.POS_BLACK;
    			this.oppColour = myBoard.POS_WHITE;
//    			ArrayList<Integer> play = myBoard.randomMove(myColour);
    			this.player = new RecursiveAI(myBoard);
    			ArrayList<Integer> play = player.ai(myColour);
    			ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(play.get(0), play.get(1)));
				ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(play.get(2), play.get(3)));
				ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(play.get(4), play.get(5)));
				myBoard.movePiece(oldQueen, newQueen, newArrow, myColour);
    			sendPlay(play.get(0), play.get(1), play.get(2), play.get(3), play.get(4), play.get(5));
    			System.out.println(myBoard.toString());
    		}else {
    			this.myColour = myBoard.POS_WHITE;
    			this.oppColour = myBoard.POS_BLACK;
    		}
        }
    	else if(messageType.compareTo(GameMessage.GAME_ACTION_MOVE)==0) {
    		if (!myBoard.checkWin(myColour) && !myBoard.checkWin(oppColour)) {
    			if(this.moving) {
	    			ArrayList<Integer> queenCurr = 
	    					(ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);    		
		    		ArrayList<Integer> queenNext = 
		    				(ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
		    		ArrayList<Integer> arrow = 
		    				(ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		    		System.out.println("Action move message");
		    		System.out.println("The opponent's starting Queen position was: " + queenCurr);
		    		System.out.println("The opponent's new Queen position is: " + queenNext);
		    		System.out.println("The opponent's blocking arrow position is: " + arrow);
		    		myBoard.movePiece(queenCurr, queenNext, arrow, oppColour);
		    		this.moving = false;
		    		this.gamegui.updateGameState(msgDetails);
	    		}
    				this.player = new RecursiveAI(myBoard);
//	    			ArrayList<Integer> play = myBoard.randomMove(myColour);
	    			ArrayList<Integer> play = player.ai(myColour);
	    			ArrayList<Integer> oldQueen = new ArrayList<Integer>(Arrays.asList(play.get(0), play.get(1)));
					ArrayList<Integer> newQueen = new ArrayList<Integer>(Arrays.asList(play.get(2), play.get(3)));
					ArrayList<Integer> newArrow = new ArrayList<Integer>(Arrays.asList(play.get(4), play.get(5)));
					System.out.println("Action move message");
		    		System.out.println("Our starting Queen position was: [" + play.get(0) + ", " + play.get(1) + "]");
		    		System.out.println("Our new Queen position is: [" + play.get(2) + ", " + play.get(3) + "]");
		    		System.out.println("Our blocking arrow position is: [" + play.get(4) + ", " + play.get(5) + "]");
		    		myBoard.movePiece(oldQueen, newQueen, newArrow, myColour);
	    			sendPlay(play.get(0), play.get(1), play.get(2), play.get(3), play.get(4), play.get(5));
	    			System.out.println(myBoard.toString());
	    			this.moving = true;
	    		}
    	}    	    	
    	return true;   	
    }
        
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return  this.gamegui;
	}

	@Override
	public void connect() {
		gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class