package com.kuusandr.santamaus.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kuusandr.santamaus.GifDecoder;
import com.kuusandr.santamaus.SantaMausGame;


public class LoadingScreen extends AbstractScreen {
    Sprite LdScreen;

    Animation<TextureRegion> animationLdr;
    float stateTime;

    public LoadingScreen(SantaMausGame mausGame) {
        super(mausGame);

        stateTime = 0f;

    }

    @Override
    public void show() {
        animationLdr = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("ui//loader.gif").read());

        Texture LdscreenTexture = new Texture(Gdx.files.internal("ui//ldrscr.png"));
        LdScreen =  new Sprite (LdscreenTexture);

        game.queueLoad();
        //game.manager.finishLoading();


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // #15

        game.viewport.apply();
        game.sb.setProjectionMatrix(game.camera.combined);

        game.camera.update();

        game.sb.begin();
        game.sb.draw(LdScreen, 0,0);
        game.sb.draw(animationLdr.getKeyFrame(stateTime, true), 320, 50);
        game.sb.end();

        game.manager.update();
        if(game.manager.update()){
            game.setScreen(new LevelScreen(game,"lvl/level0.tmx"));
        }
    }

    @Override
    public void resize (int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void hide() {

    }
}
