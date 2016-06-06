package com.example.alejandro.jocs_admin_posta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class JuegoAgregarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    String estado_seleccionado = "No iniciado";
    private Uri mCapturedImageURI;
    private String picturePath = "";
    private ImageButton mImagen;
    private Bitmap nueva = null;

    private int ano_pub = -1;
    private Juego juego_nuevo;
    private EditText mTitulo;
    private EditText mPlataforma;
    private EditText mEstudio;
    private EditText mAnoPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_editar);

        juego_nuevo = new Juego();

        mTitulo = (EditText) findViewById(R.id.editTitulo);
        mPlataforma = (EditText) findViewById(R.id.editPlataforma);
        mEstudio = (EditText) findViewById(R.id.editEstudio);
        mAnoPublicacion = (EditText) findViewById(R.id.editAnoPublicacion);

        mImagen = (ImageButton) findViewById(R.id.imagenJuego);
        final Spinner mCurso = (Spinner) findViewById(R.id.spinnerCurso);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_juego);
        toolbar.setTitle("Agregar Juego ");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }

        mTitulo.setHint("Nombre");
        mPlataforma.setHint("Plataforma");
        mEstudio.setHint("Estudio");
        mAnoPublicacion.setHint("Año publicacion");
        mImagen.setImageResource(R.drawable.ic_menu_gallery);

        iniciarSpinnerCurso(juego_nuevo, mCurso);

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

    private void iniciarSpinnerCurso(Juego juego, Spinner mCurso) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estados_juego, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mCurso.setAdapter(adapter);
        if (juego.getCurso() == "Terminado")
            mCurso.setSelection(2);
        else if (juego.getCurso() == "Iniciado")
            mCurso.setSelection(1);
        else
            mCurso.setSelection(0);

        mCurso.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        estado_seleccionado = (String) parent.getItemAtPosition(position);
        Log.e("AGREGAR JUEGO", "ESTADO SELECCIONADO: " + estado_seleccionado);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
            return true;
        }

        /*GUARDAR EL NUEVO JUEGO EDITADO Y VOLVER*/
        if (id == android.R.id.home) {
            byte[] bitmapdata;
            if (nueva != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (nueva.compress(Bitmap.CompressFormat.PNG, 100, stream))
                    Log.e("DESDE EL EDITAR JUEGO", "NO SE PUDO SACAR LOS BITS!!!!");
                bitmapdata = stream.toByteArray();
            } else {
                Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_juego);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (bMap.compress(Bitmap.CompressFormat.PNG, 50, stream))
                    Log.e("BITMAPPPP", "NO SE PUDO SACAR LOS BITS!!!!");
                bitmapdata = stream.toByteArray();
            }

            Log.e("AGREGAR JUEGO", "-----------APRETE EL AGREGAR");
            DatabaseManager.getInstance().agregarJuego(setearJuego(juego_nuevo, mTitulo.getText().toString(), mPlataforma.getText().toString(),
                    mEstudio.getText().toString(), mAnoPublicacion.getText().toString(), estado_seleccionado, bitmapdata));

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


    Juego setearJuego(Juego juego, String nombre, String plataforma, String estudio, String ano_pub, String estado, byte[] bitmapdata) {
        juego.setNombre(nombre);
        juego.setPlataforma(plataforma);
        juego.setEstudio(estudio);
        juego.setAno_publicacion(ano_pub);
        juego.setCurso(estado);
        Log.e("AGREGAR JUEGO", "AGREGANDO JUEGO CON ESTADO: " + estado);
        juego.setFotoId(R.drawable.ic_juego);
        juego.setLaFoto(bitmapdata);
        return juego;
    }
}
