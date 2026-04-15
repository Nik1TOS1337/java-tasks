package academy.tochkavhoda.misc.v3;

import academy.tochkavhoda.colors.v3.Color;
import academy.tochkavhoda.colors.v3.ColorErrorCode;
import academy.tochkavhoda.colors.v3.ColorException;
import academy.tochkavhoda.figures.v3.Point;
import academy.tochkavhoda.iface.v3.Colored;
import academy.tochkavhoda.iface.v3.HasArea;
import academy.tochkavhoda.iface.v3.Movable;
import academy.tochkavhoda.iface.v3.Resizable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMisc {

    private static final double DOUBLE_EPS = 1E-6;

    // --- ColoredPoint tests ---

    @Test
    public void testColoredPointConstructors() throws ColorException {
        ColoredPoint cp1 = new ColoredPoint(10, 20, Color.GREEN);
        assertEquals(10, cp1.getX());
        assertEquals(20, cp1.getY());
        assertEquals(Color.GREEN, cp1.getColor());

        ColoredPoint cp2 = new ColoredPoint(5, 7, "BLUE");
        assertEquals(5, cp2.getX());
        assertEquals(7, cp2.getY());
        assertEquals(Color.BLUE, cp2.getColor());

        ColoredPoint cp3 = new ColoredPoint();
        assertEquals(0, cp3.getX());
        assertEquals(0, cp3.getY());
        assertEquals(Color.RED, cp3.getColor());
    }

    @Test
    public void testColoredPointNullColorThrows() {
        assertThrows(ColorException.class, () -> new ColoredPoint(0, 0, (Color) null));
        assertThrows(ColorException.class, () -> new ColoredPoint(0, 0, (String) null));
    }

    @Test
    public void testColoredPointWrongColorThrows() {
        try {
            new ColoredPoint(0, 0, "YELLOW");
            fail();
        } catch (ColorException ex) {
            assertEquals(ColorErrorCode.WRONG_COLOR_STRING, ex.getErrorCode());
        }
    }

    @Test
    public void testColoredPointMove() throws ColorException {
        ColoredPoint cp = new ColoredPoint(5, 10, Color.GREEN);
        cp.moveRel(3, -5);
        assertEquals(8, cp.getX());
        assertEquals(5, cp.getY());

        cp.moveTo(100, 200);
        assertEquals(100, cp.getX());
        assertEquals(200, cp.getY());

        cp.moveTo(new Point(0, 0));
        assertEquals(0, cp.getX());
        assertEquals(0, cp.getY());
    }

    @Test
    public void testColoredPointSetColor() throws ColorException {
        ColoredPoint cp = new ColoredPoint(0, 0, Color.RED);
        cp.setColor(Color.BLUE);
        assertEquals(Color.BLUE, cp.getColor());

        cp.setColor("GREEN");
        assertEquals(Color.GREEN, cp.getColor());

        assertThrows(ColorException.class, () -> cp.setColor((Color) null));
        assertThrows(ColorException.class, () -> cp.setColor((String) null));
        assertThrows(ColorException.class, () -> cp.setColor("INVALID"));
    }

    @Test
    public void testColoredPointEquals() throws ColorException {
        ColoredPoint cp1 = new ColoredPoint(10, 20, Color.GREEN);
        ColoredPoint cp2 = new ColoredPoint(10, 20, Color.GREEN);
        ColoredPoint cp3 = new ColoredPoint(10, 20, Color.RED);
        ColoredPoint cp4 = new ColoredPoint(0, 20, Color.GREEN);
        assertEquals(cp1, cp2);
        assertNotEquals(cp1, cp3);
        assertNotEquals(cp1, cp4);
    }

    @Test
    public void testColoredPointImplementsInterfaces() throws ColorException {
        ColoredPoint cp = new ColoredPoint(1, 2, Color.RED);
        assertTrue(cp instanceof Movable);
        assertTrue(cp instanceof Colored);
    }

    // --- Coin tests ---

    @Test
    public void testCoinConstructors() {
        Coin c1 = new Coin(new Point(5, 10), 7);
        assertEquals(5, c1.getCenter().getX());
        assertEquals(10, c1.getCenter().getY());
        assertEquals(7, c1.getRadius());

        Coin c2 = new Coin(3, 4, 6);
        assertEquals(3, c2.getCenter().getX());
        assertEquals(4, c2.getCenter().getY());
        assertEquals(6, c2.getRadius());

        Coin c3 = new Coin(5);
        assertEquals(0, c3.getCenter().getX());
        assertEquals(5, c3.getRadius());

        Coin c4 = new Coin();
        assertEquals(1, c4.getRadius());
    }

    @Test
    public void testCoinMove() {
        Coin coin = new Coin(10, 20, 5);
        coin.moveRel(5, -10);
        assertEquals(15, coin.getCenter().getX());
        assertEquals(10, coin.getCenter().getY());

        coin.moveTo(0, 0);
        assertEquals(0, coin.getCenter().getX());

        coin.moveTo(new Point(100, 200));
        assertEquals(100, coin.getCenter().getX());
        assertEquals(200, coin.getCenter().getY());
    }

    @Test
    public void testCoinResize() {
        Coin coin = new Coin(0, 0, 10);
        coin.resize(3);
        assertEquals(30, coin.getRadius());

        coin.resize(0.5);
        assertEquals(15, coin.getRadius());
    }

    @Test
    public void testCoinArea() {
        Coin coin = new Coin(0, 0, 10);
        assertEquals(Math.PI * 100, coin.getArea(), DOUBLE_EPS);
    }

    @Test
    public void testCoinEquals() {
        Coin c1 = new Coin(new Point(1, 2), 5);
        Coin c2 = new Coin(1, 2, 5);
        Coin c3 = new Coin(new Point(1, 2), 6);
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
    }

    @Test
    public void testCoinImplementsInterfaces() {
        Coin coin = new Coin(1, 2, 3);
        assertTrue(coin instanceof Movable);
        assertTrue(coin instanceof Resizable);
        assertTrue(coin instanceof HasArea);
    }

    // --- House tests ---

    @Test
    public void testHouseConstructors() {
        House h1 = new House(new Point(1, 2), 10, 20);
        assertEquals(1, h1.getPosition().getX());
        assertEquals(10, h1.getWidth());
        assertEquals(20, h1.getHeight());

        House h2 = new House(3, 4, 15, 25);
        assertEquals(3, h2.getPosition().getX());
        assertEquals(15, h2.getWidth());

        House h3 = new House(5, 8);
        assertEquals(5, h3.getWidth());
        assertEquals(8, h3.getHeight());

        House h4 = new House();
        assertEquals(1, h4.getWidth());
    }

    @Test
    public void testHouseMove() {
        House house = new House(0, 0, 10, 5);
        house.moveRel(3, 7);
        assertEquals(3, house.getPosition().getX());
        assertEquals(7, house.getPosition().getY());

        house.moveTo(100, 200);
        assertEquals(100, house.getPosition().getX());

        house.moveTo(new Point(50, 60));
        assertEquals(50, house.getPosition().getX());
        assertEquals(60, house.getPosition().getY());
    }

    @Test
    public void testHouseArea() {
        assertEquals(50.0, new House(0, 0, 10, 5).getArea(), DOUBLE_EPS);
        assertEquals(56.0, new House(0, 0, 7, 8).getArea(), DOUBLE_EPS);
    }

    @Test
    public void testHouseEquals() {
        House h1 = new House(new Point(1, 2), 10, 20);
        House h2 = new House(1, 2, 10, 20);
        House h3 = new House(new Point(1, 2), 10, 21);
        assertEquals(h1, h2);
        assertNotEquals(h1, h3);
    }

    @Test
    public void testHouseImplementsInterfaces() {
        House house = new House(1, 2, 3, 4);
        assertTrue(house instanceof Movable);
        assertTrue(house instanceof HasArea);
    }
}
