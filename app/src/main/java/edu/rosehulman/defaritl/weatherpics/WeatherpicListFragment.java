package edu.rosehulman.defaritl.weatherpics;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by defaritl on 1/21/2016.
 */
public class WeatherpicListFragment extends Fragment implements MyDialogCallbackInterface {

    WeatherpicAdapter mWeatherpicAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWeatherpicAdapter = new WeatherpicAdapter(getFragment(), getFragmentManager());

        View rootView = inflater.inflate(R.layout.fragment_list_recyclerview, container, false);

        RecyclerView view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        view.setHasFixedSize(true);
        view.setAdapter(mWeatherpicAdapter);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WeatherpicDialogFragment wdf = new WeatherpicDialogFragment();
                wdf.setTargetFragment(getFragment(), 0);
                wdf.show(getFragmentManager(), "weatherpics");

            }
        });

        return rootView;
    }

    private Fragment getFragment(){
        return this;
    }

    @Override
    public void callbackAddButton(String caption, String url) {
        mWeatherpicAdapter.addWeatherpic(caption, url);
    }

    @Override
    public void callbackDeleteButton(String key) {
        mWeatherpicAdapter.removeWeatherpic(key);
    }

    @Override
    public void callbackEditButton(Weatherpic weatherpic) {
        mWeatherpicAdapter.editWeatherpic(weatherpic);
    }

}
