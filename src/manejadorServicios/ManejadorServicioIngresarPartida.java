package manejadorServicios;

import Control.Partida;
import Dominio.Jugador;
import callMessage.Mandadero;

public class ManejadorServicioIngresarPartida implements ManejadorServicios {

    private Mandadero mandadero;
    private Partida partida;

    public ManejadorServicioIngresarPartida(Mandadero mandadero) {
        this.mandadero = mandadero;
        partida = Partida.getInstance();
    }

    @Override
    public void ejecutar() {
        Jugador jugador = (Jugador) mandadero.getParams().get("jugador");
        jugador.getApuesta().setMontoTotal(Partida.getInstance().getMaxApuesta());
        mandadero.addRespuesta("tamTablero", partida.getTamTablero());
        mandadero.addRespuesta("cantGemas", partida.getMaxApuesta());
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }

}

