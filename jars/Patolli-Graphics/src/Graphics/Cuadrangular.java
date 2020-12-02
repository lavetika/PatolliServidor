
package Graphics;

import Dominio.Ficha;
import Enumeration.EnumCasilla;
import Enumeration.EnumDireccion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Locale;

/**
 *
 * @author laura
 */
public class Cuadrangular extends Forma {

    public Cuadrangular() {
        super();
    }


    public Cuadrangular(EnumCasilla posicion, int positionX, int positionY, int tamanio, int position, EnumDireccion direccion) {
        super(posicion, positionX, positionY, tamanio, position, direccion);
    }
  
    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(colorHuesito);
        g2d.fillRect(positionX, positionY, tamanio, tamanio);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(positionX, positionY, tamanio, tamanio);
    }

    @Override
    public void drawFicha(Ficha ficha) {
        this.ficha = ficha;
        this.ficha.getFicha().setLocation(positionX+5, positionY);
        this.ficha.setPosicionX(positionX+5);
        this.ficha.setPosicionY(positionY);
        this.ficha.getFicha().setVisible(true);
        
    }

    @Override
    public void deleteFicha() {
    }
    
    
    
    
    
    
}
