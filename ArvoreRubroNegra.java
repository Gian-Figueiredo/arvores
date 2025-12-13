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

        private String posOrdem() {
            if (this.data == null) {
                return "(null - preto)";
            } else {
                return this.leftChild.posOrdem() + " - " + this.rigthChild.posOrdem() + " - " + this.data;
            }
        }

        private String preOrdem() {
            if (this.data == null) {
                return "(null - preto)";
            } else {
                return this.data + " - " + this.leftChild.preOrdem() + " - " + this.rigthChild.preOrdem();
            }
        }

        public String toString() {
            return this.ordem();
        }

        public String toString(int ordenacao) {
            switch (ordenacao) {
                case 0:
                    return this.preOrdem();
                
                case 1:
                    return this.ordem();
            
                case 2:
                    return this.posOrdem();
                
                default:
                    return this.ordem();
            }
        }
    }
    public static void main(String[] args) {
        
    }
}
