package com.example.alejandro.jocs_admin_posta;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
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

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<Objeto> objetos;

    public ObjetoAdapter(Context context, List<Objeto> objetos) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        this.objetos = objetos;
        mBackground = mTypedValue.resourceId;
    }


    @Override
    public int getItemCount() {
        return objetos.size();
    }

    public Objeto getValueAt(int position) {
        return objetos.get(position);
    }

    @Override
    public ObjetoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.objeto_text, viewGroup, false);
        itemView.setBackgroundResource(mBackground);
        return new ObjetoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ObjetoViewHolder objetoViewHolder, int i) {
        Objeto p = objetos.get(i);

        if (i % 2 == 0)
            objetoViewHolder.mView.setBackgroundColor(Color.LTGRAY);
        else
            objetoViewHolder.mView.setBackgroundColor(Color.WHITE);

        objetoViewHolder.vNombre.setText(p.getNombre());
        objetoViewHolder.vNivel.setText("Nivel: " + p.getNivel());

        objetoViewHolder.currentObjeto = p;
    }

    public static class ObjetoViewHolder extends RecyclerView.ViewHolder {

        public static final String EXTRA_OBJETO = "objeto_id";
        public final View mView;
        protected Objeto currentObjeto;
        protected TextView vNombre;
        protected TextView vNivel;

//        public final ImageView mImageView;

        public ObjetoViewHolder(View v) {
            super(v);
            mView = v;
            vNombre = (TextView) v.findViewById(R.id.objeto_nombre);
            vNivel = (TextView) v.findViewById(R.id.objeto_nivel);

//            mImageView = (ImageView) view.findViewById(R.id.avatar);

            /* EL LISTENER */
            /* aca mandar a la activity del Objeto con sus objetos, objetos y misiones*/
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity objeto (currentObjeto) bla bla
                    Toast.makeText(v.getContext(), currentObjeto.getNombre(), Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(v.getContext(), ObjetoEditarActivity.class);
                    Bundle b = new Bundle();
                    b.putLong(EXTRA_OBJETO, currentObjeto.getId());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + vNombre.getText();
        }
    }
}
