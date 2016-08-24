package ru.medyannikov.mypyatnashka4.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import ru.medyannikov.mypyatnashka4.R;
import ru.medyannikov.mypyatnashka4.ui.base.BaseActivity;

public class SettingDialog extends BaseActivity<SettingPresenter> {

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_setting);
	}
}
