package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Objeto;

public class ObjetoAgregarActivity extends AppCompatActivity {
    private long juego_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_editar);

        juego_id = (long) getIntent().getLongExtra(JuegoObjetosFragment.ARG_JUEGO, -1);

        final Objeto objeto = new Objeto();

        final EditText mNombre = (EditText) findViewById(R.id.editNombre);
        final EditText mNivel = (EditText) findViewById(R.id.editNivel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_objeto);
        toolbar.setTitle("Agregar objeto");
        toolbar.setNavigationIcon(R.drawable.ic_done_black_24dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("OBJETO EDITAR ACTIVITY", "APRETE EL UPDATEEE " + " APRETE EL UPDATEEE");
                DatabaseManager.getInstance().agregarObjeto(new Objeto(mNombre.getText().toString(),
                        mNivel.getText().toString()), juego_id);
                onBackPressed();
            }
        });
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("OBJETO EDITAR ACTIVITY", "APRETE EL UPDATEEE " + " APRETE EL UPDATEEE");
                DatabaseManager.getInstance().agregarObjeto(new Objeto(mNombre.getText().toString(),
                        mNivel.getText().toString()),juego_id);
                onBackPressed();
            }
        });*/
        setSupportActionBar(toolbar);

        mNombre.setHint("Nombre");
        mNivel.setHint("Nivel");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego_agregar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_descartar) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
