package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;


public class Pawn extends ChessPiece {
	
	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumn()];
		Position p = new Position(0, 0);
		if (getColor() == Color.BLACK) {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && getBoard().takePiece(p) == null) {
				mat[p.getRow()][p.getColumn()] = true;
				if (p.getRow() == 2 && getBoard().takePiece(p.getRow() + 1, p.getColumn()) == null) {
					mat[p.getRow() + 1][p.getColumn()] = true;
					
				}
			}
			// se
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereAOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// sw
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereAOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//En Passant
			Position left = new Position(position.getRow(), position.getColumn()-1);
			Position right = new Position(position.getRow(), position.getColumn()+1);
			if(position.getRow() == 4 && board.positionExists(left)&& isThereAOpponentPiece(left) && (ChessPiece)board.takePiece(left) == chessMatch.getEnPassantVunerable()){
				mat[left.getRow()+1][left.getColumn()] = true;
			}
			if(position.getRow() == 4 && board.positionExists(right)&& isThereAOpponentPiece(right) && (ChessPiece)board.takePiece(right) == chessMatch.getEnPassantVunerable()){
				mat[right.getRow()+1][right.getColumn()] = true;
			}
		}
		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && getBoard().takePiece(p) == null) {
				mat[p.getRow()][p.getColumn()] = true;
				if (p.getRow() == 5 && getBoard().takePiece(p.getRow() - 1, p.getColumn()) == null) {
					mat[p.getRow() - 1][p.getColumn()] = true;
				}
			}
			// ne
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereAOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			// nw
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereAOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//En Passant
			Position left = new Position(position.getRow(), position.getColumn()-1);
			Position right = new Position(position.getRow(), position.getColumn()+1);
			if(position.getRow() == 3 && board.positionExists(left)&& isThereAOpponentPiece(left) && (ChessPiece)board.takePiece(left) == chessMatch.getEnPassantVunerable()){
				mat[left.getRow()-1][left.getColumn()] = true;
			}
			if(position.getRow() == 3 && board.positionExists(right)&& isThereAOpponentPiece(right) && (ChessPiece)board.takePiece(right) == chessMatch.getEnPassantVunerable()){
				mat[right.getRow()-1][right.getColumn()] = true;
			}
		}

		return mat;
	}
}
