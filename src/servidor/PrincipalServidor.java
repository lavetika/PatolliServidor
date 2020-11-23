package servidor;

import conexionServidor.ServidorSocket;

import java.io.IOException;

public class PrincipalServidor {

    public static void main(String[] args) {
        try{            
            new ServidorSocket(9090).iniciar();
        }catch(IOException ex){
            System.err.printf("Error: %s", ex.getMessage());
        }
    }

}
