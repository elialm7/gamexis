package com.gamexisg.multijugadorconframework.shooter.charactergroup;

import Actors.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Archer extends BaseActor {

    public Archer(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("Idle.png",1,6,0.1f,true);
        setSize(65,65);
        setRotation(180);

    }

    @Override
    public void act(float dt) {
        super.act(dt);


        setAnimationPaused(isMoving());

    }

}
