/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blobsolver;

/**
 *
 * @author Remosewa
 */
public class Pos {

    public int row = 0;
    public int col = 0;

    public Pos(int r, int c) {
        row = r;
        col = c;
    }

    @Override
    public boolean equals(Object o) {
        Pos p = (Pos) o;
        if (p.row == row && p.col == col) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(row);
        buffer.append(col);
        return buffer.toString().hashCode();
    }
}
