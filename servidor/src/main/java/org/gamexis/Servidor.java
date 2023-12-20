package org.gamexis;


import org.gamexis.protocolo.UDP;
import org.gamexis.protocolo.TCP;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor extends Thread {

    public static final int PUERTO_UDP = 8888;
    public static final int PUERTO_TCP = 3100;

    private final ExecutorService servidoresUDP =  Executors.newFixedThreadPool(5);

    private final UDP UDP_PRINCIPAL;

    private final TCP TCP;

    public Servidor() {
        //Uso esa dirección porque es la primera entre el rango de direcciones multicast
        TCP = new TCP();
        UDP_PRINCIPAL = new UDP("234.0.1.1");
    }
    public void run() {
        Thread servidorPrincipal = new Thread(this.TCP);
        servidorPrincipal.start();

        //servidoresUDP.submit(this.UDP_PRINCIPAL);
        while(!this.TCP.estaCerrado());
        System.out.println("Cerrar");
    }

}