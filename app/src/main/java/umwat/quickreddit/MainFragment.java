package umwat.quickreddit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Simon on 12/31/14.
 */
public class MainFragment extends Fragment {

    static String jsonUrl = "";
    private int touchPositionX;
    static ArrayList<Submission> submisions = new ArrayList<>();

    boolean longPress;
    static SwipeRefreshLayout swipeView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (getActivity().getIntent().getStringExtra("YUS") != null)
            jsonUrl = getActivity().getIntent().getStringExtra("YUS");
        else
            jsonUrl = "http://www.reddit.com/.json?limit=50&feed=c7292d361c6121d27f667d74d286f7077933e34f&user=Srimshady";


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);

        initSwipeView();


        FragmentManager fragmentManager = getFragmentManager();

        final FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();

        MainActivity.textView = (TextView) rootView.findViewById(R.id.empty_textview);

        MainActivity.mListView = (ListView) rootView.findViewById(R.id.thelistview);

        MainActivity.textView.setVisibility(View.VISIBLE);
        MainActivity.mListView.setVisibility(View.GONE);


        if(savedInstanceState == null) {
            final JsonFeedTask at = new JsonFeedTask(jsonUrl);

            at.execute();
        }


        MainActivity.adapter = new PostAdapter(getActivity(), R.layout.simpletext, R.id.text1, submisions);

        MainActivity.mListView.setAdapter(MainActivity.adapter);


        MainActivity.mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchPositionX = (int) event.getX();

                return false;
            }
        });
        android.support.v7.app.ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        if(actionBar.getTitle().equals("Front Page")) {
            MainActivity.mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    longPress = true;
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    String sub = submisions.get(position).getSubreddit();
                    intent.putExtra("YUS", "https://www.reddit.com/r/" + sub + ".json?limit=50");
                    intent.putExtra("SUB", sub);
                    startActivity(intent);
                    getActivity().overridePendingTransition(0, 0);


                    MainActivity.adapter.clear();
                    submisions.clear();


                    MainActivity.adapter.notifyDataSetChanged();
                    longPress = false;
                    return false;
                }
            });
        }

        MainActivity.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (!longPress) {

                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    Resources r = getResources();
                    float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, r.getDisplayMetrics());

                    if (touchPositionX > width - px) {

                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
                                (submisions.get(position).getCommentsUrl()));
                        startActivity(intent);
                    } else {


                        if (!submisions.get(position).isSelf()) {
                            Intent intent = new Intent(getActivity(), WebActivity.class);
                            intent.putExtra("URL", submisions.get(position).getUrl());
                            intent.putExtra("COMMURL", submisions.get(position).getCommentsUrl());
                            startActivity(intent);
                        } else {

                            Bundle bundle = new Bundle();
                            bundle.putString("DESC", submisions.get(position).getSelfText());
                            bundle.putString("COMM", submisions.get(position).getCommentsUrl());



                            if(submisions.get(position).getSelfText().equals("null") || submisions.get(position).getSelfText() == null){
                                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse
                                        (submisions.get(position).getCommentsUrl()));
                                startActivity(intent);
                            } else {

                                DetailsFragment detailsFragment = new DetailsFragment();


                                detailsFragment.setArguments(bundle);
                                fragmentTransaction2.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left);
                                fragmentTransaction2.replace(R.id.container, detailsFragment, "YUS").addToBackStack("YUS")
                                        .commit();
                            }
                        }

                    }


                }
            }

        });

        MainActivity.mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (MainActivity.mListView == null || MainActivity.mListView.getChildCount() == 0) ?
                                0 : MainActivity.mListView.getChildAt(0).getTop();
                swipeView.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });


        return rootView;
    }

    private void initSwipeView() {
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                MainActivity.adapter.clear();
                JsonFeedTask json = new JsonFeedTask(MainFragment.jsonUrl);
                json.execute();


            }


        });
    }


}
