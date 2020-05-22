package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [board.getRow()][board.getColumn()];
		Position p = new Position(0,0);
		
		// above
		p.setValues(position.getRow()-1,position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		//below

		p.setValues(position.getRow()+1,position.getColumn());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}


		//right
		p.setValues(position.getRow(),position.getColumn()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//left
		p.setValues(position.getRow(),position.getColumn()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//diagonalUpR
		p.setValues(position.getRow()-1,position.getColumn()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//diagonalDownR
		p.setValues(position.getRow()+1,position.getColumn()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//diagonalUpL
		p.setValues(position.getRow()-1,position.getColumn()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//diagonalDownL
		p.setValues(position.getRow()+1,position.getColumn()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Castling
		
		if(!chessMatch.getCheck() && getCounter() == 0) {
			Position pos = new Position(position.getRow(), position.getColumn()+3);
			if (testRookCastling(pos)) {
				Position position1 = new Position(position.getRow(), position.getColumn()+1);
				Position position2 = new Position(position.getRow(), position.getColumn()+2);
				if(getBoard().takePiece(position1) == null && getBoard().takePiece(position2) == null) {
					mat[position.getRow()][position.getColumn()+2] = true;
				}
			}
		}
		
		if(!chessMatch.getCheck() && getCounter() == 0) {
			Position pos = new Position(position.getRow(), position.getColumn()-4);
			if(testRookCastling(pos)) {
				Position position1 = new Position(position.getRow(), position.getColumn()-1);
				Position position2 = new Position(position.getRow(), position.getColumn()-2);
				Position position3 = new Position(position.getRow(), position.getColumn()-3);
				if(getBoard().takePiece(position1) == null && getBoard().takePiece(position2) == null && getBoard().takePiece(position3) == null) {
					mat[position.getRow()][position.getColumn()-2] = true;
				}
			}
		}
		
		
		return mat;
	}
	
	public boolean testRookCastling(Position position) {
		ChessPiece rook = (ChessPiece)getBoard().takePiece(position);
		if(rook != null && rook instanceof Rook && rook.getCounter() == 0 && rook.getColor() == getColor()) {
			return true;
		}
		return false;
	}

}
