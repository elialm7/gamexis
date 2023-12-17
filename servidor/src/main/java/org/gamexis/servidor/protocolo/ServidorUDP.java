package org.gamexis.servidor.protocolo;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

public class ServidorUDP implements Runnable {

    private final DatagramChannel channel;
    private final MembershipKey membershipKey;
    private volatile boolean cerrado = false;


    public ServidorUDP(int puerto, String direccionMulticast ) throws IOException {
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(puerto));

        InetAddress group = InetAddress.getByName(direccionMulticast);
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());

        this.membershipKey = channel.join(group, networkInterface);
        System.out.println("Servidor Multicast UDP esperando mensajes IP "+InetAddress.getLocalHost() +" y en la dirección "+direccionMulticast+"...");
    }

    public void cerrar() throws IOException {
        cerrado = !cerrado;
        membershipKey.drop();
        channel.close();
    }

    @Override
    public void run() {
        try{
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketAddress clientAddress = channel.receive(buffer);

                buffer.flip();
                String message = new String(buffer.array(), 0, buffer.limit());

                System.out.println("Mensaje recibido de " + clientAddress + ": " + message);

                if (message.equals("cerrar")) {
                    System.out.println("Cerrando el servidor...");

                    break;
                }
                ByteBuffer enviarBuffer = ByteBuffer.wrap(message.getBytes());
                channel.send(enviarBuffer,clientAddress);
            }
            System.out.println("Ya");
            cerrar();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isCerrado() {
        return cerrado;
    }
}
