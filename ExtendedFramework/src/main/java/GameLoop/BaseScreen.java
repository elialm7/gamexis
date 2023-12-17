package GameLoop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class BaseScreen implements Screen {

    protected Stage mainStage;
    protected Stage uiStage;
    public BaseScreen(){
        mainStage = new Stage();
        uiStage = new Stage();
        initialize();
    }
    public abstract void initialize();
    public abstract void update(float dt);
    /*
     * The game loop consists of three stages.
     *(1) process input (act method).
     *(2) update state of game world
     *(3) render the graphics
     * */
    @Override
    public void render(float delta) {
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
        this.uiStage.act(deltatime);
        this.uiStage.draw();
    }
    @Override
    public void resize(int width, int height) {}
    @Override
    public void show() {}
    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void dispose() {}
}
