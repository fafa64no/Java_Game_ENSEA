package game.hud;

import rendering.RenderEngine;

public class HudManager {
    private final Cursor cursor=new Cursor(
        "img/hud/cursor.png"
    );

    public HudManager(RenderEngine renderEngine){
        renderEngine.addToRenderList(cursor,0);
        System.out.println(cursor);
    }
}
