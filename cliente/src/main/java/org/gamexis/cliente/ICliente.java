package org.gamexis.cliente;

public interface ICliente {
    byte[] recibirMensaje();
    void enviarMensaje(byte[] data);
}
