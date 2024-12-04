package main.rendering.camera;

import main.physics.dynamic_objects.DynamicPoint;
import main.rendering.RenderEngine;
import main.utils.RequiresUpdates;
import main.utils.data.Config;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

import java.awt.*;

public class Camera implements RequiresUpdates {
    private final Vec2 initialOffset;
    private Vec2 offset;
    private Vec2 scale;

    private final double minScale;
    private final double maxScale;
    private int nextZoomFactor=0;

    private DynamicPoint target;

    public Camera(Vec2 offset, Vec2 scale, Vec2 cameraZoomLimits, DynamicPoint target) {
        this.initialOffset = offset;
        this.offset = offset;
        this.scale = scale;

        this.minScale = cameraZoomLimits.x;
        this.maxScale = cameraZoomLimits.y;

        this.target = target;
    }

    @Override
    public void doUpdate() {
        if(nextZoomFactor != 0) {
            scale.x = Math.clamp(scale.x + Config.defaultZoomSpeed * nextZoomFactor, minScale, maxScale);
            scale.y = Math.clamp(scale.y + Config.defaultZoomSpeed * nextZoomFactor, minScale, maxScale);
            nextZoomFactor=0;
        }
        if(target != null) {
            offset = Vec2.add(
                initialOffset,
                target.getPosition(),
                RenderEngine.getMiddleOfFrame()
            );
        }
    }

    public void transformGraphicsToCamera(Graphics2D g2d) {
        g2d.scale(scale.x,scale.y);
        g2d.translate(-offset.x,-offset.y);
    }

    public Vec4 getDisplayWindow(Vec2 extraSize){
        Vec2 cameraCenter;
        if (target == null) {
            cameraCenter = new Vec2();
        } else {
            cameraCenter = target.getPosition();
        }
        Vec2 halfWindowSize = RenderEngine.getMiddleOfFrame();
        Vec4 output = new Vec4();
        output.x = cameraCenter.x + halfWindowSize.x - extraSize.x;     // Min X
        output.y = cameraCenter.x - halfWindowSize.x + extraSize.x;     // Max X
        output.z = cameraCenter.y + halfWindowSize.y - extraSize.y;     // Min Y
        output.w = cameraCenter.y - halfWindowSize.y + extraSize.y;     // Max Y
        return output;
    }

    public void zoom(int factor){
        nextZoomFactor = factor;
    }

    public Vec2 getScale() {
        return scale;
    }
}
