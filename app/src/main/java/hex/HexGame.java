
package hex;

import java.util.ArrayList;

public class HexGame {

    private int size;
    private Player[] grid;
    private DisjointSet set;

    HexGame(int size) {
        this.size = size;
        this.grid = new Player[size * size];
        this.set = new DisjointSet(size * size);
    }

    /*
     * Mark the given position as blue and return true if there is a blue chain from
     * the left edge to the right edge
     */
    public boolean playBlue(int position, boolean displayNeighbors) {
        return play(position - 1, displayNeighbors, Player.Blue);
    }

    /*
     * Mark the given position as red and return true if there is a red chain from
     * the left edge to the right edge
     */
    public boolean playRed(int position, boolean displayNeighbors) {
        return play(position - 1, displayNeighbors, Player.Red);
    }

    private boolean play(int index, boolean displayNeighbors, Player player) {
        var neighbors = getNeighbors(index, player);
        if (displayNeighbors) {
            System.out.println("Cell " + (index) + ": " + neighbors);
        }
        if (posAvaliable(index)) {
            this.grid[index] = player;
            for (var neighbor : neighbors) {
                var neighborIdx = neighbor - 1;
                if (isSpecialEdgePos(neighborIdx) || neighborIsSameColor(player, neighborIdx)) {
                    // Link the two sets
                    set.union(index, neighborIdx);
                }
            }
            if (gameWon(index, player)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    private boolean neighborIsSameColor(Player player, int neighborIdx) {
        return this.grid[neighborIdx] == player;
    }

    private boolean isSpecialEdgePos(int neighborIdx) {
        return neighborIdx >= this.size * this.size;
    }

    private boolean posAvaliable(int index) {
        return this.grid[index] == null;
    }

    private boolean gameWon(int index, Player player) {
        var square = this.size * this.size;
        if (player == Player.Blue) {
            return set.find(square + 2) == set.find(square + 3);
        } else {
            return set.find(square) == set.find(square + 1);
        }
    }

    private ArrayList<Integer> getNeighbors(int index, Player player) {
        var list = new ArrayList<Integer>();

        // Above
        if (notOnTopRow(index)) {
            // Get above left
            list.add(index - this.size);
            // Get above right
            if (notOnRightRow(index)) {
                list.add(index - this.size + 1);
            }
        } else if (player == Player.Red) {
            // If on Top Row add special Top Row element
            list.add(this.size * this.size);
        }

        // Below
        if (notOnBottomRow(index)) {
            // Get below right
            list.add(index + this.size);
            // Get below left
            if (notOnLeftRow(index)) {
                list.add(index + this.size - 1);
            }
        } else if (player == Player.Red) {
            // If on Bottom Row add special Bottom Row element
            list.add(this.size * this.size + 1);
        }

        // Get left neighbor
        if (notOnLeftRow(index)) {
            list.add(index - 1);
        } else if (player == Player.Blue) {
            // If on left Row add special Left Row element
            list.add(this.size * this.size + 2);
        }

        // Get the right neighbor
        if (notOnRightRow(index)) {
            list.add(index + 1);
        } else if (player == Player.Blue) {
            // If on Right Row add special Right Row element
            list.add(this.size * this.size + 3);
        }

        // Increment all of the numbers to change them from indicies to positions
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + 1);
        }
        return list;
    }

    private boolean notOnLeftRow(int index) {
        return index % this.size != 0;
    }

    private boolean notOnBottomRow(int index) {
        return index + this.size < this.size * this.size;
    }

    private boolean notOnRightRow(int index) {
        return index % this.size != this.size - 1;
    }

    private boolean notOnTopRow(int index) {
        return index - this.size >= 0;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        var count = 0;
        for (int i = 0; i < this.grid.length; i++) {
            var player = this.grid[i];
            if (isOnLeftRow(i)) {
                builder.append("\n");
                builder.append(" ".repeat(count));
                count += 1;
            }
            if (player == Player.Red) {
                builder.append("\u001B[31m" + "R" + "\u001B[0m" + " ");
            } else if (player == Player.Blue) {
                builder.append("\u001B[34m" + "B" + "\u001B[0m" + " ");
            } else {
                builder.append("0 ");
            }
        }
        return builder.toString();
    }

    private boolean isOnLeftRow(int i) {
        return i % this.size == 0;
    }

    private enum Player {
        Red,
        Blue,
    }
}
