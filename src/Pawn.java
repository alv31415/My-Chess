import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/*
1) Class Constructors
2) Getters & Setters
3) Special Pawn Move Methods
4) Pawn Promotion Methods
5) Overridden Methods
*/

public class Pawn extends Piece {

    // whether the pawn has moved 2 squares forward
    private boolean hasMovedTwo = false;
    // whether the pawn can capture en passant to the left
    private boolean enPassantLeft = false;
    // whether the pawn can capture en passant to the right
    private boolean enPassantRight = false;
    // the coordinate previously occupied by the pawn
    private Coordinate previousCoordinate = new Coordinate();
    // the piece to which the pawn will be promoted
    private Piece promotedPiece;
    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    public Pawn(COLOUR colour, Coordinate OGcoord) {
        super(ID.PAWN, colour, OGcoord);
        if (getColour() == COLOUR.B)
            icon = new ImageIcon("BPawn.png");
        else if (getColour() == COLOUR.W)
            icon = new ImageIcon("WPawn.png");
    }

    public Pawn(Pawn original) {
        super(original);
    }

    //________________________________________________Getters & Setters________________________________________________

    public void setPreviousCoordinate(Coordinate previousCoordinate) {
        this.previousCoordinate = previousCoordinate;
    }

    public Coordinate getPreviousCoordinate() {
        return previousCoordinate;
    }

    public void setHasMovedTwo() {
        this.hasMovedTwo = true;
    }

    public boolean getHasMovedTwo() {
        return hasMovedTwo;
    }

    public boolean getEnPassantLeft() {
        return enPassantLeft;
    }

    public boolean getEnPassantRight() {
        return enPassantRight;
    }

    //________________________________________________Special Pawn Move Methods________________________________________________

    /**
     * Determines whether the pawn can eat to the left.
     * @param pieces the board being played in
     * @return it calculates the coordinate directly to the left front diagonal of the pawn.
     * Returns true if the coordinate is occupied by a piece of the opposite colour to the pawn moving.
     */

    private boolean canEatLeftDig(Pieces pieces) {

        int factorV;
        int factorH;

        if (getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = 1;
        } else {
            factorV = 1;
            factorH = -1;
        }

        char newFile = (char) (getFile() + factorH);
        int newRank = getRank() + factorV;
        Coordinate leftDig = new Coordinate(newFile, newRank);

        return Move.tileFull(pieces, leftDig) && Move.isNotTileColour(pieces, leftDig, getColour());
    }

    /**
     * Determines whether the pawn can eat to the right.
     * @param pieces the board being played in
     * @return it calculates the coordinate directly to the right front diagonal of the pawn.
     * Returns true if the coordinate is occupied by a piece of the opposite colour to the pawn moving.
     */

    private boolean canEatRightDig(Pieces pieces) {

        int factorV;
        int factorH;

        if (getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = -1;
        } else {
            factorV = 1;
            factorH = 1;
        }

        char newFile = (char) (getFile() + factorH);
        int newRank = getRank() + factorV;
        Coordinate rightDig = new Coordinate(newFile, newRank);

        return Move.tileFull(pieces, rightDig) && Move.isNotTileColour(pieces, rightDig, getColour());
    }

    /**
     * Determines how many squares a pawn can move forward
     * @param pieces the board being played in
     * @return an ArrayList containing the moves that a pawn can move forward.
     * 0 if frontFree has a size of 0
     * 1 if the pawn has the square in front of it free
     * 2 if the pawn is at its initial position & the squares in front of it are empty
     */

    private ArrayList<Coordinate> pawnForward(Pieces pieces) {

        ArrayList<Coordinate> potentialForward = Move.frontFree(pieces, this, 2);
        ArrayList<Coordinate> actualForward = new ArrayList<>();

        if (potentialForward.size() > 0) {

            Coordinate front1 = potentialForward.get(0);
            Coordinate front2 = Coordinate.emptyCoordinate;

            if (potentialForward.size() == 2) {
                front2 = potentialForward.get(1);
            }

            if (Move.tileFull(pieces, front1))
                return actualForward;
            else {
                actualForward.add(front1);
            }

            if (pieces.findPiece(this).equals(getOGcoord()) && front2 != Coordinate.emptyCoordinate) {
                if (Move.tileFull(pieces, front2))
                    return actualForward;
                else
                    actualForward.add(front2);
            }
        }
        return actualForward;
    }

    /**
     * Determines how many squares a pawn can capture en passant
     * @param pieces the board being played in
     * @return an ArrayList containing the moves that a pawn can move to in order to capture en passant.
     * These are added if:
     * the piece directly to the left/right contains a pawn of the opposite colour (called potentialPawn)
     * this potentialPawn has just moved 2 squares forward from its initial position
     * If the en passant capture is possible, enPassantLeft or enPassantRight are set to true
     */

    public ArrayList <Coordinate> enPassant (Pieces pieces) {
        ArrayList<Coordinate> enPassantMoves = new ArrayList<>();
        ArrayList<Coordinate> left = Move.leftFree(pieces,this,1);
        ArrayList<Coordinate> right = Move.rightFree(pieces,this,1);

        if (left.size() == 1) {
            Coordinate leftTile = left.get(0);
            boolean correctPiece = Move.tileFull(pieces,leftTile)
                                 && pieces.getPiece(leftTile).getName() == ID.PAWN
                                 && pieces.getPiece(leftTile).getColour() == COLOUR.not(getColour());
            if (correctPiece) {
                Pawn potentialPawn = (Pawn) pieces.getPiece(leftTile);
                boolean correctPassantContext = potentialPawn.getHasMovedTwo()
                                                && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getOGcoord())
                                                && !potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoords());
                if (correctPassantContext) {
                    enPassantMoves.addAll(Move.frontLDigFree(pieces, this, 1));
                    enPassantLeft = true;
                }
            }
        }

        if (right.size() == 1) {
            Coordinate rightTile = right.get(0);
            boolean correctPiece = Move.tileFull(pieces,rightTile)
                    && pieces.getPiece(rightTile).getName() == ID.PAWN
                    && pieces.getPiece(rightTile).getColour() == COLOUR.not(getColour());
            if (correctPiece) {
                Pawn potentialPawn = (Pawn) pieces.getPiece(rightTile);
                boolean correctPassantContext = potentialPawn.getHasMovedTwo()
                        && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getOGcoord())
                        && !potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoords());
                if (correctPassantContext) {
                    enPassantMoves.addAll(Move.frontRDigFree(pieces, this, 1));
                    enPassantRight = true;
                }
            }
        }

        return enPassantMoves;
    }

    //________________________________________________Pawn Promotion Methods________________________________________________

    /**
     * The promotion query for a TBIBoard to determine what the pawn will be promoted to
     * @param promotionSquare the square to which the pawn moves to
     * @return a Piece (out of Queen, Rook, Bishop or Knight), based on the user input.
     * Uses a while loop to make sure that a valid piece is provided.
     * Sets promotedPiece to the corect user input
     * @throws NullPointerException if the promotedPiece is not instantiated
     */

    public Piece promotionQuery(Coordinate promotionSquare) {

        Piece promotee = null;
        Scanner sc = new Scanner(System.in);
        boolean correctInput = false;

        System.out.print("Your pawn can be promoted. To what piece? ");

        while (!correctInput) {
            System.out.println("You can choose between: \n" +
                    "· Queen (Q) \n" +
                    "· Rook (R) \n" +
                    "· Bishop (B) \n" +
                    "· Knight (N)");
            String promoted = sc.next();
            switch (promoted) {
                case "Queen":
                case "Q":
                    promotee = new Queen(getColour(), promotionSquare);
                    correctInput = true;
                    break;
                case "Rook":
                case "R":
                    promotee = new Rook(getColour(), promotionSquare);
                    correctInput = true;
                    break;
                case "Bishop":
                case "B":
                    promotee = new Bishop(getColour(), promotionSquare);
                    correctInput = true;
                    break;
                case "Knight":
                case "N":
                    promotee = new Knight(getColour(), promotionSquare);
                    correctInput = true;
                    break;
                default:
                    System.out.println("Incorrect piece entered. Please try again");
            }
        }

        sc.close();

        Objects.requireNonNull(promotee);

        promotedPiece = promotee;

        return promotee;
    }

    /**
     * The promotion query for a GUIBoard to determine what the pawn will be promoted to.
     * It creates a JOptionPane (showing an option dialog),
     * with JButtons corresponding to the pieces that the pawn can be promoted to
     * Each has an ActionListener, which sets the promotedPiece based on which JButton has been clicked
     * Clicking the OK button confirms the piece chosen
     * If no piece is chosen, a queen is chosen as default.
     * @param promotionSquare the square to which the pawn moves to
     */

    public void GUIPromotionQuery (Coordinate promotionSquare) {

        JButton queenOption;
        JButton rookOption;
        JButton bishopOption;
        JButton knightOption;
        ImageIcon icon;

        if (this.getColour() == COLOUR.B) {
            queenOption = new JButton(new ImageIcon("BQueen.png"));
            rookOption = new JButton(new ImageIcon("BRook.png"));
            bishopOption = new JButton(new ImageIcon("BBishop.png"));
            knightOption = new JButton(new ImageIcon("BKnight.png"));
            promotedPiece = new Queen(COLOUR.B,promotionSquare);
            icon = new ImageIcon("BPawn.png");
        }
        else {
            queenOption = new JButton(new ImageIcon("WQueen.png"));
            rookOption = new JButton(new ImageIcon("WRook.png"));
            bishopOption = new JButton(new ImageIcon("WBishop.png"));
            knightOption = new JButton(new ImageIcon("WKnight.png"));
            promotedPiece = new Queen(COLOUR.W,promotionSquare);
            icon = new ImageIcon("WPawn.png");
        }

        GUIBoard.formatInvisibleButton(queenOption);
        GUIBoard.formatInvisibleButton(rookOption);
        GUIBoard.formatInvisibleButton(bishopOption);
        GUIBoard.formatInvisibleButton(knightOption);

        Object[] options = {"OK",knightOption,bishopOption,rookOption,queenOption};

        queenOption.addActionListener(actionEvent -> promotedPiece = new Queen(getColour(),promotionSquare));

        rookOption.addActionListener(actionEvent -> promotedPiece = new Rook(getColour(),promotionSquare));

        bishopOption.addActionListener(actionEvent -> promotedPiece = new Bishop(getColour(),promotionSquare));

        knightOption.addActionListener(actionEvent -> promotedPiece = new Knight(getColour(),promotionSquare));

        UIManager.put("OptionPane.background", GUIBoard.infoColour);
        UIManager.put("Panel.background", GUIBoard.infoColour);
        UIManager.put("OptionPane.messageForeground", Color.white);

        JOptionPane.showOptionDialog(null,
                "Choose a piece to promote. A queen is the default:",
                "Promotion Query",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                options,options[0]);
    }

    /**
     * Determines if a black pawn can promote
     * @param coordinate the coordinate to promote to
     * @return true if the pawn is black and its rank is the first rank (1)
     */

    public boolean canPromoteBlack (Coordinate coordinate) {
        return this.getColour() == COLOUR.B && coordinate.getRank() == BOARD.FIRST_RANK.getRankVal();
    }

    /**
     * Determines if a white pawn can promote
     * @param coordinate the coordinate to promote to
     * @return true if the pawn is white and its rank is the last rank (8)
     */

    public boolean canPromoteWhite (Coordinate coordinate) {
        return this.getColour() == COLOUR.W && coordinate.getRank() == BOARD.LAST_RANK.getRankVal();
    }

    public Piece getPromotedPiece() {
        return promotedPiece;
    }

    //________________________________________________Overridden Methods________________________________________________

    @Override
    public Pawn makeCopy() {
        return new Pawn(this);
    }

    /**
     * Produces an ArrayList containing all the raw moves available to a Pawn within a given board
     * @param pieces the board being played in
     * @return an ArrayList containing all the coordinates available to a Pawn, based on:
     * can it move 0,1 or 2 squares forward (using pawnForward)
     * can it capture on a diagonal? (using canEatLeftDig and canEatRightDig)
     * can it capture en passant? (using enPassant)
     */

    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        ArrayList<Coordinate> pawnMoves = new ArrayList<>();

        if (canEatLeftDig(pieces))
            pawnMoves.addAll(Move.frontLDigFree(pieces, this, 1));

        pawnMoves.addAll(pawnForward(pieces));

        if (canEatRightDig(pieces))
            pawnMoves.addAll(Move.frontRDigFree(pieces, this, 1));

        pawnMoves.addAll(enPassant(pieces));

        return pawnMoves;
    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
    }


}
