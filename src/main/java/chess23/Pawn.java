package chess23;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/*
1) Class Constructors
2) Overridden Methods
3) Getters & Setters
4) Special Pawn Move Methods
5) Pawn Promotion Methods
*/

/**
 * Class representing a pawn in chess.
 */
public class Pawn extends Piece {

    /**
     * Whether the {@link Pawn} has moved 2 squares forward at the start.
     */
    private boolean hasMovedTwo = false;

    /**
     * Whether the {@link Pawn} can capture en passant to the left.
     */
    private boolean enPassantLeft = false;

    /**
     * Whether the {@link Pawn} can capture en passant to the right.
     */
    private boolean enPassantRight = false;

    /**
     * Stores the {@link Coordinate} occupied by the {@link Pawn} before making a move.
     * Used to determine whether the {@link Pawn} can make use of a 2 square move.
     */
    private Coordinate previousCoordinate = new Coordinate();

    /**
     * The {@link Piece} to which the {@link Pawn} will be promoted
     */
    private Piece promotedPiece;

    /**
     * The icon representing the {@link Pawn} in the GUI.
     */
    private ImageIcon icon;

    //________________________________________________Class Constructors________________________________________________

    /**
     * Class constructor for a {@link Pawn}.
     * @param colour a {@link COLOUR}, representing the colour of the {@link Pawn}.
     * @param ogCoord a {@link Coordinate}, representing the starting square of the {@link Pawn}.
     */
    public Pawn(COLOUR colour, Coordinate ogCoord) {
        super(ID.PAWN, colour, ogCoord);

        // instantiate the icon depending on the colour of the pawn
        if (getColour() == COLOUR.B)
            icon = new ImageIcon("icons/BPawn.png");
        else if (getColour() == COLOUR.W)
            icon = new ImageIcon("icons/WPawn.png");
    }

    /**
     * Copy constructor for a {@link Pawn}.
     * @param original the {@link Pawn} we are copying.
     */
    public Pawn(Pawn original) {
        super(original);
        icon = getImageIcon();
        hasMovedTwo = getHasMovedTwo();
        enPassantLeft = getEnPassantLeft();
        enPassantRight = getEnPassantLeft();
        previousCoordinate = getPreviousCoordinate();
        promotedPiece = getPromotedPiece();
    }

    //________________________________________________Overridden Methods________________________________________________

    /**
     * Produces a deep copy of the {@link Pawn} instance.
     * @return a {@link Pawn} produced as a deep copy of the instance.
     */
    @Override
    public Pawn makeCopy() {
        return new Pawn(this);
    }

    /**
     * Produces an {@link ArrayList} containing all the raw moves available
     * to the {@link Pawn} instance.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing all the {@link Coordinate} squares
     *         reachable by the {@link Pawn}.
     */
    @Override
    public ArrayList<Coordinate> getRawMoves(Pieces pieces) {

        ArrayList<Coordinate> pawnMoves = new ArrayList<>();

        // add moves if the pawn can capture to the left
        if (canEatLeftDig(pieces))
            pawnMoves.addAll(Move.frontLDigFree(pieces, this, 1));

        // add all moves that the pawn can move forward (1 or 2 steps)
        pawnMoves.addAll(pawnForward(pieces));

        // add moves if the pawn can capture to the right
        if (canEatRightDig(pieces))
            pawnMoves.addAll(Move.frontRDigFree(pieces, this, 1));

        // add moves if the pawn can capture en passant
        pawnMoves.addAll(enPassant(pieces));

        return pawnMoves;
    }

    @Override
    public ImageIcon getImageIcon() {
        return icon;
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
     * Determines whether the pawn can eat diagonally to the left.
     * @param pieces the {@link Pieces} used for playing.
     * @return {@code true} if the coordinate at the top, left diagonal is occupied
     *         by a {@link Piece} of the opposite colour to the {@link Pawn} moving.
     */
    private boolean canEatLeftDig(Pieces pieces) {

        int factorV;
        int factorH;

        // set factors to control what the top left diagonal is for the pawn
        // for a white pawn, this involves going up a rank, and decreasing one file
        // for a black pawn, this involves going down a rank, and increasing one file
        if (getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = 1;
        } else {
            factorV = 1;
            factorH = -1;
        }

        // compute the coordinate of the top left diagonal for the pawn
        char newFile = (char) (getFile() + factorH);
        int newRank = getRank() + factorV;
        Coordinate leftDig = new Coordinate(newFile, newRank);

        // check if the square has a piece of the opposite colour
        return Move.tileFull(pieces, leftDig) && Move.isNotTileColour(pieces, leftDig, getColour());
    }

    /**
     * Determines whether the pawn can eat diagonally to the right.
     * @param pieces the {@link Pieces} used for playing.
     * @return {@code true} if the coordinate at the top, right diagonal is occupied
     *         by a {@link Piece} of the opposite colour to the {@link Pawn} moving.
     */
    private boolean canEatRightDig(Pieces pieces) {

        int factorV;
        int factorH;

        // set factors to control what the top right diagonal is for the pawn
        // for a white pawn, this involves going up a rank, and increasing one file
        // for a black pawn, this involves going down a rank, and decreasing one file
        if (getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = -1;
        } else {
            factorV = 1;
            factorH = 1;
        }

        // compute the coordinate of the top right diagonal for the pawn
        char newFile = (char) (getFile() + factorH);
        int newRank = getRank() + factorV;
        Coordinate rightDig = new Coordinate(newFile, newRank);

        // check if the square has a piece of the opposite colour
        return Move.tileFull(pieces, rightDig) && Move.isNotTileColour(pieces, rightDig, getColour());
    }

    /**
     * Determines how many squares a {@link Pawn} can move forward.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList}, containing the {@link Coordinate} squares to which the pawn can move.
     * The number of squares is:
     * <ul>
     *     <li>0 if there is a piece in front of the pawn,</li>
     *     <li>1 if the square in front of the pawn is free,</li>
     *     <li>2 if the pawn is in its initial position, and both squares in front of it are empty.</li>
     * </ul>
     */
    private ArrayList<Coordinate> pawnForward(Pieces pieces) {

        // compute the number of potential moves a pawn can make
        // since frontFree counts as a movable square those squares
        // occupied by a piece of the opposite colour, we need to do some extra processing
        ArrayList<Coordinate> potentialForward = Move.frontFree(pieces, this, 2);

        // store the actual moves that the pawn can make
        ArrayList<Coordinate> actualForward = new ArrayList<>();

        // if there are no pieces of the same colour as the pawn, it can move forward
        if (potentialForward.size() > 0) {

            // get the potential coordinates
            Coordinate front1 = potentialForward.get(0);
            Coordinate front2 = Coordinate.emptyCoordinate;

            // only initialise the second coordinate if it is a free square
            if (potentialForward.size() == 2) {
                front2 = potentialForward.get(1);
            }

            // if the first coordinate is occupied by a piece of the opposite colour,
            // no moves forward are possible
            if (Move.tileFull(pieces, front1))
                return actualForward;
            else {
                // otherwise, update the moves that the pawn can make
                actualForward.add(front1);
            }

            // check if the pawn is at its starting position, and it can actually move 2 squares forward
            if (pieces.findPiece(this).equals(getOgCoord()) && front2 != Coordinate.emptyCoordinate) {
                // if the square 2 steps forward is occupied, just return the first square move
                if (Move.tileFull(pieces, front2))
                    return actualForward;
                else
                    // otherwise, update the moves that the pawn can make
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
                                                && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getOgCoord())
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
                        && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getOgCoord())
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
                case "Queen", "Q" -> {
                    promotee = new Queen(getColour(), promotionSquare);
                    correctInput = true;
                }
                case "Rook", "R" -> {
                    promotee = new Rook(getColour(), promotionSquare);
                    correctInput = true;
                }
                case "Bishop", "B" -> {
                    promotee = new Bishop(getColour(), promotionSquare);
                    correctInput = true;
                }
                case "Knight", "N" -> {
                    promotee = new Knight(getColour(), promotionSquare);
                    correctInput = true;
                }
                default -> System.out.println("Incorrect piece entered. Please try again");
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
            queenOption = new JButton(new ImageIcon("icons/BQueen.png"));
            rookOption = new JButton(new ImageIcon("icons/BRook.png"));
            bishopOption = new JButton(new ImageIcon("icons/BBishop.png"));
            knightOption = new JButton(new ImageIcon("icons/BKnight.png"));
            promotedPiece = new Queen(COLOUR.B,promotionSquare);
            icon = new ImageIcon("icons/BPawn.png");
        }
        else {
            queenOption = new JButton(new ImageIcon("icons/WQueen.png"));
            rookOption = new JButton(new ImageIcon("icons/WRook.png"));
            bishopOption = new JButton(new ImageIcon("icons/WBishop.png"));
            knightOption = new JButton(new ImageIcon("icons/WKnight.png"));
            promotedPiece = new Queen(COLOUR.W,promotionSquare);
            icon = new ImageIcon("icons/WPawn.png");
        }

        GuiBoard.formatInvisibleButton(queenOption);
        GuiBoard.formatInvisibleButton(rookOption);
        GuiBoard.formatInvisibleButton(bishopOption);
        GuiBoard.formatInvisibleButton(knightOption);

        Object[] options = {"OK",knightOption,bishopOption,rookOption,queenOption};

        queenOption.addActionListener(actionEvent -> promotedPiece = new Queen(getColour(),promotionSquare));

        rookOption.addActionListener(actionEvent -> promotedPiece = new Rook(getColour(),promotionSquare));

        bishopOption.addActionListener(actionEvent -> promotedPiece = new Bishop(getColour(),promotionSquare));

        knightOption.addActionListener(actionEvent -> promotedPiece = new Knight(getColour(),promotionSquare));

        UIManager.put("OptionPane.background", GuiBoard.infoColour);
        UIManager.put("Panel.background", GuiBoard.infoColour);
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
}
