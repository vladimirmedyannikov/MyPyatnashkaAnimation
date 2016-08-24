package ru.medyannikov.mypyatnashka4.ui.game;

import android.os.Vibrator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.medyannikov.mypyatnashka4.R;
import ru.medyannikov.mypyatnashka4.data.GameManager;
import ru.medyannikov.mypyatnashka4.ui.base.BasePresenter;
import ru.medyannikov.mypyatnashka4.ui.base.ButtonPyatnashka;

public class GamePresenter extends BasePresenter<GameView> {
  private GameManager gameManager;
  private int sizeX = 4, sizeY = 4;
  private int[][] numbers;
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
  private int countClick = 0;


  public GamePresenter() {

  }

  public void init() {
    gameManager = new GameManager(view.context());
    numbers = new int[sizeX][sizeY];
    items = new int[sizeX * sizeY];
    countClick = 0;
  }


  public void startGame() {
    countClick = 0;
    for (int i = 0; i < sizeX; i++) {
      for (int j = 0; j < sizeY; j++) {
        numbers[i][j] = i * sizeX + j;
        items[i * sizeX + j] = i * sizeY + j;
      }
    }
    shuffleArray(items);
    for (int i = 0; i < sizeX; i++) {
      for (int j = 0; j < sizeY; j++) {
        numbers[i][j] = items[i * sizeX + j];
      }
    }

    view.showScore("0");
    view.initGameScene(items);
  }

  private void buttonImage(ButtonPyatnashka b) {
    switch (b.number) {
      case 0: {
        b.setBackgroundResource(R.drawable.drevo12);
        break;
      }
      case 1: {
        b.setBackgroundResource(R.drawable.drevo1);
        break;
      }
      case 2: {
        b.setBackgroundResource(R.drawable.drevo2);
        break;
      }
      case 3: {
        b.setBackgroundResource(R.drawable.drevo3);
        break;
      }
      case 4: {
        b.setBackgroundResource(R.drawable.drevo4);
        break;
      }
      case 5: {
        b.setBackgroundResource(R.drawable.drevo5);
        break;
      }
      case 6: {
        b.setBackgroundResource(R.drawable.drevo6);
        break;
      }
      case 7: {
        b.setBackgroundResource(R.drawable.drevo7);
        break;
      }
      case 8: {
        b.setBackgroundResource(R.drawable.drevo8);
        break;
      }
      case 9: {
        b.setBackgroundResource(R.drawable.drevo9);
        break;
      }
      case 10: {
        b.setBackgroundResource(R.drawable.drevo10);
        break;
      }
      case 11: {
        b.setBackgroundResource(R.drawable.drevo11);
        break;
      }
      case 12: {
        b.setBackgroundResource(R.drawable.drevo12);
        break;
      }
      case 13: {
        b.setBackgroundResource(R.drawable.drevo13);
        break;
      }
      case 14: {
        b.setBackgroundResource(R.drawable.drevo14);
        break;
      }
      case 15: {
        b.setBackgroundResource(R.drawable.drevo15);
        break;
      }
    }
  }

  public static void shuffleArray(int[] a) {
    int n = a.length;
    Random generator = new Random();
    for (int i = 0; i < n; i++) {
      int change = i + generator.nextInt(n - i);
      swap(a, i, change);
    }
  }

  private static void swap(int[] a, int i, int change) {
    int temp = a[i];
    a[i] = a[change];
    a[change] = temp;
  }

  public void onClickButton(ButtonPyatnashka b) {
    itemShaffle(b);
  }

  void itemShaffle(ButtonPyatnashka but2) {
    int thisI = 0, thisJ = 0, change = but2.number;
    for (int i = 0; i < sizeX; i++) {
      for (int j = 0; j < sizeY; j++) {
        if (numbers[i][j] == change) {
          thisI = i;
          thisJ = j;
        }
      }
    }
    if (but2.getAnimate()) {
      if (thisI > 0) {
        if (numbers[thisI - 1][thisJ] == 0) {
          numbers[thisI - 1][thisJ] = change;
          numbers[thisI][thisJ] = 0;
          view.animationChange(butNil, but2);
        }
      }

      if (thisI < sizeX - 1) {
        if (numbers[thisI + 1][thisJ] == 0) {
          numbers[thisI + 1][thisJ] = change;
          numbers[thisI][thisJ] = 0;
          view.animationChange(butNil, but2);
        }
      }
      if (thisJ > 0) {
        if (numbers[thisI][thisJ - 1] == 0) {
          numbers[thisI][thisJ - 1] = change;
          numbers[thisI][thisJ] = 0;
          view.animationChange(butNil, but2);
        }
      }
      if (thisJ < sizeY - 1) {
        if (numbers[thisI][thisJ + 1] == 0) {
          numbers[thisI][thisJ + 1] = change;
          numbers[thisI][thisJ] = 0;
          view.animationChange(butNil, but2);
        }
      }
    }
  }

}
