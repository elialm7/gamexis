package Actors;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.Map;

public interface MultipleAnimations <T extends Enum<T>> {
    void setCurrentType(T type);
}
