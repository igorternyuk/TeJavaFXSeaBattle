package com.igorternyuk.seabattle.tests;

import com.igorternyuk.seabattle.model.Game;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by igor on 23.03.18.
 */
public class GameTest {
    @Test
    public void testForAValidPosition(){
        int x = 3, y = 4;
        assertTrue(Game.isValidPosition(x, y));
        x = -2;
        assertFalse(Game.isValidPosition(x, y));
        x = 10;
        y = 10;
        assertFalse(Game.isValidPosition(x, y));
        x = 5;
        y = 6;
        assertTrue(Game.isValidPosition(x, y));
    }

    @Test
    public void testForNeighboursDetection(){
        List<Point> neighbours = Game.getNeighboursMoore(5, 5);
        assertEquals(new Point(4, 4), neighbours.get(0));
        assertEquals(new Point(4, 5), neighbours.get(1));
        assertEquals(new Point(4, 6), neighbours.get(2));
        assertEquals(new Point(5, 4), neighbours.get(3));
        assertEquals(new Point(5, 6), neighbours.get(4));
        assertEquals(new Point(6, 4), neighbours.get(5));
        assertEquals(new Point(6, 5), neighbours.get(6));
        assertEquals(new Point(6, 6), neighbours.get(7));

        List<Point> cornerNeighbours = Game.getNeighboursMoore(0, 0);
        assertEquals(new Point(0, 1), cornerNeighbours.get(0));
        assertEquals(new Point(1, 0), cornerNeighbours.get(1));
        assertEquals(new Point(1, 1), cornerNeighbours.get(2));
    }
}
