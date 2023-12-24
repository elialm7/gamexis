package cliente;

import java.io.IOException;
import cliente.protocolo.UDP;
public class Cliente extends Thread implements ICliente, ClienteObserver{
    private final int PUERTO_UDP = 8888;
    private final int PUERTO_TCP = 3100;

    private final UDP UDP;

    public Cliente(String servidorDireccion, String grupoMulticast) throws IOException {
        this.UDP = new UDP(servidorDireccion, grupoMulticast);
        this.UDP.registrarObservador(this);
    }

    @Override
    public void run() {
        Thread servidorUDP = new Thread(this.UDP);
        servidorUDP.start();
        while (servidorUDP.isAlive());
    }

    @Override
    public byte[] recibirMensaje() {
        return new byte[0];
    }

    @Override
    public void enviarMensaje(byte[] data) {
        this.UDP.enviarMensaje(data);
    }

    @Override
    public void actualizarEstado(byte[] data) {
        String msg = new String(data).trim();
        System.out.println("Desde UDP = "+msg);
    }
}
