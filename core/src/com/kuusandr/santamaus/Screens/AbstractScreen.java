package com.kuusandr.santamaus.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Rectangle;
import com.kuusandr.santamaus.SantaMausGame;

/**
 * @author Mats Svensson
 */
public abstract class AbstractScreen implements Screen {

    final SantaMausGame game;


    public AbstractScreen(SantaMausGame game)
    {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
