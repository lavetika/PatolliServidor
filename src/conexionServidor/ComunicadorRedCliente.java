package conexionServidor;

import Dominio.Jugador;
import callMessage.Mandadero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadorServicios.ManejadorPosicion;
import manejadorServicios.ManejadorServicioAbandono;
import manejadorServicios.ManejadorServicioAsignarTurno;
import manejadorServicios.ManejadorServicioCambiarTurno;
import manejadorServicios.ManejadorServicioConfirmarTurno;
import manejadorServicios.ManejadorServicioCrearPartida;
import manejadorServicios.ManejadorServicioIngresarPartida;
import manejadorServicios.ManejadorServicioMensaje;
import manejadorServicios.ManejadorServicios;

public class ComunicadorRedCliente implements Runnable {

    ManejadorServicios ms;
    private Socket socket;
    ServidorSocket servidor;
    boolean turno;
    Jugador jugador;

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

    public Jugador getJugador() {
        return jugador;
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

    public void turnosJugadores(Mandadero mandadero) {
        for (ComunicadorRedCliente jugador : servidor.getClientes()) {
            ms = new ManejadorServicioConfirmarTurno(mandadero, jugador);
            ms.ejecutar();
            Mandadero msj = ms.getRespuesta();
            jugador.responderPeticion(msj);
        }
    }

    @Override
    public void run() {
        try {//mande
            Mandadero mandadero = null;
            Mandadero msj;
            do {
                mandadero = (Mandadero) this.flujoEntradaDatos.readObject();
                switch (mandadero.getTipoServicio()) {
                    case INGRESAR_PARTIDA:
                        ms = new ManejadorServicioIngresarPartida(mandadero);
                        ms.ejecutar(); 
                        msj = ms.getRespuesta();

                        this.jugador = (Jugador) mandadero.getParams().get("jugador");
                        ManejadorPosicion mo = new ManejadorPosicion(mandadero, this, servidor.getClientes());
                        mo.ejecutar();
                        msj = mo.getRespuesta();
                        responderATodos(msj);

                        break; 

                    case CREAR_PARTIDA:

                        ms = new ManejadorServicioCrearPartida(mandadero);
                        ms.ejecutar();
                        msj = ms.getRespuesta();

                        if (mandadero.getParams().size() <= 0) {
                            servidor.getClientes().remove(this);
                            responderPeticion(msj);
                        } else {
                            this.jugador = (Jugador) mandadero.getParams().get("jugador");
                            ManejadorPosicion mp = new ManejadorPosicion(mandadero, this, servidor.getClientes());
                            mp.ejecutar();
                            msj = mp.getRespuesta();
                            responderPeticion(msj); 
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
                        ms = new ManejadorServicioAbandono(mandadero, this, servidor.getClientes());
                        ms.ejecutar();
                        ManejadorPosicion mp = new ManejadorPosicion(mandadero, this, servidor.getClientes());
                        mp.ejecutar();
                        msj = mp.getRespuesta();
                        responderPeticion(msj); //utiliza este por que ya no esta el solicitante en la lista de clientes de servidor.
                        responderATodos(msj);
                        break;
                    case MOVIMIENTO_FICHA:
                        ms = new ManejadorServicioCambiarTurno(mandadero);
                        msj = ms.getRespuesta();
                        //responderPeticion(msj);
                        System.out.println(msj);
                        turnosJugadores(mandadero);
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
