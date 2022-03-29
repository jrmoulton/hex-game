package hex;

public class DisjointSet {
    private int[] set;
    private int size;

    DisjointSet(int size) {
        this.size = size;
        // Add the 4 extra special elements to track edges: Top, Botton, Left, Right
        this.set = new int[this.size + 4];
        for (int i = 0; i < this.size + 4; i++) {
            this.set[i] = -1;
        }
    }

    /*
     * Returns the index of the head of a set. At that index is the number of
     * nodes in the set
     */
    public int find(int node) {
        if (isHead(node)) {
            return node;
        }
        // Set the parent directly to the head. (Path Compression)
        this.set[node] = find(this.set[node]);
        return this.set[node];
    }

    private boolean isHead(int node) {
        return this.set[node] < 0;
    }

    /*
     * Link two indicies by pointing the head of one set to the head of the other
     * set
     */
    public void union(int root1, int root2) {
        if (notInSameSet(root1, root2)) {
            if (firstTreeIsBigger(root1, root2)) {
                // Update the size count on what will be the new head
                this.set[find(root1)] += this.set[find(root2)];
                // Set the head of the smaller set to the head of the larger set
                this.set[find(root2)] = find(root1);
            } else {
                this.set[find(root2)] += this.set[find(root1)];
                this.set[find(root1)] = find(root2);
            }
        }
    }

    private boolean notInSameSet(int root1, int root2) {
        return find(root1) != find(root2);
    }

    // Check if a the set of any given node is larger than the set of another node
    private boolean firstTreeIsBigger(int index1, int index2) {
        return this.set[find(index1)] < this.set[find(index2)];
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (var num : set) {
            builder.append(String.format("%s ", num));
        }
        return builder.toString();
    }

}
