package edu.rosehulman.defaritl.auth;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import edu.rosehulman.defaritl.weatherpics.Constants;
import edu.rosehulman.defaritl.weatherpics.R;

/**
 * Created by Thais Faria on 1/26/2016.
 */
public class LoginFragment extends android.support.v4.app.Fragment{

    private boolean mLoggingIn;
    private EditText mPasswordView;
    private EditText mEmailView;
    private View mLoginForm;
    private Button mLoginEmailButton;
    private Button mLoginFacebookButton;
    private OnLoginListener mListener;

    public LoginFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoggingIn = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mEmailView = (EditText) rootView.findViewById(R.id.login_email_edit_text);
        mPasswordView = (EditText) rootView.findViewById(R.id.login_password_edit_text);
        mLoginEmailButton = (Button) rootView.findViewById(R.id.login_email_button);
        mLoginFacebookButton = (Button) rootView.findViewById(R.id.login_facebook_button);

        mLoginEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoginEmail(mEmailView.getText().toString(), mPasswordView.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mListener = (OnLoginListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnLoginListener {
        void onLoginEmail(String email, String password);

        void onFacebookLogin();
    }


}
