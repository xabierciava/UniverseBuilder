package api;

import java.util.List;

import model.PaqueteUsuario;
import model.Universo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
}
