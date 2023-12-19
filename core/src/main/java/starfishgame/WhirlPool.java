package starfishgame;

import Actors.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WhirlPool extends BaseActor {

    public WhirlPool(float x, float y, Stage s){
        super(x, y, s);
        loadAnimationFromSheet("starfishcollector/whirlpool.png", 2, 5, 0.1f, false);

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if(isAnimationFinished()){
            remove();
        }
    }
}
