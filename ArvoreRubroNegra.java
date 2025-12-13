public class ArvoreRubroNegra {

    private enum Color {
        VERMELHA, PRETA;
    }

    private class Node {
        private Integer data;
        private Color color;
        private Node leftChild;
        private Node rigthChild;

        Node(Integer valor, Color cor, Node filhoEsquerda, Node filhoDireita) {
            this.data = valor;
            this.color = cor;
            this.leftChild = filhoEsquerda;
            this.rigthChild = filhoDireita;
        }

        Node(Integer valor) {
            this.data = valor;
            this.color = Color.PRETA;
            this.leftChild = null;
            this.rigthChild = null;
        }

        private String ordem() {
            if (this.data == null) {
                return "(null - preto)";
            } else {
                return this.leftChild.ordem() + " - " + this.data + " - " + this.rigthChild.ordem();
            }
        }

    }

    private Node root;

    ArvoreRubroNegra() {
        this.root = new Node(null);
    }

    public void insert(int data) {
        if (this.root.data == null) {
            this.root = new Node(data);
            return;
        }
        Node no = this.root;
        while (no.data != null) {
            if (data > no.data) {
                no = no.rigthChild;
            } else {
                no = no.leftChild;
            }
        }
        no = new Node(data);
    }

    public String toString() {
        return this.root.ordem();
    }

    public static void main(String[] args) {

}
