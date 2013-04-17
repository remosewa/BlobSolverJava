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
public class Board {

    Blob[][] matrix;

    public Board() {
        matrix = new Blob[8][8];
    }

    //adds blob at pos p
    public void addBlob(Pos p, int team) {
        matrix[p.row][p.col] = new Blob(this, p, team);
    }

    //actually makes move. Move assumed to be valid
    public void moveBlob(Pos from, Pos to) {
        Blob b = getSpot(from);
        b.pos = to;
        matrix[from.row][from.col] = null;
        matrix[to.row][to.col] = b;
    }

    public Blob getSpot(Pos p) {
        return matrix[p.row][p.col];
    }

    //returns a copy of the board with the move adjustment
    public Board makeMove(Pos from, Pos to, int team, boolean print) {
        Board newb = copyBoard();
        if (getDistance(from, to) == 1) {
            newb.addBlob(to, team);
        } else {
            newb.moveBlob(from, to);
        }


        for (int r = to.row - 1; r <= to.row + 1; r += 1) {
            for (int c = to.col - 1; c <= to.col + 1; c += 1) {
                if (r >= 0 && c >= 0 && r < 8 && c < 8) {
                    Blob testb = newb.matrix[r][c];
                    if (testb != null && testb.team != team) {
                        testb.team = team;
                    }
                }
            }
        }

        return newb;
    }

    public boolean canMoveTo(Pos p) {
        if (getSpot(p) == null) {
            return true;
        } else {
            return false;
        }
    }

    //returns a list of all blobs with team 'team'
    public LinkedList<Blob> getMyBlobs(int team) {
        LinkedList<Blob> blist = new LinkedList<>();
        for (int r = 0; r < 8; r += 1) {
            for (int c = 0; c < 8; c += 1) {
                Blob testb = matrix[r][c];
                if (testb != null && testb.team == team) {
                    blist.add(testb);
                }
            }
        }
        return blist;
    }

    public int getDistance(Pos p1, Pos p2) {
        int dist = (int) Math.sqrt(Math.pow((p1.row - p2.row), 2) + Math.pow((p1.col - p2.col), 2));
        return dist;
    }

    //given a position, compute what the score WOULD be IF the move were made
    //does not have to be a valid move
    public int getScore(Move m, int team) {
        Pos f = m.from;
        Pos p = m.to;
        int score = 0;
        if (getDistance(f, p) == 1) {
            score += 1;
        }
        for (int r = p.row - 1; r <= p.row + 1; r += 1) {
            for (int c = p.col - 1; c <= p.col + 1; c += 1) {
                if (r >= 0 && c >= 0 && r < 8 && c < 8) {
                    Blob testb = getSpot(new Pos(r, c));
                    if (testb != null && testb.team != team) {
                        score += 1;
                    }
                }
            }
        }
        if (team != 0) {
            score = -score;
        }
        return score;
    }

    public void addBlob(Pos p, Blob b) {
        matrix[p.row][p.col] = b;
    }

    //returns a copy of self with all new blob objects
    private Board copyBoard() {
        Board newboard = new Board();
        for (int r = 0; r < 8; r += 1) {
            for (int c = 0; c < 8; c += 1) {
                if (matrix[r][c] != null) {
                    newboard.matrix[r][c] = matrix[r][c].copyBlob(newboard);
                }
            }
        }
        return newboard;
    }

    public void printBoard() {
        for (int r = 0; r < 8; r += 1) {
            String row = "";
            for (int c = 0; c < 8; c += 1) {
                Blob testb = matrix[r][c];
                if (testb == null) {
                    row += "0 ";
                } else if (testb.team == 0) {
                    row += "+ ";
                } else {
                    row += "- ";
                }
            }
            System.out.println(row);
        }

    }

    @Override
    public boolean equals(Object o) {
        Board ob = (Board) o;
        for (int r = 0; r < 8; r += 1) {
            for (int c = 0; c < 8; c += 1) {
                if (matrix[r][c] == null && ob.matrix[r][c] != null) {
                    return false;
                } else if (matrix[r][c] != null && ob.matrix[r][c] == null) {
                    return false;
                } else if (matrix[r][c] != null && matrix[r][c].team != ob.matrix[r][c].team) {
                    return false;
                }
            }
        }
        return true;
    }
}
