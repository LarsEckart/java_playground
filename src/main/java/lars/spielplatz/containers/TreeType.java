package lars.spielplatz.containers;

public class TreeType extends SetType implements Comparable<TreeType> {

    public TreeType(int i) {
        super(i);
    }

    @Override public int compareTo(TreeType arg) {
        return Integer.compare(arg.i, i);
    }
}
