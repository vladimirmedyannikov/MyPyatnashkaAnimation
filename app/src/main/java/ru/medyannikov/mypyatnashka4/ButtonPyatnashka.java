package ru.medyannikov.mypyatnashka4;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;

public class ButtonPyatnashka extends Button {
	private boolean animate = true;
	int number;
	
	public void setAnimate(boolean newAnimate)
	{
		this.animate = newAnimate;
	}
	
	public boolean getAnimate()
	{
		return this.animate;
	}
	ButtonPyatnashka(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
}
