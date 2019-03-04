package org.sortSet;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tree tree = new Tree();

       /* String str1 = "1, 2, 3, 4, 5, 6, 7";
        writeObjectToFile(str1,"infile");*/

        String str = (String) readObjectFromFile("infile.dat");
        //String str = "1, 2, 3, 4, 5, 6, 7";
        String[] numbers = str.split(",");
        for (int i = 0; i < numbers.length; i++) {
            //System.out.println("enter number:");
            //int a = sc.nextInt();
            int a = Integer.parseInt(numbers[i].trim());
            tree.add(a);
        }
        System.out.println("Sorted Set A Contains:");
        tree.inorder(tree.root);
        System.out.println("\n\nInput a number to check whether the set contains:");
        int k = sc.nextInt();
        System.out.println("\nOutput:");
        if (tree.contains(k)) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }

    }

    public static void writeObjectToFile(Object obj, String name) {
        File file = new File(name + ".dat");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            System.out.println("write object success!");
        } catch (IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile(String name) {
        Object temp = null;
        File file = new File(name);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}

class Tree {
    int size = 0;
    public Node root = null;

    private static Tree tree = null;

    public Tree() {

    }

    /*public Tree getInstance(){
        if (tree == null) {
            tree  = new Tree();
        }
        return tree;
    }*/

    public boolean isEmpty() {
        if (root == null) {
            return true;
        }
        return false;
    }

    public void add(int k) {
        if (root == null) {
            root = new Node(k);
            //root.height = 1;
        } else {
            //System.out.println("test");
            Node v = iterativeTreeSearch(k);
            //System.out.println(v);
            if (v.rightChild != null && v.leftChild != null) {
                System.out.println("The number is already in the Set");
            }
            v.data = k;
            v.leftChild = new Node();
            v.rightChild = new Node();

        }
    }

    public Node iterativeTreeSearch(int k) {
        Node v = root;
        while (v.leftChild != null || v.rightChild != null) {
            //System.out.println("test");
            if (k == v.data) {
                return v;
            } else if (k < v.data) {
                v = v.leftChild;
            } else {
                v = v.rightChild;
            }
        }
        return v;
    }

    public void remove(int k) {
        Node v = iterativeTreeSearch(k);
        if (v == null) {
            System.out.println("There is no number like " + k);
        }
        if (v.rightChild == null && v.leftChild != null) {
            v = v.leftChild;
        }
        if (v.rightChild != null && v.leftChild == null) {
            v = v.rightChild;
        }
        if (v.rightChild != null && v.leftChild != null) {
            Node u = v;
            v = v.leftChild;
            Node temp = v;
            while (temp.rightChild == null) {
                temp = temp.rightChild;
            }
            temp.rightChild = u.rightChild;
        }
    }

    public boolean contains(int k) {
        Node v = iterativeTreeSearch(k);
        if (v.data == null) {
            return false;
        } else {
            return true;
        }
    }

    public void inorder(Node node) {
        if (node.leftChild != null) {
            inorder(node.leftChild);
        }
        if (node.data != null) {
            System.out.print(node.data + ",");

        }
        if (node.rightChild != null) {
            inorder(node.rightChild);
        }
    }

    /*private void rebalanceAVL(Node v) {
        v.height =
    }*/

}


class Node {
    Integer data;
    Node leftChild;
    Node rightChild;
    //int height;

    public Node(int k) {
        this.data = k;
        this.leftChild = new Node();
        this.rightChild = new Node();
    }

    public Node() {

    }


}