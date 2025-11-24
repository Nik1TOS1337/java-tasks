package academy.tochkavhoda.figures.v1;

public class Square extends Rectangle {

    public Square(Point leftTop, int size) {
        super(leftTop, new Point(leftTop.getX() + size, leftTop.getY() + size));
    }

    public Square(int xLeft, int yTop, int size) {
        super(xLeft, yTop, xLeft + size, yTop + size);
    }

    public Square(int size) {
        super(size, size);
    }

    public Square() {
        super(1, 1);
    }

    @Override
    public void setTopLeft(Point topLeft) {
        int size = getLength();
        super.setTopLeft(topLeft);
        this.setBottomRight(new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public boolean isIntersects(Square square) {
        return super.isIntersects(square);
    }

    public boolean isInside(Square square) {
        return super.isInside(square);
    }
}