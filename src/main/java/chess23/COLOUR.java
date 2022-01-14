package chess23;

/**
 * The COLOUR enum is used to assign a certain colour to a given {@link Piece}.
 * <br>
 * The 2 possibilities are {@link #B} (black) or {@link #W} (white).
 */

public enum COLOUR {
    /**
     * Black
     */
    B {
        @Override
        public String toString() {
            return "Black";
        }

        @Override
        public String toSmallString() {
            return "b";
        }
    },
    /**
     * White
     */
    W {
        @Override
        public String toString() {
            return "White";
        }

        @Override
        public String toSmallString() {
            return "w";
        }
    };

    public abstract String toSmallString();

    /**
     * Used to obtain the opposite type of {@link COLOUR}.
     * @param colour the colour to be considered.
     * @return the opposite colour to {@code colour}.
     */
    public static COLOUR not(COLOUR colour) {
        if (colour == COLOUR.B)
            return COLOUR.W;
        else
            return COLOUR.B;

    }

}
