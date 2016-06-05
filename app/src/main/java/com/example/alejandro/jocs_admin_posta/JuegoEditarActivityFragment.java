package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Juego;

/**
 * A placeholder fragment containing a simple view.
 */
public class JuegoEditarActivityFragment extends Fragment {

    public static final String ARG_JUEGO_ID = "juego_id";

    public JuegoEditarActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_juego_editar, container, false);

        long juego_id = getArguments().getLong(ARG_JUEGO_ID);

        Juego juego = DatabaseManager.getInstance().getJuego(juego_id);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);


        EditText mTitulo = (EditText) view.findViewById(R.id.editTitulo);
        EditText mPlataforma = (EditText) view.findViewById(R.id.editPlataforma);
        EditText mEstudio = (EditText) view.findViewById(R.id.editEstudio);
        EditText mAnoPublicacion = (EditText) view.findViewById(R.id.editAnoPublicacion);

        ImageButton mImagen = (ImageButton) view.findViewById(R.id.imagenJuego);
        Spinner mEstado = (Spinner) view.findViewById(R.id.spinnerEstado);


        mTitulo.setText(juego.getNombre());
        mPlataforma.setText(juego.getPlataforma());
        mEstudio.setText(juego.getEstudio());
        mAnoPublicacion.setText(juego.getAno_publicacion());
        mImagen.setImageResource(juego.getFotoId());


        return view;
    }
}
