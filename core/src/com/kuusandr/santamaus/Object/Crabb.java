package com.kuusandr.santamaus.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kuusandr.santamaus.Random;
import com.kuusandr.santamaus.SantaMausGame;

import java.util.Timer;
import java.util.TimerTask;

import static com.kuusandr.santamaus.SantaMausGame.SCREEN_HEIGHT;
import static com.kuusandr.santamaus.SantaMausGame.TILE_HEIGHT;
import static com.kuusandr.santamaus.SantaMausGame.TILE_WIDTH;

public class Crabb {

    private SantaMausGame game;
    private Texture Spanch;

    private float SpanchElapsed;

    public Boolean live = false;

    public float x;
    public float y;

    public float dx = 0.8f;

    public Crabb(SantaMausGame gm) {
        this.game = gm;

        Spanch = game.manager.get("obj/crabb.png");

        setPosition(TILE_WIDTH*1,TILE_HEIGHT*1);

        SpanchElapsed = Random.rnd(10, 30)*1L;

    }


    public void  update(float delta)
    {
        SpanchElapsed -= delta;

        if(SpanchElapsed<=0)
        {
            live = !live;
            if (live == true)
            {
                SpanchElapsed = 30;
            }else
            {
                SpanchElapsed = Random.rnd(10, 30)*1L;
            }
        }

        if(live==false)
        {
            return;
        }


       if(x <= SantaMausGame.TILE_WIDTH)
       {
           dx = 0.8f;
       }else
           if(x >= (SantaMausGame.SCREEN_WIDTH-SantaMausGame.TILE_WIDTH-Spanch.getWidth()))
           {
                dx = -0.8f;
           }

       x = x + dx;
    }

    public void render(Batch batch)
    {
        if(live)
        {
            batch.draw(Spanch,x,y);
        }
    }

    public void setDx(float _dx)
    {
        dx = _dx;
    }

    public float getDx()
    {
        return dx;
    }

    public void setLive(Boolean _liv)
    {
        live = _liv;
    }

    public Boolean getLive()
    {
        return live;
    }

    public void setPosition(float _x,float _y)
    {
        x=_x;y=_y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

}
