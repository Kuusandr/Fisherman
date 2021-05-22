package com.kuusandr.santamaus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class ProgressBar
{
    private ShapeRenderer shapeRenderer;

    private float MaxValue = 0.0f;
    private float MinValue = 0.0f;

    private float CurrValue = 0.0f;

    public ProgressBar(float minV, float maxV, float Cc)
    {
        shapeRenderer = new ShapeRenderer();
        MaxValue      = maxV;
        MinValue      = minV;
        CurrValue     = Cc;
    }

    protected void finalize () {
        shapeRenderer.dispose();
    }


    public void draw(SpriteBatch batch,float x,float y,float w,float h)
    {
        batch.end();

        // нарисуем подложку прогресс бара
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x,y,w,h);
        shapeRenderer.end();

        // нарисуем полоску заполненности бара
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x,y,CurrValue*w/MaxValue,h);
        shapeRenderer.end();


        // нарисуем окантовку прогресс бара
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(x,y,w,h);
        shapeRenderer.end();

        batch.begin();
    }

    public float getCurrValue() {
        return CurrValue;
    }

    public void setCurrValue(float currValue) {
        CurrValue = currValue;
    }
}

