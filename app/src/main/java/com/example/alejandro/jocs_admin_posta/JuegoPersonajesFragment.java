package com.example.alejandro.jocs_admin_posta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Personaje;

import java.util.List;

/**
 * Created by Alejandro on 2/6/2016.
 */

public class JuegoPersonajesFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_JUEGO = "juego_id";
    private PersonajeAdapter personajeAdapter;
    private RecyclerView recList;
    private long juego_id;
    private GridLayoutManager glm;

    public JuegoPersonajesFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JuegoPersonajesFragment newInstance(long juego_id) {
        JuegoPersonajesFragment fragment = new JuegoPersonajesFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_JUEGO, juego_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_juego_personajes, container, false);
        juego_id = getArguments().getLong(ARG_JUEGO);


        /*FLOATING ACTION BUTTON*/
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_agregar_personaje);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, PersonajeAgregarActivity.class);
                intent.putExtra(ARG_JUEGO, juego_id);
                context.startActivity(intent);
            }
        });

        /*CARDS*/
        recList = (RecyclerView) v.findViewById(R.id.personajes_cards);
        recList.setHasFixedSize(true);
        //nro de columnas
        glm = new GridLayoutManager(v.getContext(), 2);
        recList.setLayoutManager(glm);

        List<Personaje> personajes = DatabaseManager.getInstance().getAllPersonajesFromJuego(juego_id);
        personajeAdapter = new PersonajeAdapter(personajes);
        recList.setAdapter(personajeAdapter);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("JUEGO PERSONAJES FRAG", "ON RESUMEEEE");
        List<Personaje> personajes = DatabaseManager.getInstance().getAllPersonajesFromJuego(juego_id);
        personajeAdapter = new PersonajeAdapter(personajes);
        recList.setAdapter(personajeAdapter);

    }
}