package com.activeitzone.activeecommercecms.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.activeitzone.activeecommercecms.Network.response.AppSettingsResponse;
import com.activeitzone.activeecommercecms.Network.response.AuthResponse;
import com.google.gson.Gson;

public class UserPrefs{

    private static final String PREFS_NAME = "com.activeitzone.cms.UserPrefs";

    private static SharedPreferences settings;

    private static SharedPreferences.Editor editor;

    public UserPrefs(Context ctx){
        if(settings == null){
            settings = ctx.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE );
        }
        editor = settings.edit();
    }

    public void setAuthPreferenceObject(AuthResponse authResponse, String key) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(authResponse);
        editor.putString(key, jsonObject);
        editor.commit();
    }

    public AuthResponse getAuthPreferenceObjectJson(String key) {
        String json = settings.getString(key, "");
        Gson gson = new Gson();
        AuthResponse authResponse = gson.fromJson(json, AuthResponse.class);
        return authResponse;
    }

    public void setAppSettingsPreferenceObject(AppSettingsResponse appSettingsResponse, String key) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(appSettingsResponse);
        editor.putString(key, jsonObject);
        editor.commit();
    }

    public AppSettingsResponse getAppSettingsPreferenceObjectJson(String key) {
        String json = settings.getString(key, "");
        Gson gson = new Gson();
        AppSettingsResponse appSettingsResponse = gson.fromJson(json, AppSettingsResponse.class);
        return appSettingsResponse;
    }

    public void clearPreference(){
        editor.clear().commit();
    }
}
