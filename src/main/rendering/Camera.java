package main.rendering;

import main.game.DynamicSprite;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public class Camera {
    private Vec2 offset;
    private final Vec2 initialOffset;
    private final Vec2 scale;
    private DynamicSprite targetSprite;

    public Camera(Vec2 offset, Vec2 scale) {
        this.offset = offset;
        this.scale = scale;
        this.initialOffset = this.offset;
    }

    public void setCameraTarget(DynamicSprite targetSprite){
        if(this.targetSprite==null)this.targetSprite=targetSprite;
    }

    public Vec2 getOffset() {
        return offset;
    }

    public Vec2 getScale() {
        return scale;
    }

    public void update(){
        if(this.targetSprite==null)return;
        this.offset= this.targetSprite.getPosition();
        this.offset= Vec2.add(this.targetSprite.getPosition(),RenderEngine.getMiddleOfFrame(),this.initialOffset);
    }
}
