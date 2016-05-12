package breaking.bones3.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.awt.Color;

import breaking.bones3.PlayGame;

/**
 * Created by wolos on 11/05/2016.
 */
public class Hud implements Disposable {
    public Stage stage;



    private Viewport viewport;

    private Integer score;
    private Integer vidas;
    Label scoreLabel;
    Label vidasLabel;
    Label scoreLabelTexto;
    Label vidasLabelTexto;

    public Hud(SpriteBatch sb){
        score = 0;
        vidas = 3;

        viewport = new FitViewport(PlayGame.V_WIDTH, PlayGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport,sb);
        Table table = new Table();
        table.top();
        table.setFillParent(true);


        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        vidasLabel = new Label(String.format("%01d", vidas), new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        scoreLabelTexto = new Label("Score", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
        vidasLabelTexto = new Label("LIFE", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));

        table.add(scoreLabelTexto).expand().padTop(-200);
        table.add(vidasLabelTexto).expand().padTop(-200);
        table.row(); // pula uma linha
        table.add(scoreLabel).expand().padTop(-640);
        table.add(vidasLabel).expand().padTop(-640);


        stage.addActor(table);


    }

    @Override
    public void dispose() {
        stage.dispose();

    }


}
