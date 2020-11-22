package conexionServidor;

import callMessage.Mandadero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadorServicios.ManejadorServicioMensaje;
import manejadorServicios.ManejadorServicios;

public class ComunicadorRedCliente implements Runnable {
    ManejadorServicios ms;
    private Socket socket;

    private ObjectOutputStream flujoSalidaDatos;
    private ObjectInputStream flujoEntradaDatos;

    public ComunicadorRedCliente(Socket socket) throws IOException {
        this.socket = socket;

        try {
            this.flujoSalidaDatos = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntradaDatos = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void desconectar() throws IOException {
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
    
    public void responderPeticion(Mandadero m){
        try{
            this.flujoSalidaDatos.writeObject(m);
            this.flujoSalidaDatos.flush();
//            this.socket.close();
        }catch(ClassCastException ex){
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "El objeto recibido no es un mandadero válido", ex);
        }catch(IOException ex){
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            // SE LEE EL OBJETO RECIBIDO
            Mandadero mandadero = (Mandadero) this.flujoEntradaDatos.readObject();
            
            switch (mandadero.getTipoServicio()) {
                case INGRESAR_PARTIDA:
                    System.out.println("No es el servicio que esperabamos");
                    break;

                case CREAR_PARTIDA:
                    System.out.println(mandadero.toString());
                    System.out.println("Nel, ta mal");
                    break;
                case ENVIAR_MENSAJE:
                    
                    
                    
                    ms=new ManejadorServicioMensaje(mandadero);
                    ms.ejecutar();
                    Mandadero msj= ms.getRespuesta();
                    responderPeticion(msj);
                    System.out.println(msj);
                    System.out.println("Llegó el pedido, chicaaaas");
                    
                    break;

                default:
                    System.out.println("No es el servicio que esperábamos");
                    break;
            }
            
            

        } catch (ClassCastException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "El objeto recibido no es un mensaje válido", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "No se encontró la clase", ex);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
