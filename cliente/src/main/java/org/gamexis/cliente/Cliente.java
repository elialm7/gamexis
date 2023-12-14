package org.gamexis.cliente;

import java.io.IOException;
import java.net.*;

public class Cliente {
    private final int PORT;
    private final MulticastSocket socket;
    private final InetAddress group;
    private boolean cerrado = false;

    public Cliente() throws IOException {
        this.PORT = 5000;

        socket = new MulticastSocket(null);
        String MULTICAST_GROUP = "224.0.0.1";
        group = InetAddress.getByName(MULTICAST_GROUP);

        // Reemplaza 'joinGroup(group)' por el siguiente bloque:
        socket.joinGroup(new java.net.InetSocketAddress(group, PORT), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
    }

    public void enviarMensaje(String message) throws IOException {
        if (cerrado) {
            throw new IllegalStateException("El cliente está cerrado. No se pueden enviar mensajes.");
        }

        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, PORT);
        socket.send(sendPacket);
    }

    public String recibirMensaje() throws IOException {
        if (cerrado) {
            throw new IllegalStateException("El cliente está cerrado. No se pueden recibir mensajes.");
        }

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }

    public void close() throws IOException {
        if (!cerrado) {
            socket.leaveGroup(new java.net.InetSocketAddress(group, PORT), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
            socket.close();
            cerrado = true;
        }
    }
}
