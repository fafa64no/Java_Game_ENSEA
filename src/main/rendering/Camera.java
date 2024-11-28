package main.rendering;

import main.game.DynamicSprite;
import main.game.GameEngine;
import main.utils.RequiresUpdates;
import main.utils.data.Config;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class Camera {
    private Vec2 offset;
    private final Vec2 initialOffset;
    private Vec2 scale;
    private DynamicSprite targetSprite;

    private final double minScale = Config.defaultMinCameraZoom;
    private final double maxScale = Config.defaultMaxCameraZoom;
    private int nextZoomFactor=0;

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
        this.offset= Vec2.add(this.targetSprite.getPosition(),RenderEngine.getMiddleOfFrame(),this.initialOffset);
    }

    public Vec2 getTargetTileOffset(){
        if(targetSprite==null)return new Vec2();
        return new Vec2(
                (targetSprite.getPosition().x+initialOffset.x)/(Config.smallTileSize),
                (targetSprite.getPosition().y+initialOffset.y)/(Config.smallTileSize)
        );
    }

    public Vec2 getTargetOffset(){
        if(targetSprite==null)return new Vec2();
        return new Vec2(
                targetSprite.getPosition().x+initialOffset.x,
                targetSprite.getPosition().y+initialOffset.y
        );
    }

    public Vec4 getDisplayWindow(){
        Vec2 cameraCenter = getTargetOffset();
        Vec2 halfWindowSize = new Vec2(
                RenderEngine.getInstance().getContentPane().getSize().width /(2*scale.x),
                RenderEngine.getInstance().getContentPane().getSize().height/(2*scale.y)
        );
        double lenX = 0.5*Config.smallTileSize*GameEngine.getCurrentLevel().getMap()[0].length;
        double lenY = 0.5*Config.smallTileSize*GameEngine.getCurrentLevel().getMap().length;
        return new Vec4(
                Math.clamp(cameraCenter.x-halfWindowSize.x,-lenX,lenX), // Minimum X
                Math.clamp(cameraCenter.x+halfWindowSize.x,-lenX,lenX), // Maximum X
                Math.clamp(cameraCenter.y-halfWindowSize.y,-lenY,lenY), // Minimum Y
                Math.clamp(cameraCenter.y+halfWindowSize.y,-lenY,lenY)  // Maximum Y
        );
    }

    public Vec4 getDisplayWindow(Vec2 extraSize){
        Vec2 cameraCenter = getTargetOffset();
        Vec2 halfWindowSize = new Vec2(
                RenderEngine.getInstance().getContentPane().getSize().width /(2*scale.x),
                RenderEngine.getInstance().getContentPane().getSize().height/(2*scale.y)
        );
        double lenX = 0.5*Config.smallTileSize*GameEngine.getCurrentLevel().getMap()[0].length;
        double lenY = 0.5*Config.smallTileSize*GameEngine.getCurrentLevel().getMap().length;
        return new Vec4(
                Math.clamp(cameraCenter.x-halfWindowSize.x-extraSize.x,-lenX,lenX), // Minimum X
                Math.clamp(cameraCenter.x+halfWindowSize.x+extraSize.x,-lenX,lenX), // Maximum X
                Math.clamp(cameraCenter.y-halfWindowSize.y-extraSize.y,-lenY,lenY), // Minimum Y
                Math.clamp(cameraCenter.y+halfWindowSize.y+extraSize.y,-lenY,lenY)  // Maximum Y
        );
    }

    public void updateScale(){
        if(nextZoomFactor!=0){
            scale.x=Math.clamp(scale.x+Config.defaultZoomSpeed*nextZoomFactor,minScale,maxScale);
            scale.y=Math.clamp(scale.y+Config.defaultZoomSpeed*nextZoomFactor,minScale,maxScale);
            nextZoomFactor=0;
        }
    }

    public void changeScale(int factor){
        nextZoomFactor=factor;
    }
}
