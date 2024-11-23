package main.utils.vectors;

import main.utils.data.Cfg;

public class Vec2 {
    public double x;
    public double y;

    public Vec2(){
        this.x=0;
        this.y=0;
    }

    public Vec2(double x, double y) {
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
    public static Vec2 add(Vec2 a, Vec2 b,  Vec2 c){
        return new Vec2(a.x+b.x+c.x,a.y+b.y+c.y);
    }

    public static Vec2 multiply(Vec2 a, double x){
        return new Vec2(a.x*x,a.y*x);
    }

    public static Vec2 substract(Vec2 a, Vec2 b){
        return new Vec2(a.x-b.x,a.y-b.y);
    }

    private Vec2 normalize(){
        double length=getLength();
        return new Vec2(
                x/length,
                y/length
        );
    }

    public Vec2 getClosestPoint(Vec2 target, double maxDistance){
        Vec2 offset = Vec2.substract(target,this);
        double lengthToTravel = Math.min(maxDistance,offset.getLength());
        return Vec2.add(Vec2.multiply(offset.normalize(),lengthToTravel),this);
    }

    public double getSquareLength(){
        return x*x+y*y;
    }

    public double getLength(){
        return Math.sqrt(getSquareLength());
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

    public boolean isNull() {
        return (Math.abs(x) < Cfg.minimumVectorSize)&&(Math.abs(y) < Cfg.minimumVectorSize);
    }
}
