import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        try {
            int puerto = 5000;

            Socket cliente = new Socket("localhost", puerto);

            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());

            // Interacción con el usuario para elegir qué información desea
            Scanner scanner = new Scanner(System.in);
            System.out.println("¿Qué información desea obtener? (fecha/hora): ");
            String opcion = scanner.nextLine().toLowerCase();

            // Enviar la solicitud al servidor
            salida.writeUTF(opcion);

            // Recibir y mostrar la respuesta del servidor
            String respuesta = entrada.readUTF();
            System.out.println("Respuesta del servidor: " + respuesta);

            cliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}