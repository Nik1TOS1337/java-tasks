package academy.tochkavhoda.figures.v3;

import academy.tochkavhoda.colors.v3.Color;
import academy.tochkavhoda.colors.v3.ColorErrorCode;
import academy.tochkavhoda.colors.v3.ColorException;
import academy.tochkavhoda.iface.v3.Colored;

import java.util.Objects;

public class ColoredCircle extends Circle implements Colored {
    private Color color;

    public ColoredCircle(Point center, int radius, Color color) throws ColorException {
        super(center, radius);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredCircle(Point center, int radius, String colorString) throws ColorException {
        this(center, radius, Color.colorFromString(colorString));
    }

    public ColoredCircle(int xCenter, int yCenter, int radius, Color color) throws ColorException {
        super(xCenter, yCenter, radius);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredCircle(int xCenter, int yCenter, int radius, String colorString) throws ColorException {
        this(xCenter, yCenter, radius, Color.colorFromString(colorString));
    }

    public ColoredCircle(int radius, Color color) throws ColorException {
        super(radius);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredCircle(int radius, String colorString) throws ColorException {
        this(radius, Color.colorFromString(colorString));
    }

    public ColoredCircle(Color color) throws ColorException {
        super(1);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredCircle(String colorString) throws ColorException {
        this(Color.colorFromString(colorString));
    }

    public ColoredCircle() {
        super(1);
        this.color = Color.RED;
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
