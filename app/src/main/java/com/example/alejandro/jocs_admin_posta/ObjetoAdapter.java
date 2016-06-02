package com.example.alejandro.jocs_admin_posta;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alejandro.jocs_admin_posta.model.Objeto;

import java.util.List;

/**
 * Created by Alejandro on 2/6/2016.
 */
public class ObjetoAdapter extends RecyclerView.Adapter<ObjetoAdapter.ObjetoViewHolder> {

    private List<Objeto> objetos;

    public ObjetoAdapter(List<Objeto> objetos) {
        this.objetos = objetos;
    }


    @Override
    public int getItemCount() {
        return objetos.size();
    }

    @Override
    public ObjetoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.objeto_text, viewGroup, false);

        return new ObjetoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ObjetoViewHolder objetoViewHolder, int i) {
        Objeto p = objetos.get(i);
        objetoViewHolder.vNombre.setText(p.getNombre());
        objetoViewHolder.vNivel.setText("Nivel: " + p.getNivel());

//        objetoViewHolder.vFotoId.setImageResource(p.getFotoId());

        objetoViewHolder.currentObjeto = p;
    }

    public static class ObjetoViewHolder extends RecyclerView.ViewHolder {

        public static final String EXTRA_OBJETO = "objeto";

        protected Objeto currentObjeto;

        protected TextView vNombre;
        protected TextView vNivel;

        public ObjetoViewHolder(View v) {
            super(v);
            vNombre = (TextView) v.findViewById(R.id.objeto_nombre);
            vNivel = (TextView) v.findViewById(R.id.objeto_nivel);
            v.setBottom(5);


            /* EL LISTENER */
            /* aca mandar a la activity del Objeto con sus objetos, objetos y misiones*/
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity objeto (currentObjeto) bla bla
                    Toast.makeText(v.getContext(), currentObjeto.getNombre(), Toast.LENGTH_SHORT).show();

                    /*
                    Intent intent = new Intent(v.getContext(), ObjetoInfoActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable(EXTRA_OBJETO, currentObjeto);
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                    */
                }
            });
        }
    }
}
