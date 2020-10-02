package com.example.zooapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zooapp.R;
import com.example.zooapp.models.Animais;

import java.util.List;

public class AnimaisAdapter extends RecyclerView.Adapter<AnimaisAdapter.MyViewHolder> {


    private  List<Animais> animais;
    private Context context;


    public AnimaisAdapter(List<Animais> listaAnimais, Context context) {
        this.animais = listaAnimais;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animais_detalhes,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Animais animal = animais.get(position);

        holder.nome.setText(animal.getNome());
        holder.nomeci.setText(animal.getNomeci());
        holder.vida.setText(animal.getVida());
        holder.habitat.setText(animal.getHabitat());
        holder.imageAnimal.setImageResource(animal.getImageAnimal());

        holder.btnSaber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(animal.getLink()));
                    context.startActivity(intent);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWhatsapp("Desbubra mais sobre o/a "+animal.getNome()+" neste link! \n"+animal.getLink());
            }
        });



    }
    private void sendWhatsapp(String message){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(sendIntent);
        }
    }
    @Override
    public int getItemCount() {
        return animais.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        private Button btnSaber;
        private Button btnShare;

        private TextView nome;
        private TextView nomeci;
        private TextView vida;
        private TextView habitat;
        private ImageView imageAnimal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSaber = itemView.findViewById(R.id.btnSaberMais);
            btnShare = itemView.findViewById(R.id.btnShareAnimal);
            nome = itemView.findViewById(R.id.nome);
            nomeci = itemView.findViewById(R.id.nomeci);
            vida = itemView.findViewById(R.id.vida);
            habitat = itemView.findViewById(R.id.habitat);
            imageAnimal = itemView.findViewById(R.id.imageAnimal);




        }


        }
    }



