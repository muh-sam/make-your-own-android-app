package com.muh_sam.studentinformationsystem.Security;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.muh_sam.studentinformationsystem.R;

/**
 * Created by muh_s_000 on 13/03/2015.
 */
public class RegisterFragment extends Fragment {

    Spinner ques;
    EditText ans;
    Button btnRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView registerErrorMsg;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_register, container, false);


        return rootView;
    }
}
