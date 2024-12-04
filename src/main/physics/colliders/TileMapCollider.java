package main.physics.colliders;

import main.game.level.Level;
import main.physics.ColliderType;
import main.physics.Collision;
import main.physics.layers.CollisionLayer;
import main.utils.data.CollisionConfig;
import main.utils.vectors.IVec2;
import main.utils.vectors.IVec4;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class TileMapCollider extends Collider{
    private final Level mapParent;
    private final char[][] map;
    private final Vec4[][] collisionBoxMap;

    private final int tileSize;
    private final double inverseTileSize;

    public TileMapCollider(
            boolean inverted,
            double friction,
            double modifier,
            ColliderType colliderType,
            CollisionLayer collisionLayer,
            Vec2 offset,
            Level mapParent,
            int tileSize
    ) {
        super(
                inverted,
                friction,
                modifier,
                colliderType,
                collisionLayer,
                offset,
                null
        );
        this.mapParent = mapParent;
        this.map = mapParent.getMap();
        this.collisionBoxMap = new Vec4[map.length][map[0].length];

        this.tileSize = tileSize;
        this.inverseTileSize = 1.0/tileSize;

        initCollisionBoxes();
    }

    @Override
    protected Collision boxColliderHandler(BoxCollider bc, Vec2 relativeVelocity) {
        return Collision.getReverseCollision(bc.doCollide(this,relativeVelocity));
    }

    @Override
    protected Collision tileMapColliderHandler(TileMapCollider tc, Vec2 relativeVelocity) {
        System.out.println("Warning : Testing collisions between two tile maps.\n\t-> returning null.");
        return null;
    }

    @Override
    protected Collision pointColliderHandler(PointCollider pc, Vec2 relativeVelocity) {
        return Collision.getReverseCollision(pc.doCollide(this,relativeVelocity));
    }

    public Vec4[] getCollisionBoxes(Vec2 offset){
        Vec2 windowOffset = Vec2.substract(
                offset,
                mapParent.getMapOffset(),
                this.offset
        );
        Vec2 windowOffset2 = Vec2.multiply(windowOffset,inverseTileSize);
        IVec4 collisionWindow = getCollisionWindow(windowOffset2);
        Vec4[] output = new Vec4[(collisionWindow.y-collisionWindow.x)*(collisionWindow.w-collisionWindow.z)];
        int i=0;
        for (int x = collisionWindow.x; x < collisionWindow.y; x++){
            for (int y = collisionWindow.z; y < collisionWindow.w; y++){
                output[i]=getCollisionBoxWithOffset(new IVec2(x,y));
                i++;
            }
        }
        return output;
    }

    private Vec4 getCollisionBoxWithOffset(IVec2 position) {
        Vec4 collisionBox = getCollisionBox(position);
        if(collisionBox != null) {
            Vec4 outputOffset = new Vec4(
                    position.x - 0.5*map[0].length,
                    position.x - 0.5*map[0].length,
                    position.y - 0.5*map.length,
                    position.y - 0.5*map.length
            );
            collisionBox = Vec4.multiply(
                    Vec4.add(collisionBox, outputOffset),
                    tileSize
            );
        }
        return collisionBox;
    }

    private Vec4 getCollisionBox(IVec2 position) {
        return collisionBoxMap[position.y][position.x];
    }

    private IVec4 getCollisionWindow(Vec2 offset){
        Vec2 halfWindowSize = new Vec2(CollisionConfig.tileMapColliderRange);
        return new IVec4(
                (int) Math.floor(Math.clamp(offset.x-halfWindowSize.x,0,map[0].length)),        // Minimum X
                (int) Math.ceil( Math.clamp(offset.x+halfWindowSize.x,0,map[0].length)),        // Maximum X
                (int) Math.floor(Math.clamp(offset.y-halfWindowSize.y,0,map.length)),           // Minimum Y
                (int) Math.ceil( Math.clamp(offset.y+halfWindowSize.y,0,map.length))            // Maximum Y
        );
    }

    private void initCollisionBoxes(){
        for (int x = 0; x < collisionBoxMap[0].length; x++) {
            for (int y = 0; y < collisionBoxMap.length; y++) {
                collisionBoxMap[y][x] = switch (map[y][x]){
                    case 'R' -> CollisionConfig.wallCollisionBox;
                    case 'T' -> CollisionConfig.treeCollisionBox;
                    default -> null;
                };
            }
        }
    }
}
