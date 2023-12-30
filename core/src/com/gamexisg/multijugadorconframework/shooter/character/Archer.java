package com.gamexisg.multijugadorconframework.shooter.character;

import Actors.BaseActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Archer extends BaseActor {
    private int id;
    public Archer(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("turtle-1.png");
        setSize(100,100);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            setRotation(180);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            setRotation(0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            setRotation(90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            setRotation(270);
        }




    }
}
