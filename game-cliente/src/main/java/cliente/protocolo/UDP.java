package cliente.protocolo;

import cliente.Cliente;
import cliente.ClienteObserver;
import cliente.ICliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDP implements Runnable, ICliente {
    private static final int BUFFER_SIZE = 256;
    private final DatagramChannel channel;
    private final InetSocketAddress serverSocketAddress;
    private final ByteBuffer bufferEmisor;
    private final ByteBuffer bufferReceptor;

    private ClienteObserver clienteObserver;

    public UDP(String servidorDireccion, String grupoMulticast) throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        InetAddress serverAddress = InetAddress.getByName(servidorDireccion);
        serverSocketAddress = new InetSocketAddress(serverAddress, 8888);
        bufferEmisor = bufferReceptor = ByteBuffer.allocate(BUFFER_SIZE);
        channel.join(InetAddress.getByName(grupoMulticast), NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
    }
    public void registrarObservador(ClienteObserver observador) {
        this.clienteObserver = observador;
    }

    void notificarCambioEstado(byte[] data) {
        this.clienteObserver.actualizarEstado(data);
    }
    @Override
    public void enviarMensaje(byte[] data) {
        bufferEmisor.clear();
        bufferEmisor.put(data);
        bufferEmisor.flip();
        try {
            channel.send(bufferEmisor, serverSocketAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] recibirMensaje()  {
        bufferReceptor.clear();
        SocketAddress senderAddress = null;
        try {
            senderAddress = channel.receive(bufferReceptor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (senderAddress != null) {
            bufferReceptor.flip();
            byte[] respuestaBytes = new byte[bufferReceptor.remaining()];
            bufferReceptor.get(respuestaBytes);
            return respuestaBytes;
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
                byte[] msg = this.recibirMensaje();
                if(msg !=null && msg.length>0) {
                    c++;
                    this.clienteObserver.actualizarEstado(msg);
                }
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
