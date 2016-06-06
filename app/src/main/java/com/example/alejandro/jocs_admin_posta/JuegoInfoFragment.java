package com.example.alejandro.jocs_admin_posta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Juego;

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
    private long id_juego;
    private TextView vNombre;
    private TextView vEstudio;
    private TextView vPlataforma;
    private TextView vAno_publicacion;
    private TextView vCurso;
    private ImageView vFotoId;
    private View v;

    public JuegoInfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JuegoInfoFragment newInstance(long juego_id) {
        JuegoInfoFragment fragment = new JuegoInfoFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_JUEGO, juego_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        id_juego = (long) getArguments().getLong(ARG_JUEGO);
        juego = DatabaseManager.getInstance().getJuego(id_juego);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_juego_info, container, false);

        vNombre = (TextView) v.findViewById(R.id.titulo);
        vEstudio = (TextView) v.findViewById(R.id.txtEstudio);
        vPlataforma = (TextView) v.findViewById(R.id.txtPlataforma);
        vAno_publicacion = (TextView) v.findViewById(R.id.txtAnoPublicacion);
        vCurso = (TextView) v.findViewById(R.id.txtCurso);
        vFotoId = (ImageView) v.findViewById(R.id.juego_foto);
        refresh_view();


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.editar_juego_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, JuegoEditarActivity.class);
                intent.putExtra(JuegoEditarActivity.ARG_JUEGO_ID, juego.getId());

                context.startActivity(intent);
            }
        });


        return v;
    }

    private void refresh_view() {
        vNombre.setText(juego.getNombre());
        vPlataforma.setText(juego.getPlataforma());
        vEstudio.setText(juego.getEstudio());
        vAno_publicacion.setText(juego.getAno_publicacion());
        vCurso.setText(juego.getCurso());
        vFotoId.setImageResource(juego.getFotoId());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("JUEGO INFO FRAGMENT", "ON RESUMEEEE");
        juego = DatabaseManager.getInstance().getJuego(id_juego);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_juego_info);
        toolbar.setTitle(juego.getNombre());
        refresh_view();
    }
}