package ubc.cosc322;

public class test {
	public static void main(String[] args) {
		Board practice = new Board();
		System.out.println(practice.toString());
		practice.setTile(4,5,2);
		practice.setTileFromCharIndex(57, 1);
		Board ver2 = new Board(practice);
		System.out.println(ver2.toString());
		System.out.println(practice.getTile(1,4));
		System.out.println(practice.getTileSymbol(1,4));
		ver2.movePiece(new int[] {1, 7},new int[] {8,3}, new int[] {9,4}, 1);
		System.out.println(ver2.toString());
		
	}
}
