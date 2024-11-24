package main.physics.colliders;

import main.game.level.Level;
import main.physics.Collision;
import main.utils.data.Config;
import main.utils.vectors.IVec2;
import main.utils.vectors.IVec4;
import main.utils.vectors.Vec2;
import main.utils.vectors.Vec4;

public class TilemapCollider extends SolidCollider{
    private final char[][] map;
    private final Vec4[][] collisionBoxMap;
    private final Level parent;

    private final Vec4 wallCollisionBox = new Vec4(
            0,
            1,
            0,
            1
    );
    private final Vec4 treeCollisionBox = new Vec4(
            0.5-Config.treeHitBoxSize * 0.5,
            0.5+Config.treeHitBoxSize * 0.5,
            0.5-Config.treeHitBoxSize * 0.5,
            0.5+Config.treeHitBoxSize * 0.5
    );

    public TilemapCollider(Level parent) {
        super(false, 0.5, parent.getMapOffset());
        this.parent = parent;
        this.map = parent.getMap();
        this.collisionBoxMap = new Vec4[map.length][map[0].length];

        initCollisionBoxes();
    }

    private void initCollisionBoxes(){
        for (int x = 0; x < collisionBoxMap[0].length; x++) {
            for (int y = 0; y < collisionBoxMap.length; y++) {
                collisionBoxMap[y][x] = switch (map[y][x]){
                    case 'R' -> wallCollisionBox;
                    case 'T' -> treeCollisionBox;
                    default -> null;
                };
            }
        }
    }

    public IVec4 getCollisionWindow(Vec2 offset){
        Vec2 halfWindowSize = new Vec2(Config.tilemapColliderRange);
        return new IVec4(
                (int) Math.floor(Math.clamp(offset.x-halfWindowSize.x,0,map[0].length)),        // Minimum X
                (int) Math.ceil( Math.clamp(offset.x+halfWindowSize.x,0,map[0].length)),        // Maximum X
                (int) Math.floor(Math.clamp(offset.y-halfWindowSize.y,0,map.length)),           // Minimum Y
                (int) Math.ceil( Math.clamp(offset.y+halfWindowSize.y,0,map.length))            // Maximum Y
        );
    }

    public Vec4 getCollisionBox(IVec2 position){
        return collisionBoxMap[position.y][position.x];
    }

    public Vec4[] getCollisionBoxes(Vec2 offset){
        Vec2 windowOffset = Vec2.substract(
                offset,
                super.offset
        );
        Vec2 windowOffset2 = Vec2.multiply(windowOffset,1.0/Config.smallTileSize);
        IVec4 collisionWindow = getCollisionWindow(windowOffset2);
        Vec4[] output = new Vec4[(collisionWindow.y-collisionWindow.x)*(collisionWindow.w-collisionWindow.z)];
        int i=0;
        for (int x = collisionWindow.x; x < collisionWindow.y; x++){
            for (int y = collisionWindow.z; y < collisionWindow.w; y++){
                output[i]=getCollisionBox(new IVec2(x,y));
                if(output[i]!=null) {
                    Vec4 outputOffset = new Vec4(
                            x - map[0].length/2.0,
                            x - map[0].length/2.0,
                            y - map.length/2.0,
                            y - map.length/2.0
                    );
                    output[i] = Vec4.multiply(
                            Vec4.add(output[i], outputOffset),
                            Config.smallTileSize
                    );
                    i++;
                }
            }
        }
        return output;
    }

    @Override
    public Vec2 getOffset() {
        return new Vec2();
    }

    @Override
    public Collision doCollide(Collider c, Vec2 offset) {
        return null;
    }
}
