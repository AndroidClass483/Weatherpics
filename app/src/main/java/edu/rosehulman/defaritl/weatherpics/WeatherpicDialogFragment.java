package edu.rosehulman.defaritl.weatherpics;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

/**
 * Created by defaritl on 1/21/2016.
 */
public class WeatherpicDialogFragment extends DialogFragment {

    MyDialogCallbackInterface mHost;
    private Weatherpic mWeatherpic;

    public WeatherpicDialogFragment(){
        mWeatherpic = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mHost = (MyDialogCallbackInterface) getTargetFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_dialog_weather, null);

        final EditText caption = (EditText) view.findViewById(R.id.edit_caption);
        final EditText url = (EditText) view.findViewById(R.id.edit_URL);

        if(mWeatherpic != null){
            caption.setText(mWeatherpic.getCaption());
            url.setText(mWeatherpic.getUrl());
        }

        Firebase firebase = new Firebase(Constants.FIREBASE_URL);

        if(mWeatherpic != null){
            builder.setView(view)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                          mHost.callbackAddButton(caption.getText().toString(), url.getText().toString());
                            mWeatherpic.setCaption(caption.getText().toString());
                            mWeatherpic.setUrl(url.getText().toString());

                            mHost.callbackEditButton(mWeatherpic);
                        }
                    })
                    .setNeutralButton(R.string.alert_dialog_button_delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mHost.callbackDeleteButton(mWeatherpic.getKey());
                        }
                    });
        }else{
            builder.setView(view)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!url.getText().toString().equals("")) {
                                mHost.callbackAddButton(caption.getText().toString(), url.getText().toString());
                            } else {
                                mHost.callbackAddButton(getString(R.string.text_random), Util.randomImageUrl());
                            }
                        }
                    });
        }


        builder.setView(view)
                .setNegativeButton(android.R.string.cancel, null);

        builder.setTitle("Add a Weatherpic");

        return builder.create();
    }

    public void setWeatherpicReference(Weatherpic weatherpic){
        this.mWeatherpic = weatherpic;
    }
}
