package breaking.bones3.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import breaking.bones3.PlayGame;

/**
 * Created by wolos on 12/05/2016.
 */
public class Player extends Sprite {
    public World word;
    public Body b2body;


    public Player(World world){
        this.word = world;
        definePlayer();
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/PlayGame.PPM,32/PlayGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = word.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ PlayGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }


}
