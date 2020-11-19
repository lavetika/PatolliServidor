package conexionServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket {
    
    private final int PUERTO;
    private ServerSocket servidorSockets;    
    
    public ServidorSocket(int puerto) {
        this.PUERTO = puerto;
        System.out.println("Creando el servidor...");
    }
    
    public void iniciar() throws IOException{
        try{
            System.out.println("Iniciando el servidor...");
            this.servidorSockets = new ServerSocket(this.PUERTO);
            System.out.printf("Servidor iniciado en el puerto: %d %n", this.PUERTO);

            // COMO PUEDO ACCEDER A LOS SOCKETS CUANDO LO NECESITE.. PARA NOTIFICARLES ALGO
            // COMO PUEDO LIMITAR EL NÚMERO DE CLIENTES CONECTADOS...

            while(true){
                Socket socket = this.servidorSockets.accept();
                System.out.println("Nuevo cliente intentando conectarse...");

                // ESTÁ BIEN, CREAR UN NUEVO COMUNICADOR DE RED PARA CADA CLIENTE...?
                Thread hilo = new Thread(new ComunicadorRedCliente(socket));
                hilo.start();
                
                System.out.println("Nuevo cliente conectado");
            }
        }catch(IOException ex){
            System.err.printf("Error %s %n", ex.getMessage());
            throw ex;
        }        
    }
    
}
