package com.genesisgrupoempresarial.confirmacionregistro;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.txtCodigo)
    EditText txtCodigo;

    private CoordinatorLayout coordinatorLayout;

    TransactionGenesis transactionGenesis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }

    @OnClick(R.id.btnCodigoCofirmacion)
    public void onClick() {

        ocultarTeclado();

        HashMap<String, String> parameters = new HashMap<String, String>();

        if (!txtCodigo.getText().toString().equals("")) {

            parameters.put("codigo", txtCodigo.getText().toString());

            transactionGenesis = new TransactionGenesis(this);
            transactionGenesis.getDataTransactionGenesis("data", parameters, new TransactionGenesis.VolleyCallback() {
                @Override
                public void onSuccess(JsonObject data) {
                    try {

                        Log.i("resultado", data.get("verificacion").getAsString());
                        Log.i("encontrado", data.get("encontrado").getAsString());

                        if (data.get("verificacion").getAsString().equals("OK")) {
                            if (data.get("encontrado").getAsInt() > 0) {
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "El codigo ha sido verificado, codigo existe.", Snackbar.LENGTH_LONG);

                                snackbar.show();
                            } else {
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "El codigo ha sido verificado, codigo No existe.", Snackbar.LENGTH_LONG);

                                snackbar.show();
                            }
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, "El codigo no ha podido verificarse.", Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }


                        //Llenado de componentes encabezado de la activity sucursal
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Error Genesis", e.toString());
                    }

                }
            });
        }
    }
}
