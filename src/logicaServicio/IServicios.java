package logicaServicio;

import Control.Tablero;
import Dominio.Apuesta;
import Dominio.Ficha;
import Dominio.Jugador;
import conexionServidor.ComunicadorRedCliente;

import java.util.List;

public interface IServicios {

    public void establecerApuesta(Jugador jugador, Apuesta apuesta); // TO DO (DianaBb)
    public void realizarApuesta(Jugador jugadorApostador); // TO DO
    public void iniciarPartida(List<Jugador> jugadores, Tablero tablero); //TO DO
    public void terminarPartida(Tablero tablero, List<Ficha> fichas, List<Jugador> jugadores); //TO DO
    public void movimientoFicha(Ficha ficha, int cantidad); // DOING (Laura)
    public void cambiarTurno();//DOING (Tengo mucha hambre xD)
    public void ingresarPartida(String codigo, String nickname); // DOING(Annie)
    public void crearPartida(int numJugadores, int cantGemas, String nickname, Tablero tablero); //DONE
    public void asignarTurno(List<ComunicadorRedCliente> jugadores); //DONE
    public void abandonoJugador(Jugador jugador); // DONE (Viejona)
    public void recibirMensaje(List<Jugador> jugadores, String mensaje); // DONE
    public void enviarMensaje(Jugador jugador, String mensaje); // DONE
    public void lanzarCanias(Jugador jugador); //DESCARTADO
    public void generarTablero(int tamanio); // DESCARTADO

}
