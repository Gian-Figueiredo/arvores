//Autor: Felipe Lisboa Brasil
public class Avlarquivo {

    public class node{
        int chave;
        node filhodireito;
        node filhoesquerdo;
        int altura;
    }

    public class avl{
        node raiz;

        public int altura(node n){
            if (n == null){
                return 0;
            }
            else return n.altura;
        }

        public int atualizar_altura(node n){
            if (n == null) return 0;
            n.altura = 1 + Math.max(altura(n.filhodireito), altura(n.filhoesquerdo));
            return n.altura;
        }

        public int calcular_fator_b(node n){
            if (n == null) return 0;
            return altura(n.filhodireito) - altura(n.filhoesquerdo);
        }

        private node rotacionarDireita(node y){
            node x = y.filhoesquerdo;
            node T2 = x.filhodireito;

            x.filhodireito = y;
            y.filhoesquerdo = T2;

            atualizar_altura(y);
            atualizar_altura(x);

            return x;
        }

        private node rotacionarEsquerda(node x){
            node y = x.filhodireito;
            node T2 = y.filhoesquerdo;

            y.filhoesquerdo = x;
            x.filhodireito = T2;

            atualizar_altura(x);
            atualizar_altura(y);

            return y;
        }

        private node balancear(node n){
            if (n == null) return n;

            atualizar_altura(n);
            int fb = calcular_fator_b(n);

            if (fb > 1){
                if (calcular_fator_b(n.filhodireito) < 0){
                    n.filhodireito = rotacionarDireita(n.filhodireito);
                }
                return rotacionarEsquerda(n);
            }

            if (fb < -1){
                if (calcular_fator_b(n.filhoesquerdo) > 0){
                    n.filhoesquerdo = rotacionarEsquerda(n.filhoesquerdo);
                }
                return rotacionarDireita(n);
            }

            return n;
        }

        private node inserir(node n, int chave){
            if (n == null){
                node novo = new node();
                novo.chave = chave;
                novo.altura = 1;
                return novo;
            }

            if (chave < n.chave){
                n.filhoesquerdo = inserir(n.filhoesquerdo, chave);
            } else if (chave > n.chave){
                n.filhodireito = inserir(n.filhodireito, chave);
            } else {
                return n;
            }

            atualizar_altura(n);
            return balancear(n);
        }

        public void inserir(int chave){
            raiz = inserir(raiz, chave);
        }

        private node minValueNode(node n){
            node current = n;
            while (current.filhoesquerdo != null){
                current = current.filhoesquerdo;
            }
            return current;
        }

        private node remover(node n, int chave){
            if (n == null) return null;

            if (chave < n.chave){
                n.filhoesquerdo = remover(n.filhoesquerdo, chave);
            } else if (chave > n.chave){
                n.filhodireito = remover(n.filhodireito, chave);
            } else {
                if (n.filhoesquerdo == null || n.filhodireito == null){
                    node temp = (n.filhoesquerdo != null) ? n.filhoesquerdo : n.filhodireito;
                    n = temp;
                } else {
                    node temp = minValueNode(n.filhodireito);
                    n.chave = temp.chave;
                    n.filhodireito = remover(n.filhodireito, temp.chave);
                }
            }

            if (n == null) return null;

            atualizar_altura(n);
            return balancear(n);
        }

        public void remover(int chave){
            raiz = remover(raiz, chave);
        }

        public void imprimirEmOrdem(node n){
            if (n == null) return;
            imprimirEmOrdem(n.filhoesquerdo);
            System.out.print(n.chave + " ");
            imprimirEmOrdem(n.filhodireito);
        }

        public void imprimirEmOrdem(){
            imprimirEmOrdem(raiz);
            System.out.println();
            
        }
        private void imprimirArvore(node n, int espaco){
    if (n == null) return;

    espaco += 6;

    imprimirArvore(n.filhodireito, espaco);

    System.out.println();
    for (int i = 6; i < espaco; i++){
        System.out.print(" ");
    }
    System.out.println(n.chave);

    imprimirArvore(n.filhoesquerdo, espaco);
}

public void imprimirArvore(){
    imprimirArvore(raiz, 0);
}


        public boolean buscarIterativo(int chave){
            node atual = raiz;

            while (atual != null){
                if (chave == atual.chave){
                    return true;
                }
                else if (chave < atual.chave){
                    atual = atual.filhoesquerdo;
                }
                else{
                    atual = atual.filhodireito;
                }
            }
            return false;
        }
    }

    public static void main(String[] args){
        Avlarquivo app = new Avlarquivo();
        avl Avl = app.new avl();

        Avl.inserir(2);
        Avl.inserir(20);
        Avl.inserir(347);
        Avl.inserir(40);
        Avl.inserir(25);

        System.out.print("Em-ordem: ");
        Avl.imprimirEmOrdem();
        Avl.imprimirArvore();

        Avl.remover(40);
        System.out.print("depois de remover 40: ");
        Avl.imprimirEmOrdem();
        Avl.imprimirArvore();

        System.out.println("Buscar 25: " + Avl.buscarIterativo(25));
        System.out.println("Buscar 99: " + Avl.buscarIterativo(99));
    }
}