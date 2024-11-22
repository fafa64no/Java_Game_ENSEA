package utils.vectors;

public class BVec2 {
    public boolean x;
    public boolean y;

    public BVec2() {
        this.x = true;
        this.y = true;
    }

    public BVec2(boolean x, boolean y) {
        this.x = x;
        this.y = y;
    }

    public boolean isFalse(){
        return (!x)&&(!y);
    }

    @Override
    public String toString() {
        return "BVec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
