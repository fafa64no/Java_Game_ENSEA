package main.game.characters.cubes.cube_variants;

import main.game.characters.Target;
import main.game.characters.cubes.CubeHead;
import main.game.characters.cubes.RangedCube;
import main.game.projectiles.MachineGunBullet;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

public class GatlingCube extends RangedCube {
    public GatlingCube(Vec2 position) {
        super(
                position,
                DataGen.getRangedCubeTexture(),
                DataGen.getRangedCubeDeadTexture(),
                DataGen.getRangedCubeDeploymentTextures(),
                DataGen.getRangedCubeRetractionTextures(),
                DataGen.getRangedCubeAttackTextures(),
                0.1,
                Config.gatlingCubeHealth,
                10,
                CubeHead.CUBE_HEAD_MACHINE_GUN,
                Config.largeTileSize,
                MachineGunBullet.getInstance(),
                0,
                Config.cubeSquareFollowRange,
                0.2
        );
    }

    public GatlingCube(Vec2 position, Target target) {
        super(
                position,
                DataGen.getRangedCubeTexture(),
                DataGen.getRangedCubeDeadTexture(),
                DataGen.getRangedCubeDeploymentTextures(),
                DataGen.getRangedCubeRetractionTextures(),
                DataGen.getRangedCubeAttackTextures(),
                0.1,
                Config.gatlingCubeHealth,
                10,
                CubeHead.CUBE_HEAD_MACHINE_GUN,
                Config.largeTileSize,
                MachineGunBullet.getInstance(),
                0,
                Config.cubeSquareFollowRange,
                0.2
        );

        this.currentTarget = target;
        super.startAI();
    }
}
