package org.gamexis.cliente;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Cliente {
    private  String MULTICAST_ADDRESS = "224.0.1.1";
    private String IP_SERVIDOR;
    private  final int PORT = 8888;

    private DatagramChannel channel;



    public Cliente(String serverIPAddress) {
        this.IP_SERVIDOR = serverIPAddress;
        try {
            // Configurar el canal del cliente
            channel = DatagramChannel.open();

            //Direccion del servidor
            InetAddress hostServidor = InetAddress.getByName(IP_SERVIDOR);

            // Configurar la interfaz de red local
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(hostServidor);
            channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);

            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);

            // Unirse al grupo de multidifusión en la interfaz especificada
            channel.join(group, networkInterface);

            System.out.println("Cliente Multicast UDP unido al grupo " + MULTICAST_ADDRESS + " en la interfaz " + hostServidor.getHostName()+"/"+ hostServidor.getHostAddress()+ "...");

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
            //Mantengo el serverAddress para futuros usos, y también por el channel.receive() que sin ese no funciona.
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}