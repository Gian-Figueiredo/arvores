public class ArvoreRB {

    private final boolean RUBRO = false;
    private final boolean NEGRO = true;

    private static class Node {
        Integer key;
        boolean color;
        Node left, right, parent;

        Node(Integer chave, boolean cor)  {
            this.key = chave;
            this.color = cor;
        }

        Node(Integer chave, boolean cor, Node esq, Node dir) {
            this.key = chave;
            this.color = cor;
            this.left = esq;
            this.right = dir;
        }
    }

    private final Node nil = new Node(null, NEGRO);
    private Node root;

    ArvoreRB() {
        this.root = null;
    }

    public boolean search(int x) {
        Node node = this.root;
        while (node.key != null) {
            if (node.key == x) {
                return true;
            }
            if (x < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }

    public void insert(int x) {
        if (this.root == null) {
            this.root = new Node(x, NEGRO, nil, nil);
            return;
        }

        //Procura o melhor lugar para inserir o nó
        Node node = this.root;
        if (node.key == x) {
            return;
        }
        Node next = (x < node.key) ? node.left : node.right;
        while (next != nil) {
            node = next;
            if (node.key == x) {
                return;
            }
            if (x < node.key) {
                next = node.left;
            } else {
                next = node.right;
            }
        }
        if (x < node.key) {
            node.left = new Node(x, RUBRO, nil, nil);
            node.left.parent = node;
        } else {
            node.right = new Node(x, RUBRO, nil, nil);
            node.right.parent = node;
        }
    }

    public String toString() {
        if (this.root == null) {
            return "Empty Tree";
        }
        return toString(this.root);
    }

    private String toString(Node node) {
        if (node.key == null) {
            return "";
        }
        String key = node.key + (node.color ? "(N)" : "(R)");
        return toString(node.left) + " " + key + " " + toString(node.right);
    }

    public static void main(String[] args) {
        ArvoreRB a = new ArvoreRB();
        System.out.println(a);
        a.insert(20);
        System.out.println(a);
        a.insert(10);
        a.insert(40);
        System.out.println(a);
    }
}