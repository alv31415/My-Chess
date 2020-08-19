/*
package backup;

import javax.swing.*;

public class GUITile extends JPanel {

    public final static int tileSize = 88;
    private Piece piece;
    private PieceButton button;
    private Coordinate coordinate;

    public GUITile (Coordinate coordinate, Pieces pieces) {

        this.coordinate = coordinate;

        backgroundSetter(coordinate);

        if (pieces.getPieces().get(coordinate) == null) {
            piece = null;
        }
        else {
            piece = pieces.getPiece(coordinate);
        }
        button = new PieceButton(piece);

        button.setPieces(pieces);
        button.setCoordinate(coordinate);
    }

    public void backgroundSetter (Coordinate coordinate) {
        int signature = coordinate.getFile() - GUIBoard.charFile + coordinate.getRank();
        if (signature % 2 == 0)
            setBackground(GUIBoard.brown);
        else
            setBackground(GUIBoard.pastel);
    }

    public Piece getPiece() {
        return piece;
    }

    public void placeButton() {
        this.add(button);
    }






}
*/
