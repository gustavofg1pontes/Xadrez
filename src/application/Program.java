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
        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!match.getCheckMate()) {
            try {
                Ui.clearScreen();
                Ui.printMatch(match, captured);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = Ui.readChessPosition(sc);


                boolean[][] possibleMoves = match.possibleMoves(source);
                Ui.clearScreen();
                Ui.printBoard(match.getPieces(), possibleMoves);


                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = Ui.readChessPosition(sc);

                ChessPiece capturedPiece = match.performChessMove(source, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                if (match.getPromoted() != null) {
                    String type = "";
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.println("Enter piece for promotion (B/N/R/Q): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    match.replacePromotedPiece(type);
                }
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                System.out.println("Press enter");
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                System.out.println("Press enter");
                sc.nextLine();
            }
        }

        Ui.clearScreen();
        Ui.printMatch(match, captured);
        sc.nextLine();
    }

}
