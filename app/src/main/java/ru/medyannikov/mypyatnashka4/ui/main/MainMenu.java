package ru.medyannikov.mypyatnashka4.ui.main;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import ru.medyannikov.mypyatnashka4.R;
import ru.medyannikov.mypyatnashka4.data.services.MusicService;
import ru.medyannikov.mypyatnashka4.ui.base.BaseActivity;
import ru.medyannikov.mypyatnashka4.ui.game.GameActivity;
import ru.medyannikov.mypyatnashka4.ui.record.RecordsActivity;


public class MainMenu extends BaseActivity {

	@Override

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_HOME)
		{
			Toast.makeText(getApplication(), "TEXT", Toast.LENGTH_SHORT).show();
			mServ.pauseMusic();
		}
		return false;
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		//mServ.pauseMusic();
		super.onStop();
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (mServ != null)
			mServ.getService().resumeMusic();
		super.onResume();


		final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
		final Animation animRotate2 = AnimationUtils.loadAnimation(this, R.anim.rotatetwo);
		final Animation animRotateRecords = AnimationUtils.loadAnimation(this, R.anim.rotaterecords);
		TextView play = (TextView) findViewById(R.id.play);
		TextView exit = (TextView) findViewById(R.id.exitmainmenu);
		TextView records = (TextView) findViewById(R.id.table);

		exit.startAnimation(animRotate);
		play.startAnimation(animRotate2);
		records.startAnimation(animRotateRecords);
	}
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection Scon = new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder
				binder) {
			mServ = ((MusicService.ServiceBinder)binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
	};

	void doBindService(){
		bindService(new Intent(this,MusicService.class),
				Scon,Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService()
	{
		if(mIsBound)
		{
			unbindService(Scon);
			mIsBound = false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		//doBindService();

		final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
		final Animation animRotate2 = AnimationUtils.loadAnimation(this, R.anim.rotatetwo);
		final Animation animRotate3 = AnimationUtils.loadAnimation(this, R.anim.rotate3);
		final Animation animRotateRecords = AnimationUtils.loadAnimation(this, R.anim.rotaterecords);
		final Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.textanimation);
		//v.startAnimation(animRotate);
		TextView play = (TextView) findViewById(R.id.play);
		TextView maintext = (TextView) findViewById(R.id.maintext);
		maintext.startAnimation(textAnimation);
		ObjectAnimator a;
		a = ObjectAnimator.ofInt(maintext, "textColor", Color.RED,Color.BLUE,Color.CYAN,Color.GREEN);
		a.setRepeatMode(ObjectAnimator.REVERSE);
		a.setRepeatCount(ObjectAnimator.INFINITE);
		a.setDuration(1500000);

		a.start();
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				v.startAnimation(animRotate3);
				//finish();

				Intent intent = new Intent(MainMenu.this, GameActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
			}
		});

		final TextView exit = (TextView) findViewById(R.id.exitmainmenu);
		final TextView records = (TextView) findViewById(R.id.table);
		final TextView setting = (TextView) findViewById(R.id.exitmainmenu);

		exit.startAnimation(animRotate);
		play.startAnimation(animRotate2);
		setting.startAnimation(animRotate);

		/*setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				*//*Intent settingsActivity = new Intent(getBaseContext(),
                        Preferences.class);
                     startActivity(settingsActivity);*//*
				v.startAnimation(animRotate3);
				DialogPreference dp = new SettingDialog(getApplication(), null);

			}
		});*/


		AnimationListener animListener = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				records.startAnimation(animRotateRecords);
				exit.startAnimation(animRotate2);
				setting.startAnimation(animRotate);
			}
		};
		animRotate3.setAnimationListener(animListener);
		records.startAnimation(animRotateRecords);


		records.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				v.startAnimation(animRotate3);

				Intent intent = new Intent(MainMenu.this, RecordsActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
			}
		});

		exit.setOnClickListener(new OnClickListener() {


			@Override
			public void onClick(final View v) {

				v.startAnimation(animRotate3);
				onDialog();

			}
		});



	}

	public void onDialog()
	{
		AlertDialog.Builder exitDialog;
		exitDialog = new AlertDialog.Builder(MainMenu.this);
		exitDialog.setTitle("Выход");
		exitDialog.setMessage("Вы действительно хотите выйти ?");
		exitDialog.setPositiveButton("Да",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//setResult(0);
						//mServ.stopMusic();
						finish();

					}
				});

		exitDialog.setNegativeButton("Нет",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		exitDialog.setCancelable(false);
		exitDialog.show();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		onDialog();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		//mServ.stopMusic();
		//doUnbindService();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		//mServ.pauseMusic();
		super.onPause();
	}
	
	/*public class click implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainMenu.this, MainActivity.class);
		    startActivity(intent);
		}
		
	}*/
	
/*	public class clickExit implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			v.startAnimation();
			AlertDialog.Builder exitDialog;
			exitDialog = new AlertDialog.Builder(MainMenu.this);
			exitDialog.setTitle("Выход");
			exitDialog.setMessage("Вы действительно хотите выйти ?");
			exitDialog.setPositiveButton("Да",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});

			exitDialog.setNegativeButton("Нет",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			exitDialog.setCancelable(false);
			exitDialog.show();
		}
		
	}*/


}
