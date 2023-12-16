package starfishgame.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Turtle extends  BaseActor{

    public Turtle(){
        super();
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.moveBy(-15, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.moveBy(15, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            this.moveBy(0, 15);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.moveBy(0, -15);
        }

    }
}
