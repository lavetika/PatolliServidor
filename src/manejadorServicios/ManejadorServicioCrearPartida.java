
package manejadorServicios;

import Control.Partida;
import Dominio.Jugador;
import callMessage.Mandadero;


public class ManejadorServicioCrearPartida implements ManejadorServicios{

    private Mandadero mandadero;
    private Partida partida;

    public ManejadorServicioCrearPartida(Mandadero mandadero) {
        this.mandadero = mandadero;
    }

    @Override
    public void ejecutar() {

        partida = Partida.getInstance();
        if (mandadero.getParams().size() > 0) {
            establecerPropiedades();
        } else {
            mandadero.addRespuesta("respuesta", partida.isEstado());
        }
    }

    public void establecerPropiedades() {
        partida.setCantJugadores((int) mandadero.getParams().get("cantJugadores"));
        partida.setMaxApuesta((int) mandadero.getParams().get("maxApuesta"));
        partida.addJugador((Jugador) mandadero.getParams().get("jugador"));
        partida.setEstado(true);
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }
}
