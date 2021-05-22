package com.kuusandr.santamaus.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kuusandr.santamaus.ProgressBar;
import com.kuusandr.santamaus.Quadtree.BoxKind;
import com.kuusandr.santamaus.Quadtree.QuadTree;
import com.kuusandr.santamaus.Quadtree.TreeBox;
import com.kuusandr.santamaus.SantaMausGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static com.kuusandr.santamaus.SantaMausGame.TILE_HEIGHT;


public class Cat {

    public static float UpLineCat = TILE_HEIGHT*35+9;
    public static int FullOxy   = 500;

    private final SantaMausGame game;

    private Timer GameTimer;
    private CatOxy  gtOxyTimer;

    private Texture UpPanelka;
    private Texture CatIcon;
    private Texture ScoreIcon;
    private Texture OxyIcon;

    private Animation<TextureRegion>  CatUp;
    private Animation<TextureRegion>  CatDown;
    private Texture CatSink;
    private Texture CatFat;
    private Texture CatBone;

    public float Catelapsed;

    private BitmapFont InfoCatFont;

    private final ProgressBar BarOxy;

    private float KatWidth;
    private float KatHeight;
    private float ScoreWidth;

    private float x;
    private float y;

    public float vx;
    public float vy;

    public CatStatus Status;

    public int lives;
    public int score;
    public int silver;
    public int gold;
    public int oxytime;

    public Cat(SantaMausGame gm)
    {
        this.game = gm;

        vx = 0;
        vy = 0;

        Catelapsed = 0;

        lives   = 3;
        score   = 0;
        silver  = 0;
        gold    = 0;
        oxytime = FullOxy;

        x = 0.0f; y = 0.0f;

        Status = CatStatus.Swim;

        BarOxy = new ProgressBar(0,oxytime,oxytime);

        CreateTimer();

        LoadImages();
        LoadAnim();
    }

    private void CreateTimer() {
        // создадим и запустим игровой таймер
        GameTimer = new Timer();
        gtOxyTimer = new CatOxy();
        gtOxyTimer.setGame(this);

        GameTimer.schedule(gtOxyTimer, 0, 250);
    }

    public void setOxy(float _ox)
    {
        BarOxy.setCurrValue(_ox);
    }
    public void setPosition(float _x,float _y)
    {
        x=_x; y=_y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public Rectangle getBound()
    {
        float dx = x - CatBone.getWidth()/2;
        float dy = y - CatBone.getHeight()/2;
        return new Rectangle(dx,dy ,CatBone.getWidth(),CatBone.getHeight());
    }

    public Boolean CheckUpdate(QuadTree tree,float _vx,float _vy)
    {
        float dx = x - CatBone.getWidth()/2;
        float dy = y - CatBone.getHeight()/2;

        Rectangle bounds = new Rectangle(dx + _vx,dy + _vy,CatBone.getWidth(),CatBone.getHeight());
        List returnObjects = new ArrayList();

        tree.retrieve(returnObjects, bounds );

        for (int cc = 0; cc < returnObjects.size(); cc ++) {
            TreeBox bb = (TreeBox) returnObjects.get(cc);
            if(Intersector.overlaps(bounds,bb.getBounds()))
            {
                return  false;
            }
        }
        return true;
    }

    public void ProcessKey()
    {
        if(game.RightKey)
        {
            vx = 0.7f;
        }else
        if(game.LeftKey)
        {
            vx = -0.7f;
        }else
        {
            vx = 0f;
        }
        if((game.DownKey)&&((Status==CatStatus.Swim)))
        {
            Status = CatStatus.Dive;
        }

        switch (Status)
        {
            case Dive:
            case Sink:
                vy = -0.7f;
                break;
            case Up:
                vy = 0.7f;
                break;
            case Swim:
            default:
                if(y<UpLineCat)
                {
                    vy = 0.7f;
                }else
                {
                    vy = 0;
                }
        }


    }

    public void UpDate(float _vx,float _vy)
    {
        Catelapsed += Gdx.graphics.getDeltaTime();

        x += _vx;
        y += _vy;
    }

    public void render(SpriteBatch batch)
    {
        float dx = x - CatBone.getWidth()/2;
        float dy = y - CatBone.getHeight()/2;

        switch (Status) {
            case Up:
            case Swim:
                batch.draw(CatUp.getKeyFrame(Catelapsed, true), dx, dy);
                break;
            case Dive:
                batch.draw(CatDown.getKeyFrame(Catelapsed, true), dx, dy);
                break;
            case Bone:
                batch.draw(CatBone, dx, dy);
                break;
            case Fat:
                batch.draw(CatFat, dx, dy);
                break;
            case Sink:
                batch.draw(CatSink, dx, dy);
                break;

        }
    }

    protected void LoadAnim() {
        Texture KatUpSht = (Texture) game.manager.get("cat/upsht.png");
        Texture KatDnSht  = (Texture) game.manager.get("cat/dsht.png");
        CatBone   = (Texture) game.manager.get("cat/bone.png");
        CatSink    = (Texture) game.manager.get("cat/sink.png");
        CatFat    = (Texture) game.manager.get("cat/fat.png");

        TextureRegion[][] tmp = TextureRegion.split(KatUpSht,
                KatUpSht.getWidth() ,
                KatUpSht.getHeight() / 2);

        TextureRegion[] KatUpFrame             = new TextureRegion[2];
        KatUpFrame[0] = tmp[0][0];
        KatUpFrame[1] = tmp[1][0];

        tmp = TextureRegion.split(KatDnSht,
                KatDnSht.getWidth() ,
                KatDnSht.getHeight() / 2);

        TextureRegion[] KatDnFrame             = new TextureRegion[2];
        KatDnFrame[0] = tmp[0][0];
        KatDnFrame[1] = tmp[1][0];

        CatUp   = new Animation<TextureRegion>(0.15f, KatUpFrame);
        CatDown = new Animation<TextureRegion>(0.15f, KatDnFrame);
    }

    protected void DoActionGameBar()
    {
        float cat_y   = getY();
        float oxy_cc  = BarOxy.getCurrValue();

            if ((cat_y < UpLineCat)&&(Status!=CatStatus.Sink)) {
                // Обновим бар кислорода
                if (oxy_cc > 0) {
                    BarOxy.setCurrValue(oxy_cc - 1);
                } else {
                    Status = CatStatus.Sink;
                    BarOxy.setCurrValue(FullOxy);
                }
            }
    }

    protected void LoadImages() {
        UpPanelka = (Texture) game.manager.get("ui/uppnl.png");
        CatIcon   = (Texture) game.manager.get("ui/cat.png");
        ScoreIcon = (Texture) game.manager.get("ui/score.png");
        OxyIcon   = (Texture) game.manager.get("ui/oxygen.png");
        InfoCatFont = (BitmapFont) game.manager.get("fnt/gameui.fnt");

        setLineHeight(InfoCatFont,CatIcon.getHeight());

        GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(InfoCatFont,"00");

        KatWidth = layout.width;
        KatHeight = layout.height;

        layout.setText(InfoCatFont,"999999");
        ScoreWidth = layout.width;
    }

    public void DrawInfoPanel(SpriteBatch batch)
    {
        int  otstup;
        otstup = (int)((UpPanelka.getHeight()-CatIcon.getHeight())/2.0);
        batch.draw(UpPanelka,0,SantaMausGame.SCREEN_HEIGHT - UpPanelka.getHeight(),SantaMausGame.SCREEN_WIDTH,UpPanelka.getHeight());
        batch.draw(CatIcon,10,SantaMausGame.SCREEN_HEIGHT - CatIcon.getHeight() - otstup);
        InfoCatFont.draw(batch, GetSringScore(lives, 2), 10 + CatIcon.getWidth() + 5, SantaMausGame.SCREEN_HEIGHT - (int)KatHeight );
        batch.draw(ScoreIcon, 40 + CatIcon.getWidth()+ KatWidth,SantaMausGame.SCREEN_HEIGHT - CatIcon.getHeight() - otstup);
        InfoCatFont.draw(batch,GetSringScore(score,6),45 + ScoreIcon.getWidth() + CatIcon.getWidth()+ KatWidth,SantaMausGame.SCREEN_HEIGHT - (int)KatHeight);
        batch.draw(OxyIcon,60 + ScoreIcon.getWidth() + CatIcon.getWidth()+ KatWidth + ScoreWidth, SantaMausGame.SCREEN_HEIGHT - CatIcon.getHeight() - otstup);
        BarOxy.draw(batch,OxyIcon.getWidth()+5+60 + ScoreIcon.getWidth() + CatIcon.getWidth()+ KatWidth + ScoreWidth,
                SantaMausGame.SCREEN_HEIGHT - CatIcon.getHeight() - otstup,100, CatIcon.getHeight());
    }

    private void setLineHeight(BitmapFont font, float height) {
        font.getData().setScale(height * font.getScaleY() / font.getLineHeight());
    }
    private String GetSringScore(int cc, int razr) {
        String cur_v = String.valueOf(cc);
        if(razr>cur_v.length()) {
            int mr = razr - cur_v.length();
            for (int i = 1; i < mr ; i++) {
                cur_v = "0" + cur_v;
            }
        }else
        {
            cur_v = "";
            for(int i=1;i<razr;i++) {
                cur_v = cur_v+"9";
            }
        }
        return cur_v;

    }

}
