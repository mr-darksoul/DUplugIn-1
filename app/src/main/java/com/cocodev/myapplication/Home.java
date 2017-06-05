package com.cocodev.myapplication;



import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    //DBAdapter dbAdapter;

    public Home() {
        // Required empty public constructor

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //setting up on click listeners
        TextView  title_collegeUpdates = (TextView) getView().findViewById(R.id.title_collegeUpdates);
        title_collegeUpdates.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), categoryDetails.class).putExtra(Intent.EXTRA_TEXT, getString(R.string.title_collegeUpdates));
                startActivity(intent);
            }
        });

        TextView title_universityUpdates = (TextView) getView().findViewById(R.id.title_universityUpdates);
        title_universityUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),categoryDetails.class).putExtra(Intent.EXTRA_TEXT,getString(R.string.title_universityUpdates));
                startActivity(intent);
            }
        });

//        dbAdapter=new DBAdapter(getContext());
//        dbAdapter.open();
        //dbAdapter.insertRow("i am the fucking title","I am the fucking description, i dont give a fuck");
       // populateArticleList();




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.feed_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Log.i("settings12","settings");
            Intent intent = new Intent(getActivity(),SettingsActivity.class);
            startActivity(intent);

        }
        return true;
    }

    public void populateArticleList(){
//        Cursor cursor = dbAdapter.getAllRows();
//
//        // Allow activity to manage lifetime of the cursor.
//        // DEPRECATED! Runs on the UI thread, OK for small/short queries.
//        //startManagingCursor(cursor); see what to do with this
//
//        // Setup mapping from cursor to view fields:
//        String[] fromFieldNames = new String[]
//                {DBAdapter.ARTICLE_TITLE, DBAdapter.ARTICLE_LONGDESC};
//        int[] toViewIDs = new int[]
//                {R.id.article_title,     R.id.article_shortdesc};
//
//        // Create adapter to may columns of the DB onto elemesnt in the UI.
//        SimpleCursorAdapter myCursorAdapter =
//                new SimpleCursorAdapter(
//                        getContext(),		// Context
//                        R.layout.article_layout,	// Row layout template
//                        cursor,					// cursor (set of DB records to map)
//                        fromFieldNames,			// DB Column names
//                        toViewIDs				// View IDs to put information in
//                );
//
//
//        // Set the adapter for the list view
//        GridView myList = (GridView)  getView().findViewById(R.id.article_collegeupdates_gridView);
//
//        myList.setAdapter(myCursorAdapter);
//        Cursor cursor = dbAdapter.getAllRows();
//
//        // Allow activity to manage lifetime of the cursor.
//        // DEPRECATED! Runs on the UI thread, OK for small/short queries.
//        //startManagingCursor(cursor); see what to do with this
//
//        // Setup mapping from cursor to view fields:
//        String[] fromFieldNames = new String[]
//                {DBAdapter.ARTICLE_TITLE, DBAdapter.ARTICLE_LONGDESC};
//        int[] toViewIDs = new int[]
//                {R.id.article_title,     R.id.article_shortdesc};
//
//        // Create adapter to may columns of the DB onto elemesnt in the UI.
//        SimpleCursorAdapter myCursorAdapter =
//                new SimpleCursorAdapter(
//                        getContext(),		// Context
//                        R.layout.article_layout,	// Row layout template
//                        cursor,					// cursor (set of DB records to map)
//                        fromFieldNames,			// DB Column names
//                        toViewIDs				// View IDs to put information in
//                );
//
//
//        // Set the adapter for the list view
//        GridView myList = (GridView)  getView().findViewById(R.id.article_collegeupdates_gridView);
//
//        myList.setAdapter(myCursorAdapter);


    }
}
