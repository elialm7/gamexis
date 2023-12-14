package org.gamexis.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public class Servidor extends Thread {
    private final int PORT;
    private final String MULTICAST_GROUP;
    private final MulticastSocket socket;
    private final AtomicBoolean running;
    private final byte[] buf = new byte[1024];

    public Servidor() throws IOException {
        this.PORT = 5000;
        this.MULTICAST_GROUP = "224.0.0.1"; // Puedes ajustar esto según tus necesidades

        socket = new MulticastSocket(null);
        SocketAddress socketAddress = new java.net.InetSocketAddress(PORT);
        socket.bind(socketAddress);

        InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
        socket.joinGroup(new java.net.InetSocketAddress(group, PORT), null);

        running = new AtomicBoolean(true);
    }

    public void run() {
        while (running.get()) {
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(receivePacket);

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();

                String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Recibido del cliente (" + address.getHostAddress() + ":" + port + "): " + received);

                // Puedes agregar lógica adicional según tus necesidades aquí
                if (received.equals("cerrar")) {
                    running.set(false);
                    continue;
                }
                socket.send(receivePacket);

            } catch (IOException e) {
                e.printStackTrace();
                // Manejar la excepción según tus necesidades
            }
        } // while

        try {
            socket.leaveGroup(InetAddress.getByName(MULTICAST_GROUP));
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        } finally {
            socket.close();
        }
    }

    public void stopServer() {
        running.set(false);
    }
}
