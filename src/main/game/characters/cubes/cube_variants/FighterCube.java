package main.game.characters.cubes.cube_variants;

import main.game.characters.Target;
import main.game.characters.cubes.CubeHead;
import main.game.characters.cubes.PlaneCube;
import main.game.projectiles.AerialMachineGunBullet;
import main.game.projectiles.MachineGunBullet;
import main.utils.data.Config;
import main.utils.data.DataGen;
import main.utils.vectors.Vec2;

public class FighterCube extends PlaneCube {
    public FighterCube(Vec2 position) {
        super(
                position,
                DataGen.getFighterCubeTexture(),
                DataGen.getFighterCubeDeadTexture(),
                DataGen.getFighterCubeDeploymentTextures(),
                DataGen.getFighterCubeRetractionTextures(),
                DataGen.getFighterCubeAttackTextures(),
                0.15,
                Config.fighterCubeHealth,
                10,
                CubeHead.CUBE_HEAD_FIGHTER,
                Config.largeTileSize,
                AerialMachineGunBullet.getInstance(),
                0,
                Config.cubeAttackRange,
                0,
                0.05,
                1.5,
                100
        );
    }

    public FighterCube(Vec2 position, Target target) {
        super(
                position,
                DataGen.getFighterCubeTexture(),
                DataGen.getFighterCubeDeadTexture(),
                DataGen.getFighterCubeDeploymentTextures(),
                DataGen.getFighterCubeRetractionTextures(),
                DataGen.getFighterCubeAttackTextures(),
                0.15,
                Config.fighterCubeHealth,
                10,
                CubeHead.CUBE_HEAD_FIGHTER,
                Config.largeTileSize,
                AerialMachineGunBullet.getInstance(),
                0,
                Config.cubeAttackRange,
                0,
                0.1,
                1.5,
                100
        );

        this.currentTarget = target;
        super.startAI();
    }
}
