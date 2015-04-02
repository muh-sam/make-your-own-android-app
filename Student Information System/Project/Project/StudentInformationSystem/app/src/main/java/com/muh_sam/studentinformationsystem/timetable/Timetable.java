package com.muh_sam.studentinformationsystem.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.muh_sam.studentinformationsystem.R;
import com.muh_sam.studentinformationsystem.Security.LoginActivity;
import com.muh_sam.studentinformationsystem.data.Utility;


public class Timetable extends ActionBarActivity implements TimetableFragment.CallBack {
    private final String LOG_TAG = Timetable.class.getSimpleName();
    private boolean mTwoPane;

    @Override
    public void onItemSelected(int TimetableId) {
        if(mTwoPane)
        {
            Bundle args = new Bundle();
            args.putInt(TimetableDetailActivity.TIMETABLE_KEY, TimetableId);
            TimetableFragment detailFragment = new TimetableFragment();
            detailFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.timetable_detail_container, detailFragment).commit();
        }else
        {
            Intent intent = new Intent(this, TimetableDetailActivity.class).putExtra(TimetableDetailActivity.TIMETABLE_KEY, TimetableId);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        if(findViewById(R.id.timetable_detail_container) != null)
        {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.timetable_detail_container, new TimetableDetailFragment())
                        .commit();
            }
        }
        else
        {
            mTwoPane = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Utility.Logout(this);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
