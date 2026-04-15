package academy.tochkavhoda.figures.v2;

import academy.tochkavhoda.iface.v2.Colored;

import java.util.Objects;

public class ColoredCircle extends Circle implements Colored {
    private int color;

    public ColoredCircle(Point center, int radius, int color) {
        super(center, radius);
        this.color = color;
    }

    public ColoredCircle(int xCenter, int yCenter, int radius, int color) {
        super(xCenter, yCenter, radius);
        this.color = color;
    }

    public ColoredCircle(int radius, int color) {
        super(radius);
        this.color = color;
    }

    public ColoredCircle(int color) {
        super(1);
        this.color = color;
    }

    public ColoredCircle() {
        super(1);
        this.color = 1;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColoredCircle that = (ColoredCircle) o;
        return color == that.color &&
                getRadius() == that.getRadius() &&
                Objects.equals(getCenter(), that.getCenter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCenter(), getRadius(), color);
    }
}
