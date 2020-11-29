
package Control;

import Dominio.Jugador;
import Graphics.Forma;
import conexionServidor.ComunicadorRedCliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author laura
 */
public class Partida {

    private List<ComunicadorRedCliente> jugadores;
    private int cantJugadores;
    private Tablero tablero;
    private int codigoPartida;
    private int maxApuesta;
    private List<Forma> posicion;
    private static Partida partida;
    private boolean estado;
    private int tamTablero;
    

    public static Partida getInstance() {
        
        if (partida == null) {
            partida = new Partida();
        } 
        return partida;
    }

    private Partida() {
        this.jugadores = new ArrayList<>();
        this.posicion = new ArrayList<>();
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getMaxApuesta() {
        return maxApuesta;
    }

    public void setMaxApuesta(int maxApuesta) {
        this.maxApuesta = maxApuesta;
    }

    public int getCantJugadores() {
        return cantJugadores;
    }

    public void setCantJugadores(int cantJugadores) {
        this.cantJugadores = cantJugadores;
    }
    
    public List<ComunicadorRedCliente> getJugadores() {
        return jugadores;
    }

    public boolean addJugador(ComunicadorRedCliente jugador) {
        if (this.getJugadores().size()!=Integer.MAX_VALUE) {
            return this.jugadores.add(jugador);
        }
        return false;

    }

    public void setJugadores(List<ComunicadorRedCliente> jugadores) {
        this.jugadores = jugadores;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public int getCodigoPartida() {
        return codigoPartida;
    }

    public void setCodigoPartida(int codigoPartida) {
        this.codigoPartida = codigoPartida;
    }

    public List<Forma> getPosicion() {
        return posicion;
    }

    public void setPosicion(List<Forma> posicion) {
        this.posicion = posicion;
    }

    public int getTamTablero() {
        return tamTablero;
    }

    public void setTamTablero(int tamTablero) {
        this.tamTablero = tamTablero;
    }
    
    
}
