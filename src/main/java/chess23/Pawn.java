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
     * The {@link Piece} to which the {@link Pawn} will be promoted.
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
            this.icon = new ImageIcon("icons/BPawn.png");
        else if (getColour() == COLOUR.W)
            this.icon = new ImageIcon("icons/WPawn.png");
    }

    /**
     * Copy constructor for a {@link Pawn}.
     * @param original the {@link Pawn} we are copying.
     */
    public Pawn(Pawn original) {
        super(original);
        icon = this.getImageIcon();
        hasMovedTwo = this.getHasMovedTwo();
        enPassantLeft = this.getEnPassantLeft();
        enPassantRight = this.getEnPassantLeft();
        previousCoordinate = this.getPreviousCoordinate();
        promotedPiece = this.getPromotedPiece();
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

    public Piece getPromotedPiece() {
        return promotedPiece;
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
        if (this.getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = 1;
        } else {
            factorV = 1;
            factorH = -1;
        }

        // compute the coordinate of the top left diagonal for the pawn
        char newFile = (char) (this.getFile() + factorH);
        int newRank = this.getRank() + factorV;
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
        if (this.getColour().equals(COLOUR.B)) {
            factorV = -1;
            factorH = -1;
        } else {
            factorV = 1;
            factorH = 1;
        }

        // compute the coordinate of the top right diagonal for the pawn
        char newFile = (char) (this.getFile() + factorH);
        int newRank = this.getRank() + factorV;
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
     * Determines how many squares a {@link Pawn} can capture en passant.
     * It also sets the value of {@link #enPassantLeft} and {@link #enPassantRight}.
     * @param pieces the {@link Pieces} used for playing.
     * @return an {@link ArrayList} containing the {@link Coordinate} that a {@link Pawn} can move to
     * in order to capture en passant. These are added if:
     * <ul>
     *     <li>the {@link Piece} directly to the left/right contains a {@link Pawn} of the
     *     opposite colour,</li>
     *     <li>this {@link Pawn} has just moved 2 squares forward from its initial position</li>
     * </ul>
     */
    public ArrayList <Coordinate> enPassant (Pieces pieces) {
        ArrayList<Coordinate> enPassantMoves = new ArrayList<>();

        // get the moves that can be made to the left or right
        ArrayList<Coordinate> left = Move.leftFree(pieces,this,1);
        ArrayList<Coordinate> right = Move.rightFree(pieces,this,1);

        // if the pawn can move to the left, either the square is free, or there is another piece
        if (left.size() == 1) {

            // get the coordinate to the left
            Coordinate leftTile = left.get(0);

            // determine if the square to the left is occupied by a pawn of the opposite colour
            boolean correctPiece = Move.tileFull(pieces,leftTile)
                                 && pieces.getPiece(leftTile).getName() == ID.PAWN
                                 && pieces.getPiece(leftTile).getColour() == COLOUR.not(getColour());

            // if there is a pawn of the opposite colour to the left
            if (correctPiece) {
                Pawn potentialPawn = (Pawn) pieces.getPiece(leftTile);

                // determine if the pawn has just moved two squares forward from its initial position
                boolean correctPassantContext = potentialPawn.getHasMovedTwo()
                                                && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getOgCoord())
                                                && !potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoords());

                // if the conditions for the en passant capture are appropriate,
                // add the move to the list of en passant moves possible
                // and set enPassantLeft
                if (correctPassantContext) {
                    enPassantMoves.addAll(Move.frontLDigFree(pieces, this, 1));
                    this.enPassantLeft = true;
                }
            }
        }

        // if the pawn can move to the right, either the square is free, or there is another piece
        if (right.size() == 1) {

            // get the coordinate to the right
            Coordinate rightTile = right.get(0);

            // determine if the square to the right is occupied by a pawn of the opposite colour
            boolean correctPiece = Move.tileFull(pieces,rightTile)
                    && pieces.getPiece(rightTile).getName() == ID.PAWN
                    && pieces.getPiece(rightTile).getColour() == COLOUR.not(getColour());

            // if there is a pawn of the opposite colour to the right
            if (correctPiece) {
                Pawn potentialPawn = (Pawn) pieces.getPiece(rightTile);

                // determine if the pawn has just moved two squares forward from its initial position
                boolean correctPassantContext = potentialPawn.getHasMovedTwo()
                        && potentialPawn.getPreviousCoordinate().equals(potentialPawn.getOgCoord())
                        && !potentialPawn.getPreviousCoordinate().equals(potentialPawn.getCoords());

                // if the conditions for the en passant capture are appropriate,
                // add the move to the list of en passant moves possible
                // and set enPassantRight
                if (correctPassantContext) {
                    enPassantMoves.addAll(Move.frontRDigFree(pieces, this, 1));
                    this.enPassantRight = true;
                }
            }
        }

        return enPassantMoves;
    }

    //________________________________________________Pawn Promotion Methods________________________________________________

    /**
     * Used to determine the piece to which to promote a {@link Pawn} in a {@link CliBoard}.
     * Sets the value of {@link #promotedPiece}.
     * @param promotionSquare the square to which the {@link Pawn} moves to when getting promoted.
     * @throws NullPointerException if a {@link Piece} is not correctly instantiated.
     */
    public void cliPromotionQuery(Coordinate promotionSquare) {

        Piece promotee = null;

        // instantiate scanner to get user input
        Scanner sc = new Scanner(System.in);

        // create a flag to ensure a correct input is given
        boolean correctInput = false;

        System.out.print("Your pawn can be promoted. To what piece? ");

        while (!correctInput) {
            System.out.println("You can choose between: \n" +
                    "· Queen (Q) \n" +
                    "· Rook (R) \n" +
                    "· Bishop (B) \n" +
                    "· Knight (N)");

            // get the user input
            String promoted = sc.next();

            // check if the input is in the correct format
            // if so, instantiate the appropriate piece
            // otherwise, continue asking for input
            switch (promoted) {
                case "Queen", "Q" -> {
                    promotee = new Queen(this.getColour(), promotionSquare);
                    correctInput = true;
                }
                case "Rook", "R" -> {
                    promotee = new Rook(this.getColour(), promotionSquare);
                    correctInput = true;
                }
                case "Bishop", "B" -> {
                    promotee = new Bishop(this.getColour(), promotionSquare);
                    correctInput = true;
                }
                case "Knight", "N" -> {
                    promotee = new Knight(this.getColour(), promotionSquare);
                    correctInput = true;
                }
                default -> System.out.println("Incorrect piece entered. Please try again");
            }
        }

        Objects.requireNonNull(promotee);

        promotedPiece = promotee;
    }

    /**
     * Used to determine the piece to which to promote a {@link Pawn} in a {@link GuiBoard}.
     * Sets the value of {@link #promotedPiece}.
     * If no piece is chosen, a {@link Queen} is chosen as default.
     * @param promotionSquare the {@link Coordinate} square to which the {@link Pawn} moves to when getting promoted.
     */
    public void guiPromotionQuery(Coordinate promotionSquare) {

        // initialise the buttons to select the promotion piece
        JButton queenOption;
        JButton rookOption;
        JButton bishopOption;
        JButton knightOption;

        // initialise the icon for the pawn in the promotion pane
        ImageIcon pawnIcon;

        // create buttons displaying the promotion options based on the colour of the pawn
        if (this.getColour() == COLOUR.B) {
            queenOption = new JButton(new ImageIcon("icons/BQueen.png"));
            rookOption = new JButton(new ImageIcon("icons/BRook.png"));
            bishopOption = new JButton(new ImageIcon("icons/BBishop.png"));
            knightOption = new JButton(new ImageIcon("icons/BKnight.png"));
            promotedPiece = new Queen(COLOUR.B, promotionSquare);
            pawnIcon = new ImageIcon("icons/BPawn.png");
        }
        else {
            queenOption = new JButton(new ImageIcon("icons/WQueen.png"));
            rookOption = new JButton(new ImageIcon("icons/WRook.png"));
            bishopOption = new JButton(new ImageIcon("icons/WBishop.png"));
            knightOption = new JButton(new ImageIcon("icons/WKnight.png"));
            promotedPiece = new Queen(COLOUR.W, promotionSquare);
            pawnIcon = new ImageIcon("icons/WPawn.png");
        }

        // format the buttons to just show the icon
        GuiBoard.formatInvisibleButton(queenOption);
        GuiBoard.formatInvisibleButton(rookOption);
        GuiBoard.formatInvisibleButton(bishopOption);
        GuiBoard.formatInvisibleButton(knightOption);

        // set the buttons in the promotion pane
        Object[] options = {"OK", knightOption, bishopOption, rookOption, queenOption};

        // add listener to create the appropriate piece based on the button clicked
        queenOption.addActionListener(actionEvent -> promotedPiece = new Queen(getColour(), promotionSquare));
        rookOption.addActionListener(actionEvent -> promotedPiece = new Rook(getColour(), promotionSquare));
        bishopOption.addActionListener(actionEvent -> promotedPiece = new Bishop(getColour(), promotionSquare));
        knightOption.addActionListener(actionEvent -> promotedPiece = new Knight(getColour(), promotionSquare));

        // set promotion pane options
        UIManager.put("OptionPane.background", GuiBoard.infoColour);
        UIManager.put("Panel.background", GuiBoard.infoColour);
        UIManager.put("OptionPane.messageForeground", Color.white);

        // create the promotion pane using the buttons
        JOptionPane.showOptionDialog(null,
                "Choose a piece to promote. A queen is the default:",
                "Promotion Query",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                pawnIcon,
                options,
                options[0]);
    }

    /**
     * Determines if the {@link Pawn} can promote.
     * @param coordinate the {@link Coordinate} square to which the {@link Pawn} moves to when getting promoted.
     * @return {@code true} if the {@link Pawn} is would be promoted if it reached the {@code coordinate}.
     * <br>
     * For a black {@link Pawn}, the rank of the {@code coordinate} is {@code 1}.
     * <br>
     * For a white {@link Pawn}, the rank of the {@code coordinate} is {@code 8}.
     */
    public boolean canPromote(Coordinate coordinate) {
        if (getColour() == COLOUR.B) {
            return coordinate.getRank() == BOARD.FIRST_RANK.getRankVal();
        }
        else {
            return coordinate.getRank() == BOARD.LAST_RANK.getRankVal();
        }
    }
}
