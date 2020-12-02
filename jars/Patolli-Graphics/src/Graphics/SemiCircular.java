/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Dominio.Ficha;
import Enumeration.EnumCasilla;
import Enumeration.EnumDireccion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 *
 * @author laura
 */
public class SemiCircular extends Forma {


    public SemiCircular() {
        super();
    }
    
    public SemiCircular(EnumCasilla posicion, int positionX, int positionY, int tamanio, int position, EnumDireccion direccion) {
        super(posicion, positionX, positionY, tamanio, position, direccion);
     
    }
    
    @Override
    public void draw(Graphics2D g2d){
        g2d.setColor(colorHuesito);
        g2d.fillArc(positionX, positionY, tamanio*2, tamanio*2, getRotacion(), 90);
        g2d.setColor(Color.BLACK);
        g2d.drawArc(positionX, positionY, tamanio*2, tamanio*2, getRotacion(), 90);
    }
    
    @Override
    public void drawFicha(Ficha ficha) {
        
    }

    @Override
    public void deleteFicha() {
    }

    public int getRotacion() {
        return rotacion;
    }

    public void setRotacion(int rotacion) {
        this.rotacion = rotacion;
    }
    
    
}
