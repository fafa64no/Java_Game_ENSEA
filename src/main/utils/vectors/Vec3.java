package main.utils.vectors;

public class Vec3 {
    public double x;
    public double y;
    public double z;

    public Vec3(){
        this.x=0;
        this.y=0;
        this.z=0;
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3 add(Vec3 a, Vec3 b){
        return new Vec3(
                a.x+b.x,
                a.y+b.y,
                a.z+b.z

        );
    }

    public static Vec3 multiply(Vec3 a, double t){
        return new Vec3(
                a.x*t,
                a.y*t,
                a.z*t
        );
    }
}
