package com.example.arunrawlani.dot;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

import java.util.List;


public class MainActivity extends Activity{

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());//to get user data about how they are
                                                                //using the app

        Intent intent= new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    //Set the adapter for the list view
        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();


    }

    private void addDrawerItems(){
        String[] dotArray={"Home", "Profile", "Contact"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dotArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });

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

        switch (id) {
            case R.id.menu_switch_theme:
                switchTheme();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Switch theme. Still working to incorporate this.
     */
    public void switchTheme() {

        final Intent intent = getIntent();
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int theme = extras.getInt("theme", -1);
            if (theme == R.style.AppThemeLight) {
                getIntent().removeExtra("theme");
            } else {
                intent.putExtra("theme", R.style.AppThemeLight);
            }
        } else {
            intent.putExtra("theme", R.style.AppThemeLight);
        }
        finish();
        startActivity(intent);
    }

    public void showProgress(View view){
        //response on clicking the red button
        Intent intent = new Intent(this, CircularProgressBar.class);
        startActivity(intent);
    }
}
