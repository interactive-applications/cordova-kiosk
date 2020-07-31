package jk.cordova.plugin.kiosk;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import org.apache.cordova.*;
import android.widget.*;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import jk.cordova.plugin.kiosk.KioskActivity;
import org.json.JSONObject;
import java.lang.Integer;
import java.util.HashSet;

public class KioskPlugin extends CordovaPlugin {
    
    public static final String SET_KIOSK_ENABLED = "setKioskEnabled";
    public static final String EXIT_KIOSK = "exitKiosk";
    public static final String IS_IN_KIOSK = "isInKiosk";
    public static final String IS_SET_AS_LAUNCHER = "isSetAsLauncher";
    public static final String SET_ALLOWED_KEYS = "setAllowedKeys";
    public static final String SET_CLOSE_SYSTEM_DIALOG_INTERVAL_MILLIS = "setCloseSystemDialogIntervalMillis";
    public static final String SET_CLOSE_SYSTEM_DIALOG_DURATION_MILLIS = "setCloseSystemDialogDurationMillis";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (IS_IN_KIOSK.equals(action)) {
                
                callbackContext.success(Boolean.toString(KioskActivity.running && KioskActivity.kioskEnabled));
                return true;
                
            } else if (IS_SET_AS_LAUNCHER.equals(action)) {
                
                String myPackage = cordova.getActivity().getApplicationContext().getPackageName();
                callbackContext.success(Boolean.toString(myPackage.equals(findLauncherPackageName())));
                return true;
                
            } else if (SET_KIOSK_ENABLED.equals(action)) {
                
                KioskActivity.kioskEnabled = args.getBoolean(0);
                callbackContext.success();
                return true;
                
            } else if (SET_CLOSE_SYSTEM_DIALOG_INTERVAL_MILLIS.equals(action)) {
                
                KioskActivity.closeSystemDialogIntervalMillis = args.getInt(0);
                callbackContext.success();
                return true;
                
            } else if (SET_CLOSE_SYSTEM_DIALOG_DURATION_MILLIS.equals(action)) {
                
                KioskActivity.closeSystemDialogDurationMillis = args.getInt(0);
                callbackContext.success();
                return true;
                
            } else if (EXIT_KIOSK.equals(action)) {
                
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                
                Intent chooser = Intent.createChooser(intent, "Select destination...");
                if (intent.resolveActivity(cordova.getActivity().getPackageManager()) != null) {
                    cordova.getActivity().startActivity(chooser);
                }
                
                callbackContext.success();
                return true;
                
            } else if (SET_ALLOWED_KEYS.equals(action)) {
                
                System.out.println("setAllowedKeys: " + args.toString());
                HashSet<Integer> allowedKeys = new HashSet<Integer>();
                for (int i = 0; i < args.length(); i++) {
                    allowedKeys.add(args.optInt(i));
                }
                KioskActivity.allowedKeys = allowedKeys;
                
                callbackContext.success();
                return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }
    
    private String findLauncherPackageName() {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = this.cordova.getActivity().getPackageManager().resolveActivity(intent, 0);
        return res.activityInfo.packageName;
    }
}

