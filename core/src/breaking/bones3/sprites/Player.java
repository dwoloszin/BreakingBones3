package breaking.bones3.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import breaking.bones3.PlayGame;
import breaking.bones3.screens.PlayScreen;

/**
 * Created by wolos on 12/05/2016.
 */
public class Player extends Sprite {
    public enum State{WALKING, STANDING}
    public State currentState;
    public State previousState;
    public World word;
    public Body b2body;
    private TextureRegion playerStands;
    private Animation playWalkink;
    private Animation playStand;
    private float stateTimer;
    private boolean walkingRight;


    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("PlayerDireita"));
        this.word = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        walkingRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        //andar aimation
        for(int i = 3; i < 6; i++){
            frames.add(new TextureRegion(getTexture(),i * 32,0,31,31));//busca os frames de 31 pixel na imagem
        }

        playWalkink = new Animation(0.1f,frames);
        frames.clear();



        definePlayer();
        playerStands = new TextureRegion(getTexture(),screen.getAtlas().findRegion("PlayerDireita").getRegionX(),screen.getAtlas().findRegion("PlayerDireita").getRegionY(),screen.getAtlas().findRegion("PlayerDireita").getRegionHeight(),31);//31 largura do frame
        setBounds(0,0,16/PlayGame.PPM, 16/PlayGame.PPM);
        setRegion(playerStands);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2,b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));


    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region = playerStands;
        switch (currentState){
            case WALKING:
                region = playWalkink.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
                region = playerStands;
                break;



        }
        if((b2body.getLinearVelocity().x < 0 || !walkingRight) && !region.isFlipX()){
            region.flip(true,false);
            walkingRight = false;

        }
        else if((b2body.getLinearVelocity().x > 0 || walkingRight) && region.isFlipX()){
            region.flip(true,false);
            walkingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState(){
        if(b2body.getLinearVelocity().x != 0 || b2body.getLinearVelocity().x != 0) {
            return State.WALKING;
        }

        else{
            return State.STANDING;

        }

    }


    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/PlayGame.PPM,32/PlayGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = word.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(9/ PlayGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }


}
