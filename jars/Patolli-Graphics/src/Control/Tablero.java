/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Dominio.Ficha;
import Enumeration.EnumCasilla;
import Enumeration.EnumDireccion;
import static Enumeration.EnumDireccion.ABAJO;
import static Enumeration.EnumDireccion.ARRIBA;
import static Enumeration.EnumDireccion.IZQUIERDA;
import Graphics.Canias;
import Graphics.Cuadrangular;
import Graphics.Forma;
import Graphics.SemiCircular;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author laura
 */
public final class Tablero extends JPanel {

    int cantidadTablero;
    int cantidadLado;
    int cantidadCasillasCentrales;
    int cantidadSemiCirculo;
    Frame frameTablero;
    Frame frameInicio;
    int tamanioCasilla;
    Canias lanzar;

    ArrayList<Forma> casillas;
    ArrayList<Ficha> fichas;

    public Tablero(int tamanio, Canias canias, Frame frameTablero, Frame frameInicio) {
        setLayout(new GroupLayout(this));
        setSize(900, 800);
        this.setLocation(150, 0);
        setBackground(new Color(105, 2, 5));
        this.cantidadTablero = tamanio;
        this.cantidadCasillasCentrales = 4;
        this.cantidadSemiCirculo = 8;
        this.tamanioCasilla = 40;
        this.frameTablero = frameTablero;
        this.frameInicio = frameInicio;
        this.lanzar = canias;
        this.casillas = new ArrayList<>();
        this.fichas = new ArrayList<>();

        calcularTablero();
        generarCasillas();

    }

    //Se generan las referencias de las casillas
    public void generarCasillas(){
        for (int i = 0; i < cantidadTablero; i++) {
            if (i != 12 && i != 13
                    && i != 22 && i != 23
                    && i != 32 && i != 33
                    && i != 42 && i != 43) {
                casillas.add(new Cuadrangular());
            } else {
                casillas.add(new SemiCircular());
            }
        }
        
        //falta hacer dinamico ese 24 con la cantidad de jugadores en el juego
        for (int i = 0; i < 24; i++) {
            fichas.add(new Ficha());
        }
    }

    public void calcularTablero() {
        int numeroAspas = 4;
        int numeroHileras = 2;
        this.cantidadLado = ((cantidadTablero - (cantidadCasillasCentrales + cantidadSemiCirculo)) / numeroAspas) / numeroHileras;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTablero(g);
    }

    public void drawBase(Graphics2D g2d) {
        Dimension tamano = getSize();

        int w = (int) tamano.getWidth();
        int h = (int) tamano.getHeight();

        g2d.setColor(new Color(243, 188, 39));
        g2d.fillOval(125, 75, 650, 650);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(125, 75, 650, 650);
    }

    public void drawTablero(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        showFichasAzules();
        showFichasRojas();
        showFichasVerdes();
        showFichasNaranja();

        showButtons(g);
        showPlayersIcons();
        showGemas();
        showNicknames();
        showApuestaRestante();

        drawBaseTablero(g);
        drawCentral(g2d);
        drawArriba(g2d);
        drawAbajo(g2d);

        drawDerecha(g2d);
        drawIzquierda(g2d);

    }

    public int posicionar(int x, int y, EnumDireccion direccion) {

        switch (direccion) {
            case ARRIBA:
                y = y - this.tamanioCasilla;
                return y;
            case ABAJO:
                y = y + this.tamanioCasilla;
                return y;
            case IZQUIERDA:
                x = x - this.tamanioCasilla;
                return x;
            default:
                x = x + this.tamanioCasilla;
                return x;
        }
    }

    public void drawCentral(Graphics2D g2d) {
        Dimension tamano = getSize();
        int x = (int) tamano.getWidth();
        int y = (int) tamano.getHeight();

        //Posicionarlo en el centro
        x = x / 2;
        y = y / 2;

        for (int i = 0; i < casillas.size(); i++) {
            if (casillas.get(i).getTamanio() == 0) {
                casillas.get(i).setForma(EnumCasilla.CENTRAL);
                casillas.get(i).setPositionX(x);
                casillas.get(i).setPositionY(y);
                casillas.get(i).setTamanio(tamanioCasilla);
                casillas.get(i).setPosition(1);
                casillas.get(i).setDireccion(EnumDireccion.CENTRO);
                casillas.get(i).draw(g2d);
                break;
            }
        }

        //Obtener las casillas para volver a dibujarlas
        if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
            for (int o = 0; o < casillas.size(); o++) {
                if (casillas.get(o).getPosition() == 1 && casillas.get(o).getForma() == EnumCasilla.CENTRAL) {
                    casillas.get(o).draw(g2d);
                    break;
                }
            }
        }

        //Posicionarlo
        x = posicionar(x, y, EnumDireccion.IZQUIERDA);

        for (int i = 0; i < casillas.size(); i++) {
            if (casillas.get(i).getTamanio() == 0) {
                casillas.get(i).setForma(EnumCasilla.CENTRAL);
                casillas.get(i).setPositionX(x);
                casillas.get(i).setPositionY(y);
                casillas.get(i).setTamanio(tamanioCasilla);
                casillas.get(i).setPosition(2);
                casillas.get(i).setDireccion(EnumDireccion.CENTRO);
                casillas.get(i).draw(g2d);
                break;
            }
        }

        //Obtener las casillas para volver a dibujarlas
        if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
            for (int o = 0; o < casillas.size(); o++) {
                if (casillas.get(o).getPosition() == 2 && casillas.get(o).getForma() == EnumCasilla.CENTRAL) {
                    casillas.get(o).draw(g2d);
                    break;
                }
            }
        }

        //Posicionarlo
        y = posicionar(x, y, EnumDireccion.ARRIBA);

        for (int i = 0; i < casillas.size(); i++) {
            if (casillas.get(i).getTamanio() == 0) {
                casillas.get(i).setForma(EnumCasilla.CENTRAL);
                casillas.get(i).setPositionX(x);
                casillas.get(i).setPositionY(y);
                casillas.get(i).setTamanio(tamanioCasilla);
                casillas.get(i).setPosition(3);
                casillas.get(i).setDireccion(EnumDireccion.CENTRO);
                casillas.get(i).draw(g2d);
                break;
            }
        }

        //Obtener las casillas para volver a dibujarlas
        if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
            for (int o = 0; o < casillas.size(); o++) {
                if (casillas.get(o).getPosition() == 3 && casillas.get(o).getForma() == EnumCasilla.CENTRAL) {
                    casillas.get(o).draw(g2d);
                    break;
                }
            }
        }

        //Posicionarlo
        x = posicionar(x, y, EnumDireccion.DERECHA);

        for (int i = 0; i < casillas.size(); i++) {
            if (casillas.get(i).getTamanio() == 0) {
                casillas.get(i).setForma(EnumCasilla.CENTRAL);
                casillas.get(i).setPositionX(x);
                casillas.get(i).setPositionY(y);
                casillas.get(i).setTamanio(tamanioCasilla);
                casillas.get(i).setPosition(4);
                casillas.get(i).setDireccion(EnumDireccion.CENTRO);
                casillas.get(i).draw(g2d);
                break;
            }
        }

        //Obtener las casillas para volver a dibujarlas
        if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
            for (int o = 0; o < casillas.size(); o++) {
                if (casillas.get(o).getPosition() == 4 && casillas.get(o).getForma() == EnumCasilla.CENTRAL) {
                    casillas.get(o).draw(g2d);
                    break;
                }
            }
        }

    }

    public void drawArriba(Graphics2D g2d) {
        Dimension tamano = getSize();
        int x = (int) tamano.getWidth();
        int y = (int) tamano.getHeight();

        //Posicion inicial
        x = x / 2;
        y = y / 2;

        y = posicionar(x, y, EnumDireccion.ARRIBA);

        for (int i = 0; i < cantidadLado; i++) {
            y = posicionar(x, y, EnumDireccion.ARRIBA);

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition(i + 1);
                    casillas.get(e).setDireccion(EnumDireccion.ARRIBA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }

            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == (i + 1) && casillas.get(o).getDireccion() == EnumDireccion.ARRIBA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }
        }

        x = posicionar(x, y, EnumDireccion.IZQUIERDA);
        for (int i = 1; i <= cantidadLado; i++) {
            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition(i + cantidadLado);
                    casillas.get(e).setDireccion(EnumDireccion.ARRIBA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }

            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == (i + cantidadLado) && casillas.get(o).getDireccion() == EnumDireccion.ARRIBA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

            y = posicionar(x, y, EnumDireccion.ABAJO);
        }

        drawSemiCircular(g2d, EnumDireccion.ARRIBA);
    }

    public void drawAbajo(Graphics2D g2d) {
        Dimension tamano = getSize();
        int x = (int) tamano.getWidth();
        int y = (int) tamano.getHeight();

        //Posicion inicial
        x = (x / 2) - 40;
        y = y / 2;

        for (int i = 1; i <= cantidadLado; i++) {
            y = posicionar(x, y, EnumDireccion.ABAJO);

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition(i);
                    casillas.get(e).setDireccion(EnumDireccion.ABAJO);
                    casillas.get(e).draw(g2d);
                    break;
                }

                //Obtener las casillas para volver a dibujarlas
                if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                    for (int o = 0; o < casillas.size(); o++) {
                        if (casillas.get(o).getPosition() == i && casillas.get(o).getDireccion() == EnumDireccion.ABAJO) {
                            casillas.get(o).draw(g2d);
                            break;
                        }
                    }
                }
            }
        }

        x = posicionar(x, y, EnumDireccion.DERECHA);

        for (int i = 1; i <= cantidadLado; i++) {

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition(i + cantidadLado);
                    casillas.get(e).setDireccion(EnumDireccion.ABAJO);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == (i + cantidadLado) && casillas.get(o).getDireccion() == EnumDireccion.ABAJO) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }
            
            y = posicionar(x, y, EnumDireccion.ARRIBA);

        }

        drawSemiCircular(g2d, EnumDireccion.ABAJO);
    }

    public void drawIzquierda(Graphics2D g2d) {
        Dimension tamano = getSize();
        int x = (int) tamano.getWidth();
        int y = (int) tamano.getHeight();

        //Posicion inicial
        x = x / 2;
        y = (y / 2) - 40;

        x = posicionar(x, y, EnumDireccion.IZQUIERDA);

        for (int i = 0; i < cantidadLado; i++) {
            x = posicionar(x, y, EnumDireccion.IZQUIERDA);

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition(i + 1);
                    casillas.get(e).setDireccion(EnumDireccion.IZQUIERDA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == (i + 1) && casillas.get(o).getDireccion() == EnumDireccion.IZQUIERDA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }
        }

        y = posicionar(x, y, EnumDireccion.ABAJO);

        for (int i = 0; i < cantidadLado; i++) {

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition((i + 1) + cantidadLado);
                    casillas.get(e).setDireccion(EnumDireccion.IZQUIERDA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == ((i + 1) + cantidadLado) && casillas.get(o).getDireccion() == EnumDireccion.IZQUIERDA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

            x = posicionar(x, y, EnumDireccion.DERECHA);

        }

        drawSemiCircular(g2d, EnumDireccion.IZQUIERDA);
    }

    public void drawDerecha(Graphics2D g2d) {
        Dimension tamano = getSize();
        int x = (int) tamano.getWidth();
        int y = (int) tamano.getHeight();

        //Posicion inicial
        x = x / 2;
        y = y / 2;

        for (int i = 1; i <= cantidadLado; i++) {

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition(i);
                    casillas.get(e).setDireccion(EnumDireccion.DERECHA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == i && casillas.get(o).getDireccion() == EnumDireccion.DERECHA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }
            x = posicionar(x, y, EnumDireccion.DERECHA);

        }

        y = posicionar(x, y, EnumDireccion.ARRIBA);

        for (int i = 1; i <= cantidadLado; i++) {

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {
                    casillas.get(e).setForma(EnumCasilla.INICIO);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setPosition(i + cantidadLado);
                    casillas.get(e).setDireccion(EnumDireccion.DERECHA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == (i + cantidadLado) && casillas.get(o).getDireccion() == EnumDireccion.DERECHA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }
            x = posicionar(x, y, EnumDireccion.IZQUIERDA);
        }

        drawSemiCircular(g2d, EnumDireccion.DERECHA);
    }

    public void drawSemiCircular(Graphics2D g2d, Enum direccion) {

        Dimension tamano = getSize();
        int x = (int) tamano.getWidth();
        int y = (int) tamano.getHeight();

        //Posicion inicial
        x = x / 2;
        y = y / 2;

        if (direccion == EnumDireccion.IZQUIERDA) {
            for (int i = 0; i < cantidadLado + 2; i++) {
                x = posicionar(x, y, EnumDireccion.IZQUIERDA);
            }

            y = posicionar(x, y, EnumDireccion.ARRIBA);

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(90);
                    casillas.get(e).setPosition(1);
                    casillas.get(e).setDireccion(EnumDireccion.IZQUIERDA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 1 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.IZQUIERDA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }
            

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(180);
                    casillas.get(e).setPosition(2);
                    casillas.get(e).setDireccion(EnumDireccion.IZQUIERDA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 2 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.IZQUIERDA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

        } else if (direccion == EnumDireccion.DERECHA) {

            for (int i = 0; i < cantidadLado; i++) {
                x = posicionar(x, y, EnumDireccion.DERECHA);
            }

            //Para que no tape la rayita
            x = x + 1;

            y = posicionar(x, y, EnumDireccion.ARRIBA);

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(0);
                    casillas.get(e).setPosition(1);
                    casillas.get(e).setDireccion(EnumDireccion.DERECHA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 1 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.DERECHA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(270);
                    casillas.get(e).setPosition(2);
                    casillas.get(e).setDireccion(EnumDireccion.DERECHA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 2 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.DERECHA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

        } else if (direccion == EnumDireccion.ARRIBA) {
            for (int i = 0; i < cantidadLado + 2; i++) {
                y = posicionar(x, y, EnumDireccion.ARRIBA);
            }
            x = posicionar(x, y, EnumDireccion.IZQUIERDA);

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(90);
                    casillas.get(e).setPosition(1);
                    casillas.get(e).setDireccion(EnumDireccion.ARRIBA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 1 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.ARRIBA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(360);
                    casillas.get(e).setPosition(2);
                    casillas.get(e).setDireccion(EnumDireccion.ARRIBA);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 2 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.ARRIBA) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

        } else {
            for (int i = 0; i < cantidadLado; i++) {
                y = posicionar(x, y, EnumDireccion.ABAJO);
            }

            //Para que no tape la rayita
            y = y + 1;

            x = posicionar(x, y, EnumDireccion.IZQUIERDA);

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(180);
                    casillas.get(e).setPosition(1);
                    casillas.get(e).setDireccion(EnumDireccion.ABAJO);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 1 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.ABAJO) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }

            for (int e = 0; e < casillas.size(); e++) {
                if (casillas.get(e).getTamanio() == 0) {

                    casillas.get(e).setForma(EnumCasilla.ESQUINA);
                    casillas.get(e).setPositionX(x);
                    casillas.get(e).setPositionY(y);
                    casillas.get(e).setTamanio(tamanioCasilla);
                    casillas.get(e).setRotacion(270);
                    casillas.get(e).setPosition(2);
                    casillas.get(e).setDireccion(EnumDireccion.ABAJO);
                    casillas.get(e).draw(g2d);
                    break;
                }
            }
            
            //Obtener las casillas para volver a dibujarlas
            if (casillas.get(casillas.size() - 1).getTamanio() != 0) {
                for (int o = 0; o < casillas.size(); o++) {
                    if (casillas.get(o).getPosition() == 2 
                            && casillas.get(o).getForma()== EnumCasilla.ESQUINA
                            && casillas.get(o).getDireccion() == EnumDireccion.ABAJO) {
                        casillas.get(o).draw(g2d);
                        break;
                    }
                }
            }
        }
    }

    public void drawBaseTablero(Graphics g) {
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(getClass().getResource("/Images/CalendarioDelSolFondoNegro.png")).getImage());
        Image imagen = imageIcon.getImage();

        g.drawImage(imagen, 125, 75, null);
    }

    public void showButtons(Graphics g) {
        JButton btnIniciar = new JButton("Iniciar");

        btnIniciar.setSize(150, 35);
        btnIniciar.setVisible(true);
        btnIniciar.setLocation(740, 585);
        btnIniciar.setForeground(Color.BLACK);
        btnIniciar.setBackground(new Color(250, 206, 71));
        btnIniciar.setFont(new Font("Herculanum", Font.BOLD, 14));
        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                posicionarFichas(fichas);
            }
        });
        this.add(btnIniciar);

        //Falta hacer dinamica esta parte con la cantidad de jugadores en la partida
        this.add(fichas.get(0).getFicha());
        this.add(fichas.get(6).getFicha());
        this.add(fichas.get(12).getFicha());
        this.add(fichas.get(18).getFicha());

        JButton btnLanzar = new JButton("Lanzar cañas");
        Ficha fichaNuevaPosicion;

        fichaNuevaPosicion = fichas.get(0);

        btnLanzar.setSize(150, 35);
        btnLanzar.setVisible(true);
        btnLanzar.setLocation(740, 635);
        btnLanzar.setForeground(Color.BLACK);
        btnLanzar.setBackground(new Color(250, 206, 71));
        btnLanzar.setFont(new Font("Herculanum", Font.BOLD, 14));
        btnLanzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int numero = lanzar.calcular();
                lanzar.mostrarCanias();
                moverFicha(numero, fichaNuevaPosicion);
            }
        });
        this.add(btnLanzar);
        this.add(fichaNuevaPosicion.getFicha());

        JButton btnApostar = new JButton("Apostar");
        btnApostar.setSize(110, 35);
        btnApostar.setVisible(true);
        btnApostar.setLocation(780, 685);
        btnApostar.setForeground(Color.BLACK);
        btnApostar.setBackground(new Color(250, 206, 71));
        btnApostar.setFont(new Font("Herculanum", Font.BOLD, 14));
        this.add(btnApostar);
    }

    public void showPlayersIcons() {
        JLabel lblPlayerIcon1 = new JLabel();
        lblPlayerIcon1.setSize(40, 40);
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/mascara32px.png")).getImage());
        lblPlayerIcon1.setIcon(imageIcon1);
        lblPlayerIcon1.setVisible(true);
        lblPlayerIcon1.setLocation(680, 635);
        this.add(lblPlayerIcon1);

        JLabel lblPlayerIcon2 = new JLabel();
        lblPlayerIcon2.setSize(40, 40);
        ImageIcon imageIcon2 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/mictlantecuhtli32px.png")).getImage());
        lblPlayerIcon2.setIcon(imageIcon2);
        lblPlayerIcon2.setVisible(true);
        lblPlayerIcon2.setLocation(680, 125);
        this.add(lblPlayerIcon2);

        JLabel lblPlayerIcon3 = new JLabel();
        lblPlayerIcon3.setSize(40, 40);
        ImageIcon imageIcon3 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/estatua32px.png")).getImage());
        lblPlayerIcon3.setIcon(imageIcon3);
        lblPlayerIcon3.setVisible(true);
        lblPlayerIcon3.setLocation(185, 125);
        this.add(lblPlayerIcon3);

        JLabel lblPlayerIcon4 = new JLabel();
        lblPlayerIcon4.setSize(40, 40);
        ImageIcon imageIcon4 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/quetzalcoatl32px.png")).getImage());
        lblPlayerIcon4.setIcon(imageIcon4);
        lblPlayerIcon4.setVisible(true);
        lblPlayerIcon4.setLocation(185, 635);
        this.add(lblPlayerIcon4);
    }

    public void showGemas() {
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/diamante32px.png")).getImage());

        JLabel lblGema1 = new JLabel();
        lblGema1.setSize(40, 40);
        lblGema1.setIcon(imageIcon1);
        lblGema1.setVisible(true);
        lblGema1.setLocation(790, 90);
        this.add(lblGema1);

        JLabel lblGema2 = new JLabel();
        lblGema2.setSize(40, 40);
        lblGema2.setIcon(imageIcon1);
        lblGema2.setVisible(true);
        lblGema2.setLocation(790, 50);
        this.add(lblGema2);

        JLabel lblGema3 = new JLabel();
        lblGema3.setSize(40, 40);
        lblGema3.setIcon(imageIcon1);
        lblGema3.setVisible(true);
        lblGema3.setLocation(750, 90);
        this.add(lblGema3);

        JLabel lblGema4 = new JLabel();
        lblGema4.setSize(40, 40);
        lblGema4.setIcon(imageIcon1);
        lblGema4.setVisible(true);
        lblGema4.setLocation(750, 50);
        this.add(lblGema4);

        JLabel lblGema5 = new JLabel();
        lblGema5.setSize(40, 40);
        lblGema5.setIcon(imageIcon1);
        lblGema5.setVisible(true);
        lblGema5.setLocation(830, 90);
        this.add(lblGema5);

        JLabel lblGema6 = new JLabel();
        lblGema6.setSize(40, 40);
        lblGema6.setIcon(imageIcon1);
        lblGema6.setVisible(true);
        lblGema6.setLocation(830, 50);
        this.add(lblGema6);
    }

    public void showNicknames() {
        JLabel lblNickname1 = new JLabel("El Chino");
        lblNickname1.setFont(new Font("PT Sans", Font.PLAIN, 14));
        lblNickname1.setForeground(new Color(243, 243, 220));
        lblNickname1.setSize(100, 40);
        lblNickname1.setLocation(650, 90);
        lblNickname1.setVisible(true);
        this.add(lblNickname1);

        JLabel lblNickname2 = new JLabel("GalloOro");
        lblNickname2.setFont(new Font("PT Sans", Font.PLAIN, 14));
        lblNickname2.setForeground(new Color(243, 243, 220));
        lblNickname2.setSize(100, 40);
        lblNickname2.setLocation(650, 675);
        lblNickname2.setVisible(true);
        this.add(lblNickname2);

        JLabel lblNickname3 = new JLabel("LudoVico");
        lblNickname3.setFont(new Font("PT Sans", Font.PLAIN, 14));
        lblNickname3.setForeground(new Color(243, 243, 220));
        lblNickname3.setSize(100, 40);
        lblNickname3.setLocation(190, 90);
        lblNickname3.setVisible(true);
        this.add(lblNickname3);

        JLabel lblNickname4 = new JLabel("Bebelin");
        lblNickname4.setFont(new Font("PT Sans", Font.PLAIN, 14));
        lblNickname4.setForeground(new Color(243, 243, 220));
        lblNickname4.setSize(100, 40);
        lblNickname4.setLocation(190, 675);
        lblNickname4.setVisible(true);
        this.add(lblNickname4);
    }

    public void showApuestaRestante() {
        JLabel lblTituloCodigo = new JLabel("12");
        lblTituloCodigo.setFont(new Font("PT Sans", Font.BOLD, 18));
        lblTituloCodigo.setForeground(new Color(243, 243, 220));
        lblTituloCodigo.setSize(100, 40);
        lblTituloCodigo.setLocation(795, 20);
        lblTituloCodigo.setVisible(true);
        this.add(lblTituloCodigo);
    }

    public void posicionarFichas(ArrayList<Ficha> fichas) {

        casillas.get(0).drawFicha(fichas.get(12));
        casillas.get(1).drawFicha(fichas.get(18));
        casillas.get(2).drawFicha(fichas.get(0));
        casillas.get(3).drawFicha(fichas.get(6));

    }

    public void moverFicha(int numero, Ficha ficha) {
        
        for (Forma casilla : casillas) {
            if (casilla.getDireccion() == EnumDireccion.IZQUIERDA) {
                /*
                Solo estoy validando con la ficha azul que esa va a la derecha, en el EnumDireccion.Derecha es solo para el azul
                aqui deberia de ir la direccion especifica que le toca al jugador que esta jugando.
                 */
                
                if (numero == casilla.getPosition()) {
                    casilla.drawFicha(ficha);
                }
            }
        }
    }

    public void showFichasAzules() {
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/fichaazul32px.png")).getImage());
        JLabel lblFichaAzul1 = new JLabel();
        lblFichaAzul1.setIcon(imageIcon1);
        lblFichaAzul1.setSize(40, 40);
        lblFichaAzul1.setLocation(310, 300);
        lblFichaAzul1.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaAzul1);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaAzul2 = new JLabel();
        lblFichaAzul2.setIcon(imageIcon1);
        lblFichaAzul2.setSize(40, 40);
        lblFichaAzul2.setLocation(310, 260);
        lblFichaAzul2.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaAzul2);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaAzul3 = new JLabel();
        lblFichaAzul3.setIcon(imageIcon1);
        lblFichaAzul3.setSize(40, 40);
        lblFichaAzul3.setLocation(350, 220);
        lblFichaAzul3.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaAzul3);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaAzul6 = new JLabel();
        lblFichaAzul6.setIcon(imageIcon1);
        lblFichaAzul6.setSize(40, 40);
        lblFichaAzul6.setLocation(350, 260);
        lblFichaAzul6.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaAzul6);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaAzul4 = new JLabel();
        lblFichaAzul4.setIcon(imageIcon1);
        lblFichaAzul4.setSize(40, 40);
        lblFichaAzul4.setLocation(350, 300);
        lblFichaAzul4.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaAzul4);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }
        
        JLabel lblFichaAzul5 = new JLabel();
        lblFichaAzul5.setIcon(imageIcon1);
        lblFichaAzul5.setSize(40, 40);
        lblFichaAzul5.setLocation(270, 300);
        lblFichaAzul5.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaAzul5);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }
        
        //Para volver a pintar las fichas
        if(fichas.get(fichas.size()-1).getFicha() != null){
            for (int i = 0; i < 6; i++) {
                this.add(fichas.get(i).getFicha());
            }
        }
    }

    public void showFichasRojas() {

        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/ficharoja32px.png")).getImage());
        JLabel lblFichaRoja1 = new JLabel();
        lblFichaRoja1.setIcon(imageIcon1);
        lblFichaRoja1.setSize(40, 40);
        lblFichaRoja1.setLocation(555, 300);
        lblFichaRoja1.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaRoja1);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaRoja2 = new JLabel();
        lblFichaRoja2.setIcon(imageIcon1);
        lblFichaRoja2.setSize(40, 40);
        lblFichaRoja2.setLocation(555, 260);
        lblFichaRoja2.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaRoja2);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaRoja3 = new JLabel();
        lblFichaRoja3.setIcon(imageIcon1);
        lblFichaRoja3.setSize(40, 40);
        lblFichaRoja3.setLocation(515, 220);
        lblFichaRoja3.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaRoja3);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaRoja4 = new JLabel();
        lblFichaRoja4.setIcon(imageIcon1);
        lblFichaRoja4.setSize(40, 40);
        lblFichaRoja4.setLocation(515, 260);
        lblFichaRoja4.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaRoja4);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaRoja5 = new JLabel();
        lblFichaRoja5.setIcon(imageIcon1);
        lblFichaRoja5.setSize(40, 40);
        lblFichaRoja5.setLocation(515, 300);
        lblFichaRoja5.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaRoja5);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaRoja6 = new JLabel();
        lblFichaRoja6.setIcon(imageIcon1);
        lblFichaRoja6.setSize(40, 40);
        lblFichaRoja6.setLocation(595, 300);
        lblFichaRoja6.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaRoja6);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }
        //Para volver a pintar las fichas
        if(fichas.get(fichas.size()-1).getFicha() != null){
            for (int i = 6; i < 12; i++) {
                this.add(fichas.get(i).getFicha());
            }
        }
    }

    public void showFichasVerdes() {

        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/fichaverde32px.png")).getImage());

        JLabel lblFichaVerde1 = new JLabel();
        lblFichaVerde1.setIcon(imageIcon1);
        lblFichaVerde1.setSize(40, 40);
        lblFichaVerde1.setLocation(555, 460);
        lblFichaVerde1.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaVerde1);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaVerde2 = new JLabel();
        lblFichaVerde2.setIcon(imageIcon1);
        lblFichaVerde2.setSize(40, 40);
        lblFichaVerde2.setLocation(555, 500);
        lblFichaVerde2.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaVerde1);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaVerde3 = new JLabel();
        lblFichaVerde3.setIcon(imageIcon1);
        lblFichaVerde3.setSize(40, 40);
        lblFichaVerde3.setLocation(515, 460);
        lblFichaVerde3.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaVerde3);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaVerde4 = new JLabel();
        lblFichaVerde4.setIcon(imageIcon1);
        lblFichaVerde4.setSize(40, 40);
        lblFichaVerde4.setLocation(515, 500);
        lblFichaVerde4.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaVerde4);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaVerde5 = new JLabel();
        lblFichaVerde5.setIcon(imageIcon1);
        lblFichaVerde5.setSize(40, 40);
        lblFichaVerde5.setLocation(515, 540);
        lblFichaVerde5.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaVerde5);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaVerde6 = new JLabel();
        lblFichaVerde6.setIcon(imageIcon1);
        lblFichaVerde6.setSize(40, 40);
        lblFichaVerde6.setLocation(595, 460);
        lblFichaVerde6.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaVerde6);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }
        //Para volver a pintar las fichas
        if(fichas.get(fichas.size()-1).getFicha() != null){
            for (int i = 12; i < 18; i++) {
                this.add(fichas.get(i).getFicha());
            }
        }
    }

    public void showFichasNaranja() {

        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/fichanaranja32px.png")).getImage());

        JLabel lblFichaNaranja1 = new JLabel();
        lblFichaNaranja1.setIcon(imageIcon1);
        lblFichaNaranja1.setSize(40, 40);
        lblFichaNaranja1.setLocation(310, 460);
        lblFichaNaranja1.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaNaranja1);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaNaranja2 = new JLabel();
        lblFichaNaranja2.setIcon(imageIcon1);
        lblFichaNaranja2.setSize(40, 40);
        lblFichaNaranja2.setLocation(310, 500);
        lblFichaNaranja2.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaNaranja2);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaNaranja3 = new JLabel();
        lblFichaNaranja3.setIcon(imageIcon1);
        lblFichaNaranja3.setSize(40, 40);
        lblFichaNaranja3.setLocation(350, 460);
        lblFichaNaranja3.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaNaranja3);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaNaranja4 = new JLabel();
        lblFichaNaranja4.setIcon(imageIcon1);
        lblFichaNaranja4.setSize(40, 40);
        lblFichaNaranja4.setLocation(350, 500);
        lblFichaNaranja4.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaNaranja4);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaNaranja5 = new JLabel();
        lblFichaNaranja5.setIcon(imageIcon1);
        lblFichaNaranja5.setSize(40, 40);
        lblFichaNaranja5.setLocation(350, 540);
        lblFichaNaranja5.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaNaranja5);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }

        JLabel lblFichaNaranja6 = new JLabel();
        lblFichaNaranja6.setIcon(imageIcon1);
        lblFichaNaranja6.setSize(40, 40);
        lblFichaNaranja6.setLocation(270, 460);
        lblFichaNaranja6.setVisible(true);
        
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).getFicha() == null){
                fichas.get(i).setFicha(lblFichaNaranja6);
                this.add(fichas.get(i).getFicha());
                break;
            }
        }
        
        //Para volver a pintar las fichas
        if(fichas.get(fichas.size()-1).getFicha() != null){
            for (int i = 18; i < 24; i++) {
                this.add(fichas.get(i).getFicha());
            }
        }
    }

    public Canias getLanzar() {
        return lanzar;
    }

}
