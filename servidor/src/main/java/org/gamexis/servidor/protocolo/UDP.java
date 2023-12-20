package org.gamexis.servidor.protocolo;

import org.gamexis.servidor.Servidor;
import org.gamexis.servidor.IServidor;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class UDP implements Runnable, IServidor {

    private final DatagramChannel channel;
    private final InetAddress group;
    private final Set<SocketAddress> miembros;
    private volatile boolean cerrado = false;

    public UDP(String direccionMulticast) {
        try {
            channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(Servidor.PUERTO_UDP));
            group = InetAddress.getByName(direccionMulticast);
            miembros = new HashSet<>();
            this.channel.join(group, NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
            System.out.println("Servidor Multicast UDP esperando mensajes IP " + InetAddress.getLocalHost() +
                    " y en la dirección " + direccionMulticast + "...");
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el servidor UDP", e);
        }
    }

    public void cerrar() {
        cerrado = true;
        try {
            channel.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar el canal");
        }
    }

    @Override
    public void run() {
        try {
            // Espera eventos de los canales registrados en el selector
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (!cerrado && !Thread.currentThread().isInterrupted()) {
                buffer.clear();
                SocketAddress clientAddress = channel.receive(buffer);
                buffer.flip();
                String message = new String(buffer.array(), 0, buffer.remaining(), StandardCharsets.UTF_8).trim();
                System.out.println("Mensaje recibido de " + clientAddress + ": " + message);

                if (message.equals("cerrar")) {
                    // Cliente solicitó cerrar la conexión
                    System.out.println("Cerrando el servidor...");
                    cerrar();
                    break;
                }

                // Registra al cliente como miembro
                miembros.add(clientAddress);

                // Envía el mensaje a todos los miembros del grupo multicast
                enviarMensajeATodos(message);
            }

            System.out.println("Ya");
            cerrar();
        } catch (IOException e) {
            throw new RuntimeException("Error en el servidor UDP", e);
        }
    }


    private void enviarMensajeATodos(String message) throws IOException {
        // Envía el mensaje a cada miembro del grupo multicast
        for (SocketAddress memberAddress : miembros) {
            if (!memberAddress.equals(channel.getLocalAddress())) {
                // Evita enviar el mensaje al remitente original
                channel.send(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)), memberAddress);
            }
        }
       // channel.send(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)),channel.getLocalAddress());
    }

    public boolean estaCerrado() {
        return cerrado;
    }
}
