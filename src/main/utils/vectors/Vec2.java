package main.utils.vectors;

public class Vec2 {
    public double x;
    public double y;

    public Vec2(){
        this.x=0;
        this.y=0;
    }

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

    public static Vec2 multiply(Vec2 a, double x){
        return new Vec2(a.x*x,a.y*x);
    }

    public double getAngle(){
        return Math.atan2(y,x);
    }

    @Override
    public String toString() {
        return "Vec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
