package GameLoop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameLoop extends Game {

    protected Stage mainStage;
    @Override
    public void create() {
        this.mainStage = new Stage();
        initialize();
    }

    /*
     * The game loop consists of three stages.
     *(1) process input (act method).
     *(2) update state of game world
     *(3) render the graphics
     * */
    @Override
    public void render() {

        //get deltatime: the difference in time from the last iteration of the game loop, if we assume 60fps, then deltatime should be 1/60. not always though.
        float deltatime = Gdx.graphics.getDeltaTime();
        //run act method.
        mainStage.act(deltatime);
        //user define update
        update(deltatime);
        //clear the screen before rendering
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // draw the graphics.
        this.mainStage.draw();
    }

    public abstract void initialize();
    public abstract void update(float dt);
}
