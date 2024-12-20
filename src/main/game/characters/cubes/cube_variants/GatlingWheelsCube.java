package main.game.characters.cubes.cube_variants;

import main.game.characters.Target;
import main.game.characters.cubes.CubeHead;
import main.game.characters.cubes.MovingCube;
import main.game.projectiles.MachineGunBullet;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

public class GatlingWheelsCube extends MovingCube {
    public GatlingWheelsCube(Vec2 position) {
        super(
                position,
                DataGen.getMovingCubeTexture(),
                DataGen.getMovingCubeDeadTexture(),
                DataGen.getMovingCubeDeploymentTextures(),
                DataGen.getMovingCubeRetractionTextures(),
                DataGen.getMovingCubeAttackTextures(),
                0.1,
                Config.movingCubeHealth,
                10,
                CubeHead.CUBE_HEAD_MACHINE_GUN_WHEELS,
                Config.largeTileSize,
                MachineGunBullet.getInstance(),
                0,
                Config.cubeAttackRange,
                Config.cubeSquareFollowRange,
                0.3,
                0.7
        );
    }

    public GatlingWheelsCube(Vec2 position, Target target) {
        super(
                position,
                DataGen.getMovingCubeTexture(),
                DataGen.getMovingCubeDeadTexture(),
                DataGen.getMovingCubeDeploymentTextures(),
                DataGen.getMovingCubeRetractionTextures(),
                DataGen.getMovingCubeAttackTextures(),
                0.1,
                Config.movingCubeHealth,
                10,
                CubeHead.CUBE_HEAD_MACHINE_GUN_WHEELS,
                Config.largeTileSize,
                MachineGunBullet.getInstance(),
                0,
                Config.cubeAttackRange,
                Config.cubeSquareFollowRange,
                0.3,
                0.9
        );

        this.currentTarget = target;
        super.startAI();
    }
}
