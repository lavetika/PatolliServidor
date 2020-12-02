/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorServicios;

import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;
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
        int posicion = jugadores.indexOf(jugador)+1;
        mandadero.addRespuesta("posicion", posicion);
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }
    
}
