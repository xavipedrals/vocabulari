package com.example.xavivaio.vocabulari;

/**
 * Created by xavivaio on 12/05/2015.
 */
public class Paraula {
    String paraula;
    int numTrad;

    public Paraula(){
    }

    public Paraula(String paraula, int numTrad){
        this.paraula = paraula;
        this. numTrad = numTrad;
    }

    public String getParaula() {
        return paraula;
    }

    public void setParaula(String paraula) {
        this.paraula = paraula;
    }

    public int getNumTrad() {
        return numTrad;
    }

    public void setNumTrad(int numTrad) {
        this.numTrad = numTrad;
    }
}
