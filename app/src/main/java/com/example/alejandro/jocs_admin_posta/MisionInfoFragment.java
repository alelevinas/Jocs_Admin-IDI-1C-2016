package com.example.alejandro.jocs_admin_posta;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Mision;

/**
 * A fragment representing a single Mision detail screen.
 * This fragment is either contained in a {@link MisionListActivity}
 * in two-pane mode (on tablets) or a {@link MisionInfoActivity}
 * on handsets.
 */
public class MisionInfoFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    /**
     * The dummy content this fragment is presenting.
     */
    private Mision mision;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MisionInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(MisionAdapter.MisionViewHolder.EXTRA_MISION)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mision = DatabaseManager.getInstance().getMision(getArguments().getLong(MisionAdapter.MisionViewHolder.EXTRA_MISION));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mision.getTitulo());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mision_info, container, false);

        // Show the dummy content as text in a TextView.
        if (mision != null) {
            ((TextView) rootView.findViewById(R.id.mision_detail)).setText(mision.getDescripcion() + "\nPuntuación: " + mision.getPuntuacion());
        }

        return rootView;
    }
}
