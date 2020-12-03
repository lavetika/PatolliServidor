
package Dominio;

import java.io.Serializable;
import javax.swing.JLabel;


public class Ficha implements Serializable{
    private static final long serialVersionUID = 3L;
    private JLabel ficha;
    private int posicionX;
    private int posicionY;
    private int posicionActual;

    public Ficha(JLabel ficha) {
        this.ficha = ficha;
    }

    public Ficha(JLabel ficha, int posicionX, int posicionY, int posicionActual) {
        this.ficha = ficha;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.posicionActual = posicionActual;
    }

    public int getPosicionActual() {
        return posicionActual;
    }

    public void setPosicionActual(int posicionActual) {
        this.posicionActual = posicionActual;
    }
    
    public Ficha(){
        
    }

    public JLabel getFicha() {
        return ficha;
    }

    public void setFicha(JLabel ficha) {
        this.ficha = ficha;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }
    
}
