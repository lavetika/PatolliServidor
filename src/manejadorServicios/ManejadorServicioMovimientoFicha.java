
package manejadorServicios;

import callMessage.Mandadero;

public class ManejadorServicioMovimientoFicha implements ManejadorServicios{

    Mandadero mandadero;
    
    public ManejadorServicioMovimientoFicha(Mandadero mandadero) {
        this.mandadero = mandadero;
    }    
    
    @Override
    public void ejecutar() {
        
    }

    @Override
    public Mandadero getRespuesta() {
        return mandadero;
    }
    
}
