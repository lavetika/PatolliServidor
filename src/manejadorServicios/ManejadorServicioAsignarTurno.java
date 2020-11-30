/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorServicios;

import Control.Partida;
import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;
//import java.util.ArrayList;
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
    public void ejecutar(){
        System.out.println("Capirotada");
        System.out.println(jugadores);
        Collections.shuffle(jugadores);
        System.out.println("Lista de comunicador red cliente:" +jugadores);
        Partida.getInstance().setJugadores(jugadores);
        Partida.getInstance().setJugadorActual(jugadores.get(0));
//        System.out.println("Lista de ");
        mandadero.addRespuesta("jugadoresRandom", jugadores);
    }
    
    @Override
    public Mandadero getRespuesta(){
        ejecutar();
        System.out.println("Bonito");
        System.out.println(jugadores);
        return this.mandadero;
    }

    @Override
    public String toString() {
        return "Manejador para asignar turno{" + "jugadores=" + jugadores + ", mandadero=" + mandadero + '}';
    }
    
    
    
    
    
    
    
}
