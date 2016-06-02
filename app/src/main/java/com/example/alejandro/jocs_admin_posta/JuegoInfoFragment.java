package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_juego_info, container, false);
        Juego juego = (Juego) getArguments().getSerializable(ARG_JUEGO);

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

        return v;
    }
}