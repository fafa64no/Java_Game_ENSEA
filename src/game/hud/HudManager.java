package game.hud;

import rendering.RenderEngine;
import utils.vectors.IVec2;
import utils.vectors.Vec2;

public class HudManager {
    private final Cursor cursor=new Cursor(
        "assets/textures/hud/cursor.png",
            new Vec2(3,3),
            new IVec2(9,9)
    );

    public HudManager(){
        RenderEngine.addToRenderList(cursor,0);
    }
}
