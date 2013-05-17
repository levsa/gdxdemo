package com.badlogic.gdxdemo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GdxDemo extends Game {
    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;
    private static final int BUCKET_WIDTH = 48;
    private static final int BUCKET_HEIGHT = 48;
    private static final int RAINDROP_WIDTH = 48;
    private static final int RAINDROP_HEIGHT = 48;
    private static final long RAINDROP_SPAWN_DELAY_NS = 1000000000;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    OrthographicCamera camera = new OrthographicCamera();
    SpriteBatch spriteBatch;
    Vector3 touchPosition = new Vector3();
    Array<Rectangle> raindrops = new Array<Rectangle>();
    long lastRaindropTimeInNs;

    @Override
    public void create() {
        loadAssets();
        rainMusic.setLooping(true);
        rainMusic.play();
        spriteBatch = new SpriteBatch();
        spawnRaindrop();
        camera.setToOrtho(false, APP_WIDTH, APP_HEIGHT);
    }

    @Override
    public void render() {
        Rectangle bucket = new Rectangle();
        bucket.x = APP_WIDTH / 2 - BUCKET_WIDTH / 2;
        bucket.y = 20;
        bucket.width = BUCKET_WIDTH;
        bucket.height = BUCKET_HEIGHT;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : raindrops) {
            spriteBatch.draw(dropImage, raindrop.x, raindrop.y);
        }
        spriteBatch.end();

//        if (Gdx.input.isTouched()) {
//            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPosition);
//            bucket.x = touchPosition.x - BUCKET_WIDTH / 2;
//        }

        Iterator<Rectangle> iterator = raindrops.iterator();
        while (iterator.hasNext()) {
            Rectangle raindrop = iterator.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + RAINDROP_HEIGHT < 0) {
                iterator.remove();
            }
        }
        if (TimeUtils.nanoTime() - lastRaindropTimeInNs > RAINDROP_SPAWN_DELAY_NS) {
            spawnRaindrop();
        }
        handleInput(bucket);
    }

    private void handleInput(Rectangle bucket) {
        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            bucket.x -= 10;//200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            bucket.x += 10;//200 * Gdx.graphics.getDeltaTime();
        }
        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x >= APP_WIDTH - BUCKET_WIDTH) {
            bucket.x = APP_WIDTH - BUCKET_WIDTH;
        }
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, APP_WIDTH - RAINDROP_WIDTH);
        raindrop.y = APP_HEIGHT;
        raindrop.width = RAINDROP_WIDTH;
        raindrop.height = RAINDROP_HEIGHT;
        raindrops.add(raindrop);
        lastRaindropTimeInNs = TimeUtils.nanoTime();
    }

    private void loadAssets() {
        dropImage = new Texture(Gdx.files.internal("data/droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("data/bucket.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("data/drop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("data/rain.mp3"));
    }
}
