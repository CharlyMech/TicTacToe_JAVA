import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    // ATTRIBUTES
    protected static int[][] table = new int[3][3];
    protected static Scanner s = new Scanner(System.in);
    protected static int mode;
    protected static Player j1 = new Player();
    protected static Player j2 = new Player();
    private static int turnos = 0;

    // METHODS
    static void displayTable() {
        for (int row[] : table) {
            System.out.println("\u001B[36m----------");
            System.out.print("|");
            for (int col : row) {
                if (col == 0) {
                    System.out.print("  |");
                } else if (col == 1) {
                    System.out.print("\u001B[33m X \u001B[36m|");
                } else if (col == 2) {
                    System.out.print("\u001B[31m O \u001B[36m|");
                }
            }
            System.out.println();
        }
        System.out.println("----------\u001B[0m");
    }

    static boolean playerTurn(Player p) {
        int[] turn;
        while (true) {
            displayTable();
            turn = new int[2];
            System.out.println("- Player " + p.playerNum + " turn -");
            System.out.print("Position (X): ");
            String strTurn = s.next();
            if (strTurn.length() > 2 || strTurn.length() < 2) {
                System.out.println("Introduced value not valid, try again");
                continue;
            }
            turn[0] = Integer.parseInt(strTurn.split("")[0]);
            turn[1] = Integer.parseInt(strTurn.split("")[1]);

            if (turn[0] < 1 || turn[0] > 3 || turn[1] < 1 || turn[1] > 3) {
                System.out.println("Introduced value not valid, try again");
                continue;
            }

            if (!p.writeTable(turn[0], turn[1])) {
                System.out.println("The position is already taken!");
                continue;
            }

            break;
        }

        if (checkWin(turn[0] - 1, turn[1] - 1, p.playerNum)) {
            displayTable();
            System.out.println(p.name + " WINS! Try again ;)");
            return true;
        }

        turnos++;
        if (turnos == 9) {
            turnos = 0;
            clearTable();
        }
        return false;
    }

    static void machineTurn() throws InterruptedException {
        //        displayTable();
        int items = 0;
        int[][] freeSpacesIndexes = new int[9 - turnos][2];
        for (int r = 0; r < table.length; r++) {
            for (int c = 0; c < table[r].length; c++) {
                if (table[r][c] == 0) {
                    freeSpacesIndexes[items][0] = r + 1;
                    freeSpacesIndexes[items][1] = c + 1;
                    items++;
                }
            }
        }
        int[] randomChoice = new int[2];
        if (turnos < 9) {
            randomChoice = freeSpacesIndexes[(int) (Math.random() * (freeSpacesIndexes.length))];
        } else {
            turnos = 0;
            clearTable();
            displayTable();
        }
        turnos++;
        j2.writeTable(randomChoice[0], randomChoice[1]);
        System.out.println("Machine has selected position: " + randomChoice[0] + " " + randomChoice[1]);
        // Stop 1 second
        TimeUnit.SECONDS.sleep(1);
        if (checkWin(randomChoice[0] - 1, randomChoice[1] - 1, j2.playerNum)) {
            displayTable();
            System.out.println(j2.name + " WINS! Try again ;)");
        }
    }

    static void clearTable() {
        for (int[] row : table) {
            row[0] = 0;
            row[1] = 0;
            row[2] = 0;
        }
    }

    private static boolean checkWin(int row, int col, int playerNum) {
        // Be careful that is table row&col, not user input
        switch (row) {
            case 0:
                switch (col) {
                    // ROW 0 COL 0
                    case 0:
                        // Horizontal win:
                        if (table[row][col + 1] == playerNum && table[row][col + 2] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row + 1][col] == playerNum && table[row + 2][col] == playerNum) {
                            return true;
                        }
                        // Diagonal win:
                        else if (table[row + 1][col + 1] == playerNum && table[row + 2][col + 2] == playerNum) {
                            return true;
                        }
                        break;
                    // ROW 0 COL 1
                    case 1:
                        // Horizontal win:
                        if (table[row][col + 1] == playerNum && table[row][col - 1] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row + 1][col] == playerNum && table[row + 2][col] == playerNum) {
                            return true;
                        }
                        break;
                    // ROW 0 COL 2
                    case 2:
                        // Horizontal win:
                        if (table[row][col - 1] == playerNum && table[row][col - 2] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row + 1][col] == playerNum && table[row + 2][col] == playerNum) {
                            return true;
                        }
                        // Diagonal win:
                        else if (table[row + 1][col - 1] == playerNum && table[row + 2][col - 2] == playerNum) {
                            return true;
                        }
                        break;
                }
                break;
            case 1:
                switch (col) {
                    // ROW 1 COL 0
                    case 0:
                        // Horizontal win:
                        if (table[row][col + 1] == playerNum && table[row][col + 2] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row + 1][col] == playerNum && table[row - 1][col] == playerNum) {
                            return true;
                        }
                        break;
                    // ROW 1 COL 1
                    case 1:
                        // Horizontal win:
                        if (table[row][col + 1] == playerNum && table[row][col - 1] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row + 1][col] == playerNum && table[row - 1][col] == playerNum) {
                            return true;
                        }
                        // Diagonal win 1:
                        else if (table[row - 1][col + 1] == playerNum && table[row + 1][col - 1] == playerNum) {
                            return true;
                        }
                        // Diagonal win 2:
                        else if (table[row - 1][col - 1] == playerNum && table[row + 1][col + 1] == playerNum) {
                            return true;
                        }
                        break;
                    // ROW 0 COL 2
                    case 2:
                        // Horizontal win:
                        if (table[row][col - 1] == playerNum && table[row][col - 2] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row - 1][col] == playerNum && table[row + 1][col] == playerNum) {
                            return true;
                        }
                        break;
                }
                break;
            case 2:
                switch (col) {
                    // ROW 2 COL 0
                    case 0:
                        // Horizontal win:
                        if (table[row][col + 1] == playerNum && table[row][col + 2] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row - 1][col] == playerNum && table[row - 2][col] == playerNum) {
                            return true;
                        }
                        // Diagonal win:
                        else if (table[row - 1][col + 1] == playerNum && table[row - 2][col + 2] == playerNum) {
                            return true;
                        }
                        break;
                    // ROW 2 COL 1
                    case 1:
                        // Horizontal win:
                        if (table[row][col + 1] == playerNum && table[row][col - 1] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row - 1][col] == playerNum && table[row - 2][col] == playerNum) {
                            return true;
                        }
                        break;
                    // ROW 2 COL 2
                    case 2:
                        // Horizontal win:
                        if (table[row][col - 1] == playerNum && table[row][col - 2] == playerNum) {
                            return true;
                        }
                        // Vertical win:
                        else if (table[row - 1][col] == playerNum && table[row - 2][col] == playerNum) {
                            return true;
                        }
                        // Diagonal win:
                        else if (table[row - 1][col - 1] == playerNum && table[row - 2][col - 2] == playerNum) {
                            return true;
                        }
                        break;
                }
                break;

        }

        return false;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\u001B[34m------------------------\u001B[0m");
            System.out.println("\u001B[34m| WELCOME TO TicTacToe |\u001B[0m");
            System.out.println("\u001B[34m------------------------\u001B[0m");

            System.out.println("\u001B[32m| How to play |\u001B[0m");
            System.out.println("\t- Every turn the player will introduce the table position to add his symbol");
            System.out.println("\t- The position has to be written as this: rowcolumn (13, for row 1 column 3)");
            System.out.println("\t- Both rows and colums numbers are from 1 to 3");
            System.out.println();

            // Mode select
            do {
                System.out.println("Select a play mode:\n\t1) Single Player\n\t2) 2 Players");
                mode = s.nextInt();
                if (mode != 1 & mode != 2) {
                    System.out.println("Introduce a valid number!!");
                } else {
                    break;
                }
            } while (true);

            // Evaluate mode -> we made sure that mode will be 1 or 2, so no default case is needed
            switch (mode) {
                case 1:
                    System.out.print("Introduce Player 1 name: ");
                    j1.setName(s.next());
                    j2.setName("Machine");
                    break;
                case 2:
                    System.out.print("Introduce Player 1 (X) name: ");
                    j1.setName(s.next());
                    System.out.print("Introduce Player 2 (O) name: ");
                    j2.setName(s.next());
                    break;
            }

            // MAIN GAME LOOP
            while (true) {
                boolean t1;
                boolean t2;

                // player 1
                t1 = playerTurn(j1);

                if (t1) {
                    break;
                }

                // player 2
                if (mode == 1) {
                    try {
                        machineTurn();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    t2 = playerTurn(j2);
                    if (t2) {
                        break;
                    }
                }
            }
            boolean restartFlag = true;
            while (restartFlag) {
                System.out.println("What do you want to do now?\n\t1) Play again\n\t2) Exit game");
                int finalChoice = s.nextInt();
                switch (finalChoice) {
                    case 1:
                        clearTable();
                        turnos = 0;
                        restartFlag = false;
                        break;
                    case 2:
                        System.exit(1);
                    default:
                        System.out.println("Please choose a valid option");
                        break;
                }
            }
        }
    }
}