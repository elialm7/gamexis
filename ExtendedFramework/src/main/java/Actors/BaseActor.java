package Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.compression.lzma.Base;

import java.util.Arrays;


public class BaseActor extends Actor {

    private Animation animation;
    private float elapsedtime;
    private boolean animationPaused;

    private Vector2 velocityVec;

    private Vector2 accelerationVec;
    private float acceleration;

    private float maxSpeed;
    private float deceleration;

    private Polygon boundaryPolygon;

    public BaseActor(float x, float y, Stage s) {
        super();
        setPosition(x, y);
        s.addActor(this);

        this.animation = null;
        this.elapsedtime = 0;
        this.animationPaused = false;

        this.velocityVec = new Vector2(0, 0);
        this.accelerationVec = new Vector2(0, 0);
        this.acceleration = 0;

        this.maxSpeed = 1000;
        this.deceleration = 0;

    }

    public Vector2 preventOverlap(BaseActor other){

        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return null;
        }

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

        if(!polygonOverlap){
            return null;
        }

        this.moveBy(mtv.normal.x*mtv.depth, mtv.normal.y * mtv.depth);
        return mtv.normal;

    }

    public void centerAtPosition(float x, float y){
        setPosition(x - getWidth()/2, y-getHeight()/2);
    }

    public void centerAtActor(BaseActor other){
        centerAtPosition(other.getX() + other.getWidth()/2, other.getY()+other.getHeight()/2);
    }

    public void setOpacity(float opacity){
        this.getColor().a = opacity;
    }
    public boolean overlaps(BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return false;
        }
        return Intersector.overlapConvexPolygons(poly1, poly2);
    }


    public void setBoundaryRectangle(){

        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0,w,0, w,h, 0,h};
        this.boundaryPolygon = new Polygon(vertices);

    }

    public void setBoundaryPolygon(int numsides){
        float w = getWidth();
        float h = getHeight();
        float[] vertices = new float[2*numsides];
        for(int i = 0; i < numsides; i++){
            float angle = i * 6.28f/numsides;
            vertices[2*i] = (w/2)*MathUtils.cos(angle)+(w/2);
            vertices[2*i+1] = (h/2)*MathUtils.sin(angle) + h/2;
        }
        this.boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon(){
        this.boundaryPolygon.setPosition(getX(), getY());
        this.boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        this.boundaryPolygon.setRotation(getRotation());
        this.boundaryPolygon.setScale(getScaleX(), getScaleY());
        return this.boundaryPolygon;
    }

    public void applyPhysics(float dt){
        velocityVec.add(accelerationVec.x*dt, accelerationVec.y*dt);
        float speed = getSpeed();
        if(accelerationVec.len() == 0){
            speed -= deceleration*dt;
        }
        speed = MathUtils.clamp(speed, 0,maxSpeed);
        setSpeed(speed);
        moveBy(velocityVec.x*dt, velocityVec.y*dt);
        accelerationVec.set(0,0);
    }
    public void setMaxSpeed(float ms){
        this.maxSpeed = ms;
    }
    public void setDeceleration(float dec){
        this.deceleration = dec;
    }
    public void setAcceleration(float acc){
        this.acceleration = acc;
    }
    public void accelerateAtAngle(float angle){
        this.accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }
    public void accelerateForward(){
        this.accelerateAtAngle(getRotation());
    }
    public void setSpeed(float speed){
        if(velocityVec.len() == 0){
            velocityVec.set(speed, 0);
        }else{
            /*
            * primero normalizamos el vector ,
            * es decir convertimos a su vector unitario,  y luego scalamos a la velocidad desea.
            * */
            velocityVec.nor().scl(speed);
        }
    }
    public float getSpeed(){
        return this.velocityVec.len();
    }
    public float getMotionAngle(){
        return this.velocityVec.angle();
    }
    public void setMotionAngle(float angle){
        this.velocityVec.setAngle(angle);
    }
    public boolean isMoving(){
        return(getSpeed()>0);
    }
    public void setAnimation(Animation anim){
        this.animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w, h);
        setOrigin(w/2 ,h/2);

        if(this.boundaryPolygon == null){
            setBoundaryRectangle();
        }

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
            batch.draw(animation.getKeyFrame(elapsedtime), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
