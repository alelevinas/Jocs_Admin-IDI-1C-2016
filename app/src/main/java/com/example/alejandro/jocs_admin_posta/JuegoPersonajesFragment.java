package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private static final String ARG_JUEGO = "juego";

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
        long juego_id = (long) getArguments().getLong(ARG_JUEGO);


        /*CARDS*/
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.personajes_cards);
        recList.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(v.getContext(), 2); //nro de columnas
        recList.setLayoutManager(glm);


//        DatabaseManager.initializeInstance(new JocsAdminDbHelper(v.getContext()));

//        List<Personaje> personajes = DatabaseManager.getInstance().getAllPersonajes();
        List<Personaje> personajes = DatabaseManager.getInstance().getAllPersonajesFromJuego(juego_id);
        PersonajeAdapter pa = new PersonajeAdapter(personajes);
        recList.setAdapter(pa);


        return v;
    }
}