package org.gamexis.cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Cliente {
    private final String MULTICAST_GROUP = "224.0.0.1";
    private final int PORT;
    private final MulticastSocket socket;
    private final InetAddress group;
    private boolean cerrado = false;

    public Cliente() throws IOException {
        this.PORT = 5000;

        socket = new MulticastSocket(PORT);
        group = InetAddress.getByName(MULTICAST_GROUP);
        socket.joinGroup(group);
    }

    public void enviarMensaje(String message) throws Exception {
        if (cerrado) {
            throw new IllegalStateException("El cliente está cerrado. No se pueden enviar mensajes.");
        }

        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, PORT);
        socket.send(sendPacket);
    }

    public String recibirMensaje() throws Exception {
        if (cerrado) {
            throw new IllegalStateException("El cliente está cerrado. No se pueden recibir mensajes.");
        }

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }

    public void close() {
        try {
            if (!cerrado) {
                socket.leaveGroup(group);
                socket.close();
                cerrado = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
