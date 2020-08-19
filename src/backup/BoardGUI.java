package backup;

import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JFrame{

    private final int dimension;
    private final int panelSize = 640;
    private final int squareSize = 88;
    private int width = getWidth();
    private int height = getHeight();

    public BoardGUI (int dimension) {

        this.dimension = dimension;

        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(panelSize,panelSize,panelSize,panelSize));
        panel.setLayout(new GridLayout(0, dimension+1));

        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chess Board");
        setBackground(Color.gray);
        pack();
        setVisible(true);
    }

    public void paint (Graphics g) {

        int horSeparation = 90;
        int verSeparation = 100;

        for (int row = 1; row <= dimension; row++) {
            g.setColor(Color.black);
            g.setFont(new Font("TimesRoman", Font.BOLD, 23));
            g.drawString(String.valueOf(9-row), horSeparation-25, (int) (verSeparation*1.5 + squareSize * (row - 1)));
            for (int col = 1; col <= dimension; col++) {
                if ((row+col) % 2 == 0) {
                    g.setColor(Color.white);
                }
                else {
                    g.setColor(Color.black);
                }
                g.fillRect((horSeparation + squareSize * (col - 1)), verSeparation + (squareSize * (row - 1)), squareSize, squareSize);

                if (row == dimension) {
                    g.setColor(Color.black);
                    g.setFont(new Font("TimesRoman", Font.BOLD, 23));
                    g.drawString(Character.toString('a'+(col-1)), (int) (horSeparation*1.4 + squareSize * (col - 1)), verSeparation + 25 +(squareSize * dimension) );
                }

            }
        }
    }

    public static void main(String[] args) {
        BoardGUI board = new BoardGUI(8);

    }
}
