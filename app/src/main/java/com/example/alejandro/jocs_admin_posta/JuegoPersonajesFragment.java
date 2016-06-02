package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejandro.jocs_admin_posta.model.Juego;

import java.io.Serializable;

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
    public static JuegoPersonajesFragment newInstance(Juego juego) {
        JuegoPersonajesFragment fragment = new JuegoPersonajesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_JUEGO, (Serializable) juego);
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
        Juego juego = (Juego) getArguments().getSerializable(ARG_JUEGO);


        return v;
    }
}