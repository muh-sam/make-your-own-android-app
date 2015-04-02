package com.muh_sam.studentinformationsystem.Security;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.muh_sam.studentinformationsystem.MainActivity;
import com.muh_sam.studentinformationsystem.R;
import com.muh_sam.studentinformationsystem.data.JSONParser;
import com.muh_sam.studentinformationsystem.data.SISContract;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OnClickListener;

/**
 * Created by muh_s_000 on 13/03/2015.
 */
public class LoginFragment extends Fragment {

    Button btnLogin;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    ProgressBar progressBar;
    private static String login_tag= "login";

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        inputEmail = (EditText)rootView.findViewById(R.id.loginEmail);
        inputPassword=(EditText)rootView.findViewById(R.id.loginPassword);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        loginErrorMsg = (TextView)rootView.findViewById(R.id.login_error);
        btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        Cursor cursor = getActivity().getContentResolver().query(SISContract.SisMenu.CONTENT_URI, new String[]{SISContract.SisMenu._ID}, null, null, null);

        if(cursor.moveToFirst()){

        }else
        {
            String[] sisArray = new String[]{
                    "Date sheet",
                    "Timetable"
            };
            for(int i=0; i<sisArray.length; i++)
            {
                ContentValues menuValues = new ContentValues();
                menuValues.put(SISContract.SisMenu.COLUMN_NAME_TEXT, sisArray[i]);
                getActivity().getContentResolver().insert(SISContract.SisMenu.CONTENT_URI, menuValues);
            }
        }

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtEmail=inputEmail.getText().toString();
                String password=inputPassword.getText().toString();
                Log.d("Button", "Login");
                List<NameValuePair> params=new ArrayList<>();
                params.add(new BasicNameValuePair("tag", login_tag));
                params.add(new BasicNameValuePair("email", edtEmail));
                params.add(new BasicNameValuePair("password", password));
                FetchWebData webData = new FetchWebData(getActivity());

                webData.execute(params);
            }
        });
        return rootView;
    }

    public class FetchWebData extends AsyncTask<List<NameValuePair>, Void, String[]> {
        Context mContext;
        JSONObject json;
        private String data_tag="student";
        private  String KEY_SUCCESS = "success";
        private  String KEY_ERROR_MSG = "error_msg";
        private String webURL="http://bzu.comze.com/api_access.php";
        public FetchWebData(Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String[] s) {
            if(s[0].equals("error")){
                progressBar.setVisibility(View.GONE);
                loginErrorMsg.setText(s[1]);
            }
            else {
                progressBar.setVisibility(View.GONE);
                Intent main = new Intent(getActivity(), MainActivity.class);
                // Close all views before launching Dashboard

                String[] sisMenu = new String[]{

                };

                for (int i = 0; i < sisMenu.length; i++) {
                    ContentValues menus = new ContentValues();
                    menus.put(SISContract.SisMenu.COLUMN_NAME_TEXT, sisMenu[i]);
                    getActivity().getContentResolver().insert(SISContract.SisMenu.CONTENT_URI, menus);
                }
                main.addFlags(main.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);

                super.onPostExecute(s);
            }
        }
        @Override
        protected String[] doInBackground(List<NameValuePair>... params) {
            JSONParser jsonParser = new JSONParser(mContext);

            json = jsonParser.getJSONFromUrl(webURL, params[0]);
            String[] sisSemester;
            try
            {
                if (json.getString(KEY_SUCCESS) != null)
                {

                    String res = json.getString(KEY_SUCCESS);
                    if(Integer.parseInt(res) == 1)
                    {
                        JSONObject json_user = json.getJSONObject("user");
                        String KEY_NAME = "name";
                        String KEY_EMAIL = "email";
                        String KEY_CLASS = "classs";
                        String KEY_SESSION = "session";
                        String KEY_ROLLNUM = "rollno";
                        String name = json_user.getString(KEY_NAME);
                        String email = json_user.getString(KEY_EMAIL);
                        List<NameValuePair> paramsData=new ArrayList<>();
                        paramsData.add(new BasicNameValuePair("tag", data_tag));
                        paramsData.add(new BasicNameValuePair("email", email));
                        JSONObject json_data = jsonParser.getJSONFromUrl(webURL, paramsData);
                        JSONObject json_data_user = json_data.getJSONObject("user");
                        String classs =json_data_user.getString(KEY_CLASS).toString();
                        String session=json_data_user.getString(KEY_SESSION).toString();
                        String rollno=json_data_user.getString(KEY_ROLLNUM).toString();
                        ContentValues values = new ContentValues();
                        values.put(SISContract.SisUser.COLUMN_NAME_TEXT, name);
                        values.put(SISContract.SisUser.COLUMN_EMAIL_TEXT, email);
                        values.put(SISContract.SisUser.COLUMN_CLASS_TEXT, classs);
                        values.put(SISContract.SisUser.COLUMN_SESSION_TEXT, session);
                        values.put(SISContract.SisUser.COLUMN_ROLLNO_TEXT, rollno);
                        getActivity().getContentResolver().insert(SISContract.SisUser.CONTENT_URI, values);

                        if(classs.toUpperCase().contains("MCS"))
                        {
                            sisSemester = new String[]{
                                    "1st",
                                    "2nd",
                                    "3rd",
                                    "4th"
                            };
                        }
                        else {
                            sisSemester = new String[]{
                                    "1st",
                                    "2nd",
                                    "3rd",
                                    "4th",
                                    "5th",
                                    "6th",
                                    "7th",
                                    "8th"
                            };
                        }
                        Uri semesterUri;
                        long semesterId;
                        for(int i=0; i<sisSemester.length; i++)
                        {
                            ContentValues semesterValues = new ContentValues();
                            semesterValues.put(SISContract.SisSemester.COLUMN_NAME_TEXT, sisSemester[i]);
                            semesterUri =  getActivity().getContentResolver().insert(SISContract.SisSemester.CONTENT_URI, semesterValues);
                            semesterId =  ContentUris.parseId(semesterUri);
                            List<NameValuePair> paramsTimetable = new ArrayList<NameValuePair>();
                            paramsTimetable.add(new BasicNameValuePair("tag", "timetable"));
                            paramsTimetable.add(new BasicNameValuePair("classs", classs));
                            paramsTimetable.add(new BasicNameValuePair("session", session));
                            paramsTimetable.add(new BasicNameValuePair("semester", sisSemester[i]));
                            // Getting JSONObject
                            json = jsonParser.getJSONFromUrl(webURL, paramsTimetable);

                            if(json.getString(KEY_SUCCESS) != null)
                            {
                                String resTime = json.getString(KEY_SUCCESS);
                                if(Integer.parseInt(resTime)==1)
                                {
                                    JSONObject json_Time = json.getJSONObject("user");

                                    for(int j = 1; j < 7;  j++)
                                    {
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(SISContract.SisTimetable.COLUMN_SUBJECT_TEXT, json_Time.getString("sub_name" + j));
                                        contentValues.put(SISContract.SisTimetable.COLUMN_TEACHER_TEXT, json_Time.getString("teacher" + j));
                                        contentValues.put(SISContract.SisTimetable.COLUMN_DAY_TEXT,json_Time.getString("day"+j));
                                        contentValues.put(SISContract.SisTimetable.COLUMN_TIME_TEXT, json_Time.getString("time" + j));
                                        contentValues.put(SISContract.SisTimetable.COLUMN_SEMESTER_KEY, semesterId);
                                        getActivity().getContentResolver().insert(SISContract.SisTimetable.CONTENT_URI, contentValues);
                                    }
                                }
                            }

                            List<NameValuePair> paramsDatesheet = new ArrayList<NameValuePair>();
                            paramsDatesheet.add(new BasicNameValuePair("tag", "datesheet"));
                            paramsDatesheet.add(new BasicNameValuePair("classs", classs));
                            paramsDatesheet.add(new BasicNameValuePair("session", session));
                            paramsDatesheet.add(new BasicNameValuePair("semester", sisSemester[i]));
                            // Getting JSONObject
                            json = jsonParser.getJSONFromUrl(webURL, paramsDatesheet);

                            if(json.getString(KEY_SUCCESS) != null)
                            {
                                String resDatesheet = json.getString(KEY_SUCCESS);
                                if(Integer.parseInt(resDatesheet)==1)
                                {
                                    JSONObject json_datesheet = json.getJSONObject("user");
                                    for(int j=1; j<7; j++) {

                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(SISContract.SisDatesheet.COLUMN_SUBJECT_TEXT, json_datesheet.getString("sub_name"+j));
                                        contentValues.put(SISContract.SisDatesheet.COLUMN_DATE_TEXT,json_datesheet.getString("date"+j));
                                        contentValues.put(SISContract.SisDatesheet.COLUMN_TIME_TEXT, json_datesheet.getString("time"+j));
                                        contentValues.put(SISContract.SisDatesheet.COLUMN_SEMESTER_KEY, semesterId);
                                        getActivity().getContentResolver().insert(SISContract.SisDatesheet.CONTENT_URI, contentValues);
                                    }
                                }
                            }
                        }

                        return new String[]{
                            "success"
                        };
                    }
                    else
                    {

                        return new String[]{
                                "error"
                                ,
                                json.getString(KEY_ERROR_MSG)
                        };
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }


            return null;
        }


    }
}
