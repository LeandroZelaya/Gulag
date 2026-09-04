package com.zelaya.gulag.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zelaya.gulag.Main;
import com.zelaya.gulag.elementos.Texto;
import com.zelaya.gulag.utiles.Config;
import com.zelaya.gulag.utiles.Recursos;
import com.zelaya.gulag.utiles.Render;

public class PantallaPausa implements Screen {
    private Main main;
    private Screen pantallaAnterior;
    private OrthographicCamera camara;
    private Viewport viewport;

    private Texto tituloPausa;
    private Texto opcionReanudar;
    private Texto opcionMenu;

    private Vector3 ratonPos;

    public PantallaPausa(Main main, Screen pantallaAnterior) {
        this.main = main;
        this.pantallaAnterior = pantallaAnterior;
        camara = new OrthographicCamera();
        viewport = new FitViewport(Config.ANCHO, Config.ALTO, camara);
        ratonPos = new Vector3();
    }

    @Override
    public void show() {
        // Título de Pausa
        tituloPausa = new Texto(Recursos.FUENTEMENU, 80, Color.RED, true);
        tituloPausa.setTexto("PAUSA");
        tituloPausa.setPosition(
            (int) ((Config.ANCHO / 2) - (tituloPausa.getAncho() / 2)),
            (int) (Config.ALTO * 0.70f)
        );

        // Opción Reanudar
        opcionReanudar = new Texto(Recursos.FUENTEMENU, 45, Color.WHITE, true);
        opcionReanudar.setTexto("Reanudar");
        opcionReanudar.setPosition(
            (int) ((Config.ANCHO / 2) - (opcionReanudar.getAncho() / 2)),
            (int) (Config.ALTO * 0.45f)
        );

        // Opción Salir al Menú
        opcionMenu = new Texto(Recursos.FUENTEMENU, 45, Color.WHITE, true);
        opcionMenu.setTexto("Salir al Menu");
        opcionMenu.setPosition(
            (int) ((Config.ANCHO / 2) - (opcionMenu.getAncho() / 2)),
            (int) (Config.ALTO * 0.30f)
        );
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0.1f, 0.1f, 0.15f);

        camara.update();
        Render.batch.setProjectionMatrix(camara.combined);

        // Traducir coordenadas del mouse al mundo de la cámara/viewport
        ratonPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(ratonPos);

        // Detección de hover y clics
        boolean sobreReanudar = estaSobreTexto(opcionReanudar, ratonPos.x, ratonPos.y);
        boolean sobreMenu = estaSobreTexto(opcionMenu, ratonPos.x, ratonPos.y);

        if (sobreReanudar) {
            opcionReanudar.setColor(Color.YELLOW);
            if (Gdx.input.isButtonJustPressed(0)) { // Clic izquierdo
                main.setScreen(pantallaAnterior);
                return;
            }
        } else {
            opcionReanudar.setColor(Color.WHITE);
        }

        if (sobreMenu) {
            opcionMenu.setColor(Color.YELLOW);
            if (Gdx.input.isButtonJustPressed(0)) { // Clic izquierdo
                main.setScreen(new PantallaMenu(main));
                return;
            }
        } else {
            opcionMenu.setColor(Color.WHITE);
        }

        // Atajo por si prefiere presionar ESC para volver al juego
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            main.setScreen(pantallaAnterior);
            return;
        }

        // Dibujar textos
        Render.batch.begin();
        tituloPausa.dibujar();
        opcionReanudar.dibujar();
        opcionMenu.dibujar();
        Render.batch.end();
    }

    private boolean estaSobreTexto(Texto texto, float mx, float my) {
        float x = texto.getX();
        float y = texto.getY();
        float ancho = texto.getAncho();
        float alto = 40f; // Altura estimada del área de toque

        return mx >= x && mx <= (x + ancho) && my >= (y - 10) && my <= (y + alto);
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
        if (tituloPausa != null) tituloPausa.dispose();
        if (opcionReanudar != null) opcionReanudar.dispose();
        if (opcionMenu != null) opcionMenu.dispose();
    }
}
