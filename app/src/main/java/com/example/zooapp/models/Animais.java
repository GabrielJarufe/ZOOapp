package com.example.zooapp.models;

import android.widget.ImageView;
import android.widget.TextView;

public class Animais {

    private String nome;
    private String nomeci;
    private String vida;
    private String habitat;
    private String link;
    private int imageAnimal;

    public Animais() {
    }

    public Animais(String nome, String nomeci, String vida, String habitat, int imageAnimal,String link) {
        this.nome = nome;
        this.nomeci = nomeci;
        this.vida = vida;
        this.habitat = habitat;
        this.imageAnimal = imageAnimal;
        this.link = link;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeci() {
        return nomeci;
    }

    public void setNomeci(String nomeci) {
        this.nomeci = nomeci;
    }

    public String getVida() {
        return vida;
    }

    public void setVida(String vida) {
        this.vida = vida;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public int getImageAnimal() {
        return imageAnimal;
    }

    public void setImageAnimal(int imageAnimal) {
        this.imageAnimal = imageAnimal;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
