package com.gamexisg.multijugadorconframework.shooter.character;

import Actors.SimpleActor;
import Utils.Loaders;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gamexisg.multijugadorconframework.shooter.shapes.Bullet;

public class Arrow extends SimpleActor {
    private Bullet bullet;

    public Arrow ( Bullet bullet, Stage s){
        s.addActor(this);
        this.bullet = bullet;
        setCurrentAnimation( Loaders.loadAnimationFromSheet("Arrow.png",1,1,0,false));
        setSize(bullet.getSize(), bullet.getSize());
        setOrigin(getWidth()/2,getHeight()/2);
    }

    public Bullet getBullet() {
        return bullet;
    }

    @Override
    public void setPosition(float x, float y) {
        if (getX()!=x && getY() !=y){
           float dX = x-getX();
           float dY = y-getY();
           setRotation((float) Math.toDegrees(Math.atan2(dY,dX)));
        }
        super.setPosition(x, y);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        setPosition(bullet.getPosition().x, bullet.getPosition().y);
    }
}
