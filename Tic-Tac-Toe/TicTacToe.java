
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
//Group Member: Abdulaziz Alhomaidhi, Weiran Xian, Xi Zhang
public class TicTacToe {

    
	// to print the board
    public static void printBoard(char board[][]) {
        System.out.print("   ");
        for (int col = 1; col <= board[0].length; col++) {
            if (col < 10) {
                System.out.print(" " + col + " ");
            } else if (col < 100) {
                System.out.print(" " + col);
            } else {
                System.out.print(col);
            }
            System.out.print(" ");
        }
        System.out.println();
        for (int row = 1; row <= board.length; row++) {
            if (row < 10) {
                System.out.print(" " + row + " ");
            } else if (row < 100) {
                System.out.print(" " + row);
            } else {
                System.out.print(row);
            }
            for (int col = 0; col < board[0].length - 1; col++) {
                System.out.print(" " + board[row - 1][col] + " |");
            }
            System.out.print(" " + board[row - 1][board[0].length - 1] + " ");
            System.out.println();
            // print the boundary line
            //** make sure not print ---+ in the boundary of the board
            if (row != board.length) {
                System.out.print("   ");
                for (int col = 0; col < board[0].length - 1; col++) {
                    System.out.print("---+");
                }
                System.out.println("---");
            }
        }
    }
    // check if the game is tie: if the board have no spot that is empty 
    // but there is still no winner
    // that means it is a tie game.
    public static boolean isTie(char board[][]) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    //check if there is any player who win the game
    public static boolean checkWin(char board[][], int row, int col,
            int winCount) {
        int count;
        char ch = board[row][col];
        // check row
        count = 0;
        int r = row - 1;
        while (r >= 0) {
            if (board[r][col] == ch) {
                count++;
            }
            r--;
        }
        r = row;
        while (r < board.length) {
            if (board[r][col] == ch) {
                count++;
            }
            r++;
        }
        if (count == winCount) {
            return true;
        }
        // check column
        count = 0;
        int c = col - 1;
        while (c >= 0) {
            if (board[row][c] == ch) {
                count++;
            }
            c--;
        }
        c = col;
        while (c < board[0].length) {
            if (board[row][c] == ch) {
                count++;
            }
            c++;
        }
        if (count == winCount) {
            return true;
        }
        // check diagonal
        count = 0;
        r = row - 1;
        c = col - 1;
        while (r >= 0 && c >= 0) {
            if (board[r][c] == ch) {
                count++;
            }
            r--;
            c--;
        }
        r = row;
        c = col;
        while (r < board.length && c < board[0].length) {
            if (board[r][c] == ch) {
                count++;
            }
            r++;
            c++;
        }
        if (count == winCount) {
            return true;
        }
        // check anti diagonal
        count = 0;
        r = row - 1;
        c = col + 1;
        while (r >= 0 && c < board[0].length) {
            if (board[r][c] == ch) {
                count++;
            }
            r--;
            c++;
        }
        r = row;
        c = col;
        while (r < board.length && c >= 0) {
            if (board[r][c] == ch) {
                count++;
            }
            r++;
            c--;
        }
        if (count == winCount) {
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        
                char symbols[] = { 'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'Y', 'Z' };
        char board[][] = null;
        int playerCount = 0;
        int boardSize = 0;
        int winCount = 0;
        int currentPlayer = 0;

        Scanner scanner = new Scanner(System.in);
        String answer;
        System.out.print("Could you like to resume a saved game? (Y/N): ");
        answer = scanner.nextLine();
        // read game from file
        if (answer.equals("y")|| answer.equals("Y")) {
            System.out.print("Please input the filename: ");
            String filename = scanner.nextLine();
            try {
                Scanner fileScanner = new Scanner(new File(filename)); 
                playerCount = Integer.valueOf(fileScanner.nextLine());
                currentPlayer = Integer.valueOf(fileScanner.nextLine());
                boardSize = Integer.valueOf(fileScanner.nextLine());
                winCount = Integer.valueOf(fileScanner.nextLine());
                board = new char[boardSize][boardSize];
                for (int row = 0; row < boardSize; row++) {
                    String line = fileScanner.nextLine();
                    for (int column = 0; column < boardSize; column++) {
                        board[row][column] = line.charAt(column);
                    }
                }
                fileScanner.close();
            } catch (Exception e) {
                System.out.println("File not exist or invalid file.");
                System.exit(0);
            }
        // new game 
        } else if (answer.equals("n")|| answer.equals("N")) {
            // Invalidation check
            do{
                System.out.print("How many players are playing? (1-26): ");
                playerCount = Integer.valueOf(scanner.nextLine());
            
            }while(playerCount < 1 || playerCount > 26);

            do{
                System.out.print("How large the board? (1-999): ");
                boardSize = Integer.valueOf(scanner.nextLine());
            } while(boardSize < 1 || boardSize > 999);

            do{
                System.out.print("What the win sequence count should be? (1-999). Please make sure that the"+
                    "winning sequence is smaller or equal to the board size: ");
                winCount = Integer.valueOf(scanner.nextLine());
            }while(winCount < 1 || winCount > boardSize);

            board = new char[boardSize][boardSize];
            for (int row = 0; row < boardSize; row++) {
                for (int column = 0; column < boardSize; column++) {
                    board[row][column] = ' ';
                }
            }
        } else {
            System.out.println("Invalid Inpute");
            System.exit(0);
        }
        
        boolean hasWinner = false;
        while (isTie(board) == false && hasWinner == false) {
            printBoard(board);
            System.out.println("Current player is Player" + (currentPlayer + 1)
                    + " with symbols " + symbols[currentPlayer]);
            System.out.print(
                    "Please input (row, column), or s to save, q to quit: ");
            String input = scanner.nextLine();
            // quit
            if (input.toUpperCase().startsWith("Q")) {
                System.exit(0);
            // save
            } else if (input.toUpperCase().startsWith("S")) {
                System.out.print("Please input the filename to save: ");
                String filename = scanner.nextLine();
                try {
                    PrintWriter writer = new PrintWriter(filename);
                    writer.println(playerCount);
                    writer.println(currentPlayer);
                    writer.println(boardSize);
                    writer.println(winCount);
                    for (int row = 0; row < board.length; row++) {
                        for (int col = 0; col < board[0].length; col++) {
                            writer.print(board[row][col]);
                        }
                        writer.println();
                    }
                    writer.close();
                } catch (Exception e) {
                    System.out.println("Error when create file.");
                }
                System.exit(0);
            // enter row and column for choosing a spot in the board
            } else {
                int row = Integer.valueOf(input.split(" ")[0]); //** split the input with " "
                int col = Integer.valueOf(input.split(" ")[1]);
                if (row < 1 || row > boardSize || col < 1 || col > boardSize) {
                    System.out.println("Invalid input.");
                } else if (board[row - 1][col - 1] != ' ') {
                    System.out.println("Invalid input, cell is not empty.");
                } else {
                    board[row - 1][col - 1] = symbols[currentPlayer];// first col and row are numbers
                    hasWinner = checkWin(board, row - 1, col - 1, winCount);
                    if (!hasWinner) {
                        currentPlayer = (currentPlayer + 1) % playerCount;
                    }
                }
            }
        }
        printBoard(board);
        if (hasWinner) {
            System.out.println("Winner is player" + (currentPlayer + 1)
                    + " with symbols " + symbols[currentPlayer]);
        } else {
            System.out.println("Tie occurs.");
        }
    }
    }
    

