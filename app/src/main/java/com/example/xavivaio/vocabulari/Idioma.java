package com.example.xavivaio.vocabulari;

import java.util.ArrayList;

/**
 * Created by xavivaio on 12/05/2015.
 */
public class Idioma {
    private String name;
    private int numPar;
    private int numTrad;
    private ArrayList<Paraula> paraules;

    public Idioma() {
    }

    public Idioma(String name, int numPar, int numTrad) {
        this.name = name;
        this.numPar = numPar;
        this.numTrad = numTrad;
    }

    public Idioma(String name, int numPar, int numTrad, ArrayList<Paraula> paraules){
        this.name = name;
        this.numPar = numPar;
        this.numTrad = numTrad;
        this.paraules = paraules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumPar() {
        return numPar;
    }

    public void setNumPar(int numPar) {
        this.numPar = numPar;
    }

    public ArrayList<Paraula> getParaules() {
        return paraules;
    }

    public void setParaules(ArrayList<Paraula> paraules) {
        this.paraules = paraules;
    }

    public int getNumTrad() {
        return numTrad;
    }

    public void setNumTrad(int numTrad) {
        this.numTrad = numTrad;
    }
}
