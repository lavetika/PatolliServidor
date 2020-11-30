package manejadorServicios;

import Control.Partida;
import callMessage.Mandadero;
import conexionServidor.ComunicadorRedCliente;

public class ManejadorServicioConfirmarTurno implements ManejadorServicios {

    private Mandadero mandadero;
    private Partida partida;
    private ComunicadorRedCliente jugador;

    public ManejadorServicioConfirmarTurno(Mandadero mandadero, ComunicadorRedCliente jugador) {
        this.mandadero = mandadero;
        this.partida = Partida.getInstance();
        this.jugador = jugador;
    }

    @Override
    public void ejecutar() {
        if (jugador == partida.getJugadorActual()) {
            mandadero.addRespuesta("turno", true);
        } else {
            mandadero.addRespuesta("turno", false);
        }

    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }

}
