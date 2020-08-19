/*
package backup;

import javax.swing.*;
import java.awt.*;

public class BGUI extends JFrame {

    private final int panelSize = 640;
    public final static int squareSize = 88;
    public final static int dimension = 8;
    JButton help;

    public BGUI() {

        this.setTitle("CHESS23");
        this.setSize(1286,829);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBackground(Color.gray);

        Board board = new Board();
        */
/*ImageIcon i = new ImageIcon("BBishop.png");
        JButton b = new JButton(i);
        b.setBounds(300,700,squareSize,squareSize);
        b.setVisible(true);
        b.setOpaque(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        board.add(b);*//*

        this.setContentPane(board);
        this.setVisible(true);

    }

    public class Board extends JPanel {

        public Board() {
            setLayout(new GridLayout());
            setBackground(Color.gray);
        }

        @Override
        public void setSize(Dimension d) {
            super.setSize(d);
        }

        @Override
        protected void paintComponent(Graphics g) {
            int dimRank = BOARD.FIRST_RANK.getRankVal();
            int horSeparation = 90;
            int verSeparation = 60;
            char fileChar = (char) ('a'- 1);
            Color brown = new Color(150,75,0); //brown #964B00
            Color pastel = new Color(255,222,173); //navajorwhite #FFDEAD

            Pieces pieces = new Pieces();

            for (int rank = dimension; rank >= dimRank; rank--) {
                g.setColor(Color.black);
                g.setFont(new Font("TimesRoman", Font.BOLD, 23));
                g.drawString(String.valueOf(rank), horSeparation-25, (int) (verSeparation*1.85 + squareSize * (dimension-rank)));
                for (int file = 1; file <= dimension; file++) {
                    char fileSquare = (char) (fileChar + file);
                    if ((rank+file) % 2 == 0) {
                        g.setColor(pastel);
                    }
                    else {
                        g.setColor(brown);
                    }
                    g.fillRect((horSeparation + squareSize * (file - 1)), verSeparation + (squareSize * (dimension-rank)), squareSize, squareSize);
                    Coordinate square = new Coordinate(fileSquare,rank);
                    if (pieces.getPieces().get(square) != null) {
                        Piece piece = pieces.getPiece(square);
                        int x = horSeparation + squareSize * (file - 1);
                        int y = verSeparation + squareSize * (dimension-rank);
                        add(new backup.PieceButton(piece,x,y));
                    }

                    if (rank == dimension) {
                        g.setColor(Color.black);
                        g.setFont(new Font("TimesRoman", Font.BOLD, 23));
                        g.drawString(Character.toString('a'+(file-1)), (int) (horSeparation*1.4 + squareSize * (file - 1)), verSeparation + 25 +(squareSize * dimension) );
                    }

                }
            }
        }
    }

}
*/
