package com.example.alejandro.jocs_admin_posta;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Mision;

public class MisionEditarActivity extends AppCompatActivity {

    private long mision_id;
    private EditText mTitulo;
    private EditText mDescripcion;
    private EditText mPuntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mision_editar);

        mision_id = getIntent().getLongExtra(MisionAdapter.MisionViewHolder.EXTRA_MISION, 0);
        final Mision mision = DatabaseManager.getInstance().getMision(mision_id);

        mTitulo = (EditText) findViewById(R.id.editTitulo);
        mPuntuacion = (EditText) findViewById(R.id.editPuntuacion);
        mDescripcion = (EditText) findViewById(R.id.editDescripcion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_mision);
        toolbar.setTitle("Editar " + mision.getTitulo());
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }

        mTitulo.setText(mision.getTitulo());
        mPuntuacion.setText(mision.getPuntuacion());
        mDescripcion.setText(mision.getDescripcion());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_mision, menu);
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

        if (id == R.id.action_eliminar) {
            dialogSeguro(this);
            return true;
        }

        /*GUARDAR EL NUEVO OBJETO Y VOLVER*/
        if (id == android.R.id.home) {
            Log.e("MISION EDITAR ACTIVITY", "APRETE EL UPDATEEE " + " APRETE EL UPDATEEE");
            DatabaseManager.getInstance().updateMision(mision_id, mTitulo.getText().toString(),
                    mDescripcion.getText().toString(), mPuntuacion.getText().toString());
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialogSeguro(final Activity act) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.action_eliminar_mision)
                .setMessage(R.string.mensaje_eliminar_mision)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance().eliminarMision(mision_id);
                        onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
