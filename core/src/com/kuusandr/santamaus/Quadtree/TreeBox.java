package com.kuusandr.santamaus.Quadtree;

import com.badlogic.gdx.math.Rectangle;

public class TreeBox {
    private Rectangle Bounds;
    private BoxKind  Kind;


    public TreeBox(){}

    public TreeBox(Float x, Float y, Float w, Float h){
        Bounds = new Rectangle(x,y,w,h);
    }

    public TreeBox(Rectangle _p) {Bounds = _p; }

    public TreeBox(Rectangle _p,BoxKind _kind){ Bounds = _p; Kind = _kind;}

    public Rectangle getBounds() {
        return Bounds;
    }

    public Boolean contains(TreeBox box)
    {
        if(Bounds.contains(box.getBounds()))
        {
            return true;
        }else
        {
            return false;
        }
    }

    public Boolean intersect(TreeBox box)
    {
        if(Bounds.overlaps(box.getBounds()))
        {
            return  true;
        } else
        {
            return  false;
        }
    }

    public void setBounds(Rectangle bounds) {
        Bounds = bounds;
    }

    public BoxKind getKind() {
        return Kind;
    }

    public void setKind(BoxKind kind) {
        Kind = kind;
    }
}
