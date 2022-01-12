package chess23;

public class Game {

    private final PLAYER playerWhite;
    private final PLAYER playerBlack;
    private final Pieces pieces;

    public Game(PLAYER playerWhite, PLAYER playerBlack, boolean isGui) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        this.pieces = new Pieces();
        this.pieces.setGUIGame(isGui);

        if (isGui) {
            new GuiBoard(this.pieces);
        }
        else {
            new CliBoard(this.pieces);
        }
    }

}
