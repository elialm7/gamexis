package org.gamexis.protocolo;

import org.gamexis.Servidor;
import org.gamexis.IServidor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TCP implements Runnable, IServidor {


    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private volatile boolean cerrado = false;
    private final Map<String, SocketChannel> clientes = new HashMap<>();
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
        String idCliente = "Cliente-"+System.currentTimeMillis();
        clientes.put(idCliente,socketChannel);
        System.out.println("Cliente conectado desde: " + socketChannel.getRemoteAddress());
        enviarMensaje(socketChannel,"Como estás maldita puta? Tu id es: "+ idCliente);
    }
    private void leerData(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = socketChannel.read(buffer);

        if (bytesRead == -1) {
            socketChannel.close();
            String idCliente = getClienteId(socketChannel);
            clientes.remove(idCliente);
            System.out.println("Cliente desconectado: " + socketChannel.getRemoteAddress());
        } else if (bytesRead > 0) {
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String message = new String(data);
            System.out.println("Mensaje recibido de " + socketChannel.getRemoteAddress() + ": " + message);
        }
    }
    private String getClienteId(SocketChannel socketChannel) {
        for (Map.Entry<String, SocketChannel> entry : clientes.entrySet()) {
            if (entry.getValue().equals(socketChannel)) {
                return entry.getKey();
            }
        }
        return null;
    }
    private void enviarMensaje (SocketChannel socketChannel, String mensaje) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(mensaje.getBytes(StandardCharsets.UTF_8));
        socketChannel.write(buffer);
    }
    @Override
    public void cerrar()  {
        cerrado = true;
        try {
            this.serverSocketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean estaCerrado() {
        return false;
    }
}
