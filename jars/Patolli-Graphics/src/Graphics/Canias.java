/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Zannie
 */
public class Canias extends JPanel {

    public ImageIcon cania;
    public ArrayList<ImageIcon> canias;
    ImageIcon cania0;
    ImageIcon cania1;
    JLabel lblCania;
    JLabel lblCania2;
    JLabel lblCania3;
    JLabel lblCania4;
    JLabel lblCania5;

    
    /**
     * Constructor por defecto que inicializa las variables de clase.
     */
    public Canias() {
        setSize(300, 200);
        setBackground(new Color(105, 2, 5));
        this.setLocation(1050, 600);
        this.canias = new ArrayList<>();
        this.cania0 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/cania_0_64px.png")).getImage());
        this.cania1 = new ImageIcon(new ImageIcon(getClass().getResource("/Images/cania_1_64px.png")).getImage());
        crearLabels();
        
    }
    
    
    /**
     * Calcula la cantidad de casillas que avanzará el jugador en su turno.
     * @return 
     */
    public int calcular() {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            int resultado = (Math.random() < 0.5 ? 0 : 1);
            imageCania(resultado);
            sum += resultado;
        }
        return sum;
    }

    /**
     * Añade al ArrayList el icono correspondiente al valor enviado en
     * el parámetro.
     * @param valorCania 
     */
    public void imageCania(int valorCania) {

        canias.add((valorCania < 1 ? cania0 : cania1));
    }

    /**
     * Muestra en pantalla el resultado obtenido
     * al calcular la cantidad de casillas que
     * avanzará el jugador.
     * 
     */
    public void mostrarCanias() {
        lblCania.setIcon(canias.get(0));
        lblCania2.setIcon(canias.get(1));
        lblCania3.setIcon(canias.get(2));
        lblCania4.setIcon(canias.get(3));
        lblCania5.setIcon(canias.get(4));
        canias.removeAll(canias);   
    }
    
    /**
     *  Crea y configura las etiquetas donde se mostrarán las cañas.
     */
    public void crearLabels() {
        lblCania = new JLabel();
        lblCania.setSize(64, 64);
        lblCania.setIcon(cania0);
        lblCania.setVisible(true);
        lblCania.setLocation(1350, 620);
        this.add(lblCania);

        lblCania2 = new JLabel();
        lblCania2.setSize(64, 64);
        lblCania2.setIcon(cania0);
        lblCania2.setVisible(true);
        lblCania2.setLocation(1014, 480);
        this.add(lblCania2);

        lblCania3 = new JLabel();
        lblCania3.setSize(64, 64);
        lblCania3.setIcon(cania0);
        lblCania3.setVisible(true);
        lblCania3.setLocation(1000, 560);
        this.add(lblCania3);

        lblCania4 = new JLabel();
        lblCania4.setSize(64, 64);
        lblCania4.setIcon(cania0);
        lblCania4.setVisible(true);
        lblCania4.setLocation(1050, 560);
        this.add(lblCania4);

        lblCania5 = new JLabel();
        lblCania5.setSize(64, 64);
        lblCania5.setIcon(cania0);
        lblCania5.setVisible(true);
        lblCania5.setLocation(1130, 560);
        this.add(lblCania5);
    }

}
