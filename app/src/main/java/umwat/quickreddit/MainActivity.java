package umwat.quickreddit;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    static ListView mListView;
    static PostAdapter adapter;
    MainFragment mainFragment = new MainFragment();
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getStringExtra("SUB") != null)
            getSupportActionBar().setTitle(getIntent().getStringExtra("SUB"));
        else getSupportActionBar().setTitle("Front Page");


        FragmentManager fragmentManager = getFragmentManager();

        if (savedInstanceState != null) {
            fragment = fragmentManager.findFragmentByTag("YUS");
        } else {
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, mainFragment).addToBackStack(null);
            fragmentTransaction.commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.entersub){
            AlertDialog.Builder theDialog = new AlertDialog.Builder(this);
            theDialog.setTitle("Enter Subreddit");

            final EditText jeez = new EditText(this);


            theDialog.setView(jeez);


            theDialog.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    String sub = jeez.getText().toString();
                    intent.putExtra("YUS", "https://www.reddit.com/r/" + sub + ".json?limit=50");
                    intent.putExtra("SUB", sub);
                    startActivity(intent);



                    MainActivity.adapter.clear();
                    MainFragment.submisions.clear();


                    MainActivity.adapter.notifyDataSetChanged();
                }
            });
            theDialog.setNegativeButton("Cancel", null);
            theDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mainFragment.isVisible()) {
            if(getSupportActionBar().getTitle().equals("Front Page")){
                this.finish();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                overridePendingTransition(0,0);



                MainActivity.adapter.clear();
                MainFragment.submisions.clear();


                MainActivity.adapter.notifyDataSetChanged();
            }

        }
        else {
            getFragmentManager().popBackStackImmediate();


        }

    }


}
