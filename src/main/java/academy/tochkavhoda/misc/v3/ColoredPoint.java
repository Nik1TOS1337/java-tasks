package academy.tochkavhoda.misc.v3;

import academy.tochkavhoda.colors.v3.Color;
import academy.tochkavhoda.colors.v3.ColorErrorCode;
import academy.tochkavhoda.colors.v3.ColorException;
import academy.tochkavhoda.figures.v3.Point;
import academy.tochkavhoda.iface.v3.Colored;
import academy.tochkavhoda.iface.v3.Movable;

import java.util.Objects;

public class ColoredPoint implements Movable, Colored {
    private int x;
    private int y;
    private Color color;

    public ColoredPoint(int x, int y, Color color) throws ColorException {
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public ColoredPoint(int x, int y, String colorString) throws ColorException {
        this(x, y, Color.colorFromString(colorString));
    }

    public ColoredPoint() {
        this.x = 0;
        this.y = 0;
        this.color = Color.RED;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) throws ColorException {
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    @Override
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void moveRel(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColoredPoint that = (ColoredPoint) o;
        return x == that.x && y == that.y && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color);
    }
}
