package main.game.hud;

import main.game.GameEngine;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayers;
import main.utils.Engine;
import main.utils.data.DataGen;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public class HudEngine implements Engine {
    private static HudEngine instance = null;

    private final Cursor cursor = new Cursor(
            "assets/textures/hud/cursor.png",
            new Vec2(3, 3),
            new IVec2(9, 9)
    );

    private final HealthBar healthBar;

    public HudEngine(){
        if(instance == null)instance = this;

        healthBar = new HealthBar(DataGen.getBlueBarTexture());

        RenderEngine.addToRenderList(cursor, RenderingLayers.RENDERING_LAYER_HUD);
        RenderEngine.addToRenderList(healthBar, RenderingLayers.RENDERING_LAYER_HUD);
    }

    public static HudEngine getInstance(){
        return instance;
    }

    @Override
    public void update() {
        healthBar.updateTarget();
    }
}
