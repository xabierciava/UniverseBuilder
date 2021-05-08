package com.universes.universebuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import api.ApiInterface;
import api.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarFragment extends Fragment {

    TextView nombreCorrecto, passCorrecta;
    EditText nombreEditText, psw1EditText, psw2EditText;
    TextInputLayout layPsw;
    Button botonCambiarNombre, botonCambiarPass;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarFragment newInstance(String param1, String param2) {
        EditarFragment fragment = new EditarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View view = inflater.inflate(R.layout.fragment_editar, container, false);
        nombreEditText = (EditText) view.findViewById(R.id.textInputEditTextEditarNombre);
        psw1EditText = (EditText) view.findViewById(R.id.textInputEditTextEditarPass);
        psw2EditText = (EditText) view.findViewById(R.id.textInputEditTextEditarPass2);
        layPsw = (TextInputLayout) view.findViewById(R.id.outlinedTextFieldEditarPass2);
        botonCambiarNombre = (Button) view.findViewById(R.id.botonEditarPerfilNombre);
        botonCambiarPass = (Button) view.findViewById(R.id.botonEditarPerfilPass);
        nombreCorrecto = view.findViewById(R.id.nombreCorrecto);
        passCorrecta = view.findViewById(R.id.passCorrecta);
        nombreCorrecto.setVisibility(View.INVISIBLE);
        passCorrecta.setVisibility(View.INVISIBLE);

        nombreEditText.setText("");
        psw1EditText.setText("");
        psw2EditText.setText("");

        botonCambiarNombre.setOnClickListener(v -> cambiarNombre(nombreEditText.getText().toString()));

        botonCambiarPass.setOnClickListener(v -> cambiarPass(psw1EditText.getText().toString(), psw2EditText.getText().toString()));


        return view;
    }

    public void cambiarNombre(String strNombre) {
        if (!strNombre.equals("")) {
            SharedPreferences prefs = this.getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
            String id = prefs.getString("id", "");
            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<String> call = apiInterface.cambiaNombre(id, strNombre);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        System.out.println("Success");
                        if (response.body().equals("1")) {
                            System.out.println("Nombre Cambiado");
                            nombreCorrecto.setVisibility(View.VISIBLE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("nombre", strNombre);
                            editor.apply();
                        } else {
                            System.out.println("Ha habido algún error");
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("tag", t.getMessage());
                }
            });
        }
    }


    public void cambiarPass(String strPsw1, String strPsw2) {

        if (checkPsw(strPsw1, strPsw2)) {
            layPsw.setError(null);
            if (!strPsw1.equals("")) {
                SharedPreferences prefs = this.getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
                String id = prefs.getString("id", "");
                ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
                Call<String> call = apiInterface.cambiaPass(id, strPsw1);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            System.out.println("Success");
                            if (response.body().equals("1")) {
                                System.out.println("Contraseña cambiada");
                                passCorrecta.setVisibility(View.VISIBLE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("pass", strPsw1);
                                editor.apply();

                            } else {
                                System.out.println("Ha habido algún error");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("tag", t.getMessage());
                    }
                });
            }
        } else {
            layPsw.setError("Las contraseñas no coinciden");
        }
    }

    public Boolean checkPsw(String psw1Str, String psw2Str) {
        if (psw1Str.equals(psw2Str)) {
            return true;
        } else {
            return false;
        }
    }

}