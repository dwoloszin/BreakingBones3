package breaking.bones3.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import breaking.bones3.PlayGame;
import breaking.bones3.scenes.Hud;

/**
 * Created by wolos on 11/05/2016.
 */
public class PlayScreen implements Screen {
    private PlayGame game;
    private OrthographicCamera gameCam;
    private Viewport gameport;
    private Hud hud;



    public PlayScreen(PlayGame game){
        this.game = game;

        gameCam = new OrthographicCamera();
        gameport = new FitViewport(PlayGame.V_WIDTH,PlayGame.V_HEIGHT,gameCam);
        //gameCam.setToOrtho(false,300 ,300);
        hud = new Hud(game.batch);

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //game.batch.setProjectionMatrix(gameCam.combined);
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
