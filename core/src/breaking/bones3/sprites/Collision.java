package breaking.bones3.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by wolos on 12/05/2016.
 */
public class Collision extends InteractiveTileObject {
    public Collision(World world, TiledMap map, Rectangle bounds){

        super(world,map,bounds);

    }
}
