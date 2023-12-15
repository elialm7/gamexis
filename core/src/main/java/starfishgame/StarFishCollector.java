package starfishgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class StarFishCollector extends Game {


    private SpriteBatch batch;

    private Texture turtleTexture;
    private float turtleX;
    private float turtleY;
    private Rectangle turtleRectangle;

    private Texture starfishTexture;
    private float starfishX;
    private float starfishY;
    private Rectangle starfishRectangle;

    private Texture oceanTexture;
    private Texture winMessageTexture;

    private boolean win;


    private void clearScreen(){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void handleInput(){

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            this.turtleX-=20;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            this.turtleX+=20;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            this.turtleY+=20;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.turtleY-=20;
        }

    }

    private void updateTurtleLocation(){
        this.turtleRectangle.setPosition(turtleX, turtleY);
    }

    private void checkWinningCondition(){

        if(turtleRectangle.overlaps(starfishRectangle)){
            win = true;
        }

    }

    private void drawGraphics(){

        this.batch.begin();
        this.batch.draw(oceanTexture, 0,0);
        if(!win){
            this.batch.draw(starfishTexture,this.starfishX, this.starfishY);
        }
        this.batch.draw(turtleTexture, this.turtleX,this.turtleY);
        if(win){
            batch.draw(winMessageTexture, 180, 180);
        }
        batch.end();


    }
    @Override
    public void create() {
        Gdx.app.log("Loading", "Cargando los datos para el juego");
        this.batch = new SpriteBatch();
        this.turtleTexture = new Texture(Gdx.files.internal("turtle-1.png"));
        this.turtleX = 20;
        this.turtleY = 20;
        this.turtleRectangle = new Rectangle(this.turtleX, this.turtleY, this.turtleTexture.getWidth(),this.turtleTexture.getHeight());
        this.starfishTexture = new Texture(Gdx.files.internal("starfish.png"));
        this.starfishX = 380;
        this.starfishY = 380;
        this.starfishRectangle = new Rectangle(this.starfishX, this.starfishY, this.starfishTexture.getWidth(), this.starfishTexture.getHeight());
        this.oceanTexture = new Texture(Gdx.files.internal("water.jpg"));
        this.winMessageTexture = new Texture(Gdx.files.internal("you-win.png"));
        this.win = false;
    }

    @Override
    public void render() {
        handleInput();
        updateTurtleLocation();
        checkWinningCondition();
        clearScreen();
        drawGraphics();
    }
}
