package com.kuusandr.santamaus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kuusandr.santamaus.Screens.LoadingScreen;

public class SantaMausGame extends Game implements InputProcessor {
	public static final int TILE_WIDTH = 18;
	public static final int TILE_HEIGHT = 18;

	public static final int SCREEN_WIDTH = 720;
	public static final int SCREEN_HEIGHT = 960;

	public static float VIEWPORT_LEFT;
	public static float VIEWPORT_RIGHT;

	public static final float PPM = 100;

	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT  = 1;
	public static final short  FISH_BIT   = 2;
	public static final short SETKA_BIT   = 3;


	public Boolean LeftKey=false;
	public Boolean RightKey=false;
	public Boolean DownKey=false;

	public OrthographicCamera camera;
    public Viewport viewport;
	public SpriteBatch sb;


	public AssetManager manager;

	@Override
	public void create ()
	{
		sb = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,SCREEN_WIDTH,SCREEN_HEIGHT);
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

		manager = new AssetManager();

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);


		setScreen(new LoadingScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		//super.resize(width, height);

		viewport.update(width,height);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		unLoad();
	}

	public void queueLoad() {

		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("lvl/level0.tmx", TiledMap.class );

		manager.load("ui/ldrscr.png", Texture.class);
		manager.load("ui/loader.gif", Texture.class );

		manager.load("ui/cat.png",Texture.class);
		manager.load("ui/uppnl.png",Texture.class);
		manager.load("ui/oxygen.png",Texture.class);
		manager.load("ui/score.png",Texture.class);

		manager.load("cat/upsht.png",Texture.class);
		manager.load("cat/dsht.png",Texture.class);
		manager.load("cat/bone.png",Texture.class);
		manager.load("cat/sink.png",Texture.class);
		manager.load("cat/fat.png",Texture.class);

		manager.load("fnt/gameui.fnt", BitmapFont.class);

		manager.load("pool/sky.png",Texture.class);

		manager.load("obj/leftfish.png",Texture.class);
		manager.load("obj/crabb.png",Texture.class);

	}

	public void unLoad() {
		manager.unload("cat/upsht.png");
		manager.unload("cat/dsht.png");
		manager.unload("cat/bone.png");
		manager.unload("cat/sink.png");
		manager.unload("cat/fat.png");

		manager.unload("lvl/level0.tmx");
		manager.unload("ui/ldrscr.png");
		manager.unload("ui/loader.gif");

		manager.unload("ui/cat.png");
		manager.unload("ui/uppnl.png");
		manager.unload("ui/oxygen.png");
		manager.unload("ui/score.png");

		manager.unload("fnt/gameui.fnt");


		manager.unload("pool/sky.png");

		manager.unload("obj/leftfish.png");
		manager.unload("obj/crabb.png");
	}


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK) {
            //что-то делаем при нажатии на кнопку back
            //Log.e("key", "back");
            return true;
        }
        if(keycode == Input.Keys.LEFT) {
            LeftKey = true;
        }
        if(keycode == Input.Keys.RIGHT){
            RightKey = true;
        }
        if(keycode == Input.Keys.DOWN){
        	DownKey = true;
		}
        return  false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT) {
            LeftKey = false;
        }
        if(keycode == Input.Keys.RIGHT){
            RightKey = false;
        }
		if(keycode == Input.Keys.DOWN){
			DownKey = false;
		}
        return  false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
