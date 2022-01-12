/*
package backup;

import javax.swing.*;

public class GUITile extends JPanel {

    public final static int tileSize = 88;
    private chess23.Piece piece;
    private PieceButton button;
    private chess23.Coordinate coordinate;

    public GUITile (chess23.Coordinate coordinate, chess23.Pieces pieces) {

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

    public void backgroundSetter (chess23.Coordinate coordinate) {
        int signature = coordinate.getFile() - chess23.GUIBoard.charFile + coordinate.getRank();
        if (signature % 2 == 0)
            setBackground(chess23.GUIBoard.brown);
        else
            setBackground(chess23.GUIBoard.pastel);
    }

    public chess23.Piece getPiece() {
        return piece;
    }

    public void placeButton() {
        this.add(button);
    }






}
*/
