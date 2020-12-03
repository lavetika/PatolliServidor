package callMessage;

import enumServicio.EnumServicio;
import java.io.Serializable;
import java.util.HashMap;

public class Mandadero implements Serializable {
    private static final long serialVersionUID = 6942551579298299603L;
    HashMap<String, Object> paramsPeticion;
    HashMap<String, Object> paramsPespuesta;
    EnumServicio tipoServicio;

    public Mandadero(EnumServicio tipoServicio) {
        paramsPeticion = new HashMap<>();
        paramsPespuesta = new HashMap<>();
        this.tipoServicio = tipoServicio;
    }

    public HashMap<String, Object> getParams() {
        return paramsPeticion;
    }
    
    public boolean addPeticion(String llave, Object obj) {
        if (paramsPeticion != null) {
            paramsPeticion.put(llave, obj);
            return true;
        }
        return false;
    }
    
    public boolean addRespuesta(String llave, Object obj) {
        if (paramsPespuesta != null) {
            paramsPespuesta.put(llave, obj);
            return true;
        }
        return false;
    }
        
    public EnumServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(EnumServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public HashMap<String, Object> getRespuesta() {
        return paramsPespuesta;
    }

    public void setRespuesta(HashMap<String, Object> respuesta) {
        this.paramsPespuesta = respuesta;
    }

    @Override
    public String toString() {
        return "Mandadero{" + "params=" + paramsPeticion + ", tipoServicio=" + tipoServicio + '}';
    }
    
    
    

}
