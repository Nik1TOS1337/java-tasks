package academy.tochkavhoda.figures.v3;

public class Square extends Rectangle {

    public Square(Point topLeft, int size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public Square(int x, int y, int size) {
        super(x, y, x + size, y + size);
    }

    public Square(int size) {
        super(0, -size, size, 0);
    }

    public Square() {
        super(0, -1, 1, 0);
    }

    @Override
    public void setTopLeft(Point topLeft) {
        int size = getLength();
        super.setTopLeft(topLeft);
        super.setBottomRight(new Point(topLeft.getX() + size, topLeft.getY() + size));
    }

    public boolean isIntersects(Square square) {
        return super.isIntersects(square);
    }

    public boolean isInside(Square square) {
        return super.isInside(square);
    }
}
