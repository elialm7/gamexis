package Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public abstract class SimpleActor extends Actor {
    private Animation currentAnimation;
    private float elapsedtime = 0;

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public static ArrayList<SimpleActor> getList(Stage stage, Class<?> clazz){
        ArrayList<SimpleActor> list = new ArrayList<>();
        for(Actor act: stage.getActors()){
            if(clazz.isInstance(act)){
                list.add((SimpleActor) act);
            }
        }
        return list;

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        elapsedtime+=dt;
        if(elapsedtime>=10f){
            elapsedtime=0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(currentAnimation!=null){
            batch.setColor(this.getColor());
            batch.draw((TextureRegion) currentAnimation.getKeyFrame(elapsedtime), getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            batch.setColor(Color.WHITE);
        }
    }
}
