package com.example.alejandro.jocs_admin_posta;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Personaje;

import java.io.FileNotFoundException;

public class PersonajeEditarActivity extends AppCompatActivity {

    public static final String EXTRA_PERSONAJE = "personaje";
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private Uri mCapturedImageURI;
    private ImageButton mImagen;
    private long personaje_id;
    private EditText mNombre;
    private EditText mRaza;
    private EditText mNivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje_editar);

        personaje_id = getIntent().getLongExtra(EXTRA_PERSONAJE, 0);
        final Personaje personaje = DatabaseManager.getInstance().getPersonaje(personaje_id);

        mNombre = (EditText) findViewById(R.id.editNombre);
        mRaza = (EditText) findViewById(R.id.editRaza);
        mNivel = (EditText) findViewById(R.id.editNivel);

        mImagen = (ImageButton) findViewById(R.id.imagenPersonaje);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_personaje);
        toolbar.setTitle("Editar " + personaje.getNombre());
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }


        mNombre.setText(personaje.getNombre());
        mRaza.setText(personaje.getRaza());
        mNivel.setText(personaje.getNivel());
        mImagen.setImageResource(personaje.getFotoId());


        mImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                Uri uri = Uri.parse("content://media/external/images/media");
                String provider = "com.android.providers.media.MediaProvider";

                // grant all three uri permissions!
                grantUriPermission(provider, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                grantUriPermission(provider, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                grantUriPermission(provider, uri, Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

//                btnAddOnClick(v);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, 0);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_personaje, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_descartar_cambios) {
            onBackPressed();
            return true;
        }

        if (id == R.id.action_eliminar) {
            dialogSeguro();
            return true;
        }

        /*GUARDAR EL NUEVO PERSONAJE EDITADO Y VOLVER*/
        if (id == android.R.id.home) {
            byte[] bitmapdata = new byte[]{0, 1, 0};

            Log.e("JUEGO EDITAR ACTIVITY", "APRETE EL UPDATEEE " + " APRETE EL UPDATEEE");
            DatabaseManager.getInstance().updatePersonaje(personaje_id, mNombre.getText().toString(), mRaza.getText().toString(),
                    mNivel.getText().toString(), bitmapdata);
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) return;

        ParcelFileDescriptor fd;
        try {
            fd = getContentResolver().openFileDescriptor(data.getData(), "r");
            System.out.println(data.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Bitmap nueva = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor());

        mImagen.setImageBitmap(nueva);
    }

    public void dialogSeguro() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.action_eliminar_personaje)
                .setMessage(R.string.mensaje_eliminar_personaje)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance().eliminarPersonaje(personaje_id);
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
