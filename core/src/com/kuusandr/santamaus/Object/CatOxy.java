package com.kuusandr.santamaus.Object;

import java.util.TimerTask;


public class CatOxy extends TimerTask
{
    private Cat catobj;

    public void setGame(Cat _catobj)
    {
        catobj = _catobj;
    }

    @Override
    public void run()
    {
        catobj.DoActionGameBar();
    }
}
