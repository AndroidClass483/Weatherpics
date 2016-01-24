package edu.rosehulman.defaritl.weatherpics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by defaritl on 1/21/2016.
 */
public class WeatherpicAdapter extends RecyclerView.Adapter<WeatherpicAdapter.ViewHolder>{
    private ArrayList<Weatherpic> mWeatherpicsArray;
    private Firebase mFirebase;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;

    public  WeatherpicAdapter(Fragment fragment, FragmentManager fragmentManager){
        mFragment = fragment;
        mFragmentManager = fragmentManager;
        mWeatherpicsArray = new ArrayList<>();
        mFirebase = new Firebase(Constants.FIREBASE_URL);
        mFirebase.addChildEventListener(new WeatherpicChildEventListener());
    }

    public interface OnItemLongClickListener{

    }

    public void addWeatherpic(String caption, String url){
        Weatherpic weatherpic = new Weatherpic(caption, url);
        mFirebase.push().setValue(weatherpic);
    }


    public void removeWeatherpic(String key) {
        mFirebase.child(key).removeValue();
    }

    public void editWeatherpic(Weatherpic weatherpic) {
        mFirebase.child(weatherpic.getKey()).setValue(weatherpic);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_weatherpic, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Weatherpic weatherpic = mWeatherpicsArray.get(position);
        holder.mCaption.setText(weatherpic.getCaption());
        holder.mURL.setText(weatherpic.getUrl());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WeatherpicDialogFragment wdf = new WeatherpicDialogFragment();
                wdf.setTargetFragment(mFragment, 0);
                wdf.setWeatherpicReference(mWeatherpicsArray.get(position));
                wdf.show(mFragmentManager, "hey");
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                Fragment detailFragment = new WeatherpicDetailFragment(mWeatherpicsArray.get(position));
                ft.replace(R.id.fragment, detailFragment, "Detail").addToBackStack("Back");
                ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mWeatherpicsArray.size();
    }

    /** ViewHolder class */
    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mCaption;
        public TextView mURL;

        public ViewHolder(View itemView) {
            super(itemView);

            mCaption = (TextView)itemView.findViewById(R.id.caption_text);
            mURL = (TextView)itemView.findViewById(R.id.url_text);

        }
    }

    /** Event Listener Class */
    class WeatherpicChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Weatherpic weatherpic = dataSnapshot.getValue(Weatherpic.class);
            weatherpic.setKey(dataSnapshot.getKey());
            mWeatherpicsArray.add(weatherpic);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Weatherpic weatherpic = dataSnapshot.getValue(Weatherpic.class);
            String key = dataSnapshot.getKey();
            for(int i = 0; i < mWeatherpicsArray.size(); i++){
                if(key.equals(mWeatherpicsArray.get(i).getKey())){
                    mWeatherpicsArray.get(i).setCaption(weatherpic.getCaption());
                    mWeatherpicsArray.get(i).setUrl(weatherpic.getUrl());
                }
                break;
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            for(int i = 0; i < mWeatherpicsArray.size(); i++){
                if(key.equals(mWeatherpicsArray.get(i).getKey())){
                    mWeatherpicsArray.remove(i);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

}
