package Actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TileMapActor extends Actor {


    public static int windowWidth = 800;
    public static int windowHeight = 600;


    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;

    public TileMapActor(String filename , Stage theStage){
        tiledMap = new TmxMapLoader().load(filename);

        int tileWidth = (int)tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int)tiledMap.getProperties().get("tileheight");
        int numTilesHorizontal = (int)tiledMap.getProperties().get("width");
        int numTilesVertical = (int) tiledMap.getProperties().get("height");
        int mapWidth = tileWidth * numTilesHorizontal;
        int mapHeight = tileHeight * numTilesVertical;

        BaseActor.setWorldBounds(mapWidth, mapHeight);

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true);
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, windowWidth, windowHeight);
        tiledCamera.update();
        theStage.addActor(this);
    }

    public List<MapObject> getRectangleList(String propertynName){
        List<MapObject> list = new ArrayList<>();
        for(MapLayer layer: tiledMap.getLayers()){
            for(MapObject obj: layer.getObjects()){
                if(!(obj instanceof RectangleMapObject)){
                    continue;
                }
                MapProperties props = obj.getProperties();
                if(props.containsKey("name") && props.get("name").equals(propertynName)){
                    list.add(obj);
                }
            }
        }
        return list;
    }

    public List<MapObject> getTileList(String propertyName){
        List<MapObject> list = new ArrayList<>();
        for(MapLayer layer: tiledMap.getLayers()){
            for(MapObject obj: layer.getObjects()){
                if(!(obj instanceof RectangleMapObject)){
                    continue;
                }
                MapProperties props = obj.getProperties();
                TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
                TiledMapTile t = tmtmo.getTile();
                MapProperties defaultProps = t.getProperties();
                if(defaultProps.containsKey("name") && defaultProps.get("name").equals(propertyName)){
                    list.add(obj);
                }

                Iterator<String> propertyKeys = defaultProps.getKeys();
                while(propertyKeys.hasNext()){
                    String key = propertyKeys.next();
                    if(props.containsKey(key)){
                        continue;
                    }else{
                        Object value = defaultProps.get(key);
                        props.put(key, value);
                    }
                }

            }
        }
        return list;
    }

    @Override
    public void act(float dt){
        super.act(dt);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //super.draw(batch, parentAlpha);
        Camera mainCamera = getStage().getCamera();
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        batch.end();
        tiledMapRenderer.render();
        batch.begin();

    }
}
