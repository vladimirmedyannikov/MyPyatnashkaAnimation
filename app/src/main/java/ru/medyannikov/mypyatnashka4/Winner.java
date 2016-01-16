package ru.medyannikov.mypyatnashka4;

import ru.medyannikov.mypyatnashka4.R;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class Winner extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.winner);
		
		//doBindService();
		
		
		final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
		final Animation animRotate2 = AnimationUtils.loadAnimation(this, R.anim.rotatetwo);
		final Animation animRotate3 = AnimationUtils.loadAnimation(this, R.anim.rotate3);
		
		
		final Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.textanimation);
		
		String time = getIntent().getStringExtra("time");
		 
		
		String click = getIntent().getStringExtra("click");
		
		TextView maintext = (TextView) findViewById(R.id.maintext);
		maintext.startAnimation(textAnimation);
		
		ObjectAnimator a;
		a = ObjectAnimator.ofInt(maintext, "textColor", Color.RED,Color.BLUE,Color.CYAN,Color.GREEN);
		a.setRepeatMode(ObjectAnimator.REVERSE);
		a.setRepeatCount(ObjectAnimator.INFINITE);
		a.setDuration(1500000);

		a.start();
		
		final TextView exit = (TextView) findViewById(R.id.texttime);
		final TextView records = (TextView) findViewById(R.id.clicktext);
		
		exit.startAnimation(animRotate);
		records.startAnimation(animRotate2);
		
		final TextView tvTime = (TextView) findViewById(R.id.time);
		final TextView tvClick = (TextView) findViewById(R.id.click);
		
		tvClick.setText(click);
		tvTime.setText(time);
		
		 
	}

}
