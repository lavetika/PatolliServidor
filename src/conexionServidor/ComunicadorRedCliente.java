package conexionServidor;

import Dominio.Jugador;
import Dominio.TipoJugador;
import callMessage.Mandadero;
import enumServicio.EnumServicio;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import manejadorServicios.ManejadorServicioPosicion;
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
        try {
            Mandadero mandadero = null;
            Mandadero msj = null;
            do {
                mandadero = (Mandadero) this.flujoEntradaDatos.readObject();
                switch (mandadero.getTipoServicio()) {
                    case INGRESAR_PARTIDA:
                        ingresarPartida(mandadero, msj);
                        break;

                    case CREAR_PARTIDA:
                        this.crearPartida(mandadero, msj);
                        break;

                    case ENVIAR_MENSAJE:
                        this.enviarMensaje(mandadero, msj);
                        break;

                    case ASIGNAR_TURNO:
                        this.asignarTurno(mandadero, msj);
                        break;

                    case ABANDONO_JUGADOR:
                        this.abandonoJugador(mandadero, msj);
                        break;

                    case MOVIMIENTO_FICHA:
                        this.movimientoFicha(mandadero, msj);
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

    public Jugador getHost() {
        if (!servidor.getClientes().isEmpty()) {
            //el primero en la lista será el host pero cómo uso lo del enum
//            servidor.getClientes().get(0).getJugador().setTipoJugador(TipoJugador.HOST);
            return servidor.getClientes().get(0).getJugador();
        }
        return null;
    }

    public boolean isTerminada() {
        return servidor.getClientes().size() == 1;
    }

    public void ingresarPartida(Mandadero mandadero, Mandadero msj) {
        this.jugador = (Jugador) mandadero.getParams().get("jugador");
        ms = new ManejadorServicioIngresarPartida(mandadero);
        ms.ejecutar();
        this.posicionarJugador(mandadero, msj);

    }

    public void crearPartida(Mandadero mandadero, Mandadero msj) {
        this.jugador = (Jugador) mandadero.getParams().get("jugador");
        ms = new ManejadorServicioCrearPartida(mandadero);
        ms.ejecutar();
        msj = ms.getRespuesta();

        if (mandadero.getParams().size() <= 0) {
            servidor.getClientes().remove(this);
            responderPeticion(msj);
        } else {
            this.posicionarJugador(mandadero, msj);
        }
    }

    public void asignarTurno(Mandadero mandadero, Mandadero msj) {
        ms = new ManejadorServicioAsignarTurno(servidor.getClientes(), mandadero);
        ms.ejecutar();
        ManejadorServicioPosicion mo = new ManejadorServicioPosicion(mandadero, this, servidor.getClientes());
        mo.ejecutar();
        msj = mo.getRespuesta();
        responderATodos(msj);
    }

    public void abandonoJugador(Mandadero mandadero, Mandadero msj) {
        ms = new ManejadorServicioAbandono(mandadero, this, servidor.getClientes());
        ms.ejecutar();
        msj = ms.getRespuesta();
        responderPeticion(msj);

        this.posicionarJugador(mandadero, msj);
    }

    public void enviarMensaje(Mandadero mandadero, Mandadero msj) {
        ms = new ManejadorServicioMensaje(mandadero);
        ms.ejecutar();
        msj = ms.getRespuesta();
        responderATodos(msj);

    }

    public void movimientoFicha(Mandadero mandadero, Mandadero msj) {
        ms = new ManejadorServicioCambiarTurno(mandadero);
        msj = ms.getRespuesta();
        turnosJugadores(mandadero);

    }

    public void posicionarJugador(Mandadero mandadero, Mandadero msj) {

        ManejadorServicioPosicion mo = new ManejadorServicioPosicion(mandadero, this, servidor.getClientes());
        mo.ejecutar();
        msj = mo.getRespuesta();
        msj.setTipoServicio(EnumServicio.POSICIONAR_JUGADOR);
        responderATodos(msj);
    }

}
