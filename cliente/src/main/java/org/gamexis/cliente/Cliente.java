package org.gamexis.cliente;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class Cliente implements Runnable {
    private static final int BUFFER_SIZE = 256;
    private static final String MULTICAST_GROUP = "234.0.1.1";

    private final DatagramChannel channel;
    private final InetSocketAddress serverSocketAddress;
    private final ByteBuffer bufferEmisor;
    private final ByteBuffer bufferReceptor;

    public Cliente(String servidorDireccion) throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        InetAddress serverAddress = InetAddress.getByName(servidorDireccion);
        serverSocketAddress = new InetSocketAddress(serverAddress, 8888);
        bufferEmisor = bufferReceptor = ByteBuffer.allocate(BUFFER_SIZE);
        channel.join(InetAddress.getByName(MULTICAST_GROUP), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
    }

    public void enviarMensaje(String mensaje) throws IOException {
        bufferEmisor.clear();
        bufferEmisor.put(mensaje.getBytes());
        bufferEmisor.flip();
        channel.send(bufferEmisor, serverSocketAddress);
    }

    public String recibirMensaje() throws IOException {
        bufferReceptor.clear();
        SocketAddress senderAddress = channel.receive(bufferReceptor);

        if (senderAddress != null) {
            bufferReceptor.flip();
            byte[] respuestaBytes = new byte[bufferReceptor.remaining()];
            bufferReceptor.get(respuestaBytes);
            return new String(respuestaBytes, StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

    public void salir() throws IOException {
        channel.close();
    }

    @Override
    public void run() {
        int c = 0;
        while (true) {
            try {
                String msg = this.recibirMensaje();
                if(msg != null) {
                    c++;
                    System.out.println(msg + " ["+c+"]");
                }
                Thread.sleep(30);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
