package game.hud;

import rendering.RenderEngine;

public class HudManager {
    private final Cursor cursor=new Cursor(
        "img/hud/cursor.png"
    );

    public HudManager(){
        RenderEngine.getInstance().addToRenderList(cursor,0);
        System.out.println(cursor);
    }
}
