package com.kuusandr.santamaus.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kuusandr.santamaus.SantaMausGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		try {
				LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
				config.title = "Fishercat";
				config.vSyncEnabled = true;
				config.width =  960;
				config.height = 720;
				new LwjglApplication(new SantaMausGame(), config);
			}
		catch (Exception e) {
				Gdx.app.exit();
				System.out.println(e);
			}
	}
}
