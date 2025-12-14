public class ArvoreRubroNegra {

    private enum Color {
        RED, BLACK;
    }

    private class Node {
        public Integer data;
        public Color color;
        public Node leftChild;
        public Node rigthChild;

        Node (Integer valor, Color cor, Node filhoMenor, Node filhoMaior) {
            this.data = valor;
            this.color = cor;
            this.leftChild = filhoMenor;
            this.rigthChild = filhoMaior;
        }

        public void swapColor() {
            if (this.color == Color.RED) {
                this.color = Color.BLACK;
            } else {
                this.color = Color.RED;
            }
        }

    }

    private Node root;
    private final Node NIL = new Node(null, Color.BLACK, null, null);

    ArvoreRubroNegra() {
        this.root = NIL;
    }

    

    public void insert(int x) {
        
    }

    public static void main(String[] args) {

    }
}
