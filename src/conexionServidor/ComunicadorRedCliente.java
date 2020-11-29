package conexionServidor;

import Control.Partida;
import callMessage.Mandadero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadorServicios.ManejadorServicioAbandono;
import manejadorServicios.ManejadorServicioAsignarTurno;
import manejadorServicios.ManejadorServicioCrearPartida;
import manejadorServicios.ManejadorServicioIngresarPartida;
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
        } catch (ClassCastException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "El objeto recibido no es un mandadero válido", ex);
        } catch (IOException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void responderATodos(Mandadero mandadero) {
        for (int i = 0; i < servidor.getClientes().size(); i++) {
            ComunicadorRedCliente clienteConectado = servidor.getClientes().get(i);
            clienteConectado.responderPeticion(mandadero);
        }
    }

    @Override
    public void run() {
        try {
            Mandadero mandadero = null;
            Mandadero msj;
            do {
                mandadero = (Mandadero) this.flujoEntradaDatos.readObject();
                switch (mandadero.getTipoServicio()) {
                    case INGRESAR_PARTIDA:
                        ms = new ManejadorServicioIngresarPartida(mandadero);
                        ms.ejecutar();
                        msj = ms.getRespuesta();
                        responderPeticion(msj);
                        break;

                    case CREAR_PARTIDA:
                        ms = new ManejadorServicioCrearPartida(mandadero);
                        ms.ejecutar();
                        msj = ms.getRespuesta();
                        responderPeticion(msj);
                        if (mandadero.getParams().size() <= 0) {
                            servidor.getClientes().remove(this);
                        }
                        break;

                    case ENVIAR_MENSAJE:
                        ms = new ManejadorServicioMensaje(mandadero);
                        ms.ejecutar();
                        msj = ms.getRespuesta();
                        System.out.println("Llegó el pedido");
                        responderATodos(msj);
                        break;

                    case ASIGNAR_TURNO:
                        ms = new ManejadorServicioAsignarTurno(servidor.getClientes(), mandadero);
                        msj = ms.getRespuesta();
                        System.out.println(msj);
                        System.out.println("Asignamos turno");
                        System.out.println("SS: " + servidor.getClientes());
                        break;

                    case ABANDONO_JUGADOR:
//                        System.out.println("Sin remover: "+servidor.getClientes());
                        ms = new ManejadorServicioAbandono(mandadero, this, servidor.getClientes());
                        ms.ejecutar();
                        msj = ms.getRespuesta();
                        responderPeticion(msj);
                        System.out.println(msj);
                        System.out.println("Abandonamos jugador");
                        System.out.println("Jugador removido en Servidor: " + servidor.getClientes());
//                        System.out.println("Jugador removido en Partida: "+Partida.getInstance().getJugadores());
                        
                        break;
                    default:
                        System.out.println("No es el servicio que esperábamos");
                        break;
                }
            } while (mandadero != null);

        } catch (ClassCastException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "este 3", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ComunicadorRedCliente.class.getName()).log(Level.SEVERE, "este 2", ex);
        } catch (IOException ex) {

        }
    }

}
