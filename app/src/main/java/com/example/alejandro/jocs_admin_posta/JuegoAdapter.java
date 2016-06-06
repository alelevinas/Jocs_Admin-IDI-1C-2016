package com.example.alejandro.jocs_admin_posta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alejandro.jocs_admin_posta.model.Juego;

import java.util.List;

public class JuegoAdapter extends RecyclerView.Adapter<JuegoAdapter.JuegoViewHolder> {

    private List<Juego> juegos;

    public JuegoAdapter(List<Juego> juegos) {
        this.juegos = juegos;
    }


    @Override
    public int getItemCount() {
        return juegos.size();
    }

    @Override
    public JuegoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_juego, viewGroup, false);

        return new JuegoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JuegoViewHolder juegoViewHolder, int i) {
        Juego ci = juegos.get(i);
        juegoViewHolder.vNombre.setText(ci.getNombre());
        juegoViewHolder.vPlataforma.setText(ci.getPlataforma());
        juegoViewHolder.vEstudio.setText(ci.getEstudio());
        juegoViewHolder.vAno_publicacion.setText(ci.getAno_publicacion());
        juegoViewHolder.vCurso.setText(ci.getCurso());
        juegoViewHolder.vFotoId.setImageResource(ci.getFotoId());

        juegoViewHolder.currentJuego = ci;
    }

    public static class JuegoViewHolder extends RecyclerView.ViewHolder {

        public static final String EXTRA_JUEGO = "juego";

        protected Juego currentJuego;

        protected TextView vNombre;
        protected TextView vPlataforma;
        protected TextView vEstudio;
        protected TextView vAno_publicacion;
        protected ImageView vFotoId;
        protected TextView vCurso;

        public JuegoViewHolder(View v) {
            super(v);
            vNombre = (TextView) v.findViewById(R.id.titulo);
            vEstudio = (TextView) v.findViewById(R.id.txtEstudio);
            vPlataforma = (TextView) v.findViewById(R.id.txtPlataforma);
            vAno_publicacion = (TextView) v.findViewById(R.id.txtAnoPublicacion);
            vCurso = (TextView) v.findViewById(R.id.txtCurso);
            vFotoId = (ImageView) v.findViewById(R.id.juego_foto);

            /* EL LISTENER */
            /* aca mandar a la activity del Juego con sus objetos, personajes y misiones*/
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity tabs juego (currentJuego) bla bla
                    Toast.makeText(v.getContext(), currentJuego.getNombre(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), JuegoInfoActivity.class);
                    Bundle b = new Bundle();
                    b.putLong(EXTRA_JUEGO, currentJuego.getId());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
