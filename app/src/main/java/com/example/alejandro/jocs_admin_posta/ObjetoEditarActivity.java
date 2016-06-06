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
import com.example.alejandro.jocs_admin_posta.model.Objeto;

public class ObjetoEditarActivity extends AppCompatActivity {

    private long objeto_id;
    private EditText mNombre;
    private EditText mNivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_editar);

        objeto_id = getIntent().getLongExtra(ObjetoAdapter.ObjetoViewHolder.EXTRA_OBJETO, 0);
        final Objeto objeto = DatabaseManager.getInstance().getObjeto(objeto_id);

        mNombre = (EditText) findViewById(R.id.editNombre);
        mNivel = (EditText) findViewById(R.id.editNivel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_objeto);
        toolbar.setTitle("Editar " + objeto.getNombre());
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }

        mNombre.setText(objeto.getNombre());
        mNivel.setText(objeto.getNivel());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_descartar_cambios) {
            onBackPressed();
        }
        /*GUARDAR EL NUEVO OBJETO Y VOLVER*/
        if (id == android.R.id.home) {
            Log.e("OBJETO EDITAR ACTIVITY", "APRETE EL UPDATEEE " + " APRETE EL UPDATEEE");
            DatabaseManager.getInstance().updateObjeto(objeto_id, mNombre.getText().toString(),
                    mNivel.getText().toString());
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
