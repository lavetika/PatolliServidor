/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorServicios;

import Control.Partida;
import Dominio.Jugador;
import Dominio.TipoJugador;
import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author laura
 */
public class ManejadorServicioPosicion implements ManejadorServicios {

    Mandadero mandadero;
    ComunicadorRedCliente jugador;
    List<ComunicadorRedCliente> jugadores;

    public ManejadorServicioPosicion(Mandadero mandadero, ComunicadorRedCliente jugador, List<ComunicadorRedCliente> jugadores) {
        this.mandadero = mandadero;
        this.jugador = jugador;
        this.jugadores = jugadores;
    }

    @Override
    public void ejecutar() {
        List<Jugador> listaJugadores = new ArrayList<>();

        for (int i = 0; i < this.jugadores.size(); i++) {

            listaJugadores.add(this.jugadores.get(i).getJugador());
        }

        mandadero.addRespuesta("posiciones", listaJugadores);
        
        if (jugador.getHost() != null) {
            Jugador jugadorH = jugador.getHost();
            mandadero.addRespuesta("host", jugadorH);
        }
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }

}
