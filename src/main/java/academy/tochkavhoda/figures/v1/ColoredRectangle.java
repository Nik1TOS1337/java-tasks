package academy.tochkavhoda.figures.v1;

import java.util.Objects;

public class ColoredRectangle extends Rectangle {
    private int color;

    public ColoredRectangle(Point topLeft, Point bottomRight, int color) {
        super(topLeft, bottomRight);
        this.color = color;
    }

    public ColoredRectangle(int xLeft, int yTop, int xRight, int yBottom, int color) {
        super(xLeft, yTop, xRight, yBottom);
        this.color = color;
    }

    public ColoredRectangle(int length, int width, int color) {
        super(length, width);
        this.color = color;
    }

    public ColoredRectangle(int color) {
        super(1, 1);
        this.color = color;
    }

    public ColoredRectangle() {
        super(1, 1);
        this.color = 1;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isIntersects(ColoredRectangle rectangle) {
        return super.isIntersects(rectangle);
    }

    public boolean isInside(ColoredRectangle rectangle) {
        return super.isInside(rectangle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColoredRectangle that = (ColoredRectangle) o;
        return color == that.color &&
                Objects.equals(getTopLeft(), that.getTopLeft()) &&
                Objects.equals(getBottomRight(), that.getBottomRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTopLeft(), getBottomRight(), color);
    }
}
