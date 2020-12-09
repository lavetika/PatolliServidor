package manejadorServicios;

import Control.Partida;
import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;
import java.util.List;

public class ManejadorServicioCambiarTurno implements ManejadorServicios {

    private Mandadero mandadero;
    private Partida partida;

    public ManejadorServicioCambiarTurno(Mandadero mandadero) {
        this.mandadero = mandadero;
        this.partida = Partida.getInstance();
    }

    @Override
    public void ejecutar() {
        ComunicadorRedCliente jugador = partida.getJugadorActual();
        List<ComunicadorRedCliente> jugadores = partida.getJugadores();
        int index = jugadores.indexOf(jugador);
        
        
        if (index == jugadores.size() - 1) {
            partida.setJugadorActual(jugadores.get(0));
        } else {
           partida.setJugadorActual(jugadores.get(index+1));
        }
           

    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }

}

