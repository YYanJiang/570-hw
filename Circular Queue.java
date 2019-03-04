package com.company;
import java.util.*;



public class Main {
    static int front=0,rear=0;
    static String[] Q=new String[12];
    static String value;


    public static void main(String[] args) {

        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter a value or input exit to print all value: ");
            value = sc.nextLine();
            enqueue(value);

            if ("quit".equals(value.toLowerCase())) {
                dequeue();
                break;
            }
        }



    }






    public static void enqueue(String value){
        if ("quit".equals(value.toLowerCase())){}
        else{
            Q[rear]=value;
            rear=(rear+1)%12;
        }

    }

    public static void dequeue() {

            for(int i=0; i<12; i++) {
                System.out.println(" " + Q[i]);
            }
            for(int x=0;x<12;x++){
                Q[x]=null;
            }
    }















}
