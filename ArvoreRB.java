public class ArvoreRB {
    //Árvore Rubro Negra que não aceita valor repetido e possui critério de sucessor para remoções.
    //Apenas se não houver um sucessor que é uilizado o critério de antecessor

    // Constantes de cor, para melhor entendimento do código
    private final boolean RUBRO = false;
    private final boolean NEGRO = true;

    //Criação da classe auxiliar Node
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

    //Constante nil indicando uma folha imaginária negra
    private final Node nil = new Node(null, NEGRO);
    private Node root;

    ArvoreRB() {
        //Inicializando a árvore vazia
        this.root = null;
    }

    /**
     * Função que procura um Nó específico dada a sua chave
     * @param x : Procura o nó pela sua chave correspondente
     * @return Retorna 
     */
    private Node findNode(int x) {
        Node node = this.root;
        while (node.key != null) {
            if (node.key == x) {
                return node;
            }
            if (x < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * Função que procura um nó e retorna se foi encontrado ou não
     * @param x : Chave a ser analisada
     * @return boleano se foi encontrado um nó com a chava x ou não
     */
    public boolean search(int x) {
        return findNode(x) != null;
    }

    /**
     * Função auxiliar que retorna o irmão de um determinado nó
     */
    private Node brothers(Node node) {
        Node parent = node.parent;
        return (isLeftChild(node, parent)) ? parent.right : parent.left;
    }

    /**
     * Função auxiliar que retorna o avó de um nó
     */
    private Node grandparent(Node node) {
        return node.parent.parent;
    }

    /**
     * Função auxiliar que retorna se um nó e o filho esquerdo do nó parent
     */
    private boolean isLeftChild(Node node, Node parent) {
        return parent.left.key == node.key;
    }

    // FUNÇÕES DE ROTAÇÕES ADAPTADAS PARA RUBRO NEGRA, AUTOMATICAMENTE FAZ A RECOLORAÇÃO DOS NÓS

    private void rightRotation(Node node, Node parent, Node grandparent) {
        grandparent.right = new Node(grandparent.key, !grandparent.color, parent.right, grandparent.right);
        parent.right.parent = grandparent.right;
        parent.right = null;
        grandparent.right.right.parent = grandparent.right;
        grandparent.right.parent = grandparent;

        grandparent.key = parent.key;

        grandparent.left.parent = null;
        grandparent.left.left = null;
        grandparent.left = node;
        node.parent = grandparent;
    }

    private void leftRotation(Node node, Node parent, Node grandparent) {
        grandparent.left = new Node(grandparent.key, !grandparent.color, grandparent.left, parent.left);
        parent.left.parent = grandparent.left;
        parent.left = null;
        grandparent.left.left.parent = grandparent.left;
        grandparent.left.parent = grandparent;

        grandparent.key = parent.key;

        grandparent.right.parent = null;
        grandparent.right.right = null;
        grandparent.right = node;
        node.parent = grandparent;
    }

    private void doubleRightRotation(Node node, Node parent, Node grandparent) {
        grandparent.right = new Node(grandparent.key, !grandparent.color, node.right, grandparent.right);
        grandparent.right.right.parent = grandparent.right;
        node.right.parent = grandparent.left;
        node.right = null;
        grandparent.right.parent = grandparent;

        grandparent.key = node.key;

        parent.right.parent = null;
        parent.right = node.left;
        parent.right.parent = parent;
        node.left = null;
    }

    private void doubleLeftRotation(Node node, Node parent, Node grandparent) {
        grandparent.left = new Node(grandparent.key, !grandparent.color, grandparent.left, node.left);
        grandparent.left.left.parent = grandparent.left;
        node.left.parent = grandparent.left;
        node.left = null;
        grandparent.left.parent = grandparent;

        grandparent.key = node.key;

        parent.left.parent = null;
        parent.left = node.right;
        parent.left.parent = parent;
        node.right = null;
    }

    /**
     * Função geral de rotação, analisa os casos e determina qual rotação utilizar
    */
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
        }
    }

    /**
     * Função que Corrige as inserções com recolorações e rotações
     */
    private void balancearInsecao(Node node) {
        // Se o pai do nó inserido for negro, já está balanceado
        if (node.parent.color == NEGRO) {
            return;
        }

        // Se o pai for rubro, o avó for negro e o tio for rubro
        if (node.parent.color == RUBRO && grandparent(node).color == NEGRO && brothers(node.parent).color == RUBRO) {

            // Faz as recolorações do avó, do pai e do tio
            Node a, p, t;
            a = grandparent(node);
            p = node.parent;
            t = brothers(node.parent);

            a.switchColor();
            p.switchColor();
            t.switchColor();

            // Se o avó não for a raíz e a cor do pai do avó for rubra então balanceia o avó
            if (a.key != this.root.key && a.parent.color == RUBRO) {
                balancearInsecao(a);
            }
            return;
        }

        // Se o pai for rubro, o avó negro e o irmão negro então faz as rotações
        if (node.parent.color == RUBRO && grandparent(node).color == NEGRO && brothers(node.parent).color == NEGRO) {
            rotacao(node, node.parent, grandparent(node));
        }
    }

    /**
     * Função para inserir valores na árvore
     */
    public void insert(int x) {
        // Se a árvore estiver vazia então cria um nó na raíz
        if (this.root == null) {
            this.root = new Node(x, NEGRO, nil, nil);
            return;
        }

        
        Node node = this.root;
        // Como a árvore não aceita valores repetidos, então se ela encontrar que já possui o valor, apenas não faz nada
        if (node.key == x) {
            return;
        }
        // Intera a árvore até encontrar o melhor lugar para inserir o valor
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
        // Insere o valor ou na direita ou na esquerda e depois verifica se precisa balancear e balanceia
        if (x < node.key) {
            node.left = new Node(x, RUBRO, nil, nil);
            node.left.parent = node;
            balancearInsecao(node.left);
        } else {
            node.right = new Node(x, RUBRO, nil, nil);
            node.right.parent = node;
            balancearInsecao(node.right);
        }
        // Se devido o balanceamento, a raíz ficar rubro, troca para negro
        if (this.root.color == RUBRO) {
            this.root.switchColor();
        }
    }

    /**
     * Função auxiliar que remove um nó transferindo os ponteiros do seu filho para seu pai
     * É importante observar que é considerado que esse nó não possui um dos filhos, porque esse nó é ou um sucessor ou um antecessor
     */
    private void removeNodeSimple(Node node) {
        Node parent = node.parent;
        if (isLeftChild(node, parent)) {
            parent.left = node.right;
            node.right.parent = parent;
        } else {
            parent.right = node.left;
            parent.left.parent = parent;
        }
    }

    /**
     * Encontra o sucessor, senão houver retorna null
     */
    private Node findSucessor(Node node) {
        if (node.right.key == null) {
            return null;
        }
        Node next = node.right;
        while (next.left.key != null) {
            next = next.left;
        }
        return next;
    }

    /**
     * Encontra o antecessor, senão houver retorna null
     */
    private Node findAntecessor(Node node) {
        if (node.left.key == null) {
            return null;
        }
        Node next = node.left;
        while (next.right.key != null) {
            next = next.right;
        }
        return next;
    }

    /**
     * Função para remover valores da árvore
     */
    public void remove(int x) {
        // Se a árvore estiver vazia, avisa e não faz mais nada
        if (this.root == null) {
            System.out.println("Árvore vazia");
            return;
        }

        // Procura o nó na árvore
        Node node = findNode(x);

        // Se não encontrar o nó a ser retirado, ele avisa e para
        if (node == null) {
            System.out.println("Valor não encontrado na árvore");
            return;
        }

        // Se nó a ser retirado for a raíz e essa não possuir nenhum filho, esvazia a árvore
        if (node.key == this.root.key && node.left.key == null && node.right.key == null) {
            this.root = null;
            return;
        }

        // Remove o nó pedido
        removeNode(node);
    }

    /**
     * Provavelmente a função mais complicada do código
     * Balanceia a remoção em casa de duplo negro
     */
    private void balancearDuploNegro(Node node, Node parent, Node brother) {
        // Precisa ser espelhado caso o duplo negro esteja na esquerda ou na direita
        if (isLeftChild(node, parent)) {
            // Se a cor do irmão de duplo negro for Rubro, então rotaciona para a esquerda e ajeita o duplo negro que sobrou
            if (brother.color == RUBRO) {
                leftRotation(brother.right, brother, parent);
                balancearDuploNegro(node, node.parent, brothers(node));
                return;
            } else {
                //Caso o irmão tenha a cor negra
                // E os filhos do irmão tenham cor negra
                if (brother.right.color == NEGRO && brother.left.color == NEGRO) {
                    brother.switchColor(); //muda a cor do irmão para rubro
                    if (parent.color == RUBRO) {
                        parent.switchColor(); //se o pai tiver cor rubra, muda para negra
                    } else {
                        if (parent.key == this.root.key) {
                            //Se o pai for a raíz, então a árvore está balanceada novamente e podemos parar
                            return;
                        }
                        // Senão precisamo balancear o pai, que virou um duplo negro
                        balancearDuploNegro(parent, parent.parent, brothers(parent));
                    }
                    return;
                }

                //Se o irmão tiver um filho direito Rubro então
                // Faça uma rotação pra esquerda garantindo que os filhos do pai fiquem Negros e consertando a cor da raíz
                if (brother.right.color == RUBRO) {
                    leftRotation(brother.right, brother, parent);
                    parent.left.color = NEGRO;
                    parent.right.color = NEGRO;
                    if (this.root.color == RUBRO) {
                        this.root.switchColor();
                    }
                    return;
                }

                // Se o irmão for Negro, faça uma rotação para direita e tente novamente
                if (brother.right.color == NEGRO) {
                    rightRotation(brother.left.left, brother.left, brother);
                    balancearDuploNegro(node, node.parent, brothers(node));
                    return;
                }
            }
            //Todas as funções estão espelhadas para o outro lado
        } else {
            if (brother.color == RUBRO) {
                rightRotation(brother.left, brother, parent);
                balancearDuploNegro(node, node.parent, brothers(node));
                return;
            } else {
                //Caso o irmão tenha a cor negra
                if (brother.left.color == NEGRO && brother.right.color == NEGRO) {
                    brother.switchColor(); //Muda para RUBRO
                    if (parent.color == RUBRO) {
                        parent.switchColor(); //MUDA para NEGRO
                    } else {
                        if (parent.key == this.root.key) {
                            //Se o pai for a raíz, então a árvore está balanceada novamente e podemos parar
                            return;
                        }
                        balancearDuploNegro(parent, parent.parent, brothers(parent));
                    }
                    return;
                }

                if (brother.left.color == RUBRO) {
                    rightRotation(brother.left, brother, parent);
                    parent.right.color = NEGRO;
                    parent.left.color = NEGRO;
                    if (this.root.color == RUBRO) {
                        this.root.switchColor();
                    }
                    return;
                }

                if (brother.left.color == NEGRO) {
                    leftRotation(brother.right.right, brother.right, brother);
                    balancearDuploNegro(node, node.parent, brothers(node));
                    return;
                }
            }
        }
    }

    /**
     * Função que dado um nó, encontra seu sucessor/antecessor, remove e balanceia
     */
    private void removeNode(Node node) {
        // Se o nó a ser removido não possuir filhos então
        if (node.left.key == null && node.right.key == null) {
            // Se o nó for Rubro, apenas o remove, mas se for negro, limpa seu valor, transformando em um nil duplo negro e o balanceia
            if (node.color == RUBRO) {
                removeNodeSimple(node);
            } else if (node.color == NEGRO) {
                node.key = null;
                balancearDuploNegro(node, node.parent, brothers(node));
            }
            return;
        }

        // Encontra o sucessor e se não houver um sucessor encontra seu antecessor
        // É garantido que ao rodar isso, aja pelo menos ou um sucessor ou um antecessor
        Node sucessor = findSucessor(node);
        Node antecessor = findAntecessor(node);
        Node descendente = (sucessor == null) ? antecessor : sucessor;
        // A partir de agora, chamarei de sucessor para ambos os casos


        // Se o sucessor for Rubro, então ele apenas troca a chave e remove o sucessor, não precisando fazer mais nada.
        if (descendente.color == RUBRO) {
            node.key = descendente.key;
            removeNodeSimple(descendente);
            return;
        }

        // Se o sucessor for Negro então eu substituo a chave do nó a ser retirado pelo sucessor e tento remover o sucessor
        // Isso cria um efeito em cascata que acaba sempre ou com sucessor rubro ou com a remoção de uma folha negra
        if (descendente.color == NEGRO) {
                node.key = descendente.key;
                removeNode(descendente);
                return;
        }
    }

    // Função que printa a árvore balanceada
    public void display() {
        System.out.println(toString());
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
        // Agora mostrarei uma árvore inserindo, buscando e removendo elementos, sempre mostrando a árvore balanceada

        //Criar
        System.out.println("Criação da àrvore");
        ArvoreRB tree = new ArvoreRB();
        tree.display();

        System.out.println();

        //Inserir
        int[] a = {50, 40, 70, 10, 30, 90, 100};
        System.out.println("inserindo " + a.length + " elementos");
        for (int i = 0; i < a.length; i++) {
            tree.insert(a[i]);
        }
        tree.display();

        System.out.println();

        // Procurar
        int[] b = {10, 20, 30, 40, 50, 60};
        System.out.println("Procurando " + b.length + " elementos");
        for (int j = 0; j < b.length; j++) {
            System.out.printf("%d : ", b[j]);
            System.out.println(tree.search(b[j]));
        }

        System.out.println();

        // Remover
        int[] c = {70, 90, 100, 40};
        System.out.println("Removendo " + c.length + " elementos");
        for (int k = 0; k < c.length; k++) {
            tree.remove(c[k]);
        }
        tree.display();
    }
}
