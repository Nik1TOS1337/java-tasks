package academy.tochkavhoda.misc.v3;

import academy.tochkavhoda.figures.v3.Point;
import academy.tochkavhoda.iface.v3.HasArea;
import academy.tochkavhoda.iface.v3.Movable;

import java.util.Objects;

public class House implements Movable, HasArea {
    private Point position;
    private int width;
    private int height;

    public House(Point position, int width, int height) {
        this.position = new Point(position.getX(), position.getY());
        this.width = width;
        this.height = height;
    }

    public House(int x, int y, int width, int height) {
        this.position = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    public House(int width, int height) {
        this(0, 0, width, height);
    }

    public House() {
        this(0, 0, 1, 1);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void moveTo(int x, int y) {
        position.moveTo(x, y);
    }

    @Override
    public void moveRel(int dx, int dy) {
        position.moveRel(dx, dy);
    }

    @Override
    public double getArea() {
        return (double) width * height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return width == house.width &&
                height == house.height &&
                Objects.equals(position, house.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, width, height);
    }
}
