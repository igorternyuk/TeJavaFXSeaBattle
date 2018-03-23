package com.igorternyuk.seabattle.tests;

import com.igorternyuk.seabattle.model.Cell;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by igor on 23.03.18.
 */
public class CellTest {
    private Cell cell;

    @Before
    public void setUp(){
        cell = new Cell(3, 4);
    }

    @Test
    public void testOfCellInitialState(){
        assertEquals(3, cell.getX());
        assertEquals(4, cell.getY());
        assertTrue(cell.isAlive());
    }

    @Test
    public void testOfCellPosition(){
        final Point pos = new Point(3, 4);
        assertEquals(pos, cell.getPosition());
    }

    @Test
    public void testOfTarget(){
        cell.tryToHit(3, 4);
        assertFalse(cell.isAlive());
    }

    @Test
    public void testOfMiss(){
        cell.tryToHit(9, 4);
        assertTrue(cell.isAlive());
    }
}
