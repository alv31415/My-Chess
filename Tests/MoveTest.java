import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class MoveTest {

    Pieces p = new Pieces(Boards.getTestBoard());

    int max = 8;

    int pawnBRank = 7;
    int bRank = 8;
    int pawnWRank = 2;
    int wRank = 1;

    Pawn pawnB = new Pawn(COLOUR.B, new Coordinate('g', pawnBRank));
    Rook rookB = new Rook(COLOUR.B, new Coordinate('a',8));
    Knight knightB = new Knight(COLOUR.B, new Coordinate('h', 7));
    Queen queenB = new Queen(COLOUR.B, new Coordinate('d', bRank));
    Pawn pawnWe = new Pawn(COLOUR.W, new Coordinate('e', pawnWRank));
    Pawn pawnWh = new Pawn(COLOUR.W, new Coordinate('h', pawnWRank));
    Rook rookWa = new Rook(COLOUR.W, new Coordinate('a', wRank));
    Rook rookWh = new Rook(COLOUR.W, new Coordinate('h', wRank));
    Bishop bishopW = new Bishop(COLOUR.W, new Coordinate('f', 6));
    Queen queenW = new Queen(COLOUR.W, new Coordinate('g', 5));
    King kingW = new King(COLOUR.W, new Coordinate('e', wRank));

    @Test
    public void frontFreeTest() {
        // against own piece
        assertEquals(new ArrayList<Coordinate>(), Move.frontFree(p,kingW,max));
        // move to end of board
        ArrayList<String> bishopList = new ArrayList<>(Arrays.asList("f7","f8"));
        assertEquals(bishopList.toString(), Move.frontFree(p,bishopW,max).toString());
        // eat other piece
        ArrayList<String> pawnBList = new ArrayList<>(Arrays.asList("g6","g5"));
        assertEquals(pawnBList.toString(), Move.frontFree(p,pawnB,max).toString());
        //2 opposite in a row
        ArrayList<String> knightList = new ArrayList<>(Arrays.asList("h6","h5","h4","h3","h2"));
        assertEquals(knightList.toString(), Move.frontFree(p,knightB,max).toString());
    }

    @Test
    public void backFreeTest() {

        // against own piece
        Pawn behindWQueen = new Pawn(COLOUR.W,new Coordinate('g',2));
        p.addPiece(behindWQueen.getOGcoord(),behindWQueen);
        ArrayList<String> pawnList = new ArrayList<>(Arrays.asList("g4","g3"));
        assertEquals(pawnList.toString(), Move.backFree(p,queenW,max).toString());
        // against end of board
        ArrayList<String> knightList = new ArrayList<>(Collections.singletonList("h8"));
        assertEquals(knightList.toString(), Move.backFree(p,knightB,max).toString());
        // eat other piece
        Pawn behindWQueenB = new Pawn(COLOUR.B,new Coordinate('g',4));
        p.addPiece(behindWQueenB.getOGcoord(),behindWQueenB);
        ArrayList<String> pawnList2 = new ArrayList<>(Collections.singletonList("g4"));
        assertEquals(pawnList2.toString(), Move.backFree(p,queenW,max).toString());
    }

    @Test
    public void rightFreeTest() {
        // against own piece
        ArrayList<String> kingList = new ArrayList<>(Arrays.asList("f1","g1"));
        assertEquals(kingList.toString(), Move.rightFree(p,kingW,max).toString());
        // against end of board
        ArrayList<String> pawnList = new ArrayList<>(Arrays.asList("f7","e7","d7","c7","b7","a7"));
        assertEquals(pawnList.toString(), Move.rightFree(p,pawnB,max).toString());
        // eat other piece
        Pawn behindWQueen = new Pawn(COLOUR.B,new Coordinate('g',2));
        p.addPiece(behindWQueen.getOGcoord(),behindWQueen);
        ArrayList<String> pawnList2 = new ArrayList<>(Arrays.asList("f2","g2"));
        assertEquals(pawnList2.toString(), Move.rightFree(p,pawnWe,max).toString());
    }

    @Test
    public void leftFreeTest() {
        // against own piece
        ArrayList<String> rookList = new ArrayList<>(Arrays.asList("g1","f1"));
        assertEquals(rookList.toString(), Move.leftFree(p,rookWh,max).toString());
        // against end of board
        ArrayList<String> knightList = new ArrayList<>();
        assertEquals(knightList.toString(), Move.leftFree(p,knightB,max).toString());
        // eat other piece
        Pawn behindWQueen = new Pawn(COLOUR.B,new Coordinate('g',2));
        p.addPiece(behindWQueen.getOGcoord(),behindWQueen);
        ArrayList<String> pawnList2 = new ArrayList<>(Collections.singletonList("g2"));
        assertEquals(pawnList2.toString(), Move.leftFree(p,pawnWh,max).toString());
    }

    @Test
    public void frontRDigFree() {
        // against own piece
        ArrayList<String> rookList = new ArrayList<>(Arrays.asList("b2","c3","d4","e5"));
        assertEquals(rookList.toString(), Move.frontRDigFree(p,rookWa,max).toString());
        // against end of board
        ArrayList<String> queenBList = new ArrayList<>(Arrays.asList("c7","b6","a5"));
        assertEquals(queenBList.toString(), Move.frontRDigFree(p,queenB,max).toString());
        // eat other piece
        ArrayList<String> bishopList = new ArrayList<>(Collections.singletonList("g7"));
        assertEquals(bishopList.toString(), Move.frontRDigFree(p,bishopW,max).toString());
    }

    @Test
    public void backRDigFree() {
        // against own piece
        ArrayList<String> bishopList = new ArrayList<>();
        assertEquals(bishopList.toString(), Move.backRDigFree(p,bishopW,max).toString());
        // against end of board
        ArrayList<String> bishopList2 = new ArrayList<>(Collections.singletonList("f8"));
        assertEquals(bishopList2.toString(), Move.backRDigFree(p,pawnB,max).toString());
        // eat other piece
        Pawn behindBPawn = new Pawn(COLOUR.W,new Coordinate('g',8));
        p.addPiece(behindBPawn.getOGcoord(),behindBPawn);
        ArrayList<String> pawnList2 = new ArrayList<>(Collections.singletonList("g8"));
        assertEquals(pawnList2.toString(), Move.backRDigFree(p,knightB,max).toString());
    }

    @Test
    public void frontLDigFree() {
        // against own piece
        ArrayList<String> queenList = new ArrayList<>();
        assertEquals(queenList.toString(), Move.frontLDigFree(p,queenW,max).toString());
        // against end of board
        ArrayList<String> pawnList = new ArrayList<>(Arrays.asList("d3","c4","b5","a6"));
        assertEquals(pawnList.toString(), Move.frontLDigFree(p,pawnWe,max).toString());
        // eat other piece
        ArrayList<String> queenList2 = new ArrayList<>(Arrays.asList("e7","f6"));
        assertEquals(queenList2.toString(), Move.frontLDigFree(p,queenB,max).toString());
    }

    @Test
    public void backLFreeTest() {
        // against own piece
        ArrayList<String> bishopList = new ArrayList<>(Arrays.asList("e5","d4","c3","b2"));
        assertEquals(bishopList.toString(), Move.backLDigFree(p,bishopW,max).toString());
        // against end of board
        ArrayList<String> pawnList = new ArrayList<>(Collections.singletonList("h8"));
        assertEquals(pawnList.toString(), Move.backLDigFree(p,pawnB,max).toString());
        // eat other piece
        Pawn behindBPawn = new Pawn(COLOUR.W,new Coordinate('h',8));
        p.addPiece(behindBPawn.getOGcoord(),behindBPawn);
        ArrayList<String> pawnList2 = new ArrayList<>(Collections.singletonList("h8"));
        assertEquals(pawnList2.toString(), Move.backLDigFree(p,pawnB,max).toString());
    }

    @Test
    public void frontKnightTest() {
        // full scope (all squares free)
        ArrayList<String> bishopList = new ArrayList<>(Arrays.asList("e8","g8"));
        assertEquals(bishopList.toString(), Move.frontKnight(p,bishopW).toString());
        // eat piece
        ArrayList<String> queenList = new ArrayList<>(Arrays.asList("f7","h7"));
        assertEquals(queenList.toString(), Move.frontKnight(p,queenW).toString());
        // against end of board
        ArrayList<String> rookList = new ArrayList<>(Collections.singletonList("b6"));
        assertEquals(rookList.toString(), Move.frontKnight(p,rookB).toString());
    }

    @Test
    public void backKnightTest() {
        // full scope (all squares free)
        ArrayList<String> bishopList = new ArrayList<>(Arrays.asList("e4","g4"));
        assertEquals(bishopList.toString(), Move.backKnight(p,bishopW).toString());
        // eat piece
        Pawn behindWQueen = new Pawn(COLOUR.B,new Coordinate('f',3));
        p.addPiece(behindWQueen.getOGcoord(),behindWQueen);
        Pawn behindWQueen2 = new Pawn(COLOUR.B,new Coordinate('h',3));
        p.addPiece(behindWQueen2.getOGcoord(),behindWQueen);
        ArrayList<String> queenList = new ArrayList<>(Arrays.asList("f3","h3"));
        assertEquals(queenList.toString(), Move.backKnight(p,queenW).toString());
        // against end of board
        ArrayList<String> knightList = new ArrayList<>();
        assertEquals(knightList.toString(), Move.backKnight(p,knightB).toString());
    }

    @Test
    public void rightKnightTest() {
        // full scope (all squares free)
        ArrayList<String> pawnList = new ArrayList<>(Arrays.asList("g3","g1"));
        assertEquals(pawnList.toString(), Move.rightKnight(p,pawnWe).toString());
        // eat piece
        ArrayList<String> bishopList = new ArrayList<>(Arrays.asList("h7","h5"));
        assertEquals(bishopList.toString(), Move.rightKnight(p,bishopW).toString());
        // against end of board
        ArrayList<String> rookList = new ArrayList<>(Collections.singletonList("c2"));
        assertEquals(rookList.toString(), Move.rightKnight(p,rookWa).toString());
    }

    @Test
    public void leftKnightTest() {
        // full scope (all squares free)
        ArrayList<String> queenList = new ArrayList<>(Arrays.asList("e4","e6"));
        assertEquals(queenList.toString(), Move.leftKnight(p,queenW).toString());
        // eat piece
        Pawn byWKing = new Pawn(COLOUR.B,new Coordinate('c',1));
        p.addPiece(byWKing.getOGcoord(),byWKing);
        ArrayList<String> pawnList = new ArrayList<>(Collections.singletonList("e2"));
        assertEquals(pawnList.toString(), Move.leftKnight(p,byWKing).toString());
        // against end of board
        ArrayList<String> rookList = new ArrayList<>();
        assertEquals(rookList.toString(), Move.leftKnight(p,rookWa).toString());
    }


}
