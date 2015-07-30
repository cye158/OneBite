package EULA;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.ironsquishy.biteclub.R;
import com.ironsquishy.biteclub.SplashActivity;

public class EULA {

    private String EULA_PREFIX = "eula_";
    private Activity mActivity;

    public EULA(Activity context) {
        mActivity = context;
    }

    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

    public void show() {
        PackageInfo versionInfo = getPackageInfo();

        // the eulaKey changes every time you increment the version number in the AndroidManifest.xml
        final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean hasBeenShown = false;
        prefs.getBoolean(eulaKey, false);
        if(hasBeenShown == false){

            // Show the Eula
            String title = mActivity.getString(R.string.app_name) + " v" + versionInfo.versionName;

            //Includes the updates as well so users know what changed. 
            String message = mActivity.getString(R.string.eula_title)
                    + "\n\n" + mActivity.getString(R.string.eula_copyright)
                    + "\n\n" + mActivity.getString(R.string.eula_license)
                    + "\n\n" + mActivity.getString(R.string.eula_disclaimer);

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.agree_eula, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Mark this version as read.
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(eulaKey, true);
                            editor.commit();
                            dialogInterface.dismiss();
                            mActivity.startActivity(new Intent(mActivity, SplashActivity.class));
                            mActivity.finish();
                        }
                    })
                    .setNegativeButton(R.string.disagree_eula, new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Close the activity as they have declined the EULA
                            mActivity.finish();
                        }

                    });
            builder.create().show();
        } else {
            mActivity.startActivity(new Intent(mActivity, SplashActivity.class));
            mActivity.finish();
        }
    }
}