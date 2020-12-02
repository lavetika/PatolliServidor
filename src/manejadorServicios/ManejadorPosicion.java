/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorServicios;

import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author laura
 */
public class ManejadorPosicion implements ManejadorServicios{

    Mandadero mandadero;
    ComunicadorRedCliente jugador;
    List<ComunicadorRedCliente> jugadores;
    
    public ManejadorPosicion(Mandadero mandadero, ComunicadorRedCliente jugador, List<ComunicadorRedCliente> jugadores) {
        this.mandadero = mandadero;
        this.jugador = jugador;
        this.jugadores = jugadores;
    }
    
    @Override
    public void ejecutar() {
//        int posicion = jugadores.indexOf(jugador);
//        mandadero.addRespuesta("posicion", posicion);
    List<String> nicknames= new ArrayList<>();
        for (ComunicadorRedCliente jugador : jugadores) {
            nicknames.add(jugador.getJugador().getNickname());
        }
        mandadero.addRespuesta("posiciones", nicknames);
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }
    
}
