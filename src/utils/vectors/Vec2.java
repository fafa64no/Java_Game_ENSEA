package utils.vectors;

public class Vec2 {
    public double x;
    public double y;

    public Vec2(double y, double x) {
        this.y = y;
        this.x = x;
    }

    public static Vec2 add(Vec2 a,  Vec2 b){
        return new Vec2(a.x+b.x,a.y+b.y);
    }
    public static Vec2 add(IVec2 a, IVec2 b){
        return new Vec2(a.x+b.x,a.y+b.y);
    }
    public static Vec2 add(IVec2 a, Vec2 b){
        return new Vec2(a.x+b.x,a.y+b.y);
    }
    public static Vec2 add(Vec2 a,  IVec2 b){
        return new Vec2(a.x+b.x,a.y+b.y);
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
