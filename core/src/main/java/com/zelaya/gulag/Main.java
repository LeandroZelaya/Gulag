package com.zelaya.gulag;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zelaya.gulag.pantallas.PantallaMenu;
import com.zelaya.gulag.utiles.Render;

public class Main extends Game {

    @Override
    public void create() {
        Render.batch = new SpriteBatch();

        setScreen(new PantallaMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();

        if (Render.batch != null) {
            Render.batch.dispose();
        }
    }
}
