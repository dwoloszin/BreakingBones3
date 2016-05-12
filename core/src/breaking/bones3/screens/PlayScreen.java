package breaking.bones3.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;

import breaking.bones3.PlayGame;
import breaking.bones3.scenes.Hud;
import breaking.bones3.sprites.Player;

/**
 * Created by wolos on 11/05/2016.
 */
public class PlayScreen implements Screen {
    private Player player;
    private PlayGame game;
    private OrthographicCamera gameCam;
    private Viewport gameport;
    private Hud hud;

    // TileMap Variaveis
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D variaveis
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(PlayGame game){
        this.game = game;

        gameCam = new OrthographicCamera();
        gameport = new FitViewport(PlayGame.V_WIDTH/PlayGame.PPM,PlayGame.V_HEIGHT/PlayGame.PPM,gameCam);
        //gameCam.setToOrtho(false,300 ,300);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/PlayGame.PPM);
        gameCam.position.set(gameport.getScreenWidth()/2, gameport.getScreenHeight()/2,0);

        world = new World(new Vector2(0,0),true); // para colocar gravidade setar o segundo parametro -10
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create de Doors
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth()/2)/PlayGame.PPM, (rect.getY()+ rect.getHeight()/2)/PlayGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/PlayGame.PPM,rect.getHeight()/2/PlayGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }


        //creat the collision
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+ rect.getWidth()/2)/PlayGame.PPM, (rect.getY()+ rect.getHeight()/2)/PlayGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/PlayGame.PPM,rect.getHeight()/2/PlayGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);



        }

        player = new Player(world);

    }



    @Override
    public void show() {

    }

    public void handleInput(float dt){

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& player.b2body.getLinearVelocity().x >= -1) {
            //gameCam.position.x -= 2 * dt;
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& player.b2body.getLinearVelocity().x <= 1) {
            //gameCam.position.x += 2 * dt;
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 1) {
            //gameCam.position.y += 2 * dt;

            player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)&& player.b2body.getLinearVelocity().y >= -1) {
            //gameCam.position.y -= 2 * dt;
            //if(Gdx.input.isTouched())
            player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
        }
    }


    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);




    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Render gamemap
        renderer.render();

        //render Box2DDebugLines
        b2dr.render(world,gameCam.combined);


        //o q a camera da hud ver.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();




    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width,height);
        //gameCam.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
