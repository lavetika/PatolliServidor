/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorServicios;

import Control.Partida;
import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;
import java.util.List;

/**
 *
 * @author Diana Jiménez
 */
public class ManejadorServicioAbandono implements ManejadorServicios {

    private Mandadero mandadero;
    private ComunicadorRedCliente jugador;
    private List<ComunicadorRedCliente> clientes;

    public ManejadorServicioAbandono(Mandadero mandadero, ComunicadorRedCliente jugador, List<ComunicadorRedCliente> jugadores) {
        this.mandadero = mandadero;
        this.jugador = jugador;
        this.clientes = jugadores;

    }

    @Override
    public void ejecutar() {
        clientes.remove(jugador);
        mandadero.addRespuesta("abandono", "removido");
        
        if (clientes.isEmpty()) {
            Partida.getInstance().setEstado(false);
            Partida.getInstance().setIniciado(false);
            Partida.getInstance().setJugadorActual(null);
        }
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }

}
