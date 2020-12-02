
package Graphics;

import Dominio.Ficha;
import Enumeration.EnumCasilla;
import Enumeration.EnumDireccion;
import java.awt.Color;
import java.awt.Graphics2D;


public abstract class Forma {
    
    EnumCasilla forma;
    EnumDireccion direccion;
    Ficha ficha;
    int positionX;
    int positionY;
    int position;
    static Color colorHuesito = new Color(243, 243, 220);
    int tamanio;
    int rotacion;

    public Forma() {
    }
    
    public Forma(EnumCasilla posicion, int positionX, int positionY, int tamanio, int position, EnumDireccion dirección) {
        this.forma = posicion;
        this.positionX = positionX;
        this.positionY = positionY;
        this.tamanio = tamanio;
        this.position = position;
        this.direccion = dirección;
    }
    
    public abstract void draw(Graphics2D g2d);
    
    public abstract void drawFicha(Ficha ficha);
    
    public abstract void deleteFicha();

    public EnumCasilla getForma() {
        return forma;
    }

    public void setForma(EnumCasilla forma) {
        this.forma = forma;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public static Color getColorHuesito() {
        return colorHuesito;
    }

    public static void setColorHuesito(Color colorHuesito) {
        Forma.colorHuesito = colorHuesito;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public EnumDireccion getDireccion() {
        return direccion;
    }

    public void setDireccion(EnumDireccion direccion) {
        this.direccion = direccion;
    }

    public int getRotacion() {
        return rotacion;
    }

    public void setRotacion(int rotacion) {
        this.rotacion = rotacion;
    }
    
    
    
    
}
