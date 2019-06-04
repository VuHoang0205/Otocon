package org.atmarkcafe.otocon.pref;

import android.content.Context;

/**
 * columns all VnpTableColums
 * 
 * @author macmini05
 *
 */
public class BaseShareReferences {

	public static final String KEY_PREFECTURE = "DB_Prefecture";


	public static final String KEY_CITY_BY_AREA = "city_by_area";
	public static final String KEY_CITY_BY_CITIES = "KEY_CITY_BY_CITIES";

	private Context context;
	private String name;

	public BaseShareReferences(Context context, String name) {
		this.context = context;
		this.name = name;
	}

	public BaseShareReferences(Context context) {
		this.context = context;

		name = getClass().getSimpleName();
	}

	public void set(String key, String value){
		context.getSharedPreferences(name, 0). edit().putString(key,value).commit();
	}

	public void set(String key, int value){
		context.getSharedPreferences(name, 0). edit().putInt(key,value).commit();
	}

	public void set(String key, boolean value){
		context.getSharedPreferences(name, 0). edit().putBoolean(key,value).commit();
	}

	public String get(String key){
		return context.getSharedPreferences(name, 0).getString(key, "");
	}

	public int getInt(String key, int defValue){
		return context.getSharedPreferences(name, 0).getInt(key, defValue);
	}

	public boolean getBoolean(String key, boolean defValue){
		return context.getSharedPreferences(name, 0).getBoolean(key, defValue);
	}

	public void remove(String key){
		context.getSharedPreferences(name, 0).edit().remove(key).commit();
	}

	public final void clear(){
		context.getSharedPreferences(name, 0).edit().clear().commit();
	}

}