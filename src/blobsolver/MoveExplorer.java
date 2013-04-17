/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blobsolver;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * A runnable class which explores all possible moves This class became
 * necessary in order to interrupt it exactly after 2 seconds have passed.
 *
 * @author Remosewa
 */
public class MoveExplorer {

    public volatile LinkedList<ObjQueue> processQueue = new LinkedList<>();
    public volatile LinkedList<Move> moves = null;
    private Board startBoard = null;
    int maxPrune = 2;
    double stopTime;
    public volatile int totalEvaluations = 0;

    public MoveExplorer(Board b, LinkedList<Move> ms) {
        startBoard = b;
        moves = ms;
    }

    public void start() {
        stopTime = System.currentTimeMillis() + 500;
        moves = getMoves(startBoard, 0, null);
        totalEvaluations = 1;
        while (processQueue.size() > 0 && !(System.currentTimeMillis() > stopTime)) {
            ObjQueue o = processQueue.pop();
            o.move.otherMoves = getMoves(o.move.board, o.team, null);
            totalEvaluations += 1;


        }
    }
//get all moves to a particular depth, If given a list of 'gmoves', it only computes additional moves to the list.

    public LinkedList<Move> getMoves(Board b, int team, LinkedList<Move> gmoves) {

        if (gmoves != null && gmoves.size() > 0) {
            for (Move m : gmoves) {
                getMoves(m.board, nextTeam(m.team), m.otherMoves);
            }
            return gmoves;
        } else {
            LinkedList<Move> moves = new LinkedList<>();
            HashSet<Pos> duplicateMoves = new HashSet<>();
            for (Blob blob : b.getMyBlobs(team)) {
                if (System.currentTimeMillis() > stopTime) {
                    return moves;
                }
                for (Move m : blob.getMoves()) {
                    if (System.currentTimeMillis() > stopTime) {
                        return moves;
                    }
                    if (b.getDistance(m.from, m.to) != 1 || !duplicateMoves.contains(m.to)) {
                        if (b.getDistance(m.from, m.to) == 1) {
                            duplicateMoves.add(m.to);
                        }
                        m.score = b.getScore(m, team); 
                        Board newb = b.makeMove(m.from, m.to, team, false);
                        m.board = newb;
                        m.team = team;
                        ObjQueue o = new ObjQueue(m, nextTeam(team));
                        processQueue.add(o);
                        moves.add(m);
                    }

                }
            }
            return moves;
        }
    }

    //rotates move to nextTeam
    //currently only 2 teams supported
    private static int nextTeam(int team) {
        if (team == 0) {
            return 1;
        } else {
            return 0;
        }
    }
}


