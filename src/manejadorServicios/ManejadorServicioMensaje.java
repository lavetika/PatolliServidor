
package manejadorServicios;

import Dominio.Jugador;
import callMessage.Mandadero;


public class ManejadorServicioMensaje implements ManejadorServicios{

    Jugador jugador;
    String mensaje;
    Mandadero mandadero;
    
    public ManejadorServicioMensaje(Mandadero mandadero) {
        this.jugador=(Jugador)mandadero.getParams().get("jugador");
        this.mensaje=(String)mandadero.getParams().get("mensaje");
        this.mandadero=mandadero;    
    }
    
    @Override
    public void ejecutar() {
        System.out.println(jugador.getNickname());
        System.out.println(mensaje);
        //se tiene que añadir como parámetro porque tenemos muchos tipos de resultados
        mandadero.addRespuesta("mensaje", jugador.getNickname()+": "+mensaje);        
    }
    
    @Override
    public Mandadero getRespuesta(){
        return mandadero;
    }
    
    
}
