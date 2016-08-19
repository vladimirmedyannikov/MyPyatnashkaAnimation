package ru.medyannikov.mypyatnashka4.ui.base;

import android.content.Context;
import android.widget.Button;

public class ButtonPyatnashka extends Button {
	private boolean animate = true;
	public int number;
	
	public void setAnimate(boolean newAnimate)
	{
		this.animate = newAnimate;
	}
	
	public boolean getAnimate()
	{
		return this.animate;
	}
	public ButtonPyatnashka(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
}
