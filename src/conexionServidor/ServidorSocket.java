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
        clientes = new ArrayList<>();   ///también son delfines?
        
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
                //KK
                // ESTÁ BIEN, CREAR UN NUEVO COMUNICADOR DE RED PARA CADA CLIENTE...?
                ComunicadorRedCliente cliente =new ComunicadorRedCliente(socket, this);       
                Thread hilo = new Thread(cliente);
                hilo.start();
                clientes.add(cliente); //Y si hacemos que el broker sea observador del hilo? así le llega la actualización y este se lo manda al panel
                
                System.out.println("Nuevo cliente conectado");
            }
        }catch(IOException ex){
            System.err.printf("Error %s %n", ex.getMessage());
            throw ex;
        }        
    }
    
    //NECESITAMOS EL WHILE
    public List<ComunicadorRedCliente> getClientes(){
        return clientes;
    }
    
}
