package fileIO;

public class JsonWriter {
	/**
	 * Main takes 4 arguments:
	 * 1) Type of game object:
	 *   -Object
	 *   -Enemy
	 *   -Player
	 *   -Npc
	 * 2) Position data
	 * 3) Stats (if enemy or player)
	 * 4) Dialogue array (if npc)
	 * @param args
	 * Have 2 public methods acessible by the rest of FileIO, designed to create GameObjects based on input.
	 */
	private static int id;
	//private method to display an error
	private static void error() {
		System.err.println("Error: Too little args");
		System.out.println("Usage: <ObjectType> <x> <y> [Object args]");
	}
	public static void main(String[] args) {
		//error if no arguments
		if(args.length == 0) {
			error();
		}
		//check ids to see what is being called
		else {id = Integer.parseInt(args[0]);}
		switch (id) {
		case 1: //Empty game object
			if(args.length != 3) {error();}
			break;
		case 2: //Enemy
			if(args.length < 4) {error();}
			break;
		case 3: //Player
			if(args.length < 4) {error();}
			break;
		case 4: //Npc (make sure to check the npc dialouge file (import via file)
			if(args.length < 4) {error();}
			break;
		default:
			error();
			break;
		}
		

	}

}
