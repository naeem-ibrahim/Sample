package com.algorepublic.brandmaker.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.algorepublic.brandmaker.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created on 23/10/2017.
 */

public class Helper {

    public static final String DATE_FORMAT_1 = "MMM-dd-yyyy  hh:mm:ss";
    public static final String DATE_FORMAT_2 = "MMM dd";
    public static final String DATE_FORMAT_3 = "hh:mm a";
    public static final String DATE_FORMAT_5 = "dd-MM-yyyy hh:mma";
    public static final String DATE_FORMAT_4 = "MM/dd/yyyy hh:mm a";
    private static final String TAG = "Util";
    private static String Alarm_action = "alarm_action";

    public static String encodeBase64(String s) throws UnsupportedEncodingException {
        byte[] data = s.getBytes("UTF-8");
        String encoded = Base64.encodeToString(data, Base64.NO_WRAP);
        return encoded;
    }

//    public static String getAuthorizationHeader(Context context) throws UnsupportedEncodingException {
//        String username = PreferenceUtil.getInstance(context).getUsername();
//        String password = PreferenceUtil.getInstance(context).getPassword();
//
//        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
//            return encodeBase64(username + ":" + password);
//        } else {
//            return null;
//        }
//    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static Drawable getFlagIconForCountry(Context context, String name) {
        try {
            int identifier = context.getResources().getIdentifier("ic_" + name.toLowerCase(), "drawable", context.getPackageName());
            if (identifier != 0) {
                return ResourcesCompat.getDrawable(
                        context.getResources(),
                        identifier,
                        null
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String toMd5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getHelperRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("E", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getFilePath(Context context, Uri uri) throws FileNotFoundException {
        String id = DocumentsContract.getDocumentId(uri);
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        File file = new File(context.getCacheDir().getAbsolutePath() + "/" + id);
        writeFile(inputStream, file);
        String filePath = file.getAbsolutePath();
        return filePath;

    }

    public static void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {

//        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String convertListToString(List myLit) {
        Gson gson = new Gson();
        String Json = gson.toJson(myLit);
        return Json;
    }

    public static Dialog progressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressbar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(R.color.trans_black_light);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }


    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }


//    public static String getValidNumber(String number, String countryCode) {
//        String formattedNumber = "";
//        Phonenumber.PhoneNumber phoneNumber = null;
//        try {
//            phoneNumber = PhoneNumberUtil.getInstance().parse(number, countryCode);
//            formattedNumber = PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
//
//            if (PhoneNumberUtil.getInstance().isValidNumber(phoneNumber)) {
//                return formattedNumber;
//            } else {
//                return null;
//            }
//        } catch (NumberParseException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static Calendar formatDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(format.format(calendar.getTime())));
        return calendar;

    }

    public static Calendar formatDateCalender(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_5);
        Calendar calendar = null;
        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTimeInMillis(calendar.getTime().getTime());
        return calendar;

    }

    public static String formatDateForChart(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date) * 1000);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String formatDateForMessagesHistory(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_3);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date) * 1000);
        return simpleDateFormat.format(calendar.getTime());
    }


    public static String formatDate(String format, long dateInMilli) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateInMilli);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Date getDateFromMilliseconds(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.getTime();
    }

    public static int getDifferenceInDays(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        return (int) elapsedDays;
    }


    public static String getDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        if (elapsedDays > 0) {
            return elapsedDays + " day(s) ago";
        } else if (elapsedHours > 0) {
            return elapsedHours + " hours ago";
        } else if (elapsedMinutes > 0) {
            return elapsedMinutes + " minutes ago";
        } else if (elapsedSeconds > 0) {
            return elapsedSeconds + " seconds ago";
        } else if (different < 60) {
            return " just now";
        }
        return "";
    }

    public static boolean isTheSameDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        boolean isSameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return isSameDay;
    }

    public static boolean isYesterdayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterdayDate = calendar.getTime();

        return isTheSameDays(yesterdayDate, date);
    }

    public static boolean isWeekAgo(Date date) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        boolean isSameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

        if (isSameMonth) {
            int days = getDifferenceInDays(date, cal1.getTime());
            return days <= 7;
        } else {
            return false;
        }
    }

    public static String getDeliveryStatus(Long dueDate) {
        if (dueDate == null)
            return "Expired";
        Date mDueDate = getDateFromMilliseconds(dueDate);
        Date mCurrentDate = Calendar.getInstance().getTime();
        float differenceInDays = getDifferenceInDays(mCurrentDate, mDueDate);

        if (mCurrentDate.after(mDueDate))
            return "Expired";

        if (isTheSameDays(mDueDate, mCurrentDate))
            return "Expire in " + getDifference(mCurrentDate, mDueDate);

        if (differenceInDays < 1)
            return "Due tomorrow";

        if (differenceInDays < 7)
            return "Due in " + (int) Math.ceil((double) differenceInDays) + " day(s)";

        if (differenceInDays >= 7)
            return "Due in " + (int) Math.ceil((double) differenceInDays / 7.0) + " week(s)";
        return "";
    }

    public static String getSyncTime(Long lastSyncTime) {
        if (lastSyncTime == null)
            return "Not synced yet";
        Date mElapsedDate = getDateFromMilliseconds(lastSyncTime);
        Date mCurrentDate = Calendar.getInstance().getTime();
        String difference = getDifference(mElapsedDate, mCurrentDate);
        if (difference.isEmpty()) return difference;
        return "Last synced " + difference;
    }

    public static String toCurrency(double price, String currency) {
        NumberFormat numberFormat = new DecimalFormat();
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        String formattedValue = numberFormat.format(price);
        return currency + formattedValue;
    }

    public static String toCurrency(double price, String currency, int minFraction, int maxFraction) {
        NumberFormat numberFormat = new DecimalFormat();
        numberFormat.setMinimumFractionDigits(minFraction);
        numberFormat.setMaximumFractionDigits(maxFraction);
        String formattedValue = numberFormat.format(price);
        return currency + formattedValue;
    }


    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean containsUnicode(String text) {
        char[] symbols = text.toCharArray();
        for (int i = 0; i < symbols.length; i++) {
            if (Character.UnicodeBlock.of(symbols[i]) != Character.UnicodeBlock.BASIC_LATIN) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPassword(final String password) {

        if (password != null) {
            Pattern pattern;
            Matcher matcher;

            //final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();
        } else {
            return false;
        }
    }

    public static boolean isValidEmail(final String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }



    public static boolean isLocationServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public static boolean isSystemPackage(ResolveInfo resolveInfo) {
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public static Bitmap drawablToBitmap(Drawable icon) {
        Bitmap bitmap = null;
        if (icon != null) {
            bitmap = ((BitmapDrawable) icon).getBitmap();
        }

        return bitmap;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        encoded = "data:image/png;base64," + encoded;
        return encoded;
    }

    public static String fileToBase64(File file) {
        if (file.exists() && file.length() > 0) {
            Bitmap bm = BitmapFactory.decodeFile(file.getPath());
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bOut);
            String encoded = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
            encoded = "data:image/png;base64," + encoded;
            return encoded;
        }
        return null;
    }

    public static void startFromPakage(String pkgName, Context context) {
        PackageManager pm = context.getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(pkgName);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        }
    }

    public static void startFromLink(String link, Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(browserIntent);

    }

    public static boolean isAvailable(Context ctx, Intent intent) {
        if (intent == null)
            return false;
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static Date convertStringDate(String dateC) {
        String dtStart = dateC;
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_1);
        Date date = null;
        try {
            date = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    public static int getOrientation(final String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface
                    .ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;

    }

    public static Bitmap rotateBitmap(Bitmap source, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public static File copyFileOrDirectory(String srcDir, String dstDir) {

        File finalFile = null;
        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    return copyFileOrDirectory(src1, dst1);

                }
            } else {
                finalFile = copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalFile;
    }


    public static File copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }

        return destFile;
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public static int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public static String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

//    public static Geofence createGeofence(double latitude, double longitude) {
//        int radius = 20;
//        String id = UUID.randomUUID().toString();
//        return new Geofence.Builder()
//                .setRequestId(id)
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_EXIT | Geofence.GEOFENCE_TRANSITION_ENTER)
//                .setCircularRegion(latitude, longitude, radius)
//                .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                .setNotificationResponsiveness(0)
//                .build();
//    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public static Bundle getFacebookData(JSONObject object, Context context) {

        try {
            if (object != null) {
                Bundle bundle = new Bundle();
                String id = object.getString("id");

                try {
                    URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                    Log.i("profile_pic", profile_pic + "");
                    bundle.putString("profile_pic", profile_pic.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                }

                bundle.putString("idFacebook", id);
                if (object.has("first_name"))
                    bundle.putString("first_name", object.getString("first_name"));
                if (object.has("last_name"))
                    bundle.putString("last_name", object.getString("last_name"));
                if (object.has("email"))
                    bundle.putString("email", object.getString("email"));
                if (object.has("gender"))
                    bundle.putString("gender", object.getString("gender"));
                if (object.has("birthday"))
                    bundle.putString("birthday", object.getString("birthday"));
                if (object.has("location"))
                    bundle.putString("location", object.getJSONObject("location").getString("name"));

                return bundle;

            } else {
                Toast.makeText(context, "Connection error please restart app!", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            Log.d("Error", "Error parsing JSON");
        }
        return null;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

//    public static boolean isSignupValidation(User user, Context con) {
////
////        if (!isValidEmail(user.getEmail())) {
////            Toast.makeText(con, "Invalid email", Toast.LENGTH_SHORT).show();
////            return false;
////        } else if (user.getName() == null) {
////            Toast.makeText(con, "Enter your name", Toast.LENGTH_SHORT).show();
////            return false;
////        } else if (user.getPhone() == null) {
////            Toast.makeText(con, "Enter your Phone no", Toast.LENGTH_SHORT).show();
////            return false;
////        } else if (user.getPassword() == null) {
////            Toast.makeText(con, "Enter your Password", Toast.LENGTH_SHORT).show();
////            return false;
////        } else if (user.getPasswordAgain() == null) {
////            Toast.makeText(con, "Enter your Password again", Toast.LENGTH_SHORT).show();
////            return false;
////        } else if (!user.getPassword().equalsIgnoreCase(user.getPasswordAgain().toString())) {
////            Toast.makeText(con, "You have to enter the same password", Toast.LENGTH_SHORT).show();
////            return false;
////        } else {
////            return true;
////        }
//    }

    public static boolean isEmpty(String chara) {

        if (chara == null) {
            return true;
        } else if (chara.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public static String getTimewithAmPm(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }


    //////////////////////////////////////////////////////////////////////////

    @SuppressLint("LongLogTag")
    public static long addAppointmentsToCalender(Activity curActivity, String title,
                                                 String desc, String place, int status, long startDate,
                                                 boolean needReminder) {
/***************** Event: add event *******************/
        long eventID = -1;
        try {
            String eventUriString = "content://com.android.calendar/events";
            ContentValues eventValues = new ContentValues();
            eventValues.put("calendar_id", 1); // id, We need to choose from
            // our mobile for primary its 1
            eventValues.put("title", title);
            eventValues.put("description", desc);
            eventValues.put("eventLocation", place);

            long endDate = startDate + 1000 * 10 * 10; // For next 10min
            eventValues.put("dtstart", startDate);
            eventValues.put("dtend", endDate);

            // values.put("allDay", 1); //If it is bithday alarm or such
            // kind (which should remind me for whole day) 0 for false, 1
            // for true
            eventValues.put("eventStatus", status); // This information is
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):
            eventValues.put("eventTimezone", "UTC/GMT +5:30");
            /*
             * Comment below visibility and transparency column to avoid
             * java.lang.IllegalArgumentException column visibility is invalid
             * error
             */
            // eventValues.put("allDay", 1);
            // eventValues.put("visibility", 0); // visibility to default (0),
            // confidential (1), private
            // (2), or public (3):
            // eventValues.put("transparency", 0); // You can control whether
            // an event consumes time
            // opaque (0) or transparent (1).

            eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

            Uri eventUri = curActivity.getApplicationContext()
                    .getContentResolver()
                    .insert(Uri.parse(eventUriString), eventValues);
            eventID = Long.parseLong(eventUri.getLastPathSegment());

            if (needReminder) {
                /***************** Event: Reminder(with alert) Adding reminder to event ***********        ********/

                String reminderUriString = "content://com.android.calendar/reminders";
                ContentValues reminderValues = new ContentValues();
                reminderValues.put("event_id", eventID);
                reminderValues.put("minutes", 5); // Default value of the
                // system. Minutes is a integer
                reminderValues.put("method", 1); // Alert Methods: Default(0),
                // Alert(1), Email(2),SMS(3)

                Uri reminderUri = curActivity.getApplicationContext()
                        .getContentResolver()
                        .insert(Uri.parse(reminderUriString), reminderValues);
            }

        } catch (Exception ex) {
            Log.e("Error in adding event on calendar", ex.getMessage());
        }

        return eventID;

    }

    public static Date getDate(String sdate) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.US);
        try {
            date = format.parse(sdate);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static boolean checkDOB(int day, int month, int Year) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Log.e("year", String.valueOf(year));
        if (!((day >= 1) && (day <= 31))) {
            return false;
        }
        if (!((month >= 1) && (month <= 12))) {
            return false;
        }
        if (!((year >= year - 60) && (year <= year))) {
            return false;
        } else {
            return true;
        }


    }

    public static boolean checkHeight(int foot, int inches) {
        if (!((inches >= 0) && (inches <= 12))) {
            return false;
        }
        if (!((foot >= 1) && (foot <= 9))) {
            return false;
        } else {
            return true;
        }

    }


    public static void BitmapToFile(Bitmap bitmap, Context context) {
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOutputStream = null;
        File mydir = new File(path + "/Profile/");
        if (!mydir.exists()) {
            mydir.mkdirs();
        }

        try {

            File file = new File(mydir, "profile.jpg");
            if (file.exists()) {
                file.delete();
            }

            fOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOutputStream);
            fOutputStream.flush();
            fOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Save Failed", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    public static File getBitmapFile() {
        String path = Environment.getExternalStorageDirectory().toString();
        File dir = new File(path + "/Profile/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path + "/Profile/", "profile.jpg");
        return file;
    }

    public static String getFilePath() {
        String path = Environment.getExternalStorageDirectory().toString();
        File dir = new File(path + "/Profile/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path + "/Profile/" + "profile.jpg";
    }

    public static void getHashKey(Context context) {
        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo("com.example.dell.paigham_e_nikkah", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static void snackBarWithAction(View root, Context context, String errorMsg) {
        final Snackbar actionSnackBar = Snackbar.make(root, errorMsg, Snackbar.LENGTH_INDEFINITE);
        actionSnackBar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        View custom = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null);
        actionSnackBar.getView().setPadding(0, 0, 0, 0);
        ((ViewGroup) actionSnackBar.getView()).removeAllViews();
        ((ViewGroup) actionSnackBar.getView()).addView(custom);
        TextView tv_msg = custom.findViewById(R.id.tv_msg);
        TextView tv_action = custom.findViewById(R.id.tv_action);
        CardView cv=custom.findViewById(R.id.cv);
        tv_msg.setText(errorMsg);
        tv_action.setText("OK");
        tv_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSnackBar.dismiss();
            }
        });
        actionSnackBar.show();
        YoYo.with(Techniques.StandUp)
                .duration(500)
                .repeat(0)
                .playOn(cv);

    }

    public static Snackbar showSnack(View root, Context cont, String message) {
        try {
            Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(cont, R.color.transparent));
            View custom = LayoutInflater.from(cont).inflate(R.layout.custom_snackbar, null);
            snackbar.getView().setPadding(0, 0, 0, 0);
            ((ViewGroup) snackbar.getView()).removeAllViews();
            ((ViewGroup) snackbar.getView()).addView(custom);

            View view3 = custom.findViewById(R.id.view3);
            TextView tv_msg = custom.findViewById(R.id.tv_msg);
            TextView tv_action = custom.findViewById(R.id.tv_action);
            CardView cv=custom.findViewById(R.id.cv);
            tv_action.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            tv_msg.setText(message);
            snackbar.show();
            YoYo.with(Techniques.StandUp)
                    .duration(500)
                    .repeat(0)
                    .playOn(cv);

            return snackbar;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    public static void retrySnackBar(Context context, View root, String errorMsg, final RetryCallBack retryCallBack) {
        Snackbar snackbar = Snackbar.make(root, errorMsg, Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        View custom = LayoutInflater.from(context).inflate(R.layout.custom_snackbar, null);
        snackbar.getView().setPadding(0, 0, 0, 0);
        ((ViewGroup) snackbar.getView()).removeAllViews();
        ((ViewGroup) snackbar.getView()).addView(custom);
        TextView tv_msg = custom.findViewById(R.id.tv_msg);
        TextView tv_action = custom.findViewById(R.id.tv_action);
        CardView cv=custom.findViewById(R.id.cv);
        tv_msg.setText(errorMsg);
        tv_action.setText("Retry");
        tv_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryCallBack.retry();
            }
        });
        snackbar.show();
        YoYo.with(Techniques.StandUp)
                .duration(500)
                .repeat(0)
                .playOn(cv);


    }

    public interface RetryCallBack {
        void retry();
    }


}
