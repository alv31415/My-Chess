/**
 * The COLOUR enum class is used to assign a certain colour to a given piece.
 * The 2 possibilities are B (black) or W (white)
 * There are 2 toString methods
 */

public enum COLOUR {
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
     * @param colour the colour to be considered
     * @return the opposite colour as the one passed as an argumnet
     */

    public static COLOUR not(COLOUR colour) {
        if (colour == COLOUR.B)
            return COLOUR.W;
        else
            return COLOUR.B;

    }

}
