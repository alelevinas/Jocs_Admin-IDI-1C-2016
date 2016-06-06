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

import com.example.alejandro.jocs_admin_posta.model.Mision;

import java.util.List;

/**
 * Created by Alejandro on 2/6/2016.
 */
public class MisionAdapter extends RecyclerView.Adapter<MisionAdapter.MisionViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<Mision> misions;

    public MisionAdapter(Context context, List<Mision> misions) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        this.misions = misions;
        mBackground = mTypedValue.resourceId;
    }


    @Override
    public int getItemCount() {
        return misions.size();
    }

    public Mision getValueAt(int position) {
        return misions.get(position);
    }

    @Override
    public MisionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.mision_text, viewGroup, false);
        itemView.setBackgroundResource(mBackground);
        return new MisionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MisionViewHolder misionViewHolder, int i) {
        Mision m = misions.get(i);

        if (i % 2 == 0)
            misionViewHolder.mView.setBackgroundColor(Color.LTGRAY);
        else
            misionViewHolder.mView.setBackgroundColor(Color.WHITE);

        misionViewHolder.vTitulo.setText(m.getTitulo());
//        misionViewHolder.vDescripcion.setText(m.getDescripcion());
        misionViewHolder.vPuntuacion.setText(m.getPuntuacion());

//        misionViewHolder.vFotoId.setImageResource(p.getFotoId());

//        Glide.with(misionViewHolder.mImageView.getContext())
//                .load(p.getImagen())
//                .fitCenter()
//                .into(misionViewHolder.mImageView);

        misionViewHolder.currentMision = m;
    }

    public static class MisionViewHolder extends RecyclerView.ViewHolder {

        public static final String EXTRA_MISION = "mision_id";
        public final View mView;
        public String mBoundString;
        protected Mision currentMision;
        protected TextView vTitulo;
        protected TextView vDescripcion;
        protected TextView vPuntuacion;

//        public final ImageView mImageView;

        public MisionViewHolder(View v) {
            super(v);
            mView = v;
            vTitulo = (TextView) v.findViewById(R.id.mision_titulo);
//            vDescripcion = (TextView) v.findViewById(R.id.mision_descripcion);
            vPuntuacion = (TextView) v.findViewById(R.id.mision_puntuacion);

//            mImageView = (ImageView) view.findViewById(R.id.avatar);

            /* EL LISTENER */
            /* aca mandar a la activity del Mision con sus*/
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity mision (currentMision) bla bla
//                    Toast.makeText(v.getContext(), currentMision.getTitulo(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), MisionInfoActivity.class);
                    Bundle b = new Bundle();
                    b.putLong(EXTRA_MISION, currentMision.getId());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);

                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + vTitulo.getText();
        }
    }
}
