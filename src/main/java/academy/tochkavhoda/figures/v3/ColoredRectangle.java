package academy.tochkavhoda.figures.v3;

import academy.tochkavhoda.colors.v3.Color;
import academy.tochkavhoda.colors.v3.ColorErrorCode;
import academy.tochkavhoda.colors.v3.ColorException;
import academy.tochkavhoda.iface.v3.Colored;

import java.util.Objects;

public class ColoredRectangle extends Rectangle implements Colored {
    private Color color;

    public ColoredRectangle(Point topLeft, Point bottomRight, Color color) throws ColorException {
        super(topLeft, bottomRight);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredRectangle(Point topLeft, Point bottomRight, String colorString) throws ColorException {
        this(topLeft, bottomRight, Color.colorFromString(colorString));
    }

    public ColoredRectangle(int xLeft, int yTop, int xRight, int yBottom, Color color) throws ColorException {
        super(xLeft, yTop, xRight, yBottom);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredRectangle(int xLeft, int yTop, int xRight, int yBottom, String colorString) throws ColorException {
        this(xLeft, yTop, xRight, yBottom, Color.colorFromString(colorString));
    }

    public ColoredRectangle(int length, int width, Color color) throws ColorException {
        super(length, width);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredRectangle(int length, int width, String colorString) throws ColorException {
        this(length, width, Color.colorFromString(colorString));
    }

    public ColoredRectangle(Color color) throws ColorException {
        super(1, 1);
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    public ColoredRectangle(String colorString) throws ColorException {
        this(Color.colorFromString(colorString));
    }

    public ColoredRectangle() {
        super(1, 1);
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
