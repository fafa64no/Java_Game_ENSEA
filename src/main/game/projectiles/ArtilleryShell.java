package main.game.projectiles;

import main.physics.CollisionLayers;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

public class ArtilleryShell extends BasicProjectileHandler {
    private static ArtilleryShell instance = null;

    public ArtilleryShell(){
        super(DataGen.getArtilleryShellTexture(),4000,5,10,CollisionLayers.COLLISION_LAYER_ENNEMY_PROJECTILES);
        if(instance==null)instance=this;
    }

    public static ArtilleryShell getInstance(){
        if(instance!=null)return instance;
        return new ArtilleryShell();
    }

    @Override
    public void fireInDirection(Vec2 initialPosition, double rotation) {
        projectiles[projectilePointer]=new Projectile(
                projectileLifeSpan,
                10,
                initialPosition,
                projectileSpeed,
                rotation,
                projectilePointer,
                this,
                collisionLayer,
                modifier,
                textures.length,
                10
        );
        projectilePointer=(projectilePointer+1)%projectiles.length;
    }
}
