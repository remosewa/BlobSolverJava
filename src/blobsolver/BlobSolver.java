/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blobsolver;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Remosewa
 */
public class BlobSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean moveFirst = true;
        Board board = newBoard(moveFirst);
        LinkedList<Move> moves = null;
        Scanner user_input = new Scanner(System.in);
        String input = "";
        while (moveFirst || !(input = user_input.next()).equals("exit")) {
            if (!moveFirst) {
                String[] elems = input.split(",");
                Pos movf = new Pos(Integer.parseInt(elems[0]), Integer.parseInt(elems[1]));
                Pos movt = new Pos(Integer.parseInt(elems[2]), Integer.parseInt(elems[3]));
                board = board.makeMove(movf, movt, 1, true);
                if (moves != null) {
                    moves = subMoves(moves, board);
                }
            } else {
                moveFirst = false;
            }
            MoveExplorer me = new MoveExplorer(board, moves);

            double startt = System.currentTimeMillis();
            me.start();
            //System.err.println("time taken = " + (System.currentTimeMillis() - startt));

            moves = me.moves;
            Move fm = bestMove(moves);
            moves = fm.otherMoves;
            board = board.makeMove(fm.from, fm.to, 0, true);
            System.out.println(fm.from.row + "," + fm.from.col + "," + fm.to.row + "," + fm.to.col);
        }

    }

    private static Board newBoard(boolean movefirst) {
        Board board = new Board();
        if (movefirst) {
            board.addBlob(new Pos(0, 0), 0);
            board.addBlob(new Pos(0, 7), 0);
            board.addBlob(new Pos(7, 0), 1);
            board.addBlob(new Pos(7, 7), 1);
        } else {
            board.addBlob(new Pos(0, 0), 1);
            board.addBlob(new Pos(0, 7), 1);
            board.addBlob(new Pos(7, 0), 0);
            board.addBlob(new Pos(7, 7), 0);
        }
        return board;
    }

    private static int getTreeDepth(Move m1) {
        if (m1.otherMoves.size() > 0) {
            return 1 + getTreeDepth(m1.otherMoves.getFirst());
        } else {
            return 1;
        }
    }

    //determines highest scoreing move
    private static Move bestMove(LinkedList<Move> moves) {
        int bestscore = -50;
        Move bestmove = null;
        for (Move move : moves) {
            int score = move.getScore();
            if (score > bestscore) {
                bestscore = score;
                bestmove = move;
            }
        }
        return bestmove;
    }

    //given a list of moves and a move, returns a subset of the list without the identical move
    private static LinkedList<Move> subMoves(LinkedList<Move> moves, Board b) {
        for (Move m : moves) {
            if (b.equals(m.board)) {
                return m.otherMoves;
            }
        }
        return null;
    }
}
