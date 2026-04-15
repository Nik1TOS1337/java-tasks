package academy.tochkavhoda.misc.v3;

import academy.tochkavhoda.figures.v3.Point;
import academy.tochkavhoda.iface.v3.HasArea;
import academy.tochkavhoda.iface.v3.Movable;
import academy.tochkavhoda.iface.v3.Resizable;

import java.util.Objects;

public class Coin implements Movable, Resizable, HasArea {
    private Point center;
    private int radius;

    public Coin(Point center, int radius) {
        this.center = new Point(center.getX(), center.getY());
        this.radius = radius;
    }

    public Coin(int x, int y, int radius) {
        this.center = new Point(x, y);
        this.radius = radius;
    }

    public Coin(int radius) {
        this(0, 0, radius);
    }

    public Coin() {
        this(0, 0, 1);
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void moveTo(int x, int y) {
        center.moveTo(x, y);
    }

    @Override
    public void moveRel(int dx, int dy) {
        center.moveRel(dx, dy);
    }

    @Override
    public void resize(double ratio) {
        radius = (int) (radius * ratio);
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return radius == coin.radius && Objects.equals(center, coin.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }
}
