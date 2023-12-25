package starfishgame;

import Actors.BaseActor;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


public class StarFish extends BaseActor {


    private boolean collected;
    public StarFish(float x, float y, Stage s){
        super(x,y,s);
        loadTexture("starfishcollector/starfish.png");
        setBoundaryPolygon(8);
        this.collected = false;
        Action spin = Actions.rotateBy(30, 1);
        this.addAction(Actions.forever(spin));
    }

    public boolean isCollected(){
        return collected;
    }

    public void collect(){
        this.collected = true;
        clearActions();
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
    }


}
