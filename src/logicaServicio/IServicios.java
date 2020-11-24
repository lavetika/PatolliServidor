package logicaServicio;

import Control.Tablero;
import Dominio.Apuesta;
import Dominio.Ficha;
import Dominio.Jugador;
import conexionServidor.ComunicadorRedCliente;

import java.util.List;

public interface IServicios {
    public void movimientoFicha(Ficha ficha, int cantidad);
    public void abandonoJugador(Jugador jugador);
    public void lanzarCanias(Jugador jugador);
    public void generarTablero(int tamanio);
    public void crearPartida(int numJugadores, int cantGemas, String nickname, Tablero tablero);
    public void establecerApuesta(Jugador jugador, Apuesta apuesta);
    public void realizarApuesta(Jugador jugadorApostador);
    public void ingresarPartida(String codigo, String nickname);
    public void iniciarPartida(List<Jugador> jugadores, Tablero tablero);
    public void asignarTurno(List<ComunicadorRedCliente> jugadores);
    public void terminarPartida(Tablero tablero, List<Ficha> fichas, List<Jugador> jugadores);
    public void recibirMensaje(List<Jugador> jugadores, String mensaje);
    public void enviarMensaje(Jugador jugador, String mensaje);

}
