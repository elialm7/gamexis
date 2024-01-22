package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Loaders {
    public static Animation loadAnimationFromSheet(String filename, int rows, int cols, float frameDuration, boolean loop){

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

        return anim;

    }
}
