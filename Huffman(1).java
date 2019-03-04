package org.dm;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huffman {
    private static HashMap<Character, String> codeTable = new HashMap<>();
    private static String str;
    private static Node[] arrNode;
    private static int size;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the infile path:");
        String inputPath = sc.next();

        //in case user not enter the filename

        String[] inArr = inputPath.split("/");
        if (!"infile.dat".equals(inArr[inArr.length - 1])) {
            inputPath = inputPath + "/infile.dat";
        }
        str = read(inputPath);
        System.out.println("Please enter the outfile path:");
        String outPath = sc.next();
        String[] outArr = outPath.split("/");
        if (!"outfile.dat".equals(outArr[outArr.length - 1])) {
            outPath = outPath + "/outfile.dat";
        }

        //ready to write outfile.dat
        File outfile = new File(outPath);
        FileWriter fw = new FileWriter(outfile);
        FreqCell[] huf = bubbleSort(fw);

        //trans array of Huff to queue of Node
        size = huf.length;
        arrNode = new Node[huf.length];
        int k = 0;
        for (FreqCell temp : huf
                ) {
            arrNode[k] = new Node();
            arrNode[k].setKey(temp.key);
            arrNode[k].setValue(temp.value);
            k++;
        }

        //build the Huffman Tree
        for (int i = 0; i < arrNode.length - 1; i++) {
            Node node01 = getMin();
            Node node02 = getMin();
            Node node03 = new Node();
            if (node01.getKey() == node02.getKey()) {
                if (node01.getHeight() < node02.getHeight()) {
                    node03.setLeftChild(node01);
                    node03.setRightChild(node02);
                } else {
                    node03.setLeftChild(node02);
                    node03.setRightChild(node01);
                }
            } else if (node01.getKey() < node02.getKey()) {
                node03.setLeftChild(node01);
                node03.setRightChild(node02);
            } else {
                node03.setLeftChild(node02);
                node03.setRightChild(node01);
            }
            node03.setKey(node01.getKey() + node02.getKey());
            //choice the max height of two child nodes
            node03.setHeight((node01.getHeight() > node02.getHeight() ? node01.getHeight() : node02.getHeight()) + 1);
            add(node03);
        }

        //generate Huffman code
        Node root = getMin();
        coding(root, "");

        //compute the total bits
        char[] charArr = str.toCharArray();
        int count = 0;
        for (char ss : charArr
                ) {
            count += codeTable.get(ss).length();
            System.out.println(codeTable.get(ss));
        }

        //generate the code table
        StringBuilder huffmanCodes = new StringBuilder("\nSymbol Huffman Codes:\n");
        Arrays.stream(huf).forEach(temp -> {
            char key = temp.value;
            String value = codeTable.get(key);
            huffmanCodes.append(key).append(",\t").append(value).append("\n");
        });

        fw.write(huffmanCodes.toString());
        fw.flush();
        fw.write("\nTotal Bits: " + count);
        fw.flush();
        fw.close();

    }

    //generate Huffman code
    private static void coding(Node node, String code) {
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            codeTable.put(node.getValue(), code);
            System.out.println(node.getValue() + ":" + code);
        } else {
            coding(node.getLeftChild(), code + "0");
            coding(node.getRightChild(), code + "1");
        }
    }


    private static FreqCell[] bubbleSort(FileWriter fw) throws IOException {
        FreqCell huff[] = new FreqCell[62];

        //value initialization
        //number 0-9
        for (int i = 0; i < 10; i++) {
            huff[i] = new FreqCell();
            huff[i].value = (Character) (char) (48 + i);
        }
        //letter A-Z
        for (int i = 10; i < 36; i++) {
            huff[i] = new FreqCell();
            huff[i].value = (Character) (char) (55 + i);
        }
        //letter a-z
        for (int i = 36; i < 62; i++) {
            huff[i] = new FreqCell();
            huff[i].value = (Character) (char) (61 + i);
        }


        //frequency statistic
        char[] ss = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {
            for (FreqCell aHuff : huff)
                if ((ss[i]) == aHuff.value)
                    aHuff.key++;
        }


        //sort
        int bottom = 0;// begin position
        int top = huff.length - 1;// end position
        while (bottom != top) {
            for (int i = bottom; i < top; i++) {
                if (huff[i].key < huff[i + 1].key) {
                    FreqCell p = huff[i + 1];
                    huff[i + 1] = huff[i];
                    huff[i] = p;
                }
            }
            top--;
        }

        //Delete the  symbols with 0 frequency
        //count symbols except 0
        int l = 0;
        for (int i = 0; i < 62; i++) {
            if (huff[i].key == 0) {
                l = i;
                break;
            }
        }

        //Generate frequency tables
        FreqCell[] huf = new FreqCell[l];
        for (int i = 0; i < l; i++) {
            huf[i] = new FreqCell();
            huf[i].value = huff[i].value;
            huf[i].key = huff[i].key / str.length();
        }

        StringBuilder frequencyTable = new StringBuilder("Symbol\tfrequency: \n");
        for (FreqCell temp : huf
                ) {
            frequencyTable.append(temp.value).append(",\t").append(temp.key * 100).append("%\n");
        }

        //write frequency tables
        fw.write(frequencyTable.toString());
        fw.flush();
        return huf;
    }

    //get the minimize node of queue
    private static Node getMin() {
        Node chr = arrNode[size - 1];
        arrNode[size - 1] = null;
        size--;
        return chr;
    }

    //add the new node order by frequency and heights of nodes
    private static void add(Node newNode) {
        if (size == 0 || newNode.key < arrNode[size - 1].key) {
            arrNode[size] = newNode;
            size++;
        } else {
            for (int i = 0; i < size; i++) {

                //Analyzing conditions: key, height of nodes
                if (arrNode[i].key < newNode.key) {
                    System.arraycopy(arrNode, i, arrNode, i + 1, size - i);
                    arrNode[i] = newNode;
                    size++;
                    break;
                } else if (arrNode[i].key == newNode.key) {
                    if (arrNode[i].getHeight() < newNode.getHeight()) {
                        System.arraycopy(arrNode, i, arrNode, i + 1, size - i);
                        arrNode[i] = newNode;
                        size++;
                        break;
                    }
                }
            }
        }
    }

    // read the file data
    private static String read(String pathname) {
        String tt;
        StringBuilder test = new StringBuilder();
        File file = new File(pathname);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //read all the data
            while ((tt = br.readLine()) != null) {
                test.append(tt);
            }

            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //delete the symbol
        String delete_sym = test.toString().replaceAll("\\p{Punct}", "");

        //delete "enter", "space" etc.
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(delete_sym);
        return m.replaceAll("");
    }
}

class FreqCell {

    Character value;

    double key = 0;

    @Override
    public String toString() {
        return "Huff{" +
                "value=" + value +
                ", key=" + key +
                '}';
    }
}

class Node {
    double key;
    private char value;
    private int height = 0;
    private Node leftChild;
    private Node rightChild;

    double getKey() {
        return key;
    }

    void setKey(double key) {
        this.key = key;
    }

    char getValue() {
        return value;
    }

    void setValue(char value) {
        this.value = value;
    }

    int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    Node getLeftChild() {
        return leftChild;
    }

    void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    Node getRightChild() {
        return rightChild;
    }

    void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

}




import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;

public class Test {


    public static void main(String[] args) throws IOException {
        String fileContent = readFileContent("infile.txt");

        System.out.println(fileContent);
    }

    //参数string为你的文件名
    private static String readFileContent(String infile) throws IOException {
        File file = new File(infile);

        BufferedReader bf = new BufferedReader(new FileReader(file));

        String content = "";
        StringBuilder sb = new StringBuilder();

        while(content != null){
            content = bf.readLine();

            if(content == null){
                break;
            }

            sb.append(content.trim());
        }

        bf.close();
        return sb.toString();
    }
}
