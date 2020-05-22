package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;



public class ChessMatch {
	private List <Piece> piecesOnTheBoard = new ArrayList <>();
	private List <Piece> capturedPieces = new ArrayList <>();
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece promotion;
	private ChessPiece enPassantVunerable;
	
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public ChessPiece getPromotion() {
		return promotion;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVunerable() {
		return enPassantVunerable;
	}
	
	public ChessPiece getKing(Color color) {
		List<Piece> list = piecesOnTheBoard;
		for(Piece piece : list) {
			if(((ChessPiece)piece).getColor() == color && piece instanceof King ) {
				return (ChessPiece) piece;
			}
		}
		return null;
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColumn()];
		for (int i=0; i<board.getRow(); i++) {
			for (int j=0; j<board.getColumn(); j++) {
				mat[i][j] = (ChessPiece) board.takePiece(i, j);
			}
		}
		return mat;
	}
	
	public Color opponentColor(Color currentColor) {
		if (currentColor == Color.BLACK) {
			return Color.WHITE;
		}
		else {
			return Color.BLACK;
		}
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.takePiece(position).possibleMoves();
	}
	
	public boolean testCheck(Color currentColor) {
		Color opponentColor = opponentColor(currentColor);
		List <ChessPiece> opponentList = new ArrayList<>();
		Position kingPosition = getKing(currentColor).getChessPosition().toPosition();
		for (Piece p : piecesOnTheBoard) {
			if( ((ChessPiece)p).getColor() == opponentColor ) {
				opponentList.add(((ChessPiece)p));
			}
		}
		for (ChessPiece piece : opponentList) {
			boolean[][] mat = piece.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List <ChessPiece> list = new ArrayList<>();
		for (Piece p : piecesOnTheBoard) {
			if( ((ChessPiece)p).getColor() == color ) {
				list.add(((ChessPiece)p));
			}
		}
		for (ChessPiece piece : list) {
			boolean[][] mat = piece.possibleMoves();
			for(int i=0; i<board.getRow(); i++) {
				for(int j=0; j<board.getColumn(); j++) {
					if(mat[i][j] == true) {
						Position source = piece.getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public ChessPiece peformChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		testCheck(getCurrentPlayer());
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		if(testCheck(getCurrentPlayer())) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cant put your self in check");
		}
		ChessPiece movedPiece = (ChessPiece)board.takePiece(target);
		promotion = null;
		if(movedPiece instanceof Pawn) {
			if(target.getRow() == 0 && movedPiece.getColor() == Color.WHITE || target.getRow() == 7 && movedPiece.getColor() == Color.BLACK) {
				promotion = (ChessPiece)board.takePiece(target);
				promotion = promotePiece("Q");
			}
		}
		if(testCheck(opponentColor(getCurrentPlayer()))) {
			check = true;
		}
		else {
			check = false;
		}
		if(testCheckMate(opponentColor(getCurrentPlayer()))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		if(movedPiece instanceof Pawn && target.getRow() == source.getRow()-2 || target.getRow() == source.getRow()+2) {
			enPassantVunerable = movedPiece;
		}
		else {
			enPassantVunerable = null;
		}
		
		return (ChessPiece)capturedPiece;
	}
	
	public Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.PlacePiece(p, target);
		((ChessPiece)p).increase();
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//Castling 
		if(p instanceof King && target.getColumn() == source.getColumn()+2) {
			Position pos = new Position(source.getRow(), source.getColumn()+3);
			Position rookTarget = new Position(target.getRow(), target.getColumn()-1);
			Piece rook = board.takePiece(pos);
			if(rook instanceof Rook) {
				board.removePiece(pos);
				board.PlacePiece(rook, rookTarget);
				((Rook) rook).increase();
			}
		}
		
		if(p instanceof King && target.getColumn() == source.getColumn()-2) {
			Position pos = new Position(source.getRow(), source.getColumn()-4);
			Position rookTarget = new Position(target.getRow(), target.getColumn()+1);
			Piece rook = board.takePiece(pos);
			if(rook instanceof Rook) {
				board.removePiece(pos);
				board.PlacePiece(rook, rookTarget);
				((Rook) rook).increase();
			}
		}
		
		//En Passant
		if(p instanceof Pawn && target.getColumn() != source.getColumn()) {
			if(capturedPiece == null) {
				Position capPosition;
				if(((ChessPiece)p).getColor() == Color.WHITE) {
					capPosition = new Position(target.getRow()+1, target.getColumn());
				}
				else {
					capPosition = new Position(target.getRow()-1, target.getColumn());
				}
				capturedPiece = (ChessPiece)board.removePiece(capPosition);
				piecesOnTheBoard.remove(capturedPiece);
				capturedPieces.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	
	public void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.PlacePiece(p, source);
		((ChessPiece)p).decrease();
		if (capturedPiece != null){
			board.PlacePiece(capturedPiece, target);
			piecesOnTheBoard.add(capturedPiece);
			capturedPieces.remove(capturedPiece);
		}
		
		//Castling 
		if(p instanceof King && target.getColumn() == source.getColumn()+2) {
			Position pos = new Position(source.getRow(), source.getColumn()+3);
			Position rookTarget = new Position(target.getRow(), target.getColumn()-1);
			Piece rook = board.takePiece(rookTarget);
			if(rook instanceof Rook) {
				board.removePiece(rookTarget);
				board.PlacePiece(rook, pos);
				((Rook) rook).decrease();
			}
		}
		
		if(p instanceof King && target.getColumn() == source.getColumn()-2) {
			Position pos = new Position(source.getRow(), source.getColumn()-4);
			Position rookTarget = new Position(target.getRow(), target.getColumn()+1);
			Piece rook = board.takePiece(rookTarget);
			if(rook instanceof Rook) {
				board.removePiece(rookTarget);
				board.PlacePiece(((Rook) rook), pos);
				((Rook) rook).decrease();
			}
		}
		//En Passant
		if(p instanceof Pawn && target.getColumn() != source.getColumn()) {
			if(capturedPiece == enPassantVunerable) {
				Position capPosition;
				if(((ChessPiece)p).getColor() == Color.WHITE) {
					capPosition = new Position(target.getRow()+1, target.getColumn());
				}
				else {
					capPosition = new Position(target.getRow()-1, target.getColumn());
				}
				board.PlacePiece(capturedPiece, capPosition);
			}
		}
		
	}
	
	public void validateSourcePosition(Position position) {
		if (!board.theresAPiece(position)) {
			throw new ChessException("Theres no piece on source position");
		}
		if(((ChessPiece) board.takePiece(position)).getColor() != currentPlayer) {
			throw new ChessException("The chosen piece its not yours");
		}
		if (!board.takePiece(position).isThereAnyPossibleMove()) {
			throw new ChessException("Theres any possible move for the chosen piece");
		}
		
	}
	
	public void validateTargetPosition(Position source, Position target) {
		if(!board.takePiece(source).possibleMove(target)) {
			throw new ChessException("Invalidate target position");
		}
	}
	
	
	public ChessPiece promotePiece(String type) {
		if(promotion == null) {
			throw new IllegalStateException("Theres no pieces to be promoted");
		}
		if(!type.equals("B") && !type.equals("H") && !type.equals("R") && !type.equals("Q")) {
			throw new InvalidParameterException("Invalid type for promition");
		}
		Position pos = promotion.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		ChessPiece newPiece = newPiece(promotion.getColor(), type);
		board.PlacePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		return newPiece;
	}
	
	public ChessPiece newPiece(Color color, String type) {
		if(type.equals("Q")) return new Queen(board, color);
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("H")) return new Knight(board, color);
		return new Rook(board, color);
	}
	
	public void nextTurn() {
		turn ++;
		if(currentPlayer == Color.WHITE) {
			currentPlayer = Color.BLACK;
		}
		else {
			currentPlayer = Color.WHITE;
		}
	}
	
	private void PlaceNewPiece(char column, int row, ChessPiece piece) {
		board.PlacePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void initialSetup() {
		PlaceNewPiece('e', 1, new King(board, Color.WHITE, this));
		PlaceNewPiece('d', 1, new Queen(board, Color.WHITE));
		PlaceNewPiece('f', 1, new Bishop(board, Color.WHITE));
		PlaceNewPiece('c', 1, new Bishop(board, Color.WHITE));
		PlaceNewPiece('a', 8, new Rook(board, Color.WHITE));
		PlaceNewPiece('h', 7, new Rook(board, Color.WHITE));
		PlaceNewPiece('b', 1, new Knight(board, Color.WHITE));
		PlaceNewPiece('g', 1, new Knight(board, Color.WHITE));
		PlaceNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		PlaceNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		PlaceNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		PlaceNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		PlaceNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		PlaceNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		PlaceNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		PlaceNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
		
		
		
		PlaceNewPiece('e', 8, new King(board, Color.BLACK, this));
		PlaceNewPiece('d', 8, new Queen(board, Color.BLACK));
		PlaceNewPiece('f', 8, new Bishop(board, Color.BLACK));
		PlaceNewPiece('c', 8, new Bishop(board, Color.BLACK));
		PlaceNewPiece('a', 8, new Rook(board, Color.BLACK));
		PlaceNewPiece('h', 8, new Rook(board, Color.BLACK));
		PlaceNewPiece('b', 8, new Knight(board, Color.BLACK));
		PlaceNewPiece('g', 8, new Knight(board, Color.BLACK));
		PlaceNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		PlaceNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		PlaceNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		PlaceNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		PlaceNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		PlaceNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		PlaceNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		
		
	}
}
