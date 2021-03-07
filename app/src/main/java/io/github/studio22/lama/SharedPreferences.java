package io.github.studio22.lama;

import android.content.Context;

public class SharedPreferences {
    android.content.SharedPreferences systemShare;

    public SharedPreferences(Context context){
        systemShare = context.getSharedPreferences("lama_sys_var", Context.MODE_PRIVATE);
    }

    public void setNightModeState(Boolean state){
        android.content.SharedPreferences.Editor editor = systemShare.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    public Boolean loadNightModeState(){
        return systemShare.getBoolean("NightMode", false);
    }
}
