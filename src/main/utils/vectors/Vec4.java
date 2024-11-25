package main.utils.vectors;

public class Vec4 {
    public double x;
    public double y;
    public double z;
    public double w;

    public Vec4(){
        this.x=0;
        this.y=0;
        this.z=0;
        this.w=0;
    }

    public Vec4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Vec4 add(Vec4 a, Vec4 b){
        return new Vec4(
                a.x+b.x,
                a.y+b.y,
                a.z+b.z,
                a.w+b.w

        );
    }

    public static Vec4 multiply(Vec4 a, double t){
        return new Vec4(
                a.x*t,
                a.y*t,
                a.z*t,
                a.w*t
        );
    }

    public static Vec4 offset(Vec4 a, Vec2 offset1, Vec2 offset2){
        return new Vec4(
                a.x+offset1.x+offset2.x,
                a.y+offset1.x+offset2.x,
                a.z+offset1.y+offset2.y,
                a.w+offset1.y+offset2.y
        );
    }

    public boolean contains(Vec2 position){
        return
                position.x <= this.y &&  // Compare to maximum X
                position.x >= this.x &&  // Compare to minimum X
                position.y <= this.w &&  // Compare to maximum Y
                position.y >= this.z ;   // Compare to minimum Y
    }

    public Vec4 makeItFat(Vec2 additionalSize){
        return new Vec4(
                this.x-additionalSize.x,
                this.y+additionalSize.x,
                this.z-additionalSize.y,
                this.w+additionalSize.y
        );
    }

    @Override
    public String toString() {
        return "Vec4{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
