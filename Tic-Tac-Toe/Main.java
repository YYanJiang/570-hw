package com.company;
import java.util.Scanner;

public class Main {

    public class Board {
        char[][] chess;

        private void initBoard(int size) {
            chess = new char[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    chess[i][j] = ' ';
                }
            }
            //chess[3][5] = 'a';
        }

        public void draw(int size) {
            initBoard(size);
            System.out.print("    ");
            for (int i = 1; i <= size; i++) {
                System.out.print(i + " ");
                if (i < 10) {
                    System.out.print("  ");
                } else if (i > 9 && i < 99) {
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
                //System.out.print(" ");
                if (size > 99) {
//                    if (rowNum / 100 == 0 && rowNum / 10 == 0) {
//                        System.out.print("  ");
//                    }
//                    if (rowNum / 100 == 0 && rowNum / 10 > 0) {
//                        System.out.print(" ");
//                    }
                    System.out.print("   ");
                }
                if (size < 100 && size > 9) {
                    System.out.print("  ");

                }
                for (int j = 1; j < size; j++) {
                    System.out.print("---+");
                }
                System.out.println("---");
                //}
            }

        }
    }

    public static void main(String[] args) {




        int person, board=0, win ;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a number of how many person want to play this game (max:26): ");

        person = scanner.nextInt();
        if(person<2||person>26) {
            System.out.println("\nPlease enter a number between 2 and 26!");
        }
        else{
            System.out.println("Please enter a number of the board (max:999): ");
            board = scanner.nextInt();
            if(board>999||board<1){
                System.out.println("\nPlease enter a number between 1 and 999!");
            }
            else {
                System.out.println("Please enter a number of winning sequence count : ");
                win = scanner.nextInt();
                if(person*win>board*board){
                    System.out.println("\nThis is impossible to win by anyone!");
                }
                else {
                    System.out.println("\n\n" + person + " people are going to play this game. \nThe board is " + board + ".\nThe winning sequence count will be " + win + ".\n");
                }
            }
        }


        Board board1 = new Board();
        board1.draw(board);





    }
}

