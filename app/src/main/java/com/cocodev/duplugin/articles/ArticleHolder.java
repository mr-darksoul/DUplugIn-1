package com.cocodev.duplugin.articles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.duplugin.ArticleDetails;
import com.cocodev.duplugin.R;
import com.cocodev.duplugin.SA;
import com.cocodev.duplugin.Utility.Article;
import com.cocodev.duplugin.adapter.CustomArticleHolderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.cocodev.duplugin.Utility.Utility.getTimeAgo;


public class ArticleHolder extends Fragment implements AbsListView.OnScrollListener {

    public static String key = "type";
    public final static String TYPE_HOME = "Home";
    private int preLast =0;
    private final String LAST_SCROLL_STATE = "lastScrollState";
    private String typeString = null;
    private long itemCount=0;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CustomArticleHolderAdapter mAdapter;
    public ArticleHolder(){}
    ListView mListView;
    View mFooterView;
    DatabaseReference[] databaseReferences;

    public static ArticleHolder newInstance(String type){
        ArticleHolder a = new ArticleHolder();
        a.typeString = type;
        return  a;
    }
    public static ArticleHolder newInstance(String type,DatabaseReference[] databaseReferences){
        ArticleHolder a = new ArticleHolder();
        a.typeString = type;
        a.databaseReferences=databaseReferences;
        return  a;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            typeString = savedInstanceState.getString(key);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(databaseReferences!=null)
            return;
        if(!typeString.equals(TYPE_HOME)) {
            databaseReference = firebaseDatabase.getReference()
                    .child("Categories").child("Articles").child(getTypeString());
        }else{
             databaseReference= firebaseDatabase.getReference()
                    .child("Articles");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_article_holder, container, false);
        mListView = (ListView) view.findViewById(R.id.listView_articleHolder);

        //view to be added while loading more data;
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.footer_progress_bar, null);

        mListView.addFooterView(mFooterView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mListView==null)
                    return;
                mListView.removeFooterView(mFooterView);
                mAdapter.notifyDataSetChanged();
            }
        },5000);

        //view when list is empty
        TextView textView = (TextView) view.findViewById(R.id.articleHolder_emptyView);
        textView.setText("There are currently no articles under this Category.");
        mListView.setEmptyView(textView);

        if(databaseReferences!=null){
            mAdapter = new CustomArticleHolderAdapter<String>(
                    getActivity(),
                    String.class,
                    R.layout.adapter_notice,
                    databaseReferences
            ) {

                @Override
                protected void populateView(View v, String model, int position) {
                    final CustomArticleHolderAdapter.ViewHolder viewHolder = (ViewHolder) v.getTag();

                    DatabaseReference dbref = firebaseDatabase.getReference().child("Articles")
                            .child(model);


                    DatabaseReference dbref2 = firebaseDatabase.getReference()
                            .child("College Content")
                            .child(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(SA.KEY_COLLEGE,null))
                            .child("Articles")
                            .child(model);
                    dbref2.keepSynced(true);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Article article = dataSnapshot.getValue(Article.class);
                            if (article == null) {
                                return;
                            }

                            if (viewHolder.authorView != null) {
                                viewHolder.authorView.setText(article.getAuthor());
                            }
                            viewHolder.timeView.setText(getTimeAgo(getContext(), article.getTime()));
                            viewHolder.titleView.setText(Html.fromHtml(article.getTitle()));
                            viewHolder.UID.setText(article.getUID());
                            Picasso.with(getContext()).load(article.getImageUrl()).placeholder(R.drawable.placeholder).fit().centerCrop().into(viewHolder.imageView);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "There has been some problem establishing connection with the server.", Toast.LENGTH_SHORT).show();

                        }
                    };

                    dbref.addListenerForSingleValueEvent(valueEventListener);
                    dbref2.addListenerForSingleValueEvent(valueEventListener);
                }

            };
        }else if(!typeString.equals(TYPE_HOME)){
            DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("College Content")
                    .child(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(SA.KEY_COLLEGE,null))
                    .child("Categories")
                    .child("Articles")
                    .child(getTypeString());
            temp.keepSynced(true);
            mAdapter = new CustomArticleHolderAdapter<String>(
                    getActivity(),
                    String.class,
                    R.layout.adapter_notice,
                    new DatabaseReference[]{databaseReference,
                        temp
                    }
            ) {

                @Override
                protected void populateView(View v, String model, int position) {
                    final CustomArticleHolderAdapter.ViewHolder viewHolder = (ViewHolder) v.getTag();

                    DatabaseReference dbref = firebaseDatabase.getReference().child("Articles")
                            .child(model);
                    DatabaseReference dbref2 = firebaseDatabase.getReference()
                            .child("College Content")
                            .child(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(SA.KEY_COLLEGE,null))
                            .child("Articles")
                            .child(model);
                    dbref2.keepSynced(true);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Article article = dataSnapshot.getValue(Article.class);
                            if(article==null) {
                                return;
                            }
                            if (viewHolder.authorView != null) {
                                viewHolder.authorView.setText(Html.fromHtml(article.getAuthor()));
                            }
                            viewHolder.timeView.setText(getTimeAgo(getContext(),article.getTime()));
                            viewHolder.titleView.setText(article.getTitle());
                            viewHolder.UID.setText(article.getUID());
                            Picasso.with(getContext()).load(article.getImageUrl()).placeholder(R.drawable.placeholder).fit().centerCrop().into(viewHolder.imageView);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(),"There has been some problem establishing connection with the server.",Toast.LENGTH_SHORT).show();

                        }
                    };
                    dbref2.addListenerForSingleValueEvent(valueEventListener);
                    dbref.addListenerForSingleValueEvent(valueEventListener);


                }

            };
        }else{
            DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("College Content")
                    .child(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(SA.KEY_COLLEGE,null))
                    .child("Articles");

            temp.keepSynced(true);
            mAdapter = new CustomArticleHolderAdapter<Article>(
                    getActivity(),
                    Article.class,
                    R.layout.adapter_notice,
                    new DatabaseReference[] {databaseReference,temp}
            ) {

                @Override
                protected void populateView(View v, Article model, int position) {
                    final CustomArticleHolderAdapter.ViewHolder viewHolder = (ViewHolder) v.getTag();
                    if(viewHolder.authorView!=null){
                        viewHolder.authorView.setText(Html.fromHtml(model.getAuthor()));
                    }
                    viewHolder.titleView.setText(Html.fromHtml(model.getTitle()));
                    viewHolder.timeView.setText(getTimeAgo(getContext(),model.getTime()));
                    viewHolder.UID.setText(model.getUID());
                    Picasso.with(getContext()).load(model.getImageUrl()).placeholder(R.drawable.placeholder).fit().centerCrop().into(viewHolder.imageView);
                }

                @Override
                public boolean isEmpty() {
                    if(mListView.getFooterViewsCount()!=0)
                        return false;
                    return super.isEmpty();
                }
            };


        }


        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(onItemClickListener);
        mListView.setOnScrollListener(this);

         return view;
    }



    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String UID = (String) ((TextView) view.findViewById(R.id.article_UID)).getText();
            Intent intent = new Intent(getContext(),ArticleDetails.class);
            intent.putExtra(ArticleDetails.key,UID);
            Pair<View,String> pair1 = Pair.create(view.findViewById(R.id.articleImage),getString(R.string.home_share_image));
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(),
                    pair1
            );

            startActivity(intent,optionsCompat.toBundle());

        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public String getTypeString(){
        return typeString;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(key,typeString);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListView = null;
        mAdapter.removeListener();
        databaseReference =null;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //do nothing
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Make your calculation stuff here. You have all your
        // needed info from the parameters of this function.

        // Sample calculation to determine if the last
        // item is fully visible.
        final int lastItem = firstVisibleItem + visibleItemCount-mListView.getFooterViewsCount();

        if(lastItem == totalItemCount)
        {
            if(preLast!=lastItem) {
                Log.e("his",Integer.toString(preLast)+" "+Integer.toString(lastItem));
                if(mListView.getFooterViewsCount()==0){
                    mListView.addFooterView(mFooterView);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mListView==null)
                                return;
                            mListView.removeFooterView(mFooterView);
                            mAdapter.notifyDataSetChanged();
                        }
                    },5000);
                }
                preLast = lastItem;
                //to avoid multiple calls for last item
                //mAdapter.populateMoreList(mListView,mFooterView);
            }
        }

    }
}
