package chess23;

public class App {

    public static void main(String[] args) {

        if (args.length == 1) {
            if (args[0].equals("help")) {
                System.out.println("Welcome to Chess23!");
                System.out.println("To play a game, provide the following:\n" +
                        "    - the type of game board ('gui' or 'cli')\n" +
                        "    - the type of player for white ('h' (human), 'r' (random ai) or 'm' (minimax ai))\n" +
                        "    - the type of player for black ('h' (human), 'r' (random ai) or 'm' (minimax ai))");
                System.out.println("The format is as follows: <game board> <white> <black>.");
            }
            else {
                System.out.println("Use 'help' as an argument to see how to play a game!");
            }
        }
        else if (args.length == 3) {
            String boardType = args[0];
            String white = args[1];
            String black = args[2];
            PLAYER playerWhite = null;
            PLAYER playerBlack = null;

            switch (white) {
                case "h" -> playerWhite = PLAYER.HUMAN;
                case "r" -> playerWhite = PLAYER.AI_RANDOM;
                case "m" -> playerWhite = PLAYER.AI_MINIMAX;
                default -> {
                    System.out.println("The second argument (for the white player) is invalid: expected 'h', 'r' or 'm'" +
                            " but got " + white + ".");
                    System.exit(1);
                }
            }

            switch (black) {
                case "h" -> playerBlack = PLAYER.HUMAN;
                case "r" -> playerBlack = PLAYER.AI_RANDOM;
                case "m" -> playerBlack = PLAYER.AI_MINIMAX;
                default -> {
                    System.out.println("The third argument (for the black player) is invalid: expected 'h', 'r' or 'm'" +
                            " but got " + black + ".");
                    System.exit(1);
                }
            }

            if (boardType.equals("gui")) {
                new Game(playerWhite, playerBlack, true);
            }
            else if (boardType.equals("cli")) {
                new Game(playerWhite, playerBlack, false);
            }
            else {
                System.out.println("The first argument (for the type of board) is invalid: expected 'gui' or 'cli'" +
                        " but got " + boardType + ".");
                System.exit(1);
            }
        }
        else {
            System.out.println("Expected 1 or 3 arguments, but " + args.length + " were given." +
                    " Type 'help' to get the expected arguments.");
            System.exit(1);
        }

    }

}
