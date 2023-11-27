package app.raincheck.online;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GlobalConf extends Application {
    public  static String gameURL;

    @Override
    public void onCreate() {
        super.onCreate();


    }

    public static void setupRemoteConfig(Context context, Activity activity, Boolean hasFirebase) {
        if (Boolean.TRUE.equals(hasFirebase)) {
            FirebaseApp.initializeApp(context);
            FirebaseRemoteConfig remoteCFG = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings settingsCFG = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3600)
                    .build();

            remoteCFG.setConfigSettingsAsync(settingsCFG);

            remoteCFG.fetchAndActivate().addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    Log.d("FirebaseCFG:", "Loading Successful");
                    gameURL = remoteCFG.getString("gameURL");

                    String endPoint = gameURL;
                    Log.d("WZ", gameURL);
                    RequestQueue requestQueue = Volley.newRequestQueue(context);



                } else {
                    Log.e("FirebaseCFG:", "Loading not Successful", task.getException());
                }
            });
        }
    }
}