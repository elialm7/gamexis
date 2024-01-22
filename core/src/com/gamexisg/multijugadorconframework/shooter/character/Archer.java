package com.gamexisg.multijugadorconframework.shooter.character;

import Actors.MultipleAnimations;
import Actors.SimpleActor;
import Utils.Loaders;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;
import java.util.Map;

public class Archer extends SimpleActor implements MultipleAnimations<TypeAnimation> {

    private final Map<TypeAnimation, Animation> animationMap;
    private TypeAnimation currentType;
    private final float timeToChangeToIdleAnimation = 0.1f;
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
        setCurrentAnimation(animationMap.get(currentType));
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

    }






}
