package com.example.zooapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zooapp.R;
import com.example.zooapp.models.YoutubeConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;

public class PrincipalFragment extends Fragment implements  YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {
    private static final String TAG = "PrincipalFragment";
    YouTubePlayerView youTubePlayerView;
    private Button btnPesquisar;
    private EditText txtPesquisa;
    public static final String API_KEY="AIzaSyB6g_azFvF92lx3VFU9VhIOwuPMPnLQjWs";
    public static final String VIDEO_ID="odD9q6SScZ0";

    public PrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        Log.d(TAG,"OnCreate: Starting");
        txtPesquisa=view.findViewById(R.id.txtPesquisa);
        btnPesquisar=view.findViewById(R.id.btnPesquisar);
        youTubePlayerView= view.findViewById(R.id.youtubePlay);
        youTubePlayerView.initialize(API_KEY,this);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String a = String.valueOf(txtPesquisa.getText());
                a.replace(" ","+");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+a));
                getActivity().startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if(!wasRestored){
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(getActivity(),1).show();
        }else{
            Toast.makeText(getActivity(), "Erro ao iniciar o youtube"+youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    protected void onFragmentResult(int requestCode,int resultado, Intent data){
        if(resultado==1){

        getYoutubePlayerProvider().initialize(API_KEY,this);
        }
    }

    protected YouTubePlayer.Provider getYoutubePlayerProvider(){

        return youTubePlayerView;
    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}
