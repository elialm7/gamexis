package com.gamexisg.multijugadorconframework.shooter.character;

import Utils.Loaders;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.Map;

public class Archer extends Actor {

    private final Map<TypeAnimation, Animation> animationMap;
    private TypeAnimation currentType;
    private final float timeToChangeToIdleAnimation = 0.1f;
    private Animation animation;
    private float elapsedtime = 0;
    private float timeCurrentToChange = 0;
    public Archer() {

        animationMap = new HashMap<>();
        animationMap.put(TypeAnimation.IDLE, Loaders.loadAnimationFromSheet("Idle.png",1,6,.1f,true));
        animationMap.put(TypeAnimation.RUN, Loaders.loadAnimationFromSheet("Run.png",1,8,.1f,true));
        animationMap.put(TypeAnimation.ATTACK, Loaders.loadAnimationFromSheet("Shot_2.png",1,13,.25f,false));
        setRotation(180);
        setSize(80,80);
        setOrigin(getWidth()/2,getHeight()/2);
        setCurrentType(TypeAnimation.IDLE);

    }



    public void setCurrentType(TypeAnimation currentType) {
        timeCurrentToChange = timeToChangeToIdleAnimation;
        this.currentType = currentType;
        animation = animationMap.get(currentType);
    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if(currentType!=TypeAnimation.IDLE){
            if(timeCurrentToChange>0){
                timeCurrentToChange -=dt;
            }else{
                setCurrentType(TypeAnimation.IDLE);
            }
        }
        elapsedtime+=dt;
        if(elapsedtime>10f){
            elapsedtime=0;
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(animation!=null){
            batch.draw((TextureRegion) animation.getKeyFrame(elapsedtime), getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }



    public enum TypeAnimation {
        RUN, IDLE, ATTACK
    }
}
