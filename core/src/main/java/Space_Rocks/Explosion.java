package Space_Rocks;

import Actors.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Explosion extends BaseActor {
    public Explosion(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("spacerocks/explosion.png", 6,6,0.003f, false);

    }

    @Override
    public void act(float dt) {
        super.act(dt);
        if(isAnimationFinished()){
            remove();
        }
    }
}
