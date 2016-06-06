package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Mision;

public class MisionAgregarActivity extends AppCompatActivity {
    private long juego_id;
    private EditText mTitulo;
    private EditText mDescripcion;
    private EditText mPuntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mision_editar);

        juego_id = getIntent().getLongExtra(JuegoMisionesFragment.ARG_JUEGO, -1);


        mTitulo = (EditText) findViewById(R.id.editTitulo);
        mDescripcion = (EditText) findViewById(R.id.editDescripcion);
        mPuntuacion = (EditText) findViewById(R.id.editPuntuacion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_mision);
        toolbar.setTitle("Agregar misión");
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }

        mTitulo.setHint("Título");
        mPuntuacion.setHint("Puntuación");
        mDescripcion.setHint("Descripción");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego_agregar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_descartar) {
            onBackPressed();
        }

        /*GUARDAR LA NUEVO MISION Y VOLVER*/
        if (id == android.R.id.home) {
            Log.e("MISION AGREGAR ACTIVITY", "AGREGANDO MISION");
            DatabaseManager.getInstance().agregarMision(new Mision(mTitulo.getText().toString(),
                    mDescripcion.getText().toString(), mPuntuacion.getText().toString()), juego_id);
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}