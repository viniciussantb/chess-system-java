package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner (System.in);
		ChessMatch chessMatch = new ChessMatch();
		List <ChessPiece> captured = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printChessMatch(chessMatch,captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				boolean[][] possibleMoves = chessMatch.possibleMoves(source); 
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.peformChessMove(source, target);
				if(capturedPiece != null) {
					captured.add(capturedPiece);
				}
				if(chessMatch.getPromotion() != null) {
					System.out.print("Promoion! Chose the promotion piece (B/H/R/Q):  ");
					String type = sc.next();
					chessMatch.promotePiece(type);
					sc.nextLine();
				}
				
				
			}
			catch(InputMismatchException e) {
				System.out.println("");
				System.out.print(e.getMessage());
				sc.nextLine();
			}
			catch(ChessException e) {
				System.out.println("");
				System.out.print(e.getMessage());
				sc.nextLine();
				
			}

		}
		
	UI.clearScreen();
	UI.printChessMatch(chessMatch, captured);
	sc.close();

	}

}
