package academy.tochkavhoda.figures.v3;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

public class FigureBag {
    private final Bag<Figure> bag;

    public FigureBag() {
        this.bag = new HashBag<>();
    }

    public void addFigure(Figure figure) {
        bag.add(figure);
    }

    public void addFigure(Figure figure, int count) {
        bag.add(figure, count);
    }

    public boolean removeFigure(Figure figure) {
        if (!bag.contains(figure)) {
            return false;
        }
        bag.remove(figure);
        return true;
    }

    public boolean removeFigure(Figure figure, int count) {
        if (!bag.contains(figure)) {
            return false;
        }
        bag.remove(figure, count);
        return true;
    }

    public int getFigureCopies(Figure figure) {
        return bag.getCount(figure);
    }
}
