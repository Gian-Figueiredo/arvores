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

        public void switchColor() {
            this.color = !this.color;
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

    private Node brothers(Node node) {
        Node parent = node.parent;
        return (isLeftChild(node, parent)) ? parent.right : parent.left;
    }

    private Node grandparent(Node node) {
        return node.parent.parent;
    }

    private boolean isLeftChild(Node node, Node parent) {
        return parent.left.key == node.key;
    }

    private void rightRotation(Node node, Node parent, Node grandparent) {
        grandparent.right = new Node(grandparent.key, !grandparent.color, parent.right, grandparent.right);
        grandparent.right.parent = grandparent;

        grandparent.key = parent.key;

        grandparent.left.parent = null;
        grandparent.left.left = null;
        grandparent.left = node;
        node.parent = grandparent;
    }

    private void leftRotation(Node node, Node parent, Node grandparent) {
        grandparent.left = new Node(grandparent.key, !grandparent.color, grandparent.left, parent.left);
        grandparent.left.parent = grandparent;

        grandparent.key = parent.key;

        grandparent.right.parent = null;
        grandparent.right.right = null;
        grandparent.right = node;
        node.parent = grandparent;
    }

    private void doubleRightRotation(Node node, Node parent, Node grandparent) {
        grandparent.right = new Node(grandparent.key, grandparent.color, parent.right, grandparent.right);
        grandparent.right.parent = grandparent;

        grandparent.key = node.key;

        parent.right.parent = null;
        parent.right = node.left;
        parent.right.parent = parent;
    }

    private void doubleLeftRotation(Node node, Node parent, Node grandparent) {
        grandparent.left = new Node(grandparent.key, !grandparent.color, grandparent.left, parent.left);
        grandparent.left.parent = grandparent;

        grandparent.key = node.key;

        parent.left.parent = null;
        parent.left = node.right;
        parent.left.parent = parent;
    }

    private void rotacao(Node node, Node parent, Node grandparent) {
        if (isLeftChild(node, parent) && isLeftChild(parent, grandparent)) {
            rightRotation(node, parent, grandparent);
            return;
        }

        if (!isLeftChild(node, parent) && isLeftChild(parent, grandparent)) {
            doubleRightRotation(node, parent, grandparent);
            return;
        }

        if (isLeftChild(node, parent) && !isLeftChild(parent, grandparent)) {
            doubleLeftRotation(node, parent, grandparent);
            return;
        }

        if (!isLeftChild(node, parent) && !isLeftChild(parent, grandparent)) {
            leftRotation(node, parent, grandparent);
            return;
        }
    }

    private void fixInsert(Node node) {
        if (node.parent.color == NEGRO) {
            return;
        }
        if (node.parent.color == RUBRO && grandparent(node).color == NEGRO && brothers(node.parent).color == RUBRO) {
            Node a, p, t;
            a = grandparent(node);
            p = node.parent;
            t = brothers(node.parent);

            a.switchColor();
            p.switchColor();
            t.switchColor();

            if (a.key == this.root.key || a.parent.key == this.root.key) {
                return;
            } else {
                fixInsert(a);
                return;
            }
        }
        if (node.parent.color == RUBRO && grandparent(node).color == NEGRO && brothers(node.parent).color == NEGRO) {
            //rotações
            rotacao(node, node.parent, grandparent(node));
        }
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
            fixInsert(node.left);
        } else {
            node.right = new Node(x, RUBRO, nil, nil);
            node.right.parent = node;
            fixInsert(node.right);
        }
        if (this.root.color == RUBRO) {
            this.root.switchColor();
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
        return key + " " + toString(node.left)  + " " + toString(node.right);
    }

    public static void main(String[] args) {
        ArvoreRB a = new ArvoreRB();
        System.out.println(a);
        int[] b = {40, 20, 10, 50, 25, 60};
        for (int i = 0; i < 6; i++) {
            a.insert(b[i]);
        }
        System.out.println(a);
    }
}