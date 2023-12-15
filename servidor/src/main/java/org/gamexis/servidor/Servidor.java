package org.gamexis.servidor;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

public class Servidor extends Thread {
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 8888;

    public void run() {
        try {
            // Configurar el canal del servidor
            DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET)
                    .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                    .bind(new InetSocketAddress(PORT));

            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            System.out.println(InetAddress.getLocalHost());
            MembershipKey membershipKey = channel.join(group, networkInterface);

            System.out.println("Servidor Multicast UDP esperando mensajes...");

            // Bucle principal del servidor
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketAddress clientAddress = channel.receive(buffer);

                buffer.flip();
                String message = new String(buffer.array(), 0, buffer.limit());

                System.out.println("Mensaje recibido de " + clientAddress + ": " + message);

                if (message.equals("cerrar")) {
                    System.out.println("Cerrando el servidor...");
                    membershipKey.drop(); // Dejar el grupo multicast antes de cerrar el canal
                    channel.close();
                    break;
                }
                ByteBuffer enviarBuffer = ByteBuffer.wrap(message.getBytes());
                channel.send(enviarBuffer,clientAddress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}