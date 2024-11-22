package utils.vectors;

public class IVec2 {
    public int x;
    public int y;

    public IVec2(){
        this.x=0;
        this.y=0;
    }

    public IVec2(int x, int y){
        this.x=x;
        this.y=y;
    }

    public boolean isNull(){
        return (this.x==0)&&(this.y==0);
    }

    public double getLength(){
        return Math.sqrt(this.x*this.x+this.y*this.y);
    }

    public Vec2 normalize(){
        double length=this.getLength();
        return new Vec2(this.x/length,this.y/length);
    }

    public double getAngle(){
        return Math.atan2(y,x);
    }

    public static IVec2 add(IVec2 a, IVec2 b){
        return new IVec2(a.x+b.x,a.y+b.y);
    }

    public static IVec2 add(IVec2 a, IVec2 b, IVec2 c){
        return new IVec2(a.x+b.x+c.x,a.y+b.y+c.y);
    }

    public static IVec2 multiply(IVec2 a, int x){
        return new IVec2(a.x*x,a.y*x);
    }

    @Override
    public String toString() {
        return "IVec2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
