package com.example.alejandro.jocs_admin_posta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.db_utils.JocsAdminDbHelper;
import com.example.alejandro.jocs_admin_posta.model.Juego;
import com.example.alejandro.jocs_admin_posta.model.Objeto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alejandro on 2/6/2016.
 */

public class JuegoObjetosFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_JUEGO = "juego";

    public JuegoObjetosFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JuegoObjetosFragment newInstance(Juego juego) {
        JuegoObjetosFragment fragment = new JuegoObjetosFragment();
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
        View v = inflater.inflate(R.layout.fragment_juego_objetos, container, false);
        Juego juego = (Juego) getArguments().getSerializable(ARG_JUEGO);


        /*CARDS*/
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.objetos_texts);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext()); //nro de columnas
        recList.setLayoutManager(llm);


        DatabaseManager.initializeInstance(new JocsAdminDbHelper(v.getContext()));

//        List<Objeto> objetos = DatabaseManager.getInstance().getAllObjetos();
        List<Objeto> objetos = DatabaseManager.getInstance().getAllObjetosFromJuego(juego.getId());
        ObjetoAdapter pa = new ObjetoAdapter(objetos);
        recList.setAdapter(pa);


        return v;
    }
}