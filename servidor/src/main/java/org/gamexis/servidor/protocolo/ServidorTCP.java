package org.gamexis.servidor.protocolo;

import org.gamexis.servidor.extension.Acciones;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServidorTCP implements Runnable, Acciones {

    private final int PUERTO;
    private ServerSocketChannel canalSocketServidor;
    private final Selector selector;

    public ServidorTCP(int puertoTCP) {
        this.PUERTO = puertoTCP;
        try {
            canalSocketServidor = ServerSocketChannel.open();
            selector = Selector.open();
            canalSocketServidor.socket().bind(new InetSocketAddress(PUERTO));
            canalSocketServidor.configureBlocking(false);
            canalSocketServidor.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("TCP Server listening on port " + PUERTO);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) continue;

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isAcceptable()) {
                        // Accept the new connection
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("Accepted connection from: " + clientChannel.getRemoteAddress());
                    } else if (key.isReadable()) {
                        // Handle TCP data
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                      //  handleTCPData(clientChannel);
                    }

                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void cerrar() throws IOException {

    }

    @Override
    public boolean estaCerrado() {
        return false;
    }
}
