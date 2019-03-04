package org.dm;



        import java.io.*;
        import java.util.*;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class Huffman {
    static HashMap<Character, String> codeTable = new HashMap<>();
    //static String string = "abbee,./?[[{dd\njj\tjjiilll\nzzzwww\rwww";
    static String str;
    static Node[] arrNode;
    static int size;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String inputPath;
        System.out.println("Please enter the infile path:");
        inputPath = sc.next();

        //writeInfile(string);
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


        File outfile = new File(outPath);
        FileWriter fw = new FileWriter(outfile);
//        fw.write(string);
//        fw.flush();
//        fw.close();
        Huff[] huf = bubbleSort(fw);
        size = huf.length;
        arrNode = new Node[huf.length];
        int k = 0;
        for (Huff temp : huf
                ) {
            arrNode[k] = new Node();
            arrNode[k].setKey(temp.key);
            arrNode[k].setValue(temp.value);
            k++;
        }

//        while (!preQue.isEmpty()) {
//            Huff temp = preQue.remove();
//

//            System.out.println(arrNode[i]);
//        }

        //Node[] arr02 = new Node[size];

        System.out.println(Arrays.toString(arrNode));
        for (int i = 0; i < arrNode.length - 1; i++) {
            Node node01 = getMin();
            Node node02 = getMin();
//            node01 = PriorityQueue.getMin();
//            node02 = PriorityQueue.getMin();
            //System.out.println(ch01);
            Node node03 = new Node();
            //node03.setValue(' ');
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
            node03.setHeight((node01.getHeight() > node02.getHeight() ? node01.getHeight() : node02.getHeight()) + 1);
            add(node03);
            //System.out.println(print());
        }

        Node root = getMin();
        coding(root, "");

        /**
         * test coding()
         */
        char[] charArr = str.toCharArray();
        int count = 0;
        for (char ss : charArr
                ) {
            count += codeTable.get(ss).length();
            System.out.println(codeTable.get(ss));
        }
        String huffmanCodes = "\nSymbol Huffman Codes:\n";
        for (Huff temp : huf
                ) {
            char key = temp.value;
            String value = codeTable.get(key);
            huffmanCodes += key + ",\t" + value + "\n";
        }
//        Iterator iter = codeTable.entrySet().iterator();
//        while (iter.hasNext()) {
//            HashMap.Entry entry = (HashMap.Entry) iter.next();
//            char key = (Character) entry.getKey();
//            String value = (String) entry.getValue();
//            huffmanCodes += key + ",\t" + value +"\n";
//        }
        fw.write(huffmanCodes);
        fw.flush();
        fw.write("\nTotal Bits: " + count);
        fw.flush();
        fw.close();

    }

    /**
     * 需要把 codeTable 也写出到文件
     *
     * @param node
     * @param code
     */
    public static void coding(Node node, String code) {
        if (node.getLeftChild() == null && node.getRightChild() == null) {
            codeTable.put(node.getValue(), code);
            System.out.println(node.getValue() + ":" + code);
        } else {
            coding(node.getLeftChild(), code + "0");
            coding(node.getRightChild(), code + "1");
        }
    }


    public static Huff[] bubbleSort(FileWriter fw) throws IOException {

        Huff huff[] = new Huff[62];
        //String[] str = new String[]{"a","a","a","b","a","b","2","2","0","A","A","A","B","c","D","2","d","d"};

        //Node[] prequeceyArray = new Node[62];


        //value赋值
        for (int i = 0; i < 10; i++) {
            huff[i] = new Huff();
            huff[i].value = (Character) (char) (48 + i);
            //huff[i]= new Huff((char) (48+i),0);

        }

        for (int i = 10; i < 36; i++) {
            huff[i] = new Huff();
            //this.key[i] = String.valueOf(55+i);
            //huff[i]= new Huff((char) (55+i),0);
            huff[i].value = (Character) (char) (55 + i);
        }

        for (int i = 36; i < 62; i++) {
            huff[i] = new Huff();
            //this.key[i] = String.valueOf(61+i);
            //huff[i] = new Huff((char) (61+i), 0);
            huff[i].value = (Character) (char) (61 + i);
        }


        //频率统计
        char[] ss = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < huff.length; j++)
                if ((ss[i]) == huff[j].value)
                    huff[j].key++;
        }


        //排序
        int bottom = 0;// 索引开始位置
        int top = huff.length - 1;// 索引结束位置
        while (bottom != top) {
            for (int i = bottom; i < top; i++) {
                if (huff[i].key < huff[i + 1].key) {
                    Huff p = huff[i + 1];
                    huff[i + 1] = huff[i];
                    huff[i] = p;
                }
            }
            top--;
        }


        //统计非0的字符
        int l = 0;
        for (int i = 0; i < 62; i++) {
            if (huff[i].key == 0) {
                l = i;
                break;
            }
        }
        /**
         * 最后要输出huf，字符出现频率
         */
        Huff[] huf = new Huff[l];
        LinkedList<Huff> preQue = new LinkedList<>();
        for (int i = 0; i < l; i++) {
            preQue.add(huff[i]);
            huf[i] = new Huff();
            huf[i].value = huff[i].value;
            huf[i].key = huff[i].key / str.length();
        }

        //写出频率表
        String frequencyTable = "Symbol\tfrequency: \n";
        for (Huff temp : huf
                ) {
            frequencyTable += temp.value + ",\t" + temp.key * 100 + "%\n";
        }
        fw.write(frequencyTable);
        fw.flush();


        return huf;

    }


    public static Node getMin() {

        Node chr = arrNode[size - 1];
        arrNode[size - 1] = null;
        //System.out.println(chr);
//        arr01[size - 1].value = -1;
//        arr01[size - 1].key = '!';
        size--;
        //System.out.println("size:"+ size);
        return chr;
    }

    public static void add(Node newNode) {
        if (size == 0 || newNode.key < arrNode[size - 1].key) {
            arrNode[size] = newNode;
            size++;
        } else {
            for (int i = 0; i < size; i++) {
                /**
                 * 判断条件，key值，两个节点的深度
                 */
                if (arrNode[i].key < newNode.key) {
                    System.arraycopy(arrNode, i, arrNode, i + 1, size - i);
                    arrNode[i] = newNode;
                    size++;
                    break;
                } else if (arrNode[i].key == newNode.key) {
                    if (arrNode[i].getHeight() < newNode.getHeight()) {
                        for (int j = size; j > i; j--) {
                            arrNode[j] = arrNode[j - 1];
                        }
                        arrNode[i] = newNode;
                        size++;
                        break;
                    }
                }

            }
        }


    }

    public static String print() {
        return Arrays.toString(arrNode);
    }

    static String read(String pathname) {// read the file data
        String tt = "";
        String test = new String();
        File file = new File(pathname);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //read all the data
            while ((tt = br.readLine()) != null) {
                test += tt;
            }

            System.out.println(" " + test);  //print the data which were read
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String delete_sym = test.replaceAll("\\p{Punct}", ""); //delete the symbol

        Pattern p = Pattern.compile("\\s*|\t|\r|\n"); //delete "enter", "space" etc.
        Matcher m = p.matcher(delete_sym);
        String delete_space = m.replaceAll("");
        System.out.println(delete_space);
        return delete_space;

    }

    static void writeInfile(String string) {
        try {
            FileWriter fw = new FileWriter("infile.dat");
            fw.write(string);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static void writeOutfile(String string, boolean isEnd) {
//        try {
//            FileWriter fw = new FileWriter("outfile.dat");
//            fw.write(string);
//            fw.flush();
//            if (isEnd == true) {
//                fw.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

class Huff {

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
    char value;
    int height = 0;
    Node leftChild;
    Node rightChild;

    public Node() {

    }

    public Node(int key, char value) {
        this.key = key;
        this.value = value;
    }

    public double getKey() {
        return key;
    }

    public void setKey(double key) {
        this.key = key;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

