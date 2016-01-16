package ru.medyannikov.mypyatnashka4;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class SettingDialog extends DialogPreference {

	public SettingDialog(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setPersistent(false);
		setDialogLayoutResource(R.layout.settingdialog);
	}

	public SettingDialog(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onPrepareDialogBuilder(Builder builder) {
		// TODO Auto-generated method stub
		
		builder.setTitle("Setting");
        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
        super.onPrepareDialogBuilder(builder);  
	}
}
