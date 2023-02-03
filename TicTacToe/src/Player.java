public class Player {
    private static int number = 0;
    protected int playerNum;
    protected String name;

    // CONSTRUCTOR
    public Player() {
        Player.number++;
        this.playerNum = Player.number;
    }

    // GAME METHODS
    public boolean writeTable(int row, int col) {
        if (Main.table[row - 1][col - 1] != 0) {
            return false;
        }
        Main.table[row - 1][col - 1] = this.playerNum;
        return true;
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    // SETTERS
    protected void setName(String name) {
        this.name = name;
    }
}
