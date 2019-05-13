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
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ScrollView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.AdapterView;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {
	
	
	private Toolbar _toolbar;
	private DrawerLayout _drawer;
	private double n = 0;
	private String folder = "";
	private String workdir = "";
	
	private ArrayList<String> list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private ScrollView vscroll2;
	private LinearLayout linear1;
	private ListView listview2;
	private Button download;
	private LinearLayout _drawer_linear1;
	private LinearLayout _drawer_linear2;
	private Button _drawer_button_settings;
	private Button _drawer_button_about;
	private ImageView _drawer_imageview1;
	private TextView _drawer_textview1;
	private TextView _drawer_textview2;
	
	private SharedPreferences db;
	private AlertDialog.Builder about;
	private Intent chact = new Intent();
	private Intent tosite = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = (DrawerLayout) findViewById(R.id._drawer);ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		vscroll2 = (ScrollView) findViewById(R.id.vscroll2);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		listview2 = (ListView) findViewById(R.id.listview2);
		download = (Button) findViewById(R.id.download);
		_drawer_linear1 = (LinearLayout) _nav_view.findViewById(R.id.linear1);
		_drawer_linear2 = (LinearLayout) _nav_view.findViewById(R.id.linear2);
		_drawer_button_settings = (Button) _nav_view.findViewById(R.id.button_settings);
		_drawer_button_about = (Button) _nav_view.findViewById(R.id.button_about);
		_drawer_imageview1 = (ImageView) _nav_view.findViewById(R.id.imageview1);
		_drawer_textview1 = (TextView) _nav_view.findViewById(R.id.textview1);
		_drawer_textview2 = (TextView) _nav_view.findViewById(R.id.textview2);
		db = getSharedPreferences("prefs", Activity.MODE_PRIVATE);
		about = new AlertDialog.Builder(this);
		
		listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				chact.putExtra("gamedir", list.get((int)(_position)));
				chact.setClass(getApplicationContext(), GameActivity.class);
				chact.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(chact);
			}
		});
		
		download.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				tosite.setAction(Intent.ACTION_VIEW);
				tosite.setData(Uri.parse("https://github.com/lesnoilis/textengine-books"));
				startActivity(tosite);
			}
		});
		
		_drawer_button_settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				chact.setClass(getApplicationContext(), SettingsActivity.class);
				chact.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(chact);
			}
		});
		
		_drawer_button_about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				about.setTitle("Text quest engine v 1.0");
				about.setMessage("Я шестнадцатилетний разработчик из России. Я очень люблю текстовые квесты, поэтому и создал этот движок. Особая благодарность моему помощнику RznNike, чьи pull request'ы очень помогли мне в разработке данного приложения. Пользуйтесь!");
				about.setPositiveButton("Проект на Github", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						tosite.setAction(Intent.ACTION_VIEW);
						tosite.setData(Uri.parse("https://github.com/lesnoilis/textengine"));
						startActivity(tosite);
					}
				});
				about.setNeutralButton("Я на 4pda", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						tosite.setAction(Intent.ACTION_VIEW);
						tosite.setData(Uri.parse("http://4pda.ru/forum/index.php?showuser=6659375"));
						startActivity(tosite);
					}
				});
				about.setNegativeButton("Моя почта", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						tosite.setAction(Intent.ACTION_VIEW);
						tosite.setData(Uri.parse("mailto:kvaapps@gmail.com"));
						tosite.putExtra("subject", "Text engine");
						tosite.putExtra("body", "Напишите ваш отзыв, сообщите о баге или ошибке.");
						startActivity(tosite);
					}
				});
				about.create().show();
			}
		});
	}
	private void initializeLogic() {
		setTitle("Textengine");
		workdir = "/storage/emulated/0/Textengine";
		if (!FileUtil.isExistFile(workdir)) {
			FileUtil.makeDir(workdir);
		}
		_refreshList();
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
		
	}
	
	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}
	private void _refreshList () {
		listmap.clear();
		FileUtil.listDir(workdir, list);
		n = 0;
		for(int _repeat13 = 0; _repeat13 < (int)(list.size()); _repeat13++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("folder", list.get((int)(n)));
				listmap.add(_item);
			}
			
			folder = Uri.parse(list.get((int)(n))).getLastPathSegment();
			n++;
		}
		listview2.setAdapter(new Listview2Adapter(listmap));
		((BaseAdapter)listview2.getAdapter()).notifyDataSetChanged();
	}
	
	
	public class Listview2Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.book, null);
			}
			
			final LinearLayout linear4 = (LinearLayout) _v.findViewById(R.id.linear4);
			final ImageView imageview1 = (ImageView) _v.findViewById(R.id.imageview1);
			final TextView name = (TextView) _v.findViewById(R.id.name);
			
			name.setText(Uri.parse(listmap.get((int)_position).get("folder").toString()).getLastPathSegment());
			imageview1.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(listmap.get((int)_position).get("folder").toString().concat("/icon.png"), 1024, 1024));
			
			return _v;
		}
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
