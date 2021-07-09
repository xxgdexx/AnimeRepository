package com.cibertec.proyectoanime.AnimeApi;

import com.cibertec.proyectoanime.Models.AnimeRespuesta;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AnimeApiService {

    @GET("anime")
    Call<AnimeRespuesta> obtenerListaAnime(@Query("limit") int limit,@Query("offset") int offset );

    @GET("anime/{id}")
    Call<Optional<AnimeRespuesta>> buscaAnime(int id);
}
