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
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
    private Board myBoard = null;
    private boolean moving = false;
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
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
//    	System.out.println("Congratulations!!! "
//    			+ "I am called because the server indicated that the login is successful");
//    	System.out.println("The next step is to find a room and join it: "
//    			+ "the gameClient instance created in my constructor knows how!"); 
//
//    	//get room list from game client, iterate through and print
//    	List<Room> room = gameClient.getRoomList();
//    	for (int i=0; i < room.size(); i++) {
//    		System.out.println(i + ": " + room.get(i).getName());
//    	}
//    	//user defines preferred room to join
//    	System.out.println("Which room number would you like to join?");
//    	Scanner scan = new Scanner(System.in);
//    	int want = scan.nextInt();
//    	while (want < 0 || want > 18) {
//    		System.out.println("Invalid input. Try again.");
//    		want = scan.nextInt();
//        }
//    	//join relevant room
//    	gameClient.joinRoom(room.get(want).getName());
//    	scan.close();
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }
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

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//prints parameters of each message, depending on message type
    	if(messageType.compareTo(GameMessage.GAME_STATE_BOARD)==0) {
    		ArrayList<Integer> board = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		myBoard = new Board();
    		System.out.println(myBoard.toString());
			this.gamegui.setGameState(myBoard.getBoard());
    	}
    	else if(messageType.compareTo(GameMessage.GAME_ACTION_START)==0) {
    		ArrayList<Integer> board = myBoard.getBoard();//(ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		this.gamegui.setGameState(board);
    		System.out.println("Action start message");
    		if(board == null)
    			System.out.println("No board information");
    		else {
    			printGameState(board);
    			this.gamegui.setGameState(board);
    		}
    		
    		String black = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
    		String white = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
    		System.out.println("The Black Player's name is " + black + ", and the White player's name is " + white + ".");
    		
    		if(black.contains(userName)) {
    			this.myColour = myBoard.POS_BLACK;
    			this.oppColour = myBoard.POS_WHITE;
    			ArrayList<Integer> play = myBoard.randomMove(myColour);
    			sendPlay(play.get(0), play.get(1), play.get(2), play.get(3), play.get(4), play.get(5));
    			System.out.println(myBoard.toString());
    			this.moving = true;
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
		    		System.out.println("The current Queen position is: " + queenCurr);
		    		System.out.println("The next Queen position is: " + queenNext);
		    		System.out.println("The blocking arrow position is: " + arrow);
		    		myBoard.movePiece(queenCurr, queenNext, arrow, oppColour);
		    		this.moving = false;
		    		this.gamegui.updateGameState(msgDetails);
	    		}
	    			ArrayList<Integer> play = myBoard.randomMove(myColour);
	    			sendPlay(play.get(0), play.get(1), play.get(2), play.get(3), play.get(4), play.get(5));
	    			System.out.println(myBoard.toString());
	    			this.moving = true;
	    		}
    	}
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document. 
    	    	
    	return true;   	
    }
    
    //prints current game state in 10 x 10 board format
    public void printGameState(ArrayList<Integer> gameState) {
		System.out.println("The current game state is: \n- - - - - - - - - - - -");
		for (int i = 11; i < gameState.size(); i++) {
			if (i == 12)						System.out.print("| " + gameState.get(i) +  " ");
			else if (i == gameState.size()-1)   System.out.println(gameState.get(i) +  " |\n- - - - - - - - - - - -");
			else if ((i+1) % 11 == 0)			System.out.print(gameState.get(i) +  " |\n| ");
			else if (i%11 == 0)					System.out.print("");
			else								System.out.print(gameState.get(i) +  " ");
		}    	
    }
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return  this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class