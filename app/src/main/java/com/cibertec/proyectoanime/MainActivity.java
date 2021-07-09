package com.cibertec.proyectoanime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.cibertec.proyectoanime.AnimeApi.AnimeApiService;
import com.cibertec.proyectoanime.Models.Anime;
import com.cibertec.proyectoanime.Models.AnimeRespuesta;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="ANIME";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private ListaAnimeAdapter listaAnimeAdapter;

    private int offset;

    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaAnimeAdapter = new ListaAnimeAdapter(this);
        recyclerView.setAdapter(listaAnimeAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2 );
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView,int dx, int dy){
                super.onScrolled(recyclerView,dx,dy);
                if (dy>0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount= layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar){
                        if ((visibleItemCount+pastVisibleItems)>=totalItemCount){
                            Log.i(TAG,"Llegamos al final.");
                            aptoParaCargar=false;
                            offset+=20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }

        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://kitsu.io/api/edge/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset=0;
        obtenerDatos(offset);
    }

    private  void obtenerDatos(int offset){
        AnimeApiService service= retrofit.create(AnimeApiService.class);
       Call<AnimeRespuesta> animeRespuestaCall= service.obtenerListaAnime(6, 0);

       animeRespuestaCall.enqueue(new Callback<AnimeRespuesta>() {
           @Override
           public void onResponse(Call<AnimeRespuesta> call, Response<AnimeRespuesta> response) {
               aptoParaCargar=true;


               if (response.isSuccessful()){
                   AnimeRespuesta animeRespuesta= response.body();
                  ArrayList<Anime>listaAnime = animeRespuesta.getResults();


                listaAnimeAdapter.adicionarListaAnime(listaAnime);

               } else {
                   Log.e(TAG,"onResponse: "+ response.errorBody());
               }
           }

           @Override
           public void onFailure(Call<AnimeRespuesta> call, Throwable t) {
               aptoParaCargar=true;

               Log.e(TAG,"onFailure: "+ t.getMessage());
           }
       });
    }
}