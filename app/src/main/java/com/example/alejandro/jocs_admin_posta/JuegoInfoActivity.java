package com.example.alejandro.jocs_admin_posta;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.alejandro.jocs_admin_posta.db_utils.DatabaseManager;
import com.example.alejandro.jocs_admin_posta.model.Juego;

public class JuegoInfoActivity extends AppCompatActivity {

    Juego j;
    long id_juego;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_info);

        Bundle b = getIntent().getExtras();
        id_juego = b.getLong(JuegoAdapter.JuegoViewHolder.EXTRA_JUEGO);
        j = DatabaseManager.getInstance().getJuego(id_juego);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_juego_info);
        toolbar.setTitle(j.getNombre());
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eliminar_juego) {
            dialogSeguro();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialogSeguro() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.action_eliminar_juego)
                .setMessage(R.string.mensaje_eliminar_juego)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance().eliminarJuego(j.getId());
                        onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        j = DatabaseManager.getInstance().getJuego(j.getId());
        if (j.getNombre() == null)
            onBackPressed();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return JuegoInfoFragment.newInstance(id_juego);
                case 1:
                    //Personajes
                    return JuegoPersonajesFragment.newInstance(id_juego);
                case 2:
                    //Objetos
                    return JuegoObjetosFragment.newInstance(id_juego);
                case 3:
                    //Misiones
                    return JuegoMisionesFragment.newInstance(id_juego);
                default:
                    return JuegoInfoFragment.newInstance(id_juego);
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Info";
                case 1:
                    return "Personajes";
                case 2:
                    return "Objetos";
                case 3:
                    return "Misiones";
            }
            return null;
        }
    }
}
