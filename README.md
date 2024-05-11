# IESCanariasSimulator

## Introduccion

IESCanariasSimulator es un videojuego hecho en javafx, cuenta con diferentes escenarios que puedes recorrer, elementos y npcs del escenario con los que interactuar, un minijuego con el que poner a prueba tu punteria y un sistema de combate por turnos, con combates aleatorias y dos bosses.

## Manual de uso
### Menu
![Menu inicial](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/menu.PNG)
En el menu de inicio contaremos con 4 opciones diferentes
- Jugar
- Configuracion
- Acerca de
- Salir

#### Jugar
Al seleccionar esta opcion se abrira una pantalla que contendra una pequeña introduccion de la historia del juego.
![Introduccion](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/jugar.PNG)

Si seleccionamos el boton que pone "¡Por supuesto!" accederemos al instituto.

#### Configuracion
Al seleccionar esta opcion abriremos el menu de configuracion donde podremos elegir el volumen de la musica del juego.
![Configuracion](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/confi.PNG)

#### Acerca de
Al seleccionar esta opcion se abrira una pantalla con los creditos del juego.
![Creditos](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/Creditos.PNG)

#### Salir
Al seleccionar esta opcion se cerrara la aplicacion.

### Escenario
Una vez hayamos iniciado el juego empezaremos en el instituto, podremos recorrerlo, asi como los diferentes escenarios.
![Instituto](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/instituto.PNG)

Dentro del mundo del juego hay diferentes npcs y elementos con los que se pueden interactuar
![Npcs](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/npc.PNG)
![Elementos del escenario](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/interactuarElementos.PNG)

### Combate
Mientras caminas por el mundo empezaran combates aleatorios
![Elementos del escenario](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/combate.PNG)

La interfaz del combate cuenta con los siguientes elementos

- Barras de vida: indican la salud del jugador y del enemigo. Empezara siendo verde y cuando baje del 50% y del 25%, cambiara a amarillo y rojo respectivamente.
- Texto vida: una representacion numerica de la vida restante, tanto del jugador como del enemigo.
- Texto nombre: indicara el nombre del jugador y del enemigo.
- Texto daño: cuando el jugador o el enemigo realice un ataque saldra un texto en pantalla indicando el daño del ataque.
- Sprite: el sprite situado a la izquierda es el del jugador y el situado a la derecha el del enemigo.
- Boton ataque: si seleccionas esta opcion realizaras un ataque fisico.
- Boton magico: si seleccionas esta opcion realizaras un ataque magico.
- Boton Fe: si seleccionas esta opcion realizaras un ataque magico menos poderoso pero con probabilidades de que sea critico.
- Boton Escapar: si seleccionas esta opcion intentaras huir del combate, si falla el enemigo te atacara y el jugador pierde el turno.

#### Game Over
Si la vida del jugador baja a 0 accedera a la pantalla de Game Over.
![Game Over](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/gameOver.PNG)
- Boton de reintentar: el jugador volvera a empezar el juego desde el principio
- Boton de salir: para salir de la aplicacion.


### Minijuego
El jugador podra jugar a un minijuego de punteria, con el objetivo de conseguir la puntuacion mas alta posible, la interfaz del minijuego cuenta con los siguientes elementos:
![Minijuego](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/minijuego.PNG)
- Texto tiempo: indica el tiempo restante para que termine el minijuego.
- Texto Puntos: indica la puntuacion actual del jugador en el minijuego.
- Arma: representacion del jugador.
- Enemigo: demonios rojos a los que el jugador tendra que disparar para conseguir puntos.

#### Acceder al minijuego
Para acceder al minijuego primero hay que llegar al parque en la parte izquierda del mapa.
![Acceder al minijuego parte 1](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/accederMinijuego1.PNG)
Una vez en el parque el jugador debera entrar por la puerta a la sala de arcade, aqui tiene que interactuar con la maquina de arcade para empezar el minijuego.
![Acceder al minijuego parte 2](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/accederMinijuego2.PNG)

### Final Boss
Batalla final contra el profesor Fran

#### Acceder al boss
Para acceder al boss final el jugador tendra que dirigirnos al instituto y subir al aula de 2ºDAM, aqui debera interactuar con el profesor para iniciar la batalla final.
![Habla con Fran](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/accederBossFran.PNG)

#### Victoria
Si derrotas a Fran aprobaras el curso y podras volver al menu principal.
A no ser...

## Jugador
![Jugador](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/Right2_HD.png)

## Enemigos
![Enemigo 1](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/Enemy1.png)
![Enemigo 2](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/Enemy2.png)
![Enemigo 3](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/Enemy3.png)
![Fran](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/Fran.png)

## Controles
### Menu
![Raton](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/raton.png)

### Escenario
![movimiento](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/directional.png)
![movimiento](https://raw.githubusercontent.com/dam-dad/IESCanariasSimulator/main/capturas/wasd.png)

## Ideas descartadas
Aqui presentaremos algunas ideas que descartamos por falta de tiempo:
#### Musica
Añadir una cancion de pelea y una cancion para el boss final principalmente. En general, una mayor seleccion musical.

### Boss
Boss secreto Betty.

### Progresion
Aumento de estadisticas al ganar combates.

### Objetos
Implementacion de objetos de recuperacion de vida y de aumento de estadistica.
Uso de objetos en combate.

### Dinero
Implementacion de sistema monetario.
Supermercado donde comprar objetos.

### Minijuegos
Ampliacion de la sala de arcade, incluyendo mas minijuegos.

### Misiones secundarias
Objetivos secundarios mediante la interaccion con los npcs.
Recompensas de aumento de estadisticas o de dinero al completar estos objetivos.