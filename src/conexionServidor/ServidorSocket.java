package conexionServidor;

import Control.Partida;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorSocket {

    private final int PUERTO;
    private ServerSocket servidorSockets;
    private List<ComunicadorRedCliente> clientes;
    private int maximoJugadores;
    
    public ServidorSocket(int puerto) {
        this.PUERTO = puerto;
        System.out.println("Creando el servidor...");
        clientes = new ArrayList<>();
        this.maximoJugadores = 2;
    }

    public void iniciar() throws Exception {
        try {
            System.out.println("Iniciando el servidor...");
            this.servidorSockets = new ServerSocket(this.PUERTO);
            System.out.printf("Servidor iniciado en el puerto: %d %n", this.PUERTO);

            while (true) {
                Socket socket = this.servidorSockets.accept();

                if (clientes.size() <= maximoJugadores) {
                    if(Partida.getInstance().isEstado()){
                        maximoJugadores = Partida.getInstance().getCantJugadores();//la cantidad de jugadores
                    }//maybe
                    System.out.println("Nuevo cliente intentando conectarse...");
                    ComunicadorRedCliente cliente = new ComunicadorRedCliente(socket, this);
                    Thread hilo = new Thread(cliente);
                    hilo.start();
                    clientes.add(cliente);

                    System.out.println("Nuevo cliente conectado");
                    
                }else{
                    socket.close();
//                    throw new IOException("Mmmm, ayer se cerró pedido, chica ¯|_(ツ)_|¯");
                }


                
            }
        } catch (IOException ex) {
//            System.err.printf("Error %s %n", ex.getMessage());
            throw ex;
        }
    }

    public List<ComunicadorRedCliente> getClientes() {
//        System.out.println("Lista de clientes en ServidorSocket: "+clientes);
        return clientes;
    }
    
    
  

}
