public class Script1 {
    public static void main(String[] args) {
        char[][][] boardCollection = new char[10][12][12];
        for (int board=0; board<boardCollection.length; board++) {
            generateBoard(boardCollection[board]);
            generateShip(boardCollection[board]);
            printBoard(boardCollection[board], board);
        }
    }
    public static void generateBoard(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                if (row == 0 || row == board.length - 1 || column == 0 || column == board[row].length - 1) {
                    board[row][column] = '*';
                } else {
                    board[row][column] = '-';
                }
            }
        }
    }
    public static void generateShip(char[][] newBoard) {
        int[] shipCollection = {5, 4, 3, 3, 2, 67, 66, 82, 83, 68};
        for (int eachShip = 0; eachShip < shipCollection.length/2; eachShip++) {
            int[][] shipInfo = {{shipCollection[eachShip],shipCollection[eachShip+5],(int)(Math.random()*10+1),(int)(Math.random()*10+1),(int)(Math.random()*4),0}, new int[shipCollection[eachShip]], new int[shipCollection[eachShip]]};
            while (shipInfo[0][5] == 0) {
                shipInfo[0][5] = 1;
                shipInfo[0][2] = (int)(Math.random()*10+1);
                shipInfo[0][3] = (int)(Math.random()*10+1);
                shipInfo[1] = new int[shipCollection[eachShip]];
                shipInfo[2] = new int[shipCollection[eachShip]];
                for (int shipLength = 0; shipLength < shipInfo[0][0]; shipLength++) {
                    if (newBoard[shipInfo[0][2]][shipInfo[0][3]] == '-') {
                        shipInfo[1][shipLength] = shipInfo[0][2];
                        shipInfo[2][shipLength] = shipInfo[0][3];
                        if (shipInfo[0][4] == 0) {
                            shipInfo[0][2]++;
                        } else if (shipInfo[0][4] == 1) {
                            shipInfo[0][2]--;
                        } else if (shipInfo[0][4] == 2) {
                            shipInfo[0][3]++;
                        } else if (shipInfo[0][4] == 3) {
                            shipInfo[0][3]--;
                        }
                    } else {
                        shipInfo[0][5] = 0;
                    }
                }
            }
            for (int shipLength = 0; shipLength < shipInfo[0][0]; shipLength++) {
                newBoard[shipInfo[1][shipLength]][shipInfo[2][shipLength]] = (char) shipInfo[0][1];
            }
        }
    }
    public static void printBoard(char[][] board, int serialNumber) {
        System.out.println("\n-----------------BOARD"+(serialNumber+1)+"-----------------\n 0123456789");
        for (int row = 1; row < board.length-1; row++) {
            System.out.print((char) (row + 64));
            for (int column = 1; column < board[row].length-1; column++) {
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }
}
