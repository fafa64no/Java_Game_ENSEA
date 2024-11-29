package main.game;

import main.utils.vectors.Vec2;

import java.awt.image.BufferedImage;

public abstract class DynamicSprite extends Sprite{
    protected Vec2 currentVelocity;

    public DynamicSprite(Vec2 position, String texturePath) {
        super(position, texturePath);
        this.currentVelocity=new Vec2();
    }

    public DynamicSprite(Vec2 position, BufferedImage texture) {
        super(position, texture);
        this.currentVelocity=new Vec2();
    }

    public DynamicSprite(Vec2 position, String texturePath, Vec2 velocity) {
        super(position, texturePath);
        this.currentVelocity = velocity;
    }

    protected void setVelocity(Vec2 vel){
        currentVelocity=Vec2.multiply(vel,GameEngine.getCurrentLevel().getGroundSpeed(position));
    }

    public Vec2 getCurrentVelocity(){return currentVelocity;}

    public void goToNextPosition(){
        position.x+=currentVelocity.x;
        position.y+=currentVelocity.y;
    }
}
