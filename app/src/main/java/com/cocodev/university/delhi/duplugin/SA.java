package com.cocodev.university.delhi.duplugin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class SA extends AppCompatActivity {

    public static final String KEY_COLLEGE    = "com.cocodev.myapplication.collegechoices";
    public static final String KEY_DEPARTMENT = "com.cocodev.myapplication.departmentchoices";
    public static final String KEY_NOTIFY     = "com.cocodev.myapplication.notify";
    public static final String fileName_HP    = "com.cocodev.myapplication.homepreferences";
    public static final String fileName_EP    = "com.cocodev.myapplication.eventpreferences";
    private Toolbar toolbar;
    TextView mGeneral;
     Spinner collegeChoices ;
     Spinner departmentChoices;
    Switch notify;

    //if College or Department has changed
    private boolean RELOAD_PARENT_ACTIVITY = false;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        DatabaseReference hpDatabaseReference = FirebaseDatabase.getInstance().getReference().child("CategoryList").child("Articles");
        hpDatabaseReference.addListenerForSingleValueEvent(hpValueEventListener);
        DatabaseReference epDatabaseReference = FirebaseDatabase.getInstance().getReference().child("CategoryList").child("Events");
        epDatabaseReference.addListenerForSingleValueEvent(epValueEventListener);
        collegeChoices = (Spinner) findViewById(R.id.spinner_college);
        departmentChoices = (Spinner) findViewById(R.id.spinner_course);
        notify = (Switch) findViewById(R.id.switch_notify_SA);
        mGeneral = (TextView) findViewById(R.id.title_general);
        initNotify();
        initCollegeSpinner();
        ShowcaseView showcaseView1;

        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.sp_college),true)){
                showcaseView1 = new  ShowcaseView.Builder(this)
                .setTarget(new ViewTarget((View) collegeChoices))
                .withHoloShowcase()
                .setStyle(R.style.TextAppearance_ShowcaseView_Title)
                .setContentTitle("Select College and Department")
                .setContentText("This information is required to provide you latest college and department updates.")
                .hideOnTouchOutside()
                .build();
            showcaseView1.setOnShowcaseEventListener(new OnShowcaseEventListener() {
                @Override
                public void onShowcaseViewHide(ShowcaseView showcaseView) {
                    showShowcaseViewEventInterst();
                }

                @Override
                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                }

                @Override
                public void onShowcaseViewShow(ShowcaseView showcaseView) {

                }

                @Override
                public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                }
            });
                ;


            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(getString(R.string.sp_college),false).commit();
        }








    }

    private void showShowcaseViewEventInterst() {
        if(PreferenceManager.getDefaultSharedPreferences(SA.this).getBoolean(getString(R.string.sp_interest),true)){
            ShowcaseView showcaseView2 = new ShowcaseView.Builder(SA.this)
                    .setTarget(new ViewTarget((View) findViewById(R.id.eventPreferences)))
                    .withHoloShowcase()
                    .setStyle(R.style.TextAppearance_ShowcaseView_Title)
                    .setContentTitle("Select Your Interests")
                    .setContentText("This information is required to provide you latest updates and notifications of only subjects of your " +
                            "interest.")
                    .hideOnTouchOutside()
                    .build()
            ;
            showcaseView2.setOnShowcaseEventListener(new OnShowcaseEventListener() {
                @Override
                public void onShowcaseViewHide(ShowcaseView showcaseView) {
                    if(PreferenceManager.getDefaultSharedPreferences(SA.this).getBoolean(getString(R.string.sp_backButton),true)){
                        new ShowcaseView.Builder(SA.this)
                                .setTarget(new ViewTarget((View) toolbar.getChildAt(1)))
                                .withHoloShowcase()
                                .setStyle(R.style.TextAppearance_ShowcaseView_Title)
                                .setContentTitle("Press Back Button To Begin Action...")
                                .hideOnTouchOutside()
                                .build();
                        PreferenceManager.getDefaultSharedPreferences(SA.this).edit().putBoolean(getString(R.string.sp_backButton),false).commit();
                    }
                }

                @Override
                public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

                }

                @Override
                public void onShowcaseViewShow(ShowcaseView showcaseView) {

                }

                @Override
                public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                }
            });
            PreferenceManager.getDefaultSharedPreferences(SA.this).edit().putBoolean(getString(R.string.sp_interest),false).commit();
        }
    }

    private void initNotify() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checked = sharedPref.getBoolean(KEY_NOTIFY,true);
        notify.setChecked(checked);
        notify.setOnCheckedChangeListener(notifyChangedListener);
    }
    CompoundButton.OnCheckedChangeListener notifyChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SA.this);
            sharedPref.edit().putBoolean(KEY_NOTIFY,isChecked).commit();
            notify.setChecked(isChecked);
        }
    };
    private void initDepartmentSpinner() {

        final ArrayList<String> departments =new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,departments);
        Log.e("tag",(String)collegeChoices.getSelectedItem());
        if(collegeChoices.getSelectedItem()==null)
            return;
        DatabaseReference collegesDR = FirebaseDatabase.getInstance().getReference().child("CollegeList").child((String)collegeChoices.getSelectedItem());
        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(SA.this);
        final String selection = sharedpref.getString(KEY_DEPARTMENT,"");
        collegesDR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot temp = iterator.next();
                    String department = temp.getKey().toString();
                    departments.add(department);
                    arrayAdapter.notifyDataSetChanged();
                    if(department.equals(selection)){
                        departmentChoices.setSelection(arrayAdapter.getPosition(department));
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SA.this,"There was some error establishing connection with the server",Toast.LENGTH_SHORT).show();
            }

        });

        departmentChoices.setAdapter(arrayAdapter);
        departmentChoices.setOnItemSelectedListener(departmentSelectedListener);



    }

    private void initCollegeSpinner() {

        final ArrayList<String> colleges =new ArrayList<String>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,colleges);
        DatabaseReference collegesDR = FirebaseDatabase.getInstance().getReference().child("CollegeList");

        SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(SA.this);
        final String selection = sharedpref.getString(KEY_COLLEGE,"");

        collegesDR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    DataSnapshot temp = iterator.next();

                    //get name of the department
                    String department = temp.getKey().toString();
                    colleges.add(department);
                    //to reflect changes in the ui
                    arrayAdapter.notifyDataSetChanged();

                    if(department.equals(selection)){
                        collegeChoices.setSelection(arrayAdapter.getPosition(department));
                        initDepartmentSpinner();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //We will see this later
                Toast.makeText(SA.this,"There has been some problem connecting to the server.",Toast.LENGTH_SHORT).show();
            }
        });
        collegeChoices.setAdapter(arrayAdapter);
        collegeChoices.setOnItemSelectedListener(collegeSelectedListener);


    }

    //whenever the selection changes update the shared preferences`
    AdapterView.OnItemSelectedListener collegeSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String college = (String) parent.getItemAtPosition(position);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SA.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_COLLEGE,college);
            editor.commit();
            initDepartmentSpinner();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //whenever the selection changes update the shared preferences
    AdapterView.OnItemSelectedListener departmentSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String department = (String) parent.getItemAtPosition(position);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SA.this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_DEPARTMENT,department);
            editor.commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //Add Switches to Home Preferences
    ValueEventListener hpValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SharedPreferences sharedPreferences = getSharedPreferences(fileName_HP,MODE_PRIVATE);
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.homePreferences);
            while (iterator.hasNext()){
                DataSnapshot temp = iterator.next();
                Switch aSwitch = new Switch(getApplicationContext());
                if(!sharedPreferences.contains(temp.getKey())){
                    Toast.makeText(SA.this, "New Category Added", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(temp.getKey().toString(),true);
                    editor.commit();
                    Home.setHomePreferencesChanged(true);
                }
                if(!temp.getKey().equals(sharedPreferences.getString(KEY_DEPARTMENT,""))) {
                    aSwitch.setChecked(sharedPreferences.getBoolean(temp.getKey().toString(), true));
                    aSwitch.setText(temp.getKey());
                    aSwitch.setTextColor(Color.BLACK);
                    aSwitch.setOnCheckedChangeListener(hpOnCheckedChangeListener);
                    linearLayout.addView(aSwitch);
                }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(SA.this,"There has been some problem connecting to the server.",Toast.LENGTH_SHORT).show();
        }
    };

    //add switches to Events Preferences
    ValueEventListener epValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SharedPreferences sharedPreferences = getSharedPreferences(fileName_EP,MODE_PRIVATE);
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.eventPreferences);
            while (iterator.hasNext()){
                DataSnapshot temp = iterator.next();
                Switch aSwitch = new Switch(getApplicationContext());
                if(!sharedPreferences.contains(temp.getKey())){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(temp.getKey().toString(),true);
                    editor.commit();
                }
                aSwitch.setChecked(sharedPreferences.getBoolean(temp.getKey().toString(),true));
                aSwitch.setText(temp.getKey());
                aSwitch.setTextColor(Color.BLACK);
                aSwitch.setOnCheckedChangeListener(epOnCheckedChangeListener);
                linearLayout.addView(aSwitch);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SA.this,"There has been some problem connecting to the server.",Toast.LENGTH_SHORT).show();
        }
    };

    //whenever the selection changes update the shared preferences
    CompoundButton.OnCheckedChangeListener hpOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences sharedPreferences = getSharedPreferences(fileName_HP,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(buttonView.getText().toString(),isChecked);
            editor.commit();
            Home.setHomePreferencesChanged(true);
        }
    };


    //whenever the selection changes update the shared preferences
    CompoundButton.OnCheckedChangeListener epOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SharedPreferences sharedPreferences = getSharedPreferences(fileName_EP,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(buttonView.getText().toString(),isChecked);
            editor.commit();
            Events.setEventsPreferencesChanged(true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(RELOAD_PARENT_ACTIVITY || isTaskRoot()){
            RELOAD_PARENT_ACTIVITY=false;
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
