package breaking.bones3.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import breaking.bones3.tools.B2WorldCreator;

/**
 * Created by wolos on 11/05/2016.
 */
public class PlayScreen implements Screen {
    private Player player;
    private PlayGame game;

    private TextureAtlas atlas;

    //playScreen Variaveis
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

        atlas = new TextureAtlas("Player.pack");

        //Camera seguir o jogador
        gameCam = new OrthographicCamera();
        gameport = new FitViewport(PlayGame.V_WIDTH/PlayGame.PPM,PlayGame.V_HEIGHT/PlayGame.PPM,gameCam);

        //Criando a hud
        hud = new Hud(game.batch);

        //carregar o map e configurar
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/PlayGame.PPM);

        //centralizar a camera
        gameCam.position.set(gameport.getScreenWidth()/2, gameport.getScreenHeight()/2,0);

        //criando o box
        world = new World(new Vector2(0,0),true); // para colocar gravidade setar o segundo parametro -10
        b2dr = new Box2DDebugRenderer();
        //criar o mundo com objetos
        new B2WorldCreator(world,map);


        player = new Player(world, this);

    }

    public TextureAtlas getAtlas(){
        return atlas;


    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& player.b2body.getLinearVelocity().x >= -0.5) {
            //gameCam.position.x -= 2 * dt;
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& player.b2body.getLinearVelocity().x <= 0.5) {
            //gameCam.position.x += 2 * dt;
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 0.5) {
            //gameCam.position.y += 2 * dt;

            player.b2body.applyLinearImpulse(new Vector2(0, 0.1f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)&& player.b2body.getLinearVelocity().y >= -0.5) {
            //gameCam.position.y -= 2 * dt;
            //if(Gdx.input.isTouched())
            player.b2body.applyLinearImpulse(new Vector2(0, -0.1f), player.b2body.getWorldCenter(), true);
        }


    }


    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        player.update(dt);

        // fixar a camera com o player
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;

        //atualizar a camera com a correta posicao
        gameCam.update();

        //renderizar tudo
        renderer.setView(gameCam);




    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render gamemap
        renderer.render();

        //render Box2DDebugLines camera principal
        b2dr.render(world,gameCam.combined);


        //o q a camera da hud ver.
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();






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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
