package starfishgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.compression.lzma.Base;
import starfishgame.Actors.BaseActor;
import starfishgame.Actors.Turtle;
import starfishgame.GameLoop.GameLoop;

public class StarFishCollector extends GameLoop {


    private Turtle turtle;
    private BaseActor starfish;
    private BaseActor ocean;
    private BaseActor winMessage;
    private boolean winCondition;
    @Override
    public void initialize() {
        ocean = new BaseActor();
        ocean.setTexture( new Texture( Gdx.files.internal("water.jpg") )  );
        mainStage.addActor(ocean);

        starfish = new BaseActor();
        starfish.setTexture( new Texture(Gdx.files.internal("starfish.png")) );
        starfish.setPosition( 380,380 );
        mainStage.addActor( starfish );

        turtle = new Turtle();
        turtle.setTexture( new Texture(Gdx.files.internal("turtle-1.png")) );
        turtle.setPosition( 20,20 );
        mainStage.addActor( turtle );

        winMessage = new BaseActor();
        winMessage.setTexture( new Texture(Gdx.files.internal("you-win.png")) );
        winMessage.setPosition( 180,180 );
        winMessage.setVisible( false );
        mainStage.addActor( winMessage );
        winCondition = false;
    }

    @Override
    public void update(float dt) {

        if(turtle.overlaps(starfish)){

            starfish.remove();
            winMessage.setVisible(true);

        }


    }
}