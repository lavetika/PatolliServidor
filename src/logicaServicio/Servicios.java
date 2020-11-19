package logicaServicio;

import Control.Tablero;
import Dominio.Apuesta;
import Dominio.Ficha;
import Dominio.Jugador;

import java.util.List;

public class Servicios implements IServicios {


    @Override
    public void movimientoFicha(Ficha ficha, int cantidad) {

    }

    @Override
    public void abandonoJugador(Jugador jugador) {

    }

    @Override
    public void lanzarCanias(Jugador jugador) {

    }

    @Override
    public void generarTablero(int tamanio) {

    }

    @Override
    public void establecerApuesta(Jugador jugador, Apuesta apuesta) {

    }

    @Override
    public void realizarApuesta(Jugador jugadorApostador) {

    }

    @Override
    public void ingresarPartida(String codigo, String nickname) {

    }

    @Override
    public void asignarTurno(List<Jugador> jugadores) {

    }

    @Override
    public void recibirMensaje(List<Jugador> jugadores, String mensaje) {

    }

    @Override
    public void enviarMensaje(Jugador jugador, String mensaje) {

    }

    @Override
    public void terminarPartida(Tablero tablero, List<Ficha> fichas, List<Jugador> jugadores) {

    }

    @Override
    public void iniciarPartida(List<Jugador> jugadores, Tablero tablero) {

    }

    @Override
    public void crearPartida(int numJugadores, int cantGemas, String nickname, Tablero tablero) {

    }
}
