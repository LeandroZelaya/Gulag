package com.zelaya.gulag.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zelaya.gulag.Main;
import com.zelaya.gulag.ui.HUD;
import com.zelaya.gulag.elementos.Mapa;
import com.zelaya.gulag.entidades.Jugador;
import com.zelaya.gulag.gestores.GestorEntradas;
import com.zelaya.gulag.utiles.Config;
import com.zelaya.gulag.utiles.Recursos;
import com.zelaya.gulag.utiles.Render;

public class PantallaJuego implements Screen {
    private Main main;
    private OrthographicCamera camara;
    private Viewport viewport;
    private Mapa mapa;
    private Jugador jugador;
    private GestorEntradas gestorEntradas;
    private Music musicaJuego;
    private HUD hud;

    private Rectangle hitboxRivalDummy;
    private int vidaJugador = 100;
    private int vidaRival = 100;
    private boolean rondaTerminada = false;
    private float tiempoDesdeUltimoAtaque = 0;

    private final float CENTRO_MAPA_X = 640f / 2f;
    private final float CENTRO_MAPA_Y = 368f / 2f;

    private ShapeRenderer shapeRenderer;

    public PantallaJuego(Main main) {
        this.main = main;
        camara = new OrthographicCamera();
        viewport = new FitViewport(Config.ANCHO, Config.ALTO, camara);
    }

    @Override
    public void show() {
        if (mapa == null) {
            mapa = new Mapa(Recursos.MAPA);
        }

        if (gestorEntradas == null) {
            gestorEntradas = new GestorEntradas();
        }
        Gdx.input.setInputProcessor(gestorEntradas);

        if (jugador == null) {
            jugador = new Jugador(
                mapa,
                16f,
                144f,
                gestorEntradas
            );
        }

        if (hud == null) {
            hud = new HUD(Render.batch);
        }

        hitboxRivalDummy = new Rectangle(300f, 144f, 32f, 32f);

        if (musicaJuego == null) {
            musicaJuego = Gdx.audio.newMusic(Gdx.files.internal(Recursos.MUSICA_JUEGO));
            musicaJuego.setLooping(true);
            musicaJuego.setVolume(0.4f);
        }
        musicaJuego.play();

        camara.zoom = 0.51f;
        viewport.apply();
        if (shapeRenderer == null) {
            shapeRenderer = new ShapeRenderer();
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0.1f, 0.1f, 0.1f);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (musicaJuego != null) musicaJuego.pause();
            main.setScreen(new PantallaPausa(main, this));
            return;
        }

        if (!rondaTerminada) {
            jugador.actualizar(delta, camara);
            tiempoDesdeUltimoAtaque += delta;

            if (gestorEntradas.atacando && tiempoDesdeUltimoAtaque > 0.4f) {
                if (jugador.getBounds().overlaps(hitboxRivalDummy)) {
                    vidaRival -= 20;
                    hud.actualizarVidaRival(Math.max(vidaRival, 0));
                    tiempoDesdeUltimoAtaque = 0;
                    System.out.println("¡Golpe conectado! Vida rival: " + vidaRival);
                }
            }

            if (vidaRival <= 0) {
                rondaTerminada = true;

                if (musicaJuego != null) {
                    musicaJuego.stop();
                }

                main.setScreen(new PantallaMenu(main));
                return;
            }
        }

        viewport.apply();
        camara.position.set(CENTRO_MAPA_X, CENTRO_MAPA_Y, 0);
        camara.update();

        mapa.dibujar(camara);
        jugador.dibujar(camara);

        shapeRenderer.setProjectionMatrix(camara.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        shapeRenderer.rect(hitboxRivalDummy.x, hitboxRivalDummy.y, hitboxRivalDummy.width, hitboxRivalDummy.height);
        shapeRenderer.end();

        hud.stage.act(delta);
        hud.stage.draw();
    }

    @Override
    public void resize(int ancho, int alto) {
        viewport.update(ancho, alto, true);
        hud.resize(ancho, alto);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {
        if (musicaJuego != null && !musicaJuego.isPlaying()) {
            musicaJuego.play();
        }
    }

    @Override
    public void hide() {
        if (musicaJuego != null) {
            musicaJuego.stop();
        }
    }

    @Override
    public void dispose() {
        if (musicaJuego != null) musicaJuego.dispose();
        if (mapa != null) mapa.dispose();
        if (jugador != null) jugador.dispose();
        if (hud != null) hud.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();

    }
}
