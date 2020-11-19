package conexionServidor;


import callMessage.Mandadero;
import enumServicio.EnumServicio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ComunicadorRedCliente implements Runnable {

    private Socket socket;
    
    private ObjectOutputStream flujoSalidaDatos;
    private ObjectInputStream flujoEntradaDatos;
    //private Jugador jugador;

    public ComunicadorRedCliente(Socket socket) throws IOException {
        this.socket = socket;
        
        try{
            this.flujoSalidaDatos = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntradaDatos = new ObjectInputStream(socket.getInputStream());
        }catch(IOException ex){
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public void desconectar() throws IOException{
        try{
            this.socket.close();
        }catch(IOException ex){
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    @Override
    public void run() {
        try{            
            // SE LEE EL OBJETO RECIBIDO
            Mandadero mandadero=(Mandadero)this.flujoEntradaDatos.readObject();
            System.out.println(mandadero);

            // SE COMPRUEBA SU TIPO, Y SE CANALIZA AL MANEJADOR APROPIADO
            if(EnumServicio.INGRESAR_PARTIDA.equals(mandadero.getTipoServicio())){

            }else{
                System.out.println("No es el servicio que esperábamos");
            }
            
            // COMO DEBERÍA CONTESTAR AL SERVIDOR, QUE NOS FALTA???
            
        }catch(ClassCastException ex){
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "El objeto recibido no es un mensaje válido", ex);            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "No se encontró la clase", ex);            
        }catch(IOException ex){
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
}
