
package Dominio;

import java.io.Serializable;
import java.util.List;


public class Jugador implements Serializable{
    private static final long serialVersionUID = 4L;
    private String nickname;
    private Apuesta apuesta;
    private int CodigoPartida;
    private List<Ficha> fichas;
    private TipoJugador tipoJugador;

    public Jugador(){
        
    }
    
    public Jugador(int codigo){
        this.CodigoPartida = codigo;
    }
    
    public Jugador(String nickname) {
        this.nickname=nickname;
    }

    public Jugador(String nickname, Apuesta apuesta, List<Ficha> fichas, TipoJugador tipoJugador) {
        this.nickname = nickname;
        this.apuesta = apuesta;
        this.fichas = fichas;
        this.tipoJugador = tipoJugador;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public int getCodigoPartida() {
        return CodigoPartida;
    }

    public void setCodigoPartida(int CodigoPartida) {
        this.CodigoPartida = CodigoPartida;
    }
    
    public Apuesta getApuesta() {
        return apuesta;
    }

    public void setApuesta(Apuesta apuesta) {
        this.apuesta = apuesta;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(List<Ficha> fichas) {
        this.fichas = fichas;
    }

    public TipoJugador getTipoJugador() {
        return tipoJugador;
    }

    public void setTipoJugador(TipoJugador tipoJugador) {
        this.tipoJugador = tipoJugador;
    }
    
    
    
    
    
    
}
