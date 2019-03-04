package org.mm;

public class Board {
    int size;
    char[][] chess;
    int step = 0;

    public Board (int size) {
        chess = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                chess[i][j] = ' ';
            }
        }
        this.size = size;
    }

    /*public void initBoard(int size){

        //chess[3][5] = 'a';
    }*/

    public void draw(int size) {
        //initBoard(size);
        System.out.print("    ");
        for (int i = 1; i <= size; i++) {
            System.out.print(i + " ");
            if (i < 10) {
                System.out.print("  ");
            } else if (i > 9 && i < 99){
                System.out.print(" ");
            }
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            //if (i % 2 == 0) {
                /*int rowNum = i / 2 + 1;*/
                System.out.print(i + 1);
                if (size > 99 && i < 9) {
                    System.out.print(" ");
                }
                if (size > 99 && i < 99) {
                    System.out.print(" ");
                }
                if (size < 99 && i < 9) {
                    System.out.print(" ");
                }
                for (int j = 0; j < size; j++) {
                    System.out.print(" " + chess[i][j] + " |");

                }
                System.out.println();
           // } else {
                System.out.print("  ");
                if (size > 99) {
//                    if (rowNum / 100 == 0 && rowNum / 10 == 0) {
//                        System.out.print("  ");
//                    }
//                    if (rowNum / 100 == 0 && rowNum / 10 > 0) {
//                        System.out.print(" ");
//                    }
                    System.out.print(" ");
                }
                for (int j = 1; j < size; j++) {
                    System.out.print("---+");
                }
                System.out.println("---+");
            //}
        }

    }

    public void play(char play, int x, int y) {
        chess[x-1][y-1] = play;
        step++;
        System.out.println("step:" + step);
    }

    public int isWin(int x, int y, char play, int winSequence) {
        int lx = x - 1;
        int ly = y - 1;
        int sequence = 0;
        int temp = 0;
        //horizontal
        while (lx > 0 && chess[lx-1][ly] == play) {
            lx--;
        }
        while (lx < size - 1 && chess[lx][ly] == play) {
            sequence++;
            lx++;
        }
        //vertical
        lx = x - 1;
        while (ly > 0 && chess[lx][ly-1] == play) {
            ly--;
        }
        while (ly < size - 1 && chess[lx][ly] == play) {
            temp++;
            ly++;
        }
        sequence = sequence > temp ? sequence : temp;
        //left-up
        temp = 0;
        ly = y - 1;
        while (ly > 0 && lx > 0 && chess[lx-1][ly-1] == play) {
            lx--;
            ly--;
        }
        while (ly < size - 1 && lx < size - 1 && chess[lx][ly] == play) {
            temp++;
            lx++;
            ly++;
        }
        sequence = sequence > temp ? sequence : temp;
        //right-up
        temp = 0;
        lx = x - 1;
        ly = y - 1;
        while (lx < size - 1 && ly > 0 && chess[lx+1][ly-1] == play) {
            lx++;
            ly--;
        }
        while (lx > 0 && lx < size - 1 && chess[lx][ly] == play) {
            temp++;
            lx--;
            ly++;
        }
        sequence = sequence > temp ? sequence : temp;
        if (sequence >= winSequence) {
            return 1;
        }
        if (step == size * size) {
            //System.out.println("tie");
            return 0;
        }
        return -1;
    }


}
