/*
package backup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;

// png files from https://marcelk.net/chess/pieces/

public class PieceButton extends JButton implements ActionListener {

    chess23.Piece piece;
    chess23.Pieces pieces;
    chess23.Coordinate coordinate;

    BufferedImage invisible = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
    ImageIcon invisibleIcon = new ImageIcon(invisible);


    public PieceButton(chess23.Piece piece) {
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

    private void processClick(HashSet<chess23.Coordinate> potentials) {

        for (int rank = 1; rank <= chess23.GUIBoard.dimension; rank++) {
            for (char file = chess23.GUIBoard.firstFile; file <= chess23.GUIBoard.lastFile; file++) {
                int processedRank = rank - chess23.GUIBoard.firstRank;
                int processedFile = file - chess23.GUIBoard.firstFile;
                chess23.Coordinate potentialCoord = new chess23.Coordinate(file, rank);
                if (potentials.contains(potentialCoord))
                    chess23.GUIBoard.board[processedRank][processedFile].setBackground(chess23.GUIBoard.intermediate);
                else
                    chess23.GUIBoard.board[processedRank][processedFile].backgroundSetter(potentialCoord);
            }
        }
    }

    public void setPieces(chess23.Pieces pieces) {
        this.pieces = pieces;
    }

    public void setCoordinate(chess23.Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void selectOrigin() {
        processClick(piece.getPotentialMoves());
        chess23.GUIBoard.increaseCounter();
        chess23.GUIBoard.setMovingPiece(piece);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (coordinate != null && chess23.Coordinate.inBoard(coordinate)) {
            if (chess23.GUIBoard.getCounter() == 0) {
                if (piece != null && piece.getColour() == chess23.GUIBoard.getTurn()) {
                    selectOrigin();

                }
            } else {
                chess23.Piece movingPiece = chess23.GUIBoard.getMovingPiece();
                if (movingPiece.isValidMove(coordinate, chess23.GUIBoard.getTurn())) {
                    pieces.makeMove(coordinate, movingPiece);
                    new chess23.GUIBoard(pieces);
                    chess23.GUIBoard.clearCounter();
                    chess23.GUIBoard.setTurn();
                } else {
                    if (piece != null && piece.getColour() == chess23.GUIBoard.getTurn()) {
                        selectOrigin();
                    }
                }
            }


            */
/*if (backup.chess23.GUIBoard.getOrigin() != null && backup.chess23.GUIBoard.getDestination() != null) {
                System.out.println(backup.chess23.GUIBoard.getOrigin().toString() + " is origin.");
                System.out.println(backup.chess23.GUIBoard.getDestination().toString() + " is destination.");
                System.out.println(backup.chess23.GUIBoard.getCounter() + " clicks.");
            }*//*


        }
    }
}
*/
