package com.example.alejandro.jocs_admin_posta;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static class JuegoViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;
        protected ImageView vPhotoId;

        public JuegoViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.title);
            vPhotoId = (ImageView) v.findViewById(R.id.juego_photo);
        }
    }

    @Override
    public void onBindViewHolder(JuegoViewHolder juegoViewHolder, int i) {
        Juego ci = juegos.get(i);
        juegoViewHolder.vName.setText(ci.name);
        juegoViewHolder.vSurname.setText(ci.surname);
        juegoViewHolder.vEmail.setText(ci.email);
        juegoViewHolder.vTitle.setText(ci.name + " " + ci.surname);
        juegoViewHolder.vPhotoId.setImageResource(ci.photoId);
    }
}
