package com.zelaya.gulag.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zelaya.gulag.utiles.Config;

public class HUD {
    public Stage stage;
    private Viewport viewport;
    private Label etiquetaVidaJugador;
    private Label etiquetaVidaRival;

    public HUD(SpriteBatch sb) {
        viewport = new FitViewport(Config.ANCHO, Config.ALTO, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table tabla = new Table();
        tabla.top();
        tabla.setFillParent(true);

        etiquetaVidaJugador = new Label("Jugador: 100", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        etiquetaVidaRival = new Label("Rival: 100", new Label.LabelStyle(new BitmapFont(), Color.RED));

        tabla.add(etiquetaVidaJugador).expandX().padTop(10).left().padLeft(20);
        tabla.add(etiquetaVidaRival).expandX().padTop(10).right().padRight(20);

        stage.addActor(tabla);
    }

    public void actualizarVidaJugador(int vida) {
        etiquetaVidaJugador.setText("Jugador: " + vida);
    }

    public void actualizarVidaRival(int vida) {
        etiquetaVidaRival.setText("Rival: " + vida);
    }

    public void resize(int ancho, int alto) {
        viewport.update(ancho, alto, true);
    }

    public void dispose() {
        stage.dispose();
    }
}
