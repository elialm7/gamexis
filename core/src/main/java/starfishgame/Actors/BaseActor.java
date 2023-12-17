package starfishgame.Actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {

    private TextureRegion region;
    private Rectangle rectangle;

    public BaseActor(){
        super();
        this.region = new TextureRegion();
        this.rectangle = new Rectangle();
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
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Color c = getColor();
        batch.setColor(c);
        if(isVisible()){
            batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
