public class Main {

    public static class Node {

        protected int value;
        protected Node leftNode;
        protected Node rightNode;
    
        public Node(int _value, 
                    Node  _leftNode, 
                    Node _rightNode) {
            this.value = _value;
            this.leftNode = _leftNode;
            this.rightNode = _rightNode;
        }
    
        public void walk() {
            System.out.println(this.value);
    
            this.leftNode.walk();
            this.rightNode.walk();
        }
    
        public int getTreeSize() {
            return this.leftNode.getTreeSize() + 
                   this.rightNode.getTreeSize() + 1;
        }
    
        public Node getLeft() { 
            return this.leftNode; 
        }
    
        public Node getRight() {
            return this.rightNode;
        }
    }

    public static class NoNode extends Node {
    
        public NoNode(int _value) {
            super(_value, null, null);
        }
    
        @Override
        public void walk() {
            System.out.println(this.value);
        }
    
        @Override
        public int getTreeSize() {
            return 1;
        }
    
        @Override 
        public Node getLeft() { 
            return null; 
        }
    
        public Node getRight() {
            return null;
        }
    }

    public static Node buildTree() {
        Node root = new Node(1, 
                            new Node(2, 
                                    new Node(3, 
                                            new NoNode(8), 
                                            new NoNode(9)),
                                    new NoNode(4)), 
                            new Node(5, 
                                    new NoNode(6), 
                                    new Node(7, 
                                            new NoNode(10), 
                                            new NoNode(11))));

        return root;
    }

    public static void main(String[] args) {
        Node root = buildTree();
         
        System.out.println("Walking down the tree:");
        root.walk();
        System.out.println("the size of the tree is " + root.getTreeSize());
    }

}