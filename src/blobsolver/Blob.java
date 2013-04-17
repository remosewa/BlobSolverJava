/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blobsolver;

import java.util.LinkedList;

/**
 *
 * @author Remosewa
 */
public class Blob {

    Board board = null;
    Pos pos = null;
    public int team = 0;

    public Blob(Board b, Pos p, int t) {
        board = b;
        pos = p;
        team = t;
    }

    //returns a Linked list of possible moves
    public LinkedList<Move> getMoves() {
        LinkedList<Move> rlist = new LinkedList<>();
        for (int r = pos.row - 2; r <= pos.row + 2; r += 1) {
            for (int c = pos.col - 2; c <= pos.col + 2; c += 1) {
                Pos testp = new Pos(r, c);
                //if(r == 0 && c == 1 && !board.canMoveTo(testp))
                //    System.out.println("SHITNot");
                if (r >= 0 && c >= 0 && r < 8 && c < 8 && board.canMoveTo(testp)) {
                    Move m = new Move(pos,testp);
                    rlist.add(m);
                }
            }
        }
        return rlist;
    }
    
    public Blob copyBlob(Board b){
       return (new Blob(b,pos,team));
    }
}
