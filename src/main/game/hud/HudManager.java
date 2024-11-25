package main.game.hud;

import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public class HudManager {

    public HudManager(){
        Cursor cursor = new Cursor(
                "assets/textures/hud/cursor.png",
                new Vec2(3, 3),
                new IVec2(9, 9)
        );
        RenderEngine.addToRenderList(cursor, RenderingLayers.RENDERING_LAYER_AIR);
    }
}
