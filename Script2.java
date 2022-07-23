import javax.swing.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;


public class Script2 {
    public static void main(String[] args) throws IOException{
        char[][] board = new char[10][10];
        File folder = new File("ships");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println("-----------------" + file.getName() + "-----------------");;
                board = generateBoard(file, board);
                if (validateBoard(board)) {
                    printBoard(board);
                }
                board = new char[10][10];
            }
        }
    }
    public static char[][] generateBoard(File shipFile, char[][] boardBluePrint) throws java.io.FileNotFoundException {
        Scanner sc = new Scanner(shipFile);
        int count=0;
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            count++;
        }
        sc.close();
        sc = new Scanner(shipFile);
        int[] count2array = new int[count];
        int x = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            count2array[x] = line.length();
            x++;
        }
        sc.close();
        boardBluePrint = new char[count][];
        for (int i = 0; i < count; i++) {
            boardBluePrint[i] = new char[count2array[i]];
        }
        sc = new Scanner(shipFile);
        count = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            for (int i = 0; i < line.length(); i++){
                boardBluePrint[count][i] = line.charAt(i);
            }
            count++;
        }
        sc.close();
        return boardBluePrint;
    }
    public static void printBoard(char[][] board){
        System.out.println(" 0123456789");
        for(int row = 0; row < board.length; row++){
            System.out.print((char) ('A' + row));
            for(int column = 0; column < board[row].length; column++){
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }
    public static boolean validateBoard(char[][] board) {
        boolean isValid = true;
        int[] shipCollection = {5, 4, 3, 3, 2, 67, 66, 82, 83, 68};
        if (board.length > 10){
            System.out.println("Too many rows");
            isValid = false;
        } else if (board.length < 10){
            System.out.println("Not enough rows");
            isValid = false;
        }
        if (isValid) {
            for (int row = 0; row < board.length; row++) {
                if (board[row].length > 10) {
                    System.out.println("Row " + (char) (row+65) + " too long");
                    isValid = false;
                } else if (board[row].length < 10) {
                    System.out.println("Row " + (char) (row+65) + " too short");
                    isValid = false;
                }
            }
        }
        int[][][] shipComponents = new int[5][2][];
        if (isValid) {
            int[] shipLengthChecker = new int[5];
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (board[row][col] == 'C') {
                        shipLengthChecker[0]++;
                    } else if (board[row][col] == 'B') {
                        shipLengthChecker[1]++;
                    } else if (board[row][col] == 'R') {
                        shipLengthChecker[2]++;
                    } else if (board[row][col] == 'S') {
                        shipLengthChecker[3]++;
                    } else if (board[row][col] == 'D') {
                        shipLengthChecker[4]++;
                    } else if (board[row][col] != '-') {
                        System.out.println(board[row][col] + " is an invalid symbol");
                        isValid = false;
                    }
                }
            }
            for (int shipLength = 0; shipLength < shipCollection.length/2; shipLength++) {
                shipComponents[shipLength][0] = new int[shipLengthChecker[shipLength]];
                shipComponents[shipLength][1] = new int[shipLengthChecker[shipLength]];
                if (shipCollection[shipLength] < shipLengthChecker[shipLength]) {
                    System.out.println("There are too many " + (char) (shipCollection[shipLength+5]) + "'s");
                    isValid = false;
                } else if (shipCollection[shipLength] > shipLengthChecker[shipLength]) {
                    System.out.println("There are too few " + (char) (shipCollection[shipLength+5]) + "'s");
                    isValid = false;
                }
            }
        }
        if (isValid) {
            // This section placement of each component for each ship
            // There was, an issue here
            // for ship5 and ship9
            int[] shipCounter = {0, 0, 0, 0, 0};
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    if (board[row][col] == 'C') {
                        shipComponents[0][0][shipCounter[0]] = row;
                        shipComponents[0][1][shipCounter[0]] = col;
                        shipCounter[0]++;
                    } else if (board[row][col] == 'B') {
                        shipComponents[1][0][shipCounter[1]] = row;
                        shipComponents[1][1][shipCounter[1]] = col;
                        shipCounter[1]++;
                    } else if (board[row][col] == 'R') {
                        shipComponents[2][0][shipCounter[2]] = row;
                        shipComponents[2][1][shipCounter[2]] = col;
                        shipCounter[2]++;
                    } else if (board[row][col] == 'S') {
                        shipComponents[3][0][shipCounter[3]] = row;
                        shipComponents[3][1][shipCounter[3]] = col;
                        shipCounter[3]++;
                    } else if (board[row][col] == 'D') {
                        shipComponents[4][0][shipCounter[4]] = row;
                        shipComponents[4][1][shipCounter[4]] = col;
                        shipCounter[4]++;
                    }
                }
            }
            for (int shipNumber=0; shipNumber<shipComponents.length; shipNumber++) {
                int[] shipDirection = {0,0};
                int[][] shipComponent = shipComponents[shipNumber];
                int[] lastComponent = {shipComponent[0][0], shipComponent[1][0]};
                if (lastComponent[0] > shipComponent[0][1]) {
                    shipDirection[0] = -1;
                } else if (lastComponent[0] < shipComponent[0][1]) {
                    shipDirection[0] = 1;
                }
                if (lastComponent[1] > shipComponent[1][1]) {
                    shipDirection[1] = -1;
                } else if (lastComponent[1] < shipComponent[1][1]) {
                    shipDirection[1] = 1;
                }
                for (int location = 1; location < shipComponent[0].length; location++) {
                    if (shipComponent[0][location] != lastComponent[0] + shipDirection[0] || shipComponent[1][location] != lastComponent[1] + shipDirection[1]) {
                        System.out.println((char) shipCollection[shipNumber + 5] + " is placed incorrectly");
                        isValid = false;
                    }
                    lastComponent[0] = shipComponent[0][location];
                    lastComponent[1] = shipComponent[1][location];
                }
            }
        }
        return isValid;
    }
}
