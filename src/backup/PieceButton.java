/*
package backup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;

// png files from https://marcelk.net/chess/pieces/

public class PieceButton extends JButton implements ActionListener {

    Piece piece;
    Pieces pieces;
    Coordinate coordinate;

    BufferedImage invisible = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
    ImageIcon invisibleIcon = new ImageIcon(invisible);


    public PieceButton(Piece piece) {
        this.piece = piece;

        if (piece != null)
            setIcon(piece.getImageIcon());
        else
            setIcon(invisibleIcon);

        setSize(GUITile.tileSize, GUITile.tileSize);
        setVisible(true);
        setOpaque(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setVisible(true);
        addActionListener(this);
    }

    private void processClick(HashSet<Coordinate> potentials) {

        for (int rank = 1; rank <= GUIBoard.dimension; rank++) {
            for (char file = GUIBoard.firstFile; file <= GUIBoard.lastFile; file++) {
                int processedRank = rank - GUIBoard.firstRank;
                int processedFile = file - GUIBoard.firstFile;
                Coordinate potentialCoord = new Coordinate(file, rank);
                if (potentials.contains(potentialCoord))
                    GUIBoard.board[processedRank][processedFile].setBackground(GUIBoard.intermediate);
                else
                    GUIBoard.board[processedRank][processedFile].backgroundSetter(potentialCoord);
            }
        }
    }

    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void selectOrigin() {
        processClick(piece.getPotentialMoves());
        GUIBoard.increaseCounter();
        GUIBoard.setMovingPiece(piece);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (coordinate != null && Coordinate.inBoard(coordinate)) {
            if (GUIBoard.getCounter() == 0) {
                if (piece != null && piece.getColour() == GUIBoard.getTurn()) {
                    selectOrigin();

                }
            } else {
                Piece movingPiece = GUIBoard.getMovingPiece();
                if (movingPiece.isValidMove(coordinate, GUIBoard.getTurn())) {
                    pieces.makeMove(coordinate, movingPiece);
                    new GUIBoard(pieces);
                    GUIBoard.clearCounter();
                    GUIBoard.setTurn();
                } else {
                    if (piece != null && piece.getColour() == GUIBoard.getTurn()) {
                        selectOrigin();
                    }
                }
            }


            */
/*if (backup.GUIBoard.getOrigin() != null && backup.GUIBoard.getDestination() != null) {
                System.out.println(backup.GUIBoard.getOrigin().toString() + " is origin.");
                System.out.println(backup.GUIBoard.getDestination().toString() + " is destination.");
                System.out.println(backup.GUIBoard.getCounter() + " clicks.");
            }*//*


        }
    }
}
*/
