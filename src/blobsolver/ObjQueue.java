/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blobsolver;

/**
 * A simple class to hold required data in order to compute move.othermoves
 * Intended to go in a queue and popped off when ready.
 * @author Remosewa
 */
public class ObjQueue {

    Move move = null;
    int team = 0;

    public ObjQueue(Move m, int team) {
        move = m;
        this.team = team;
    }
}
