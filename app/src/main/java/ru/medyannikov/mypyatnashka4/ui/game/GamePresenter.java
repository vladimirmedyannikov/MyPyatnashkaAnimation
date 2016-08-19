package ru.medyannikov.mypyatnashka4.ui.game;

import android.os.Vibrator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import ru.medyannikov.mypyatnashka4.data.GameManager;
import ru.medyannikov.mypyatnashka4.ui.base.BasePresenter;
import ru.medyannikov.mypyatnashka4.ui.base.ButtonPyatnashka;

public class GamePresenter extends BasePresenter<GameView> {
  private GameManager gameManager;

  private Chronometer chronometer;
  private Vibrator vb;
  int vibroEnabled = 1;
  TableLayout tbl;
  TextView tx;
  int[] items;
  List<Button> btnList = new ArrayList<Button>();
  ButtonPyatnashka butNil;
  int timeAnimation = 2, theme = 2;
  private boolean mIsBound;


  public GamePresenter() {
  }
  public void init() {
    gameManager = new GameManager(view.context());
  }
}
