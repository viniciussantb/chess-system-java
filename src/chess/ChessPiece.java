package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	
	private Color color;
	private int counter;
	
	public ChessPiece(Board board,Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void decrease() {
		counter --;
	}
	
	public void increase() {
		counter ++;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	public boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().takePiece(position);
		return piece == null || piece.getColor() != getColor();
	}
	
	protected boolean isThereAOpponentPiece(Position position) {
		ChessPiece piece = (ChessPiece)getBoard().takePiece(position);
		return getBoard().theresAPiece(position) && piece.color != getColor();
	}
	
}
