package conexionServidor;

import callMessage.Mandadero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadorServicios.ManejadorServicioAsignarTurno;
import manejadorServicios.ManejadorServicioMensaje;
import manejadorServicios.ManejadorServicios;

public class ComunicadorRedCliente implements Runnable {

    ManejadorServicios ms;
    private Socket socket;
    ServidorSocket servidor;
    boolean turno;

    private ObjectOutputStream flujoSalidaDatos;
    private ObjectInputStream flujoEntradaDatos;

    public ComunicadorRedCliente(Socket socket, ServidorSocket servidor) throws IOException {
        this.socket = socket;
        this.servidor = servidor;
        this.turno = false;

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

    public void responderPeticion(Mandadero m) {
        try {
            this.flujoSalidaDatos.writeObject(m);
            this.flujoSalidaDatos.flush();
//            this.socket.close();
        } catch (ClassCastException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "El objeto recibido no es un mandadero válido", ex);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void responderATodos(Mandadero mandadero) {
        for (int i = 0; i < servidor.getClientes().size(); i++) {
            ComunicadorRedCliente clienteConectado = servidor.getClientes().get(i);    //según esto ya nos falta solo este método y la lista
            clienteConectado.responderPeticion(mandadero);
        }
    }

    @Override
    public void run() {
        try {                        
           Mandadero mandadero = null;            
            do {               
                mandadero = (Mandadero) this.flujoEntradaDatos.readObject();
                switch (mandadero.getTipoServicio()) {
                    case INGRESAR_PARTIDA:
                        
                        System.out.println("No es el servicio que esperabamos");
                        break;

                    case CREAR_PARTIDA:
                        System.out.println(mandadero.toString());
                        System.out.println("Nel, está mal");
                        break;
                        
                    case ENVIAR_MENSAJE:
                        ms = new ManejadorServicioMensaje(mandadero);
                        ms.ejecutar();
                        Mandadero msj = ms.getRespuesta();                       
                        System.out.println(msj);
                        System.out.println("Llegó el pedido");
                        responderATodos(msj);
                        break;
                        
                    case ASIGNAR_TURNO:
                        ms = new ManejadorServicioAsignarTurno(servidor.getClientes(), mandadero);
                        Mandadero m = ms.getRespuesta();  
                        System.out.println(m);
                        System.out.println("Asignamos turno");
                        
                        break;
                        
                    default:
                        System.out.println("No es el servicio que esperábamos");
                        break;
                }
            } while (!mandadero.getRespuesta().get("mensaje").equals("adioh"));

        } catch (ClassCastException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "El objeto recibido no es un mensaje válido", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "No se encontró la clase", ex);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    

}
