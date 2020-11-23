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
    ServidorSocket servidor;
    //con el otro mensaje, sí

    private ObjectOutputStream flujoSalidaDatos;
    private ObjectInputStream flujoEntradaDatos;

    public ComunicadorRedCliente(Socket socket, ServidorSocket servidor) throws IOException {
        this.socket = socket;
        this.servidor = servidor;

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
            // SE LEE EL OBJETO RECIBIDO
            
           Mandadero mandadero = null;
            //no tenemos un while pa recibir muchas peticiones
            do {
                //este iba aquí
                 mandadero = (Mandadero) this.flujoEntradaDatos.readObject();
                switch (mandadero.getTipoServicio()) {
                    case INGRESAR_PARTIDA:
                        System.out.println("No es el servicio que esperabamos");
                        break;

                    case CREAR_PARTIDA:
                        System.out.println(mandadero.toString());
                        System.out.println("Nel, ta mal");
                        break;
                    case ENVIAR_MENSAJE:
                        ms = new ManejadorServicioMensaje(mandadero);
                        ms.ejecutar();
                        Mandadero msj = ms.getRespuesta();
                       ///responderPeticion(msj);
                        System.out.println(msj);
                        System.out.println("Llegó el pedido, chicaaaas");
                        responderATodos(msj);
                        break;
                    default:
                        System.out.println("No es el servicio que esperábamos");
                        break;
                }  //intentamos así?
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
