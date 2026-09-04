# Gulag

## Integrante

- Leandro Zelaya

## Descripción del Proyecto

Gulag es un videojuego de acción y combate 2D con perspectiva top-down inspirado en el estilo de juego de Hotline Miami.
Dos jugadores conectados mediante una red se enfrentarán en combates 1 vs 1 dentro de escenarios cerrados ambientados en instalaciones soviéticas deterioradas.
Cada ronda comienza con ambos jugadores desarmados y deberán disputar las armas que aparecerán progresivamente en el mapa para obtener ventaja sobre su oponente.
El objetivo es reducir la barra de vida del rival a cero para ganar rondas. La partida se desarrolla al mejor de cinco rondas, resultando ganador el primer jugador que consiga tres victorias.
El proyecto utilizará una arquitectura cliente-servidor. Un servidor central coordinará el estado de la partida y sincronizará las acciones de los dos clientes conectados en tiempo real.

## Tecnologías Utilizadas

- Java 21 LTS
- LibGDX 1.14.2
- LWJGL3 (plataforma de escritorio)
- Gradle
- Sockets TCP y UDP para la comunicación en red

## Estado Actual

El proyecto cuenta con un prototipo funcional que incluye:
- **Gestión de Pantallas:** Estructura modular basada en `Screen` (Menú principal, Pantalla de Pausa y Pantalla de Juego).
- **Mapeo y Entidades:** Carga y renderizado de mapas Tiled y movimiento fluido del jugador adaptado con `Viewport` y `OrthographicCamera`.
- **Entradas Centralizadas:** Procesamiento de controles de teclado y mouse mediante clases adaptadoras específicas (`InputAdapter`).
- **Mecánica Central y HUD:** Interfaz de usuario integrada con `Scene2D` para visualizar las barras de vida, sistema de colisiones para detección de daño y objetivos de prueba.
- **Cambio de Estado:** Lógica de fin de ronda que conmuta el flujo del juego de vuelta al menú principal.

## Wiki del Proyecto

La documentación completa puede consultarse en la Wiki del repositorio:

https://github.com/LeandroZelaya/Gulag/wiki

## Cómo compilar y ejecutar

### Requisitos

- JDK 21

### Clonar el repositorio

```bash
git clone [https://github.com/LeandroZelaya/Gulag.git](https://github.com/LeandroZelaya/Gulag.git)

### Ingresar a la carpeta del proyecto

```bash
cd Gulag
```

### Ejecutar en Windows

```bash
gradlew.bat lwjgl3:run
```

### Ejecutar en Linux/macOS

```bash
./gradlew lwjgl3:run
```
