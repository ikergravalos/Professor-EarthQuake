package com.ikeres.app.professorearthquake;

/**
 * Created by Iker on 21/05/2016.
 */
public class Terremoto {
    String lugar,magnitud,hora;

    public Terremoto() {

    }

    public String getHora() {

        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
    }
}
