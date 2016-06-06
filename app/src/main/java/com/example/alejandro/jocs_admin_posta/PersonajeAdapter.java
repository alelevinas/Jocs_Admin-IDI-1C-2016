package com.example.alejandro.jocs_admin_posta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejandro.jocs_admin_posta.model.Personaje;

import java.util.List;

/**
 * Created by Alejandro on 2/6/2016.
 */
public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder> {

    private List<Personaje> personajes;

    public PersonajeAdapter(List<Personaje> personajes) {
        this.personajes = personajes;
    }


    @Override
    public int getItemCount() {
        return personajes.size();
    }

    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_personaje, viewGroup, false);

        return new PersonajeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder personajeViewHolder, int i) {
        Personaje p = personajes.get(i);
        personajeViewHolder.vNombre.setText(p.getNombre());
        personajeViewHolder.vRaza.setText(p.getRaza());
        personajeViewHolder.vNivel.setText("Nivel: " + p.getNivel());

        personajeViewHolder.vFotoId.setImageResource(p.getFotoId());

        personajeViewHolder.currentPersonaje = p;
    }

    public static class PersonajeViewHolder extends RecyclerView.ViewHolder {

        public static final String EXTRA_PERSONAJE = "personaje";

        protected Personaje currentPersonaje;

        protected TextView vNombre;
        protected TextView vRaza;
        protected TextView vNivel;
        protected ImageView vFotoId;

        public PersonajeViewHolder(View v) {
            super(v);
            vNombre = (TextView) v.findViewById(R.id.personaje_nombre);
            vRaza = (TextView) v.findViewById(R.id.txtPersonaje_raza);
            vNivel = (TextView) v.findViewById(R.id.txtPersonaje_nivel);
            vFotoId = (ImageView) v.findViewById(R.id.personaje_foto);


            /* EL LISTENER */
            /* aca mandar a la activity del Personaje con sus objetos, personajes y misiones*/
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity personaje (currentPersonaje) bla bla
//                    Toast.makeText(v.getContext(), currentPersonaje.getNombre(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), PersonajeEditarActivity.class);
                    Bundle b = new Bundle();
                    b.putLong(EXTRA_PERSONAJE, currentPersonaje.getId());
                    intent.putExtras(b);
                    v.getContext().startActivity(intent);

                }
            });
        }


    }
}