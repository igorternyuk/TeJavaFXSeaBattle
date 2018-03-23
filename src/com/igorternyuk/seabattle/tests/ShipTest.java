package com.igorternyuk.seabattle.tests;

import com.igorternyuk.seabattle.model.Cell;
import com.igorternyuk.seabattle.model.Ship;
import com.igorternyuk.seabattle.model.ShipDirection;
import com.igorternyuk.seabattle.model.ShipType;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by igor on 23.03.18.
 */
public class ShipTest {
    private Ship horizontalShip, verticalShip;

    @Before
    public void setUp(){
        horizontalShip = new Ship(1, 1, ShipType.FOUR_DECKED, ShipDirection.HORIZONTAL);
        verticalShip = new Ship(3, 5, ShipType.THREE_DECKED, ShipDirection.VERTICAL);
    }

    @Test
    public void testOfShipsLength(){
        assertEquals(4, horizontalShip.getLength());
        assertEquals(3, verticalShip.getLength());
    }

    @Test
    public void testOfShipsDirection(){
        assertEquals(ShipDirection.HORIZONTAL, horizontalShip.getDirection());
        assertEquals(ShipDirection.VERTICAL, verticalShip.getDirection());
    }

    @Test
    public void testOfHit(){
        assertTrue(horizontalShip.hit(2, 1));
        assertFalse(horizontalShip.hit(2, 4));
        assertTrue(verticalShip.hit(3, 6));
        assertFalse(verticalShip.hit(3, 1));
    }

    @Test
    public void testOfAliveCellsCount(){
        assertTrue(horizontalShip.hit(1, 1));
        assertTrue(horizontalShip.hit(3, 1));
        assertEquals(2, horizontalShip.getNumberOfAliveCells());
        assertTrue(verticalShip.hit(3, 5));
        assertTrue(verticalShip.hit(3, 6));
        assertEquals(1, verticalShip.getNumberOfAliveCells());
    }

    @Test
    public void testOfShipCellCollision(){
        final Cell bottomCell = new Cell(1, 2);
        final Cell rightCell = new Cell(5, 1);
        assertTrue(horizontalShip.collides(bottomCell));
        assertTrue(horizontalShip.collides(rightCell));
        assertFalse(horizontalShip.collides(new Cell(8, 8)));

        final Cell topCell = new Cell(3, 4);
        final Cell leftCell = new Cell(2, 6);
        assertTrue(verticalShip.collides(topCell));
        assertTrue(verticalShip.collides(leftCell));
        assertFalse(verticalShip.collides(new Cell(7, 8)));
    }

    @Test
    public void testOfShipShipCollision(){
        final Ship hShip = new Ship(2, 2, ShipType.FOUR_DECKED, ShipDirection.HORIZONTAL);
        final Ship vShip = new Ship(3, 0, ShipType.FOUR_DECKED, ShipDirection.VERTICAL);
        final Ship farShip = new Ship(8, 4, ShipType.THREE_DECKED, ShipDirection.VERTICAL);
        assertTrue(hShip.collides(vShip));
        assertFalse(hShip.collides(farShip));
        assertFalse(vShip.collides(farShip));
    }
}
