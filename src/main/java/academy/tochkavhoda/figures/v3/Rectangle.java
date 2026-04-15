package academy.tochkavhoda.figures.v3;

import academy.tochkavhoda.iface.v3.Stretchable;

import java.util.Objects;

public class Rectangle extends Figure implements Stretchable {
    private Point topLeft;
    private Point bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = new Point(topLeft.getX(), topLeft.getY());
        this.bottomRight = new Point(bottomRight.getX(), bottomRight.getY());
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom) {
        this.topLeft = new Point(xLeft, yTop);
        this.bottomRight = new Point(xRight, yBottom);
    }

    public Rectangle(int length, int width) {
        this.topLeft = new Point(0, -width);
        this.bottomRight = new Point(length, 0);
    }

    public Rectangle() {
        this(1, 1);
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public int getLength() {
        return bottomRight.getX() - topLeft.getX();
    }

    public int getWidth() {
        return bottomRight.getY() - topLeft.getY();
    }

    @Override
    public void moveTo(int x, int y) {
        int length = getLength();
        int width = getWidth();
        topLeft.moveTo(x, y);
        bottomRight.moveTo(x + length, y + width);
    }

    @Override
    public void moveRel(int dx, int dy) {
        topLeft.moveRel(dx, dy);
        bottomRight.moveRel(dx, dy);
    }

    @Override
    public void resize(double ratio) {
        int newLength = (int) (getLength() * ratio);
        int newWidth = (int) (getWidth() * ratio);
        bottomRight.setX(topLeft.getX() + newLength);
        bottomRight.setY(topLeft.getY() + newWidth);
    }

    @Override
    public void stretch(double xRatio, double yRatio) {
        int newLength = (int) (getLength() * xRatio);
        int newWidth = (int) (getWidth() * yRatio);
        bottomRight.setX(topLeft.getX() + newLength);
        bottomRight.setY(topLeft.getY() + newWidth);
    }

    @Override
    public double getArea() {
        return getLength() * getWidth();
    }

    @Override
    public double getPerimeter() {
        return 2 * (getLength() + getWidth());
    }

    @Override
    public boolean isInside(int x, int y) {
        return x >= topLeft.getX() && x <= bottomRight.getX() &&
                y >= topLeft.getY() && y <= bottomRight.getY();
    }

    public boolean isIntersects(Rectangle rectangle) {
        return !(rectangle.topLeft.getX() > this.bottomRight.getX() ||
                rectangle.bottomRight.getX() < this.topLeft.getX() ||
                rectangle.topLeft.getY() > this.bottomRight.getY() ||
                rectangle.bottomRight.getY() < this.topLeft.getY());
    }

    public boolean isInside(Rectangle rectangle) {
        return isInside(rectangle.topLeft) && isInside(rectangle.bottomRight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(topLeft, rectangle.topLeft) &&
                Objects.equals(bottomRight, rectangle.bottomRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight);
    }
}
