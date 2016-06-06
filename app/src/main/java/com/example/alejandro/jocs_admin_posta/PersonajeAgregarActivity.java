package com.example.alejandro.jocs_admin_posta;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Personaje;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class PersonajeAgregarActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final String ARG_JUEGO = "juego_id";

    private Uri mCapturedImageURI;
    private String picturePath = "";
    private ImageButton mImagen;
    private Bitmap nueva = null;

    private int ano_pub = -1;
    private long juego_id;
    private EditText mNombre;
    private EditText mRaza;
    private EditText mNivel;
    private Personaje personaje_nuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje_editar);

        juego_id = (long) getIntent().getLongExtra(ARG_JUEGO, -1);

        personaje_nuevo = new Personaje();

        mNombre = (EditText) findViewById(R.id.editNombre);
        mRaza = (EditText) findViewById(R.id.editRaza);
        mNivel = (EditText) findViewById(R.id.editNivel);

        mImagen = (ImageButton) findViewById(R.id.imagenPersonaje);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_editar_personaje);
        toolbar.setTitle("Agregar Personaje ");
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }

        mNombre.setHint("Nombre");
        mRaza.setHint("Raza");
        mNivel.setHint("Nivel");
        mImagen.setImageResource(R.drawable.ic_menu_gallery);

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

        /*GUARDAR EL NUEVO OBJETO Y VOLVER*/
        if (id == android.R.id.home) {
            byte[] bitmapdata;
            if (nueva != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (nueva.compress(Bitmap.CompressFormat.PNG, 100, stream))
                    Log.e("DESDE EL EDIT PERSONAJE", "NO SE PUDO SACAR LOS BITS!!!!");
                bitmapdata = stream.toByteArray();
            } else {
                Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_juego);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (bMap.compress(Bitmap.CompressFormat.PNG, 50, stream))
                    Log.e("BITMAPPPP", "NO SE PUDO SACAR LOS BITS!!!!");
                bitmapdata = stream.toByteArray();
            }

            Log.e("AGREGAR PERSONAJE", "-----------APRETE EL AGREGAR");
            DatabaseManager.getInstance().agregarPersonaje(setearPersonaje(personaje_nuevo, mNombre.getText().toString(), mRaza.getText().toString(),
                    mNivel.getText().toString(), bitmapdata), juego_id);

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    public void btnAddOnClick(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnChoosePath)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeGallery();
                        dialog.dismiss();
                    }
                });
        dialog.findViewById(R.id.btnTakePhoto)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeTakePhoto();
                        dialog.dismiss();
                    }
                });

        // show dialog on screen
        dialog.show();
    }

    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
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

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode,
                                              Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE &&
                        resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver()
                            .query(selectedImage, filePathColumn, null, null,
                                    null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    mImagen.setImageDrawable(Drawable.createFromPath(picturePath));
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE &&
                        resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor =
                            managedQuery(mCapturedImageURI, projection, null,
                                    null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    picturePath = cursor.getString(column_index_data);
                    cursor.close();
                    mImagen.setImageDrawable(Drawable.createFromPath(picturePath));
                }
        }
    }*/

    Personaje setearPersonaje(Personaje personaje, String nombre, String raza, String nivel, byte[] bitmapdata) {
        personaje.setNombre(nombre);
        personaje.setRaza(raza);
        personaje.setNivel(nivel);
        Log.e("AGREGAR PERSONAJE", "AGREGANDO PERSONAJE CON NOMBRE: " + nombre);
        personaje.setFotoId(R.drawable.ic_android);
//        personaje.setLaFoto(bitmapdata);
        return personaje;
    }
}
