package api;

import model.PaqueteUsuario;
import model.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("add_usuario.php")
    Call<String> insertaUsuario(@Query("nombre") String nombre, @Query("pass") String pass, @Query("email") String email);

    @GET("get_usuario.php")
    Call<PaqueteUsuario> getUsuario(@Query("email") String email, @Query("pass") String pass);

}
