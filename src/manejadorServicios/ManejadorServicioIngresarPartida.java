package manejadorServicios;

import Control.Partida;
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
        mandadero.addRespuesta("tamTablero", partida.getTamTablero());
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }

}
