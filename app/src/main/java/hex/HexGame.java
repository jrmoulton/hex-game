
package hex;

import java.util.ArrayList;

public class HexGame {

    private int size;
    private Player[] grid;
    private DisjointSet set;

    HexGame(int size) {
        this.size = size;
        this.grid = new Player[size * size];
        this.set = new DisjointSet(size);
    }

    public boolean playBlue(int position, boolean displayNeighbors) {
        if (this.grid[position - 1] == null) { // Check if the pos is open
            this.grid[position - 1] = Player.Blue;
            if (displayNeighbors) {
                System.out.print("Cell " + position + ": ");
            }
            var deleteme = getNeighbors(position - 1, Player.Blue);
            var temp = 0;
            for (var neighbor : deleteme) {
                // The order of these two arguments might need to be reversed
                set.union(position - 1, neighbor, Player.Blue);
            }
            if (gameWon(position - 1, Player.Blue)) {
                return true;
            }
        }
        return false;

    }

    public boolean playRed(int position, boolean displayNeighbors) {
        if (this.grid[position - 1] == null) {
            this.grid[position - 1] = Player.Red;
            if (displayNeighbors) {
                System.out.print("Cell " + position + ": ");
            }
            var deleteme = getNeighbors(position - 1, Player.Red);
            var temp = 0;
            for (var neighbor : deleteme) {
                // Delete the player.red
                set.union(position - 1, neighbor, Player.Red);
            }
            if (gameWon(position - 1, Player.Red)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean gameWon(int index, Player player) {
        if (player == Player.Blue) {
            return this.set.onLeftAndRightEdges(index);
        } else {
            return this.set.onTopAndBottomEdges(index);
        }
    }

    private ArrayList<Integer> getNeighbors(int index, Player player) {
        var list = new ArrayList<Integer>();

        // Get left neighbor
        if (index % this.size != 0 && this.grid[index - 1] != null && this.grid[index - 1].equals(player)) {
            list.add(index - 1);
        }
        // Get the right neighbor
        if (index % this.size != this.size - 1 && this.grid[index + 1] != null && this.grid[index + 1].equals(player)) {
            list.add(index + 1);
        }
        // Get above left
        if (index - this.size > 0 && this.grid[index - this.size] != null
                && this.grid[index - this.size].equals(player)) {
            list.add(index - this.size);
        }
        // Get above right
        if (index - this.size > 0 && this.grid[index - this.size + 1] != null
                && this.grid[index - this.size + 1].equals(player)) {
            list.add(index - this.size + 1);
        }
        // Get below right
        if (index + this.size < this.size * this.size && this.grid[index + this.size] != null
                && this.grid[index + this.size].equals(player)) {
            list.add(index + this.size);
        }
        // Get below left
        if (index + this.size < this.size * this.size && this.grid[index + this.size - 1] != null
                && this.grid[index + this.size - 1].equals(player)) {
            list.add(index + this.size - 1);
        }
        return list;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        var count = 0;
        for (int i = 0; i < this.grid.length; i++) {
            var player = this.grid[i];
            if (i % this.size == 0) {
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

    public enum Player {
        Red,
        Blue,
    }
}
