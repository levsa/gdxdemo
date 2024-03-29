package com.badlogic.gdxdemo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: levsa
 * Date: 5/17/13
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DesktopStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "GdxDemo";
        cfg.useGL20 = true;
        cfg.width = GdxDemo.APP_WIDTH;
        cfg.height = GdxDemo.APP_HEIGHT;
        new LwjglApplication(new GdxDemo(), cfg);
    }
}