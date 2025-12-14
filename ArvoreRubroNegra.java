public class ArvoreRubroNegra {
    //Primeiro, irei criar uma árvore binária de busca, depois, criarei uma árvore AVL, por último farei uma Rubro Negra

    private static class Node {
        public Integer data;
        public Node leftChild;
        public Node rigthChild;

        /**
         * Cria um nó normal
         * @param valor Valor a ser armazenado no nó 
         * @param filhoMenor nó a esquerda (o valor armazenado é menor que o valor desse nó)
         * @param filhoMaior nó a direita (o valor armazenado é maior que o valor desse nó)
         */
        Node (Integer valor, Node filhoMenor, Node filhoMaior) {
            //Criar um nó normal
            this.data = valor;
            this.leftChild = filhoMenor;
            this.rigthChild = filhoMaior;
        }

    }

    private Node root;
    private static final Node NIL = new Node(null, null, null);

    ArvoreRubroNegra() {
        this.root = NIL;
    }
  
    public void insert(int x) {
        if (this.root.data == null) {
            this.root = new Node(x, NIL, NIL);
            return;
        }
        Node no = this.root;
        boolean esquerda;
        while (true) {
            if (x == no.data) {
                return;
            }
            if (x < no.data) {
                if (no.leftChild == NIL) {
                    esquerda = true;
                    break;
                }
                no = no.leftChild;

            } else {
                if (no.rigthChild == NIL) {
                    esquerda = false;
                    break;
                }
                no = no.rigthChild;

            }
        }
        if (esquerda) {
            no.leftChild = new Node(x, NIL, NIL);
        } else {
            no.rigthChild = new Node(x, NIL, NIL);
        }
    }

    public String ordem(Node no) {
        String left = (no.leftChild == NIL) ? "" : " - " + this.ordem(no.leftChild);
        String right = (no.rigthChild == NIL) ? "" : " - " + this.ordem(no.rigthChild);
        return no.data.toString() + left + right;
    }

    public String toString() {
        if (this.root == NIL) {
            return "Empty Tree";
        }
        return ordem(this.root);
    }

    /**
     * Função de debug, excluir depois
     * @param arg
     */
    public static void println(String arg) {
        System.out.println(arg);
    }
    
    public static void main(String[] args) {
        ArvoreRubroNegra teste = new ArvoreRubroNegra();
        teste.insert(5);
        teste.insert(1);
        teste.insert(4);
        teste.insert(10);
        println(teste.toString());
    }
}
