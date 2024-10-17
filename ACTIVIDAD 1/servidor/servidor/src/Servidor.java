import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servidor {

    public static void main(String[] args) {
        try {
            // Definir el puerto de escucha
            int puerto = 5000;

            // Crear un ServerSocket
            ServerSocket servidor = new ServerSocket(puerto);

            System.out.println("Servidor escuchando en el puerto " + puerto);

            while (true) {
                // Esperar una nueva conexión
                Socket cliente = servidor.accept();

                // Crear un hilo para atender al cliente
                HiloServidor hilo = new HiloServidor(cliente);
                hilo.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class HiloServidor extends Thread {

    private Socket cliente;

    public HiloServidor(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            // Obtener los flujos de entrada y salida del socket
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

            // Leer el mensaje del cliente
            String mensaje = entrada.readUTF();

            // Mostrar el mensaje recibido por consola
            System.out.println("Cliente: " + mensaje);

            // Obtener la fecha o la hora actual
            String respuesta;
            if (mensaje.equalsIgnoreCase("hora")) {
                respuesta = new SimpleDateFormat("HH:mm:ss").format(new Date());
            } else if (mensaje.equalsIgnoreCase("fecha")) {
                respuesta = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            } else {
                respuesta = "Mensaje no válido";
            }

            // Enviar la respuesta al cliente
            salida.writeUTF(respuesta);

            // Cerrar el socket
            cliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
