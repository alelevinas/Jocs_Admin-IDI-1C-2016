package com.example.alejandro.jocs_admin_posta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.db_utils.JocsAdminDbHelper;
import com.example.alejandro.jocs_admin_posta.model.Juego;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);


        /*TOOLBAR*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FLOATING ACTION BUTTON*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, JuegoAgregarActivity.class);
                context.startActivity(intent);
            }
        });


        /*CARDS*/
        recList = (RecyclerView) findViewById(R.id.juegos_cards);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        DatabaseManager.initializeInstance(new JocsAdminDbHelper(this.getApplicationContext()), this.getApplicationContext());

        DatabaseManager.getInstance().restart();

        List<Juego> juegos = DatabaseManager.getInstance().getAllJuegos();
        JuegoAdapter ca = new JuegoAdapter(juegos);
        recList.setAdapter(ca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_ayuda) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.action_ayuda)
                    .setMessage(R.string.mensaje_ayuda)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        if (id == R.id.action_todos) {
            List<Juego> juegos = DatabaseManager.getInstance().getAllJuegos();
            JuegoAdapter ca = new JuegoAdapter(juegos);
            recList.setAdapter(ca);
        }
        if (id == R.id.action_no_iniciados) {
            List<Juego> juegos = DatabaseManager.getInstance().getAllJuegosNoIniciados();
            JuegoAdapter ca = new JuegoAdapter(juegos);
            recList.setAdapter(ca);
        }
        if (id == R.id.action_iniciados) {
            List<Juego> juegos = DatabaseManager.getInstance().getAllJuegosIniciados();
            JuegoAdapter ca = new JuegoAdapter(juegos);
            recList.setAdapter(ca);
        }
        if (id == R.id.action_terminados) {
            List<Juego> juegos = DatabaseManager.getInstance().getAllJuegosTerminados();
            JuegoAdapter ca = new JuegoAdapter(juegos);
            recList.setAdapter(ca);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Juego> juegos = DatabaseManager.getInstance().getAllJuegos();
        JuegoAdapter ca = new JuegoAdapter(juegos);
        recList.setAdapter(ca);
    }
}
