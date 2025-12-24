//Autor: Gian Victor Gonçalves Figueiredo

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

    public boolean search(int x) {
        return findNode(x) != null;
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

    private void balancearInsecao(Node node, Node parent) {
        if (parent.color == NEGRO) {
            return;
        }
        if (parent.color == RUBRO && parent.parent.color == NEGRO && brothers(parent).color == RUBRO) {
            Node a, p, t;
            a = grandparent(node);
            p = node.parent;
            t = brothers(node.parent);

            a.switchColor();
            p.switchColor();
            t.switchColor();

            if (a.key != this.root.key && a.parent.color == RUBRO) {
                balancearInsecao(a, a.parent);
            }
            return;
        }
        if (parent.color == RUBRO && parent.parent.color == NEGRO && brothers(parent).color == NEGRO) {
            //rotações
            rotacao(node, parent, grandparent(node));
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
            balancearInsecao(node.left, node);
        } else {
            node.right = new Node(x, RUBRO, nil, nil);
            node.right.parent = node;
            balancearInsecao(node.right, node);
        }
        if (this.root.color == RUBRO) {
            this.root.switchColor();
        }
    }

    private void removeNodeSimple(Node node) {
        // Como o nó a ser removido fisicamente será o sucessor, ele possui no máximo um filho direto.
        Node parent = node.parent;
        if (isLeftChild(node, parent)) {
            parent.left = node.right;
            node.right.parent = parent;
        } else {
            parent.right = node.left;
            parent.left.parent = parent;
        }
    }

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

    public void remove(int x) {
        if (this.root == null) {
            System.out.println("Árvore vazia");
            return;
        }

        Node node = findNode(x);

        if (node == null) {
            System.out.println("Valor não encontrado na árvore");
            return;
        }

        removeNode(node);
    }

    private void balancearDuploNegro(Node node, Node parent, Node brother) {
        if (isLeftChild(node, parent)) {
            if (brother.color == RUBRO) {
                leftRotation(brother.right, brother, parent);
                balancearDuploNegro(node, node.parent, brothers(node));
                return;
            } else {
                //Caso o irmão tenha a cor negra
                if (brother.right.color == NEGRO && brother.left.color == NEGRO) {
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

                if (brother.right.color == RUBRO) {
                    leftRotation(parent, brother, brother.right);
                    parent.left.color = NEGRO;
                    parent.right.color = NEGRO;
                    if (this.root.color == RUBRO) {
                        this.root.switchColor();
                    }
                    return;
                }

                if (brother.right.color == NEGRO) {
                    rightRotation(brother.left.left, brother.left, brother);
                    balancearDuploNegro(node, node.parent, brothers(node));
                    return;
                }
            }
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
                    rightRotation(parent, brother, brother.right);
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

    private void removeNode(Node node) {
        if (node.left.key == null && node.right.key == null) {
            if (node.color == RUBRO) {
                removeNodeSimple(node);
            } else if (node.color == NEGRO) {
                node.key = null;
                balancearDuploNegro(node, node.parent, brothers(node));
            }
            return;
        }

        Node sucessor = findSucessor(node);
        Node antecessor = findAntecessor(node);
        Node descendente = (sucessor == null) ? antecessor : sucessor;

        if (descendente.color == RUBRO) {
            node.key = descendente.key;
            removeNodeSimple(descendente);
            return;
        }

        if (descendente.color == NEGRO) {
            if (node.color == RUBRO) {
                Integer temp = node.key;
                node.key = descendente.key;
                descendente.key = temp;
                removeNode(descendente);
                return;
            } else if (node.color == NEGRO) {
                node.key = descendente.key;
                descendente.key = null;
                balancearDuploNegro(descendente, descendente.parent, brothers(descendente));
                return;
            }
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
        int[] b = {90,100,110,120,50,60,70,80,10,20,30,40,130,140,150,160};
        for (int i = 0; i < b.length; i++) {
            System.out.println(a);
            a.insert(b[i]);
        }
        System.out.println(a);
    }
}
