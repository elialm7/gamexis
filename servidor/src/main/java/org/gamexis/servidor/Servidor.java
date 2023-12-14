package org.gamexis.servidor;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;

public class Servidor extends Thread {
    private final int PUERTO;
    private final String GRUPO_MULTICAST;
    private final MulticastSocket socket;
    private final AtomicBoolean enEjecucion;
    private NetworkInterface interfaz = null;


    private final byte[] buf = new byte[1024];

    public Servidor() throws IOException {
        this.PUERTO = 5000;
        this.GRUPO_MULTICAST = "224.0.0.1"; // Puedes ajustar esto según tus necesidades

        socket = new MulticastSocket(PUERTO);

        // Obtener la interfaz de red correcta

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements()) {
            NetworkInterface currentInterface = interfaces.nextElement();

            if (!currentInterface.isLoopback() && !currentInterface.isVirtual() && currentInterface.supportsMulticast() && currentInterface.isUp()) {
                interfaz = currentInterface;
                break;
            }
        }

        if (interfaz != null) {
            socket.joinGroup(new java.net.InetSocketAddress(InetAddress.getByName(GRUPO_MULTICAST), PUERTO), interfaz);
        } else {
            throw new IOException("No se encontró una interfaz de red válida para unirse al grupo multicast.");
        }

        enEjecucion = new AtomicBoolean(true);
    }

    public void run() {
        while (enEjecucion.get()) {
            DatagramPacket paqueteRecibido = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(paqueteRecibido);

                InetAddress direccionCliente = paqueteRecibido.getAddress();
                int puertoCliente = paqueteRecibido.getPort();

                String recibido = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                System.out.println("Recibido del cliente (" + direccionCliente.getHostAddress() + ":" + puertoCliente + "): " + recibido);

                // Puedes agregar lógica adicional según tus necesidades aquí
                if (recibido.equals("cerrar")) {
                    enEjecucion.set(false);
                    continue;
                }
                socket.send(paqueteRecibido);

            } catch (IOException e) {
                e.printStackTrace();
                // Manejar la excepción según tus necesidades
            }
        }

        try {
            socket.leaveGroup(new InetSocketAddress(GRUPO_MULTICAST, PUERTO), interfaz);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar la excepción según tus necesidades
        } finally {
            socket.close();
        }
    }

    public void detenerServidor() {
        enEjecucion.set(false);
    }
}
