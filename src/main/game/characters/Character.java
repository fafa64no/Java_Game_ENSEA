package main.game.characters;

import main.game.DynamicSprite;
import main.utils.vectors.IVec2;
import main.utils.vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Character extends DynamicSprite {
    private final double velocityMultiplier;

    protected Vec2 currentDir = new Vec2();
    protected final Vec2 previousDir = new Vec2();
    protected final IVec2 textureSize;

    protected final double maxHealth;
    protected double currentHealth;
    protected LifeStates lifeState;
    protected final BufferedImage deadTexture;

    public Character(Vec2 position, String texturePath, String deadTexturePath, double velocityMultiplier, IVec2 textureSize) {
        super(position, texturePath);
        this.velocityMultiplier = velocityMultiplier;
        this.textureSize=textureSize;
        this.maxHealth=100;
        this.currentHealth=maxHealth;
        this.lifeState=LifeStates.CURRENTLY_ALIVE;
        BufferedImage deadTexture=null;
        try {deadTexture = ImageIO.read(new File(deadTexturePath));}
        catch (Exception e){e.printStackTrace();}
        this.deadTexture=deadTexture;
    }

    public Character(Vec2 position, BufferedImage texture, BufferedImage deadTexture, double velocityMultiplier, IVec2 textureSize) {
        super(position, texture);
        this.velocityMultiplier = velocityMultiplier;
        this.textureSize=textureSize;
        this.maxHealth=100;
        this.currentHealth=maxHealth;
        this.lifeState=LifeStates.CURRENTLY_ALIVE;
        this.deadTexture=deadTexture;
    }

    public Character(Vec2 position, String texturePath, String deadTexturePath, double velocityMultiplier, IVec2 textureSize, double maxHealth) {
        super(position, texturePath);
        this.velocityMultiplier = velocityMultiplier;
        this.textureSize=textureSize;
        this.maxHealth=maxHealth;
        this.currentHealth=maxHealth;
        this.lifeState=LifeStates.CURRENTLY_ALIVE;
        BufferedImage deadTexture=null;
        try {deadTexture = ImageIO.read(new File(deadTexturePath));}
        catch (Exception e){e.printStackTrace();}
        this.deadTexture=deadTexture;
    }

    public Character(Vec2 position, BufferedImage texture, BufferedImage deadTexture, double velocityMultiplier, IVec2 textureSize, double maxHealth) {
        super(position, texture);
        this.velocityMultiplier = velocityMultiplier;
        this.textureSize=textureSize;
        this.maxHealth=maxHealth;
        this.currentHealth=maxHealth;
        this.lifeState=LifeStates.CURRENTLY_ALIVE;
        this.deadTexture=deadTexture;
    }

    public void setInput(Vec2 vel) {
        if(lifeState==LifeStates.CURRENTLY_DEAD){
            super.setVelocity(new Vec2());
            this.currentDir=new Vec2();
        }else{
            super.setVelocity(Vec2.multiply(vel,this.velocityMultiplier));
            if (this.currentDir.x!=0||this.currentDir.y!=0){
                this.previousDir.x=this.currentDir.x;
                this.previousDir.y=this.currentDir.y;
            }
            this.currentDir=vel;
        }
    }

    public void takeDamage(double damage){
        currentHealth-=damage;
        if(currentHealth<0&&lifeState!=LifeStates.CURRENTLY_DEAD)killYourself();
    }

    public void killYourself(){
        lifeState=LifeStates.CURRENTLY_DEAD;
    }

    public double getHealthRatio(){
        return currentHealth/maxHealth;
    }
}
