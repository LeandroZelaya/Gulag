package com.zelaya.gulag.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zelaya.gulag.Main;
import com.zelaya.gulag.utiles.Config;
import com.zelaya.gulag.utiles.Render;

public class PantallaJuego implements Screen {

    private Main main;
    private OrthographicCamera camara;
    private Viewport viewport;

    public PantallaJuego(Main main) {
        this.main = main;
        camara = new OrthographicCamera();
        viewport = new FitViewport(Config.ANCHO, Config.ALTO, camara);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camara.update();
        Render.batch.setProjectionMatrix(camara.combined);
    }

    @Override
    public void resize(int ancho, int alto) {
        viewport.update(ancho, alto, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
