package logic;

/**
 * Created by Lasse on 05-08-2016.
 */
public class ImagePoint {

    private int x, y, value;

    public ImagePoint(int x, int y, int value){
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        ImagePoint point = (ImagePoint) obj;
        return point.getX() == this.getX() && point.getY() == this.getY();
    }
}
