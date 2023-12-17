package ExtendedFramework.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;


public class BaseActor extends Actor {

    private TextureRegion region;
    private Rectangle rectangle;

    private Animation animation;
    private float elapsedtime;
    private boolean animationPaused;

    public BaseActor(){
        super();
        this.region = new TextureRegion();
        this.rectangle = new Rectangle();

        this.animation = null;
        this.elapsedtime = 0;
        this.animationPaused = false;
    }

    /**
     *
     */
    public BaseActor(float x, float y, Stage s){
        super();
        setPosition(x, y);
        s.addActor(this);
    }



    public void setAnimation(Animation anim){
        this.animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2 ,h/2);
    }

    public void setAnimationPaused(boolean pause){
        this.animationPaused = pause;
    }

    public Animation loadAnimationFromFiles(String[] fileNames, float frameduration, boolean loop){
        int fileCount = fileNames.length;
        Array<TextureRegion> texturearray = new Array<>();
        Arrays.stream(fileNames).map(filename -> new Texture(Gdx.files.internal(filename))).forEach(texture -> {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            texturearray.add(new TextureRegion(texture));
        });

        Animation anim = new Animation(frameduration, texturearray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }else {
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }
        if (this.animation == null){
            setAnimation(anim);
        }
        return anim;
    }

    public Animation loadAnimationFromSheet(String filename, int rows, int cols, float frameDuration, boolean loop){

        Texture texture = new Texture(Gdx.files.internal(filename));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth()/cols;
        int frameHeight = texture.getHeight()/rows;
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);

        Array<TextureRegion> textureArray = new Array<>();
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                textureArray.add(temp[r][c]);
            }
        }
        Animation anim = new Animation(frameDuration, textureArray);
        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }else{
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }
        if(this.animation == null){
            setAnimation(anim);
        }

        return anim;

    }


    public boolean isAnimationFinished(){
        return this.animation.isAnimationFinished(elapsedtime);
    }

    public Animation loadTexture(String filename){
        String[] fileNames = new String[1];
        fileNames[0] = filename;
        return loadAnimationFromFiles(fileNames, 1, true);
    }


    public void setTexture(Texture t){
        this.region.setRegion(t);
        setSize(t.getWidth(), t.getHeight());
        rectangle.setSize(t.getWidth(), t.getHeight());

    }
    public Rectangle getRectangle(){
        this.rectangle.setPosition(getX(), getY());
        return this.rectangle;
    }

    public boolean overlaps(BaseActor actor){
        return this.getRectangle().overlaps(actor.getRectangle());
    }

    public void act(float dt){
        super.act(dt);
        if(!animationPaused){
            elapsedtime += dt;
        }
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = getColor();
        batch.setColor(c);
        if(animation!= null && isVisible()){
            batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
