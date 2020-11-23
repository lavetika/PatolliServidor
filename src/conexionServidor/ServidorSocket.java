package conexionServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServidorSocket {

    private final int PUERTO;
    private ServerSocket servidorSockets;
    private List<ComunicadorRedCliente> clientes;

    public ServidorSocket(int puerto) {
        this.PUERTO = puerto;
        System.out.println("Creando el servidor...");
        clientes = new ArrayList<>();
    }

    public void iniciar() throws IOException {
        try {
            System.out.println("Iniciando el servidor...");
            this.servidorSockets = new ServerSocket(this.PUERTO);
            System.out.printf("Servidor iniciado en el puerto: %d %n", this.PUERTO);

            while (true) {
                Socket socket = this.servidorSockets.accept();
                System.out.println("Nuevo cliente intentando conectarse...");
                ComunicadorRedCliente cliente = new ComunicadorRedCliente(socket, this);
                Thread hilo = new Thread(cliente);
                hilo.start();
                clientes.add(cliente);

                System.out.println("Nuevo cliente conectado");
            }
        } catch (IOException ex) {
            System.err.printf("Error %s %n", ex.getMessage());
            throw ex;
        }
    }

    public List<ComunicadorRedCliente> getClientes() {
        return clientes;
    }

}
