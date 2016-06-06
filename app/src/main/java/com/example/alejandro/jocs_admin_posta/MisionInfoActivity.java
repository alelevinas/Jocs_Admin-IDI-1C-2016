package com.example.alejandro.jocs_admin_posta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Mision;


public class MisionInfoActivity extends AppCompatActivity {

    private long id_mision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mision_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        id_mision = getIntent().getLongExtra(MisionAdapter.MisionViewHolder.EXTRA_MISION, -1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_editar_mision);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MisionEditarActivity.class);
                Bundle arguments = new Bundle();
                arguments.putLong(MisionAdapter.MisionViewHolder.EXTRA_MISION, id_mision);
                intent.putExtras(arguments);
                startActivity(intent);
            }
        });

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(MisionAdapter.MisionViewHolder.EXTRA_MISION, id_mision);
            MisionInfoFragment fragment = new MisionInfoFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mision_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MISION INFO ACT", "ON RESUMEEEE");
        Mision m = DatabaseManager.getInstance().getMision(id_mision);

        if (m.getTitulo() == null)
            onBackPressed();


    }
}
