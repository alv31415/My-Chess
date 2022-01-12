package chess23;

/**
 * The chess23.ID enum class is used to identify the "type" of a piece (chess23.King, chess23.Queen, chess23.Rook, chess23.Bishop, chess23.Knight, and chess23.Pawn)
 * Each has 2 toString methods
 */

public enum ID {
    KING {
        @Override
        public String toString() {
            return "K";
        }
        public String toFullString() {
            return "chess23.King";
        }
    },
    QUEEN {
        @Override
        public String toString() {
            return "Q";
        }
        public String toFullString() {
            return "chess23.Queen";
        }
    },
    ROOK {
        @Override
        public String toString() {
            return "R";
        }
        public String toFullString() {
            return "chess23.Rook";
        }
    },
    BISHOP {
        @Override
        public String toString() {
            return "B";
        }
        public String toFullString() {
            return "chess23.Bishop";
        }
    },
    KNIGHT {
        @Override
        public String toString() {
            return "N";
        }
        public String toFullString() {
            return "chess23.Knight";
        }
    },
    PAWN {
        @Override
        public String toString() {
            return "";
        }
        public String toFullString() {
            return "chess23.Pawn";
        }
    };

    public abstract String toFullString();

}
