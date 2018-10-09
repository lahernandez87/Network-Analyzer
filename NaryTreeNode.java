import java.util.ArrayList;

public class NaryTreeNode {
    final String LABEL;
    final int N;
    private final ArrayList<NaryTreeNode> children;

    public NaryTreeNode(String LABEL, int n) {
        this.LABEL = LABEL;
        this.N = n;
        children = new ArrayList<>(n);
    }

    private boolean addChild(NaryTreeNode node) {
        if (children.size() < N) {
            return children.add(node);
        }

        return false;
    }

    public boolean addChild(String label) {
        return addChild(new NaryTreeNode(label, N));
    }

    public ArrayList<NaryTreeNode> getChildren() {
        return new ArrayList<>(children);
    }

    public NaryTreeNode getChild(int index) {
        if (index < children.size()) {
            return children.get(index);
        }

        return null;
    }

    public static void print(NaryTreeNode root) {
        printUtil(root, 0);
    }

    private static void printUtil(NaryTreeNode node, int depth) {
        for (int i = 0; i < depth; ++i) {
            System.out.print("   ");
        }

        System.out.println(node.LABEL);

        for (NaryTreeNode child : node.getChildren()) {
            printUtil(child, depth + 1);
        }
    }
}