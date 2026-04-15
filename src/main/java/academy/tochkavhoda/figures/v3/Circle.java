package academy.tochkavhoda.figures.v3;

import java.util.Objects;

public class Circle extends Figure {
    private Point center;
    private int radius;

    public Circle(Point center, int radius) {
        this.center = new Point(center.getX(), center.getY());
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius) {
        this.center = new Point(xCenter, yCenter);
        this.radius = radius;
    }

    public Circle(int radius) {
        this(0, 0, radius);
    }

    public Circle() {
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
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean isInside(int x, int y) {
        double dx = x - center.getX();
        double dy = y - center.getY();
        return (dx * dx + dy * dy) <= (long) radius * radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return radius == circle.radius && Objects.equals(center, circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }
}
