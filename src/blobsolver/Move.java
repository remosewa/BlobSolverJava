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
public class Move {

    public Pos from = null;
    public Pos to = null;
    public int score = 0;
    public int tempscore = 0;
    public Board board = null;
    public LinkedList<Move> otherMoves = new LinkedList<>();
    public int team = 0;

    public Move(Pos from, Pos to) {
        this.from = from;
        this.to = to;
    }

    //gets score of move considering otherMoves
    public int getScore() {
        boolean checkedYet = false;
        int otherScore = 0;
        for (Move m : otherMoves) {
            int tscore = m.getScore();
            if (!checkedYet) {
                otherScore = tscore;
                checkedYet = true;
            } else if (team == 0 && tscore < otherScore) {
                otherScore = tscore;
                checkedYet = true;
            } else if (team != 0 && tscore > otherScore) {
                otherScore = tscore;
                checkedYet = true;
            }
        }

        tempscore = score + otherScore;
        return score + otherScore;
    }

    @Override
    public boolean equals(Object o) {
        Move m = (Move) o;
        if (m.from.equals(from) && m.to.equals(to)) {
            return true;
        } else {
            return false;
        }
    }
}
