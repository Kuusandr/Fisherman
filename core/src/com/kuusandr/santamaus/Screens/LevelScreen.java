package com.kuusandr.santamaus.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.kuusandr.santamaus.Object.Cat;
import com.kuusandr.santamaus.Object.CatStatus;
import com.kuusandr.santamaus.Object.Crabb;
import com.kuusandr.santamaus.Quadtree.BoxKind;
import com.kuusandr.santamaus.Quadtree.QuadTree;
import com.kuusandr.santamaus.Quadtree.TreeBox;
import com.kuusandr.santamaus.SantaMausGame;
import com.kuusandr.santamaus.TextureMapObjectRenderer;

import java.util.ArrayList;
import java.util.List;

import static com.kuusandr.santamaus.SantaMausGame.TILE_HEIGHT;
import static com.kuusandr.santamaus.SantaMausGame.TILE_WIDTH;


public class LevelScreen extends AbstractScreen  {

    private Texture ribkaCoin;


    private String LevelName = "";
    private TiledMap map;
    //private OrthogonalTiledMapRenderer renderer;
    private TextureMapObjectRenderer renderer;

    private final QuadTree quadsetka;
    private QuadTree quadriba;

    private Sprite SkyBck;


    private ArrayList<TreeBox> ryba;
    private ArrayList<TreeBox> setka;
    private ArrayList<TreeBox> akula;


    private TiledMapTileLayer BassejnLayer;
    private TiledMapTileLayer DnoLayer;
    private TiledMapTileLayer PriboyLayer;

    float torque = 0.0f;

    private Cat CatPlayer;

    private Crabb Spanch;

    public LevelScreen(SantaMausGame game,String LevelName) {
        super(game);

        this.LevelName = LevelName;
        this.ryba = new ArrayList<TreeBox>();
        this.setka = new ArrayList<TreeBox>();
        this.akula = new ArrayList<TreeBox>();


        CatPlayer = new Cat(game);
        CatPlayer.setPosition(TILE_WIDTH*20,CatPlayer.UpLineCat);

        Spanch = new Crabb(game);

        quadsetka = new QuadTree(0, new Rectangle(0,0,SantaMausGame.SCREEN_WIDTH,SantaMausGame.SCREEN_HEIGHT));
        quadriba  = new QuadTree(0, new Rectangle(0,0,SantaMausGame.SCREEN_WIDTH,SantaMausGame.SCREEN_HEIGHT));

        LoadLevelMap();

        renderer = new TextureMapObjectRenderer(map); //OrthogonalTiledMapRenderer(map);
        renderer.setView(game.camera);


        ribkaCoin = game.manager.get("obj/leftfish.png");
    }

    protected void LoadLevelMap() {

        quadsetka.clear();

        map = game.manager.get(LevelName);
        //create pipe bodies/fixtures
        MapObjects collisionObjects = map.getLayers().get("Collide").getObjects();
        for (int i = 0; i < collisionObjects.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);

            TreeBox   bb = new TreeBox(obj.getRectangle(), BoxKind.Wall);

            setka.add(bb);
            quadsetka.insert(bb);
        }

        collisionObjects = map.getLayers().get("Setka").getObjects();
        for (int i = 0; i < collisionObjects.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);

            TreeBox   bb = new TreeBox(obj.getRectangle(), BoxKind.Wall);

            setka.add(bb);
            quadsetka.insert(bb);
        }


        collisionObjects = map.getLayers().get("Fish").getObjects();
        for (int i = 0; i < collisionObjects.getCount(); i++) {
            RectangleMapObject obj = (RectangleMapObject) collisionObjects.get(i);
            Rectangle rect = obj.getRectangle();

            TreeBox   bb = new TreeBox(obj.getRectangle(), BoxKind.Fish);

            ryba.add(bb);
            quadriba.insert(bb);
        }

        BassejnLayer=(TiledMapTileLayer) map.getLayers().get("Bassejn");
        DnoLayer    =(TiledMapTileLayer)map.getLayers().get("Dno");
        PriboyLayer =(TiledMapTileLayer)map.getLayers().get("Priboy");

        map.getLayers().remove(DnoLayer);
    }


    @Override
    public void show() {
        Texture SkyTexture = game.manager.get("pool/sky.png");

        SkyBck =  new Sprite(SkyTexture);
        SkyBck.setPosition(0,SantaMausGame.SCREEN_HEIGHT-SkyTexture.getHeight());

    }

    private void DebugDraw()
    {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        Rectangle   rect;

        shapeRenderer.setProjectionMatrix(game.sb.getProjectionMatrix());

        for (TreeBox bb : ryba) {
            rect = bb.getBounds();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line); //I'm using the Filled ShapeType, but remember you have three of them
            shapeRenderer.setColor(Color.FOREST);
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height); //assuming you have created those x, y, width and height variables
            shapeRenderer.end();
        }
        for (TreeBox bb : setka) {
                rect = bb.getBounds();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line); //I'm using the Filled ShapeType, but remember you have three of them
                shapeRenderer.setColor(Color.FOREST);
                shapeRenderer.rect(rect.x,rect.y,rect.width,rect.height); //assuming you have created those x, y, width and height variables
                shapeRenderer.end();
            }

        rect = CatPlayer.getBound();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); //I'm using the Filled ShapeType, but remember you have three of them
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rect.x,rect.y,rect.width,rect.height); //assuming you have created those x, y, width and height variables
        shapeRenderer.end();
    }


    public void RybaDraw()
    {
        Rectangle bounds;

        for (TreeBox rybdraw : ryba) {
            if(rybdraw.getKind() == BoxKind.Fish)
            {
                bounds = rybdraw.getBounds();
                game.sb.draw(ribkaCoin,bounds.x,bounds.y);
            }
        }
    }


    private void EatFish()
    {
        List returnObjects = new ArrayList();
        Rectangle _bounds = CatPlayer.getBound();
        Array<TreeBox> eatenFish = new Array<TreeBox>();

        quadriba.retrieve(returnObjects, _bounds );
        for (int cc = 0; cc < returnObjects.size(); cc ++) {
            TreeBox bb = (TreeBox) returnObjects.get(cc);
            if (Intersector.overlaps(_bounds, bb.getBounds())) {
                CatPlayer.score += 100;
                eatenFish.add(bb);
            }
        }
        //теперь удалим сьеденную рыбку
        for (TreeBox _riba:eatenFish)
        {
            ryba.remove(_riba);
        }
        quadriba.clear();
        for (TreeBox _riba:ryba) {
            quadriba.insert(_riba);
        }
    }

    private void UpDateCat()
    {
        if(CatPlayer.Status==CatStatus.Sink)
        {
            if(CatPlayer.getY()>= TILE_HEIGHT)
            {
                CatPlayer.UpDate(0,CatPlayer.vy);
            }else
            {
                CatPlayer.lives -= 1;
                CatPlayer.setPosition(TILE_WIDTH*20,CatPlayer.UpLineCat);
                CatPlayer.Status = CatStatus.Swim;
            }
            return;
        }

        if(CatPlayer.CheckUpdate(quadsetka,0,CatPlayer.vy))
        {
            CatPlayer.UpDate(0,CatPlayer.vy);
            if(CatPlayer.getY()>=CatPlayer.UpLineCat)
            {
                CatPlayer.setPosition(CatPlayer.getX(), CatPlayer.UpLineCat);
                CatPlayer.setOxy(Cat.FullOxy);
                CatPlayer.Status = CatStatus.Swim;
            }
        } else {
            if (CatPlayer.Status == CatStatus.Dive)
            {
                CatPlayer.Status = CatStatus.Up;
            } else
                if(CatPlayer.Status == CatStatus.Up)
                {
                    CatPlayer.Status = CatStatus.Swim;
                }

        }
        if(CatPlayer.CheckUpdate(quadsetka,CatPlayer.vx,0))
        {
            CatPlayer.UpDate(CatPlayer.vx,0);
        }
    }

    @Override
    public void render(float delta) {
        CatPlayer.ProcessKey();
        UpDateCat();EatFish();

        Spanch.update(delta);

        // clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.viewport.apply();
        game.sb.setProjectionMatrix(game.camera.combined);

        game.camera.update();
        renderer.setView(game.camera);


        game.sb.begin();
        SkyBck.draw(game.sb);

        CatPlayer.DrawInfoPanel(game.sb);

        renderer.render();

        renderer.getBatch().begin();

        Spanch.render(renderer.getBatch());
        renderer.renderTileLayer(DnoLayer);
        renderer.getBatch().end();

        RybaDraw();

        CatPlayer.render(game.sb);


        game.sb.end();

    }


    @Override
    public void resize (int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void hide() {

    }
}
