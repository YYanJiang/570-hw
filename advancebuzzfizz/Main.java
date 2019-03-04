package com.company;


public class Main {

    public static void main(String[] args) {

        int start=10,end=250;
        int region = end - start + 1;
        int[] arr= new int[region];

        for (int i = 0; i < region; i++) {
            arr[i] = start + i;
        }


        buzzFizz(arr);

    }

    public static void buzzFizz(int[] arr) {

        int length=arr.length;

        for (int i = 0; i < length; i++) {

            if (arr[i] % 5 == 0 && arr[i] % 3 == 0) {
                System.out.print("BuzzFizz\n");
            } else {
                if (arr[i] % 3 == 0) {
                    System.out.print("Buzz");
                } else if (arr[i] % 5 == 0) {
                    System.out.print("Fizz");
                } else {
                    System.out.print(arr[i]);
                }
                System.out.println();
            }


        }
    }
}





