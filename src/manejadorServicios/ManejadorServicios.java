/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorServicios;

import callMessage.Mandadero;


/**
 *
 * @author Diana Jiménez
 */
public interface ManejadorServicios {
    public void ejecutar();
    public Mandadero getRespuesta();
}
