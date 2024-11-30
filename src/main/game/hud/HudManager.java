package main.game.hud;

import main.game.GameEngine;
import main.rendering.RenderEngine;
import main.rendering.RenderingLayer;
import main.utils.RequiresUpdates;
import main.utils.data.DataGen;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

public class HudManager implements RequiresUpdates {
    private static HudManager instance = null;

    private final Cursor cursor = new Cursor(
            "assets/textures/hud/cursor.png",
            new Vec2(3, 3),
            new IVec2(9, 9)
    );

    private final HealthBar healthBar;

    public HudManager(){
        if(instance == null)instance = this;

        healthBar = new HealthBar(DataGen.getBlueBarTexture());

        RenderEngine.addToRenderList(cursor, RenderingLayer.RENDERING_LAYER_HUD);
        RenderEngine.addToRenderList(healthBar, RenderingLayer.RENDERING_LAYER_HUD);

        GameEngine.addRequiresUpdates(this);
    }

    public static HudManager getInstance(){
        return instance;
    }

    @Override
    public void updateRemainingTime() {
        healthBar.updateTarget();
    }
}
