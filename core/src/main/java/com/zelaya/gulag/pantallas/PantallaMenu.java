package com.zelaya.gulag.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zelaya.gulag.Main;
import com.zelaya.gulag.elementos.Imagen;
import com.zelaya.gulag.elementos.Texto;
import com.zelaya.gulag.utiles.Config;
import com.zelaya.gulag.utiles.Recursos;
import com.zelaya.gulag.utiles.Render;

public class PantallaMenu implements Screen {

    private Main main;

    private Imagen fondo;

    private Texto titulo;
    private Texto opcionJugar;
    private Texto opcionSalir;

    private int opcionSeleccionada = 0; // 0 = Jugar, 1 = Salir

    private OrthographicCamera camara;
    private Viewport viewport;

    public PantallaMenu(Main main) {
        this.main = main;
        camara = new OrthographicCamera();
        viewport = new FitViewport(Config.ANCHO, Config.ALTO, camara);
    }

    @Override
    public void show() {

        fondo = new Imagen(Recursos.FONDOMENU);
        fondo.setSize(Config.ANCHO, Config.ALTO);

        // Título
        titulo = new Texto(Recursos.FUENTEMENU, 90, Color.RED, true);
        titulo.setTexto("GULAG");
        titulo.setPosition(
            (int) ((Config.ANCHO / 2) - (titulo.getAncho() / 2)),
            (int) (Config.ALTO * 0.75f)
        );

        // Botón Jugar
        opcionJugar = new Texto(Recursos.FUENTEMENU, 55, Color.WHITE, true);
        opcionJugar.setTexto("Jugar");
        opcionJugar.setPosition(
            (int) ((Config.ANCHO / 2) - (opcionJugar.getAncho() / 2)),
            (int) (Config.ALTO * 0.45f)
        );

        // Botón Salir
        opcionSalir = new Texto(Recursos.FUENTEMENU, 55, Color.WHITE, true);
        opcionSalir.setTexto("Salir");
        opcionSalir.setPosition(
            (int) ((Config.ANCHO / 2) - (opcionSalir.getAncho() / 2)),
            (int) (Config.ALTO * 0.32f)
        );
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Cambiar opción seleccionada con flechas
        if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            opcionSeleccionada = 1;
        }
        if (Gdx.input.isKeyJustPressed(Keys.UP)) {
            opcionSeleccionada = 0;
        }

        // Actualizar colores según selección
        if (opcionSeleccionada == 0) {
            opcionJugar.setColor(Color.YELLOW);
            opcionSalir.setColor(Color.WHITE);
        } else {
            opcionJugar.setColor(Color.WHITE);
            opcionSalir.setColor(Color.YELLOW);
        }

        // Confirmar selección con ENTER
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            if (opcionSeleccionada == 0) {
                main.setScreen(new PantallaJuego(main));
            } else {
                Gdx.app.exit();
            }
        }

        camara.update();
        Render.batch.setProjectionMatrix(camara.combined);

        Render.batch.begin();
        fondo.dibujar();
        titulo.dibujar();
        opcionJugar.dibujar();
        opcionSalir.dibujar();
        Render.batch.end();
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
    public void dispose() {
        fondo.dispose();
        titulo.dispose();
        opcionJugar.dispose();
        opcionSalir.dispose();
    }
}
