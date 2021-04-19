package api;

import java.util.List;

import model.Pagina;
import model.PaqueteUsuario;
import model.Universo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("add_usuario.php")
    Call<String> insertaUsuario(@Query("nombre") String nombre, @Query("pass") String pass, @Query("email") String email);

    @GET("get_usuario.php")
    Call<PaqueteUsuario> getUsuario(@Query("email") String email, @Query("pass") String pass);

    @GET("cambiar_nombre.php")
    Call<String> cambiaNombre(@Query("id") String id, @Query("nombre") String nombre);

    @GET("cambiar_pass.php")
    Call<String> cambiaPass(@Query("id") String id, @Query("pass") String pass);

    @POST("add_universo.php")
    Call<String> insertaUniverso(@Body Universo universo);

    @GET("get_universos_usuario.php")
    Call<List<Universo>> getUniversoUsuario(@Query("usuario") String id);

    @GET("get_universo_id.php")
    Call<Universo> getUniversoId(@Query("id") String id);

    @GET("get_etiquetas_id.php")
    Call<List<String>> getEtiquetasId(@Query("id") String id);

    @POST("editar_universo.php")
    Call<String> editarUniverso(@Body Universo universo);

    @Multipart
    @POST("upload.php")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @POST("add_pagina.php")
    Call<String> insertaPagina(@Body Pagina pagina);

}
