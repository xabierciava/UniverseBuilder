package api;

import java.util.Date;
import java.util.List;
import model.usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("email")
    Call<List<usuario>> getUsuarios();

    @GET("existe_usuario.php")
    Call<List<usuario>> getUsuario(@Query("codigo") String codigo);

    @GET("get_usuario.php")
    Call<List<usuario>> getUsuarioDetail(@Query("codigo") String codigo);

  /*
    @GET("get_dispositivo.php")
    Call <dispositivo> getDispo (@Query("codigo") String codigo);

    @GET("get_dispositivos.php")
    Call<List<dispositivo>> getDispositivo(@Query("codigo") String codigo);

    @GET("get_curas.php")
    Call<List<cura>> getCuras(@Query("codigo") String codigo);

    @GET("get_all_curas.php")
    Call<List<cura>> getAllCuras(@Query("codigo") String codigo);

    @GET("get_insercion.php")
    Call<List<insercion>> getInsercion(@Query("codigo") String codigo);

    @GET("get_recordatorios.php")
    Call<List<recordatorio>> getRecordatorios(@Query("codigo") String codigo);

    @GET("inserta_fichaje.php")
    Call<Integer> insertaFichaje(@Query("email") String email, @Query("tipo") Integer tipo, @Query("negocio") Integer codigo, @Query("fecha") Date fecha, @Query("latitud") Float latitud, @Query("longitud") Float longitud);

    @GET("inserta_cura.php")
    Call<String> insertaCura(@Query("fecha_cura") String fecha, @Query("incidencias") String incidencias, @Query("longitud_insercion") float insercion, @Query("procedimiento") String procedimiento, @Query("antiseptico") String antiseptico, @Query("complicaciones") String complicaciones, @Query("persona_realiza") String persona, @Query("lugar_realiza") String lugar, @Query("dispositivo") String dispositivo, @Query("usuario") String usuario);

    @GET("inserta_recordatorio.php")
    Call<String> insertaRecordatorio(@Query("id_usuario") String id_usuario, @Query("dispositivo") String dispositivo);

    @GET("get_longitudes.php")
    Call<String[]>getLongitudes(@Query("id_usuario") String id_usuario, @Query("dispositivo") String dispositivo);
    */
}
