package org.dm;

import java.util.*;

public class TestStack {
    private static Exception ExpressionException = new Exception("Expression is wrong! ");

    public static void main(String[] args) {
        //int code = ".".charAt(0);
        //System.out.println(code);
        Scanner input = new Scanner(System.in);
        boolean flag = true;
        System.out.println("\n------------ Welcome to RPN calculator --------------\n");
        System.out.println("Note: You can use  +, -, *, /, %, POW, and ()  to write expression.\n" +
                "      (Can not use negative number!)");
        System.out.println("eg:\n    infix: (3 - (1+3)) * 5 POW 2 + 4 % 3");
        String infixStr;
        while (flag) {
            System.out.println("Please input Infix or enter \"quit\" to exit: ");
            infixStr = input.nextLine();
            if ("quit".equals(infixStr.toLowerCase())) {
                flag = false;
            } else {
                try {
                    Queue<String> infixQ = trans2Infix(infixStr);
                    if (!isRightExpression(infixQ)) {
                        System.err.println("Expression is wrong! \n");
                        continue;
                    }
                    Queue<String> postQ = convert(infixQ);

                    System.out.printf("\n    postfix: ");
                    for (String string : postQ
                            ) {
                        System.out.print(string);
                        System.out.printf(" ");
                    }
                    System.out.println("\n\n\nThe result of Calculation: " + calculate(postQ));
                } catch (Exception e) {
                    System.out.println("\n" + e + "\n");
                }
                System.out.println("-----------------------------------------------------\n");
            }
        }
        System.out.println("--------------- Exit the Calculator -------------------");

    }

    //trans String to infix Queue
    private static Queue<String> trans2Infix(String expression) throws Exception {
        Queue<String> infix = new LinkedList<>();
        String[] strings = expression.split("");
        //System.out.println(Arrays.toString(strings));
        int code;
        for (int i = 0; i < strings.length; i++) {
            String cell = "";
            code = strings[i].charAt(0);
            if (code == 80) {
                cell = strings[i] + strings[i + 1] + strings[i + 2];
                if ("POW".equals(cell)) {
                    infix.add(cell);
                    i += 2;
                } else {
                    throw ExpressionException;
                }
            } else if ((code >= 48 && code <= 57) || code == 46) {
                for (int j = i; j < strings.length; j++) {
                    code = strings[j].charAt(0);
                    if ((code >= 48 && code <= 57) || code == 46) {
                        cell = cell + strings[j];
                        i = j;
                    } else {
                        i = j - 1;
                        break;
                    }
                }
                infix.add(cell);
            } else if (code == 37 || code == 40 || code == 41 || code == 42 || code == 43 || code == 45 || code == 47) {
                infix.add(strings[i]);
            }
        }
        return infix;
    }

    private static Boolean isRightExpression(Queue<String> expression) {
        int left = 0;
        int right = 0;
        int operand = 0;
        int operator = 0;
        for (String str : expression
             ) {
            switch (str) {
                case "(" : left++; continue;
                case ")" : right++; break;
                case "+" :
                case "-" :
                case "*" :
                case "/" :
                case "%" :
                case "POW" :operator++ ;   break;
                default:
                    operand++;
                    break;
            }
//            if (isNumber(str)){
//
//            }
        }
        if (left != right) {
            return false;
        }
        if (operand != operator + 1){
            return false;
        }
        return true;
    }

    //convert infixQueue to postQueue
    private static Queue<String> convert(Queue<String> infixQ) {
        Stack<String> opStack = new Stack<>();
        Queue<String> postQ = new LinkedList<>();
        String t;
        while (!infixQ.isEmpty()) {
            t = infixQ.remove();
            if (isNumber(t)) {
                postQ.add(t);
            } else if (opStack.isEmpty()) {
                opStack.push(t);
            } else if ("(".equals(t)) {
                //push '('
                opStack.push(t);
            } else if (")".equals(t)) {
                while (!"(".equals(opStack.peek())) {
                    postQ.add(opStack.pop());
                }
                //pop '('
                opStack.pop();
            } else {
                while (!opStack.isEmpty() && !"(".equals(opStack.peek()) && priority(t) <= priority(opStack.peek())) {
                    postQ.add(opStack.pop());
                    // when the priority is bigger then other
                }
                opStack.push(t);
            }
        }
        //pop out all operator
        while (!opStack.isEmpty()) {
            postQ.add(opStack.pop());
        }
        return postQ;
    }

    //read the postfix and calculate
    private static double calculate(Queue<String> postQ) {
        Stack<Double> stack = new Stack<>();
        //Is it a empty stack ？
        while (!postQ.isEmpty()) {
            //if it's a number ,push into the stack
            if (isNumber(postQ.peek())) {
                stack.push(Double.parseDouble(postQ.remove()));
            } else {
                if (!stack.empty()) {
                    //if it's a calculating character, calculate it and push the result into the stack
                    double topNum = stack.pop();
                    double nextNum = stack.pop();
                    stack.push(count(nextNum, topNum, postQ.remove()));
                }
            }
        }
        //the top of the stack is the final result
        return stack.pop();

    }

    //Mathematical calculation of numbers
    private static double count(double x, double y, String operator) {
        double answer = 0;
        if (operator.trim().equals("+"))
            answer = x + y;
        if (operator.trim().equals("-"))
            answer = x - y;
        if (operator.trim().equals("*"))
            answer = x * y;
        if (operator.trim().equals("/"))
            answer = x / y;
        if (operator.trim().equals("%"))
            answer = x % y;
        if (operator.trim().equals("POW"))
            answer = Math.pow(x, y);
        return answer;
    }

    //decide if it's a number
    private static boolean isNumber(String s) {
        int chr = s.charAt(0);
        return chr >= 48 && chr <= 57;
    }

    private static int priority(String op) {
        //bigger number references higher priority
        if ("(".equals(op) || ")".equals(op)) {
            return 1;
        } else if ("+".equals(op) || "-".equals(op)) {
            return 2;
        } else if ("*".equals(op) || "/".equals(op) || "%".equals(op)) {
            return 3;
        } else if ("POW".equals(op)) {
            //the highest priority
            return 4;
        } else return 0;
    }

}
