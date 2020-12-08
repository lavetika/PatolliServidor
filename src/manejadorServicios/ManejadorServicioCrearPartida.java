package manejadorServicios;

import Control.Partida;
import callMessage.Mandadero;

public class ManejadorServicioCrearPartida implements ManejadorServicios {

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
            mandadero.addRespuesta("cantGemas", partida.getMaxApuesta());
            
        }
    }

    public void establecerPropiedades() {
        partida.setCantJugadores((int) mandadero.getParams().get("cantJugadores"));
        partida.setMaxApuesta((int) mandadero.getParams().get("maxApuesta"));
        partida.setTamTablero((int) mandadero.getParams().get("tamTablero"));
        partida.setEstado(true);
    }

    @Override
    public Mandadero getRespuesta() {

        return mandadero;
    }
}
