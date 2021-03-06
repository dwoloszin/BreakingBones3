package breaking.bones3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import breaking.bones3.screens.PlayScreen;

public class PlayGame extends Game {
	public SpriteBatch batch;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;
	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 480;
	public static final float PPM = 100; //pixel per meter


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
}
