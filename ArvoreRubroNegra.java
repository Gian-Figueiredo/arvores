public class ArvoreRubroNegra {

    private enum Color {
        VERMELHA, PRETA;
    }

    private class Node {
        private int data;
        private Color color;
        private Node left_child;
        private Node right_child;

        Node(int valor, Color cor, Node filho_esquerda, Node filho_direita) {
            this.data = valor;
            this.color = cor;
            this.left_child = filho_esquerda;
            this.right_child = filho_direita;
        }

        Node(int valor) {
            this.data = valor;
            this.color = Color.PRETA;
            this.left_child = null;
            this.right_child = null;
        }
    }
    public static void main(String[] args) {
        
    }
}
