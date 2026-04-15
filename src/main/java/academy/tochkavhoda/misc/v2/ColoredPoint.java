package academy.tochkavhoda.misc.v2;

import academy.tochkavhoda.figures.v2.Point;
import academy.tochkavhoda.iface.v2.Colored;
import academy.tochkavhoda.iface.v2.Movable;

import java.util.Objects;

public class ColoredPoint implements Movable, Colored {
    private int x;
    private int y;
    private int color;

    public ColoredPoint(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public ColoredPoint() {
        this(0, 0, 1);
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
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
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
