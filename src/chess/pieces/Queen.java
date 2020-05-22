package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece{
	public Queen(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumn()];
		Position p = new Position(0,0);
		boolean flag = true;
		//above
		p.setValues(position.getRow()-1, position.getColumn());
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow()-1, p.getColumn());
		}
		//below
		flag = true;
		p.setValues(position.getRow()+1, position.getColumn());
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow()+1, p.getColumn());
		}
		//west
		flag = true;
		p.setValues(position.getRow(), position.getColumn()-1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow(), p.getColumn()-1);
		}
		//east
		flag = true;
		p.setValues(position.getRow(), position.getColumn()+1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow(), p.getColumn()+1);
		}
		//nw
		flag = true;
		p.setValues(position.getRow()-1, position.getColumn()-1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow()-1, p.getColumn()-1);
		}
		//ne
		flag = true;
		p.setValues(position.getRow()-1, position.getColumn()+1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow()-1, p.getColumn()+1);
		}
		//se
		flag = true;
		p.setValues(position.getRow()+1, position.getColumn()+1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow()+1, p.getColumn()+1);
		}
		//sw
		flag = true;
		p.setValues(position.getRow()+1, position.getColumn()-1);
		while(getBoard().positionExists(p) && canMove(p) && flag) {
			mat[p.getRow()][p.getColumn()] = true;
			if(isThereAOpponentPiece(p)) {
				flag = false;
			}
			p.setValues(p.getRow()+1, p.getColumn()-1);
		}
		return mat;
	}

}
