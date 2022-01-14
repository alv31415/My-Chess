package chess23;

/**
 * The PLAYER enum is used to identify the type of player used to play chess..
 */
public enum PLAYER {
    /**
     * A human player.
     */
    HUMAN,

    /**
     * A player which makes random moves.
     */
    AI_RANDOM,

    /**
     * A player which uses minimax to decide the next move.
     */
    AI_MINIMAX
}
