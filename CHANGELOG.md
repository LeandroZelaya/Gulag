# Changelog

## [0.1.0] - 16/7/2026

### Agregado

- Creación inicial del proyecto Gulag mediante LibGDX.
- Configuración del archivo .gitignore.
- Creación del archivo README.md.
- Creación del archivo CHANGELOG.md.
- Creación de la estructura inicial de la Wiki del proyecto.

## [0.2.0] - 2026-08-22

### Agregado
- Definición de la estructura de paquetes del proyecto (pantallas, entidades, gestores, ui)
- Clases vacías creadas: PantallaMenu, PantallaJuego, Jugador, GestorEntradas, GestorRecursos, HUD
- 
## [0.3.0] - 2026-08-25

### Agregado
- Clase `Config` con las constantes de resolución del juego (ANCHO y ALTO).
- Clase `Recursos` con las rutas de los assets (fondo del menú y fuente).
- Clase `Render` con el `SpriteBatch` estático compartido entre pantallas.
- Clase `Imagen` para cargar y dibujar texturas mediante `Sprite`.
- Clase `Texto` para generar y dibujar fuentes con `FreeTypeFontGenerator`.
- Implementación completa de `PantallaMenu` con fondo, título y dos opciones navegables con teclado (Jugar / Salir).
- Implementación base de `PantallaJuego` con cámara y viewport configurados.
- Implementación de `Main` con inicialización del `SpriteBatch` y carga de la pantalla inicial.
- Assets agregados: imagen de fondo del menú y fuente personalizada.

## [0.4.0] - 2026-09-04

### Agregado
- Centralización del procesamiento de entradas de teclado y mouse mediante la clase `GestorEntradas` (`InputAdapter`).
- Implementación de la interfaz de usuario (`HUD`) utilizando `Scene2D` (`Stage` y `Label`) para visualizar las barras de vida en tiempo real.
- Mecánica central de combate y sistema de daño contra un rival de prueba (dummy), incluyendo detección de colisiones por `Rectangle` y control de *cooldown* de ataque.
- Lógica de cambio de estado de juego: transición automática de vuelta al menú principal (`PantallaMenu`) al agotarse la vida del rival.
- Integración temporal de `ShapeRenderer` para la visualización visual del hitbox del rival de prueba durante las pruebas de desarrollo.
- Actualización de la documentación y el estado actual del prototipo en el archivo `README.md`.
