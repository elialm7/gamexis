package com.gamexisg.multijugadorconframework.shooter.charactergroup;

import Actors.BaseActor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gamexisg.multijugadorconframework.shooter.character.Archer;
import com.gamexisg.multijugadorconframework.shooter.character.Square;
import com.gamexisg.multijugadorconframework.shooter.utils.GameUtils;

public class MainBaseActor extends BaseActor {

    private int health;
    private final Archer archer;
    private final Square square;
    private final Label floatingText;
    private boolean isAttacking = false;
    public MainBaseActor(int id, int health, float x, float y, Stage s) {
        super(x,y,s);
        setSize(80,80);
        setId(id);
        setHealth(health);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = GameUtils.generateBitmapFont(15,Color.GOLD);

        floatingText = new Label("HP: "+health, labelStyle);
        floatingText.setColor(Color.GREEN);
        floatingText.setPosition(-5,-25);

        square = new Square();

        archer = new Archer();
        archer.setPosition(-getWidth()/4,-(getHeight()/2-10));
        addActor(square);
        addActor(floatingText);
        addActor(archer);
    }


    public void setAttacking(boolean attacking) {
        if(attacking) archer.setCurrentType(Archer.TypeAnimation.ATTACK);
        isAttacking = attacking;
    }

    @Override
    public void setPosition(float x, float y) {

        if(archer !=null){
            if(x!=getX() || y !=getY())
                archer.setCurrentType(Archer.TypeAnimation.RUN);
            if(x<getX() && archer.getScaleX()<0){
                archer.setScaleX(-archer.getScaleX());
            }else if (x>getX() && archer.getScaleX()>0){
                archer.setScaleX(-archer.getScaleX());
            }
        }
        super.setPosition(x, y);
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
        super.act(dt);
        if(getHealth()==0){
            remove();
        }else{
            floatingText.setText("HP: "+ health);
        }
    }



}
