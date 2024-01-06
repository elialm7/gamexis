package com.gamexisg.multijugadorconframework.shooter.charactergroup;

import Actors.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gamexisg.multijugadorconframework.shooter.character.Square;
import com.gamexisg.multijugadorconframework.shooter.utils.GameUtils;

public class MainBaseActor extends BaseActor {

    private int health;
    private final Archer archer;
    private final Square square;
    private final Label floatingText;
    public MainBaseActor(int id, int health, float x, float y, Stage s) {
        super(x,y,s);

        setId(id);
        setHealth(health);
        setWidth(50);
        setHeight(50);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GameUtils.generateBitmapFont(15,Color.GOLD);

        floatingText = new Label("HP: "+health, labelStyle);
        floatingText.setColor(Color.GREEN);
        floatingText.setPosition(-5,-25);

        square = new Square();

        archer = new Archer(x,y,s);
        archer.setPosition(-getHeight()*1.5f,-getWidth()*1.5f);

        addActor(square);
        addActor(archer);
        addActor(floatingText);
    }
    public void setMainCharacter(boolean isMainCharacter){
        if(isMainCharacter){
            square.setColor(Color.BLUE);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void act(float dt){

        if(getHealth()==0){
            clearChildren();
            getChildren().forEach(Actor::remove);
            getChildren().clear();
            remove();
        }else{
            floatingText.setText("HP: "+ health);
        }
    }



}
