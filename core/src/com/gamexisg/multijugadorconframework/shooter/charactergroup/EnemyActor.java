package com.gamexisg.multijugadorconframework.shooter.charactergroup;

import Actors.BaseActor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class EnemyActor extends BaseActor {
    public EnemyActor(int id,float x, float y, Stage s) {
        super(x, y, s);
        setId(id);
        Image circulo = new Image(new Texture("circulo.png"));
        circulo.setSize(20,20);
        circulo.setPosition(-circulo.getHeight()/2,-circulo.getWidth()/2);
        circulo.setOrigin(circulo.getHeight()/2,circulo.getWidth()/2);
        addActor(circulo);
    }
}
