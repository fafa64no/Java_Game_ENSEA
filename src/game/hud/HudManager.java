package game.hud;

import rendering.RenderEngine;

public class HudManager {
    private final Cursor cursor=new Cursor(
        "img/hud/cursor.png"
    );

    public HudManager(){
        RenderEngine.addToRenderList(cursor,0);
    }
}
