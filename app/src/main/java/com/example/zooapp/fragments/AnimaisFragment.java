package com.example.zooapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zooapp.R;
import com.example.zooapp.adapter.AnimaisAdapter;
import com.example.zooapp.models.Animais;

import java.util.ArrayList;
import java.util.List;

public class AnimaisFragment extends Fragment {

    private RecyclerView recyclerAnimais;
    private List<Animais> animais = new ArrayList<>();
    public AnimaisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animais, container, false);

        recyclerAnimais = view.findViewById(R.id.recyclerAnimais);
        //layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerAnimais.setLayoutManager(layoutManager);
        //define adapter
        this.PrepararAnimais();
        AnimaisAdapter animaisAdapter = new AnimaisAdapter(animais,getActivity());
        recyclerAnimais.setAdapter(animaisAdapter);
        return view;
    }

    public  void PrepararAnimais(){
        Animais a = new Animais("TUCANO-DE-BICO-VERDE","Ramphastos dicolorus","+-40 anos","Áreas florestadas, desde o litoral até as zonas montanhosas",R.drawable.animal_tucan,"http://www.zoologico.com.br/animais/nativos-da-mata/tucano-de-bico-verde/");
        this.animais.add(a);
        a = new Animais("CERVO-NOBRE","Cervus elaphus","+-22 anos","Florestas, campos e montanhas",R.drawable.animalcervo,"http://www.zoologico.com.br/animais/mamiferos/cervo-nobre/");
        this.animais.add(a);
        a = new Animais("LAGARTO-RABO-DE-MACACO","Corucia zebrata","+20 anos","Florestas tropicais",R.drawable.animallagarto,"http://www.zoologico.com.br/animais/repteis/lagarto-rabo-de-macaco/");
        this.animais.add(a);
        a = new Animais("BICHO-PAU","Phibalosoma phyllinum","18 a 30 meses","Florestas tropicais",R.drawable.animalbichopau,"http://www.zoologico.com.br/animais/invertebrados/bicho-pau/");
        this.animais.add(a);
        a = new Animais("SAPO-CURURU","Bufo marinus","10 a 15 anos","Áreas abertas do Chaco, Cerrado e Mata Atlântica",R.drawable.animalsapo,"http://www.zoologico.com.br/animais/anfibios/sapo-cururu/");
        this.animais.add(a);
        a = new Animais("TATU-GALINHA","Dasypus novemcinctus","20 anos","Muito adaptável, ocorre em diferentes habitats",R.drawable.animaltatu,"http://www.zoologico.com.br/animais/anfibios/sapo-cururu/");
        this.animais.add(a);
    }



}