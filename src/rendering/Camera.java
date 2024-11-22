package rendering;

import game.DynamicSprite;
import utils.vectors.IVec2;
import utils.vectors.Vec2;

public class Camera {
    private IVec2 offset;
    private final IVec2 initialOffset;
    private final Vec2 scale;
    private DynamicSprite targetSprite;

    public Camera(IVec2 offset, Vec2 scale) {
        this.offset = offset;
        this.scale = scale;
        this.initialOffset = this.offset;
    }

    public void setCameraTarget(DynamicSprite targetSprite){
        if(this.targetSprite==null)this.targetSprite=targetSprite;
    }

    public IVec2 getOffset() {
        return offset;
    }

    public Vec2 getScale() {
        return scale;
    }

    public void update(){
        if(this.targetSprite==null)return;
        this.offset= this.targetSprite.getPosition();
        this.offset= IVec2.add(this.targetSprite.getPosition(),RenderEngine.getMiddleOfFrame(),this.initialOffset);
    }
}
