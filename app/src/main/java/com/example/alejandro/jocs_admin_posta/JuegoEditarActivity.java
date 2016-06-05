package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Juego;

import java.util.ArrayList;
import java.util.List;

public class JuegoEditarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String ARG_JUEGO_ID = "juego_id";
    String estado_seleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_editar);

        long juego_id = getIntent().getLongExtra(ARG_JUEGO_ID, 0);
        Juego juego = DatabaseManager.getInstance().getJuego(juego_id);

        final EditText mTitulo = (EditText) findViewById(R.id.editTitulo);
        final EditText mPlataforma = (EditText) findViewById(R.id.editPlataforma);
        final EditText mEstudio = (EditText) findViewById(R.id.editEstudio);
        final EditText mAnoPublicacion = (EditText) findViewById(R.id.editAnoPublicacion);

        final ImageButton mImagen = (ImageButton) findViewById(R.id.imagenJuego);
        final Spinner mEstado = (Spinner) findViewById(R.id.spinnerEstado);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Editar " + juego.getNombre());
        toolbar.setNavigationIcon(R.drawable.ic_menu_share);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager.getInstance().updateJuego(mTitulo.getText().toString(), mPlataforma.getText().toString(),
                        mEstudio.getText().toString(), mAnoPublicacion.getText().toString(), estado_seleccionado, mImagen.getDrawable());
            }
        });
        setSupportActionBar(toolbar);


        mTitulo.setText(juego.getNombre());
        mPlataforma.setText(juego.getPlataforma());
        mEstudio.setText(juego.getEstudio());
        mAnoPublicacion.setText(juego.getAno_publicacion());
        mImagen.setImageResource(juego.getFotoId());

        List<String> l = new ArrayList<>();
        l.add("No iniciado");
        l.add("Iniciado");
        l.add("Terminado");


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estados_juego, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mEstado.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        estado_seleccionado = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
