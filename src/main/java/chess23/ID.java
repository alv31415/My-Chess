package chess23;

/**
 * The ID enum is used to identify the "type" of a
 * piece (king, queen, rook, bishop, knight, or pawn).
 */
public enum ID {
    /**
     * A king.
     */
    KING {
        @Override
        public String toString() {
            return "K";
        }
        public String toFullString() {
            return "King";
        }
    },
    /**
     * A queen.
     */
    QUEEN {
        @Override
        public String toString() {
            return "Q";
        }
        public String toFullString() {
            return "Queen";
        }
    },
    /**
     * A rook.
     */
    ROOK {
        @Override
        public String toString() {
            return "R";
        }
        public String toFullString() {
            return "Rook";
        }
    },
    /**
     * A bishop.
     */
    BISHOP {
        @Override
        public String toString() {
            return "B";
        }
        public String toFullString() {
            return "Bishop";
        }
    },
    /**
     * A knight.
     */
    KNIGHT {
        @Override
        public String toString() {
            return "N";
        }
        public String toFullString() {
            return "Knight";
        }
    },
    /**
     * A pawn.
     */
    PAWN {
        @Override
        public String toString() {
            return "";
        }
        public String toFullString() {
            return "Pawn";
        }
    };

    public abstract String toFullString();

}
