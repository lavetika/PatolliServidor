package logicaServicio;

import Control.Tablero;
import Dominio.Apuesta;
import Dominio.Ficha;
import Dominio.Jugador;
import conexionServidor.ComunicadorRedCliente;

import java.util.List;

public interface IServicios {
    public void movimientoFicha(Ficha ficha, int cantidad); // DOING
    public void crearPartida(int numJugadores, int cantGemas, String nickname, Tablero tablero); //DOING
    public void ingresarPartida(String codigo, String nickname); // DOING
    public void asignarTurno(List<ComunicadorRedCliente> jugadores); //DOING
    public void abandonoJugador(Jugador jugador); // TO DO
    public void establecerApuesta(Jugador jugador, Apuesta apuesta); // TO DO
    public void realizarApuesta(Jugador jugadorApostador); // TO DO
    public void iniciarPartida(List<Jugador> jugadores, Tablero tablero); //TO DO
    public void terminarPartida(Tablero tablero, List<Ficha> fichas, List<Jugador> jugadores); //TO DO
    public void recibirMensaje(List<Jugador> jugadores, String mensaje); // DONE
    public void enviarMensaje(Jugador jugador, String mensaje); // DONE
    public void lanzarCanias(Jugador jugador); //DESCARTADO
    public void generarTablero(int tamanio); // DESCARTADO

}
