package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumn()];
		Position p = new Position(0,0);
		//nw
		p.setValues(position.getRow() - 1, position.getColumn()-1);
		boolean flag = true;
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow() - 1, p.getColumn()-1);
		}
		
		//ne
		p.setValues(position.getRow() - 1, position.getColumn()+1);
		flag = true;
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow() - 1, p.getColumn()+1);
		}
		
		//sw
		flag = true;
		p.setValues(position.getRow() + 1, position.getColumn()-1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow() + 1, p.getColumn()-1);
		}
		
		//se
		flag = true;
		p.setValues(position.getRow() + 1, position.getColumn()+1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow() + 1, p.getColumn()+1);
		}
		
		return mat;
	}	

}
