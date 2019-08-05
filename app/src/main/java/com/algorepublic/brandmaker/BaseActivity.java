package com.algorepublic.brandmaker;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BaseActivity extends AppCompatActivity {
    private State lifecycleState = null;
    public Dialog dialog;
    public static InputMethodManager imeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLifecycleState(State.CREATED);
        imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
    }

    public void showDialog() {
        dialog.show();
    }

    public void removeDialog() {
        dialog.dismiss();
    }

    public File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "FFM");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                + ".jpg");
        return mediaFile;
    }


    public void callFragmentWithReplace(int containerId, Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .setCustomAnimations(R.anim.slide_in_enter, R.anim.slide_in_exit,
                        R.anim.slide_pop_enter, R.anim.slide_pop_exit);
        if (tag != null)
            transaction.addToBackStack(tag)
                    .commit();
        else
            transaction
                    .commit();
    }

    public void clearBackstack()
    {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public void setToast(String msg, Context context)
    {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    public void callFragment(int containerId, Fragment fragment, String tag) {


        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_enter, R.anim.slide_in_exit,
                        R.anim.slide_pop_enter, R.anim.slide_pop_exit)
                .replace(containerId, fragment, tag);

        if (tag != null)
            transaction.addToBackStack(tag).commit();
        else
            transaction.commit();
    }

    public void callFragmentWithoutAnimation(int containerId, Fragment fragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag);

        if (tag != null)
            transaction.addToBackStack(tag).commit();
        else
            transaction.addToBackStack(null).commit();
    }


    public void callFragmentAdd(int containerId, Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_enter, R.anim.slide_in_exit, R.anim.slide_pop_enter, R.anim.slide_pop_exit)
                .add(containerId, fragment, tag);
        if (tag != null) {
            transaction.addToBackStack(tag)
                    .commit();
        }
        else {
            transaction.commit();
        }
    }

    public String Get24FormatTime(String date) {
        String a = date.replaceAll("\\D+", "");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(a));

        String delegate = "hh:mm";
        String time = (String) DateFormat.format(delegate, Calendar.getInstance().getTime());

        return (time);
    }

    public String Get24FormatTime1(Long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        String delegate = "hh:mm";
        String time = (String) DateFormat.format(delegate, calendar.getTime());

        return (time);
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public String DateMilli(String date) {
        String a = date.replaceAll("\\D+", "");
        return a;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void hideKeyPad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setLifecycleState(State.STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLifecycleState(State.RESUMED);
    }

    @Override
    protected void onPause() {
        super.onPause();

        setLifecycleState(State.PAUSED);
    }


    @Override
    protected void onStop() {
        super.onStop();
        setLifecycleState(State.STOPPED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setLifecycleState(State.DESTROYED);
    }

    /**
     * @return the lifecycleState
     */
    public State getLifecycleState() {
        return lifecycleState;
    }

    /**
     * @param lifecycleState the lifecycleState to set
     */
    public void setLifecycleState(State lifecycleState) {
        this.lifecycleState = lifecycleState;
    }

    /**
     * The possibles states of an activity lifecycle.
     */
    public static enum State {
        CREATED, STARTED, RESUMED, PAUSED, STOPPED, DESTROYED;
    }
}
