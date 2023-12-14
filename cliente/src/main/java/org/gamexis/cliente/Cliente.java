package org.gamexis.cliente;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Cliente {
    private String serverIPAddress;
    private static final String MULTICAST_ADDRESS = "224.0.0.1";
    private static final int PORT = 8888;

    private DatagramChannel channel;



    public Cliente(String serverIPAddress) {
        this.serverIPAddress = serverIPAddress;

        try {
            // Configurar el canal del cliente
            channel = DatagramChannel.open(StandardProtocolFamily.INET)
                    .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                    .bind(new InetSocketAddress(0)) // 0 indica que se elija un puerto aleatorio
                    .setOption(StandardSocketOptions.IP_MULTICAST_IF, NetworkInterface.getByInetAddress(InetAddress.getByName(serverIPAddress)));

            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            channel.join(group, NetworkInterface.getByInetAddress(InetAddress.getByName(serverIPAddress)));

           // System.out.println("Cliente Multicast UDP conectado al servidor en " + serverIPAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(mensaje.getBytes());
            channel.send(buffer, new InetSocketAddress(MULTICAST_ADDRESS, PORT));
            System.out.println("Mensaje enviado: " + mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String recibirMensaje() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketAddress serverAddress = channel.receive(buffer);
            buffer.flip();
            String msg = new String(buffer.array(), 0, buffer.limit());
            System.out.println("Recibido "+msg+" desde cliente");
            return msg ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void salir() {
        try {
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}