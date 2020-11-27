package servidor;

import conexionServidor.ServidorSocket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalServidor {

    public static void main(String[] args) {
        try{            
            new ServidorSocket(9090).iniciar();
        }catch(IOException ex){
            System.err.printf("Error: %s", ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(PrincipalServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
