package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumn()];
		Position p = new Position(0,0);
		boolean flag = true;
		//above
		p.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow() - 1, p.getColumn());
		}
		//right
		flag = true;
		p.setValues(position.getRow(), position.getColumn()+1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow(), p.getColumn()+1);
		}
		
		//left
		flag = true;
		p.setValues(position.getRow(), position.getColumn()-1);
		while(getBoard().positionExists(p) && canMove(p) && flag){
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow(), p.getColumn()-1);
		}
		
		//below
		flag = true;
		p.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if (isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow() + 1, p.getColumn());
		}
		
		return mat;
		
	}
}