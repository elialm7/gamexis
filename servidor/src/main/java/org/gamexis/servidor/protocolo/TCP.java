package org.gamexis.servidor.protocolo;

import org.gamexis.servidor.Servidor;
import org.gamexis.servidor.IServidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class TCP implements Runnable, IServidor {


    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private volatile boolean cerrado = false;
    public TCP() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(Servidor.PUERTO_TCP));
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Servidor escuchando en el puerto " + Servidor.PUERTO_TCP);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        try {
            while (!cerrado && !Thread.currentThread().isInterrupted()) {
                int readyChannels = selector.select();

                if (readyChannels == 0) {
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isAcceptable()) {
                        aceptarConexion(serverSocketChannel, selector);
                    } else if (key.isReadable()) {
                        leerData(key);
                    }

                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void aceptarConexion(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Cliente conectado desde: " + socketChannel.getRemoteAddress());
    }
    private void leerData(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = socketChannel.read(buffer);

        if (bytesRead == -1) {
            socketChannel.close();
            System.out.println("Cliente desconectado: " + socketChannel.getRemoteAddress());
        } else if (bytesRead > 0) {
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String message = new String(data);
            System.out.println("Mensaje recibido de " + socketChannel.getRemoteAddress() + ": " + message);
        }
    }

    @Override
    public void cerrar()  {
        cerrado = true;
    }

    @Override
    public boolean estaCerrado() {
        return false;
    }
}
