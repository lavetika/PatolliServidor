
package manejadorServicios;

import Control.Partida;
import Dominio.Jugador;
import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author laura
 */
public class ManejadorServicioAsignarTurno implements ManejadorServicios{
    
    private List<ComunicadorRedCliente> jugadores;
    private Mandadero mandadero;

    public ManejadorServicioAsignarTurno(List<ComunicadorRedCliente> jugadores, Mandadero m) {
        this.jugadores = jugadores;
        this.mandadero = m;
    }
    
    @Override
    public void ejecutar() {
        Collections.shuffle(jugadores);
        Partida.getInstance().setJugadores(jugadores);
        Partida.getInstance().setJugadorActual(jugadores.get(0));

        List<Jugador> listaJugadores = new ArrayList<>();

        for (int i = 0; i < this.jugadores.size(); i++) {
            listaJugadores.add(this.jugadores.get(i).getJugador());
        }
        Partida.getInstance().setIniciado(true);
        mandadero.addRespuesta("jugadoresRandom", listaJugadores);
    }

    @Override
    public Mandadero getRespuesta(){
        return this.mandadero;
    }

    @Override
    public String toString() {
        return "Manejador para asignar turno{" + "jugadores=" + jugadores + ", mandadero=" + mandadero + '}';
    }
    
    
    
    
    
    
    
}

