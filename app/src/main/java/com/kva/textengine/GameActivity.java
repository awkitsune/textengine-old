package com.kva.textengine;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.support.v7.app.AppCompatActivity;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class GameActivity extends AppCompatActivity {
	
	
	private String gamedir = "";
	private double n = 0;
	private String json = "";
	private String bookname = "";
	private HashMap<String, Object> bookInfo = new HashMap<>();
	private String info = "";
	private double nMax = 0;
	private HashMap<String, Object> kostyl_1 = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> book = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout linear1;
	private TextView name;
	private TextView author;
	private TextView text;
	private LinearLayout linear2;
	private Button button1;
	private Button button2;
	
	private SharedPreferences saves;
	private AlertDialog.Builder gameover;
	private Intent chact = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.game);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		name = (TextView) findViewById(R.id.name);
		author = (TextView) findViewById(R.id.author);
		text = (TextView) findViewById(R.id.text);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		saves = getSharedPreferences("saves", Activity.MODE_PRIVATE);
		gameover = new AlertDialog.Builder(this);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (n == nMax) {
					gameover.setTitle("Игра закончена");
					gameover.setMessage("Хотите начать сначала?");
					gameover.setPositiveButton("Да", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							n = 1;
							_setData();
						}
					});
					gameover.setNegativeButton("Неа...", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							saves.edit().putString(bookname.concat("_completed"), "1").commit();
							chact.setClass(getApplicationContext(), MainActivity.class);
							chact.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(chact);
						}
					});
					gameover.create().show();
				}
				else {
					n = Double.parseDouble(book.get((int)3).get(String.valueOf((long)(n))).toString());
					_setData();
				}
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (n == nMax) {
					gameover.setTitle("Игра закончена");
					gameover.setMessage("Хотите начать сначала?");
					gameover.setPositiveButton("Да", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							n = 1;
							_setData();
						}
					});
					gameover.setNegativeButton("Неа...", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							saves.edit().putString(bookname.concat("_completed"), "1").commit();
							chact.setClass(getApplicationContext(), MainActivity.class);
							chact.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(chact);
						}
					});
					gameover.create().show();
				}
				else {
					n = Double.parseDouble(book.get((int)4).get(String.valueOf((long)(n))).toString());
					_setData();
				}
			}
		});
	}
	private void initializeLogic() {
		gamedir = getIntent().getStringExtra("gamedir");
		bookname = Uri.parse(gamedir).getLastPathSegment();
		json = FileUtil.readFile(gamedir.concat("/data.json"));
		info = FileUtil.readFile(gamedir.concat("/info.json"));
		n = 1;
		book.clear();
		bookInfo = new HashMap<>();
		kostyl_1 = new HashMap<>();
		book = new Gson().fromJson(json, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		bookInfo = new Gson().fromJson(info, new TypeToken<HashMap<String, Object>>(){}.getType());
		kostyl_1 = book.get((int)4);
		if (saves.getString(bookname, "").equals("")) {
			saves.edit().putString(bookname, String.valueOf((long)(n))).commit();
		}
		else {
			n = Double.parseDouble(saves.getString(bookname, ""));
		}
		nMax = kostyl_1.size() - 1;
		name.setText(bookInfo.get("name").toString());
		author.setText("by ".concat(bookInfo.get("author").toString()));
		_setData();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		saves.edit().putString(bookname, String.valueOf((long)(n))).commit();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		saves.edit().putString(bookname, String.valueOf((long)(n))).commit();
	}
	
	@Override
	public void onBackPressed() {
		saves.edit().putString(bookname, String.valueOf((long)(n))).commit();
		chact.setClass(getApplicationContext(), MainActivity.class);
		chact.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(chact);
	}
	private void _setData () {
		text.setText(book.get((int)0).get(String.valueOf((long)(n))).toString());
		button1.setText(book.get((int)1).get(String.valueOf((long)(n))).toString());
		button2.setText(book.get((int)2).get(String.valueOf((long)(n))).toString());
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
