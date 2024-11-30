package main.utils.vectors;

public class IVec4 {
    public int x;
    public int y;
    public int z;
    public int w;

    public IVec4(){
        this.x=0;
        this.y=0;
        this.z=0;
        this.w=0;
    }

    public IVec4(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public boolean contains(IVec2 position){
        return
            position.x <= this.y &&  // Compare to maximum X
            position.x >= this.x &&  // Compare to minimum X
            position.y <= this.w &&  // Compare to maximum Y
            position.y >= this.z ;   // Compare to minimum Y
    }

    @Override
    public String toString() {
        return "IVec4{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}
