package com.example.alejandro.jocs_admin_posta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejandro.jocs_admin_posta.model.Juego;

import java.io.Serializable;

/**
 * Created by Alejandro on 2/6/2016.
 */
public class JuegoInfoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_JUEGO = "juego";
    private Juego juego;

    public JuegoInfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JuegoInfoFragment newInstance(Juego juego) {
        JuegoInfoFragment fragment = new JuegoInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_JUEGO, (Serializable) juego);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        juego = (Juego) getArguments().getSerializable(ARG_JUEGO);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_juego_info, menu);
        menu.add("Otra coas de INFOOO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_juego_info, container, false);

        TextView vNombre = (TextView) v.findViewById(R.id.titulo);
        TextView vEstudio = (TextView) v.findViewById(R.id.txtEstudio);
        TextView vPlataforma = (TextView) v.findViewById(R.id.txtPlataforma);
        TextView vAno_publicacion = (TextView) v.findViewById(R.id.txtAnoPublicacion);
        TextView vCurso = (TextView) v.findViewById(R.id.txtCurso);
        ImageView vFotoId = (ImageView) v.findViewById(R.id.juego_foto);

        vNombre.setText(juego.getNombre());
        vPlataforma.setText(juego.getPlataforma());
        vEstudio.setText(juego.getEstudio());
        vAno_publicacion.setText(juego.getAno_publicacion());
        vCurso.setText(juego.getCurso());
        vFotoId.setImageResource(juego.getFotoId());


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.editar_juego_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Context context = view.getContext();
                Intent intent = new Intent(context, JuegoEditarActivity.class);
                intent.putExtra(JuegoEditarActivityFragment.ARG_JUEGO_ID, juego.getId());

                context.startActivity(intent);
            }
        });


        return v;
    }
}