package ru.medyannikov.mypyatnashka4.ui.game;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.medyannikov.mypyatnashka4.R;
import ru.medyannikov.mypyatnashka4.data.GameManager;
import ru.medyannikov.mypyatnashka4.data.db.RecordsDataSource;
import ru.medyannikov.mypyatnashka4.data.services.MusicService;
import ru.medyannikov.mypyatnashka4.ui.base.BaseActivity;
import ru.medyannikov.mypyatnashka4.ui.base.ButtonPyatnashka;
import ru.medyannikov.mypyatnashka4.ui.winner.Winner;

public class GameActivity extends BaseActivity<GamePresenter> implements GameView {
  private int countClick = 0;
  private int[][] numbers;
  private int sizeX = 4, sizeY = 4;
  private Chronometer chronometer;
  private Vibrator vb;
  int vibroEnabled = 1;
  TableLayout tbl;
  TextView tx;
  int[] items;
  List<Button> btnList = new ArrayList<Button>();
  ButtonPyatnashka butNil;
  GridLayout gr;
  Handler handler;
  int timeAnimation = 2, theme = 2;
  Button bMenu;
  private MusicService mServ;
  private boolean mIsBound;

  private GameManager gameManager;

  private RecordsDataSource datasource;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    presenter = new GamePresenter();
    presenter.attachView(this);

    gameManager = new GameManager(this);
    gr = (GridLayout) findViewById(R.id.GridLayout1);
    gr.setRowCount(getSizeX());
    gr.setColumnCount(getSizeY());
    countClickText = (TextView) findViewById(R.id.countclick);

    vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    countClick = 0;
    numbers = new int[sizeX][sizeY];
    items = new int[getSizeX() * getSizeY()];
    init();
    bMenu = (Button) findViewById(R.id.menu);
    bMenu.setOnClickListener(v -> openOptionsMenu());
    tx = new TextView(getApplication());
    tx.setText("");
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.newGame: {
        init();
        return true;
      }

      case R.id.exit: {
        onBackPressed();
        return true;
      }

      case R.id.timeAnimation: {
        final String[] mChooseTime = getResources().getStringArray(R.array.animation_settings);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.setting_animation_label)
            .setCancelable(true)
            .setNeutralButton(R.string.cancel, (dialog, id) -> {
              dialog.cancel();
            })
            .setSingleChoiceItems(mChooseTime, timeAnimation, (dialog, item1) -> {
              timeAnimation = item1;
              dialog.cancel();
            });
        builder.show();
        return true;
      }

      case R.id.vibro: {
        final String[] mChooseVibration = getResources().getStringArray(R.array.chooser_on_off);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.setting_vibration_label)
            .setCancelable(false)
            .setNeutralButton(R.string.cancel, (dialog, id) -> {
              dialog.cancel();
            })

            .setSingleChoiceItems(mChooseVibration, vibroEnabled, (dialog, item1) -> {
              vibroEnabled = item1;
              dialog.cancel();
            });
        builder.show();
        return true;
      }

      case R.id.theme: {
        final String[] mChooseTheme = { "Синяя", "Зеленая", "Дерево" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Тема:").setCancelable(false).setNeutralButton("Отмена", (dialog, id) -> {
          dialog.cancel();
        }).setSingleChoiceItems(mChooseTheme, theme, (dialog, item1) -> {
          theme = item1;
          for (int i = 0; i < gr.getChildCount(); i++) {
            switch (theme) {
              case 0: {
                gr.getChildAt(i).setBackgroundResource(R.drawable.button_blue);
                ((ButtonPyatnashka) gr.getChildAt(i)).setText(
                    String.valueOf(((ButtonPyatnashka) gr.getChildAt(i)).number));
                break;
              }
              case 1: {
                gr.getChildAt(i).setBackgroundResource(R.drawable.button_green);
                ((ButtonPyatnashka) gr.getChildAt(i)).setText(
                    String.valueOf(((ButtonPyatnashka) gr.getChildAt(i)).number));
                break;
              }
              case 2: {
                buttonImage((ButtonPyatnashka) gr.getChildAt(i));
                ((ButtonPyatnashka) gr.getChildAt(i)).setText("");
                break;
              }
              default: {
                gr.getChildAt(i).setBackgroundResource(R.drawable.button_blue);
              }
            }
          }
        });
        builder.show();
        return true;
      }

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  void itemShaffle(ButtonPyatnashka but2) {

    int thisI = 0, thisJ = 0, change = but2.number;
    for (int i = 0; i < getSizeX(); i++) {
      for (int j = 0; j < getSizeY(); j++) {
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

          animationChange(butNil, but2);
        }
      }

      if (thisI < getSizeX() - 1) {
        if (numbers[thisI + 1][thisJ] == 0) {
          numbers[thisI + 1][thisJ] = change;
          numbers[thisI][thisJ] = 0;
          animationChange(butNil, but2);
        }
      }
      if (thisJ > 0) {
        if (numbers[thisI][thisJ - 1] == 0) {
          numbers[thisI][thisJ - 1] = change;
          numbers[thisI][thisJ] = 0;
          animationChange(butNil, but2);
        }
      }
      if (thisJ < getSizeY() - 1) {
        if (numbers[thisI][thisJ + 1] == 0) {
          numbers[thisI][thisJ + 1] = change;
          numbers[thisI][thisJ] = 0;
          animationChange(butNil, but2);
        }
      }
    }
  }
  public synchronized void animationChange(final ButtonPyatnashka butNil2, final ButtonPyatnashka but) {
    try {
      if (but.getAnimate()) {
        AsyncTask<Void, Float, Boolean> myAsk = new AsyncTask<Void, Float, Boolean>() {
          final float x = butNil2.getX(), y = butNil2.getY(), x1 = but.getX(), y1 = but.getY();
          ButtonPyatnashka butClick;
          boolean changeElem = false;
          TranslateAnimation animTranslate;

          final float fromX = x1, fromY = y1;
          final float toX = x, toY = y;

          @Override protected void onPreExecute() {
            super.onPreExecute();
            butNil = butNil2;
            butClick = but;

            // butNil.setX(x1);
            // butNil.setY(y1);
            // Process.setThreadPriority(Thread.MAX_PRIORITY);
            // //Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
          }

          @Override protected Boolean doInBackground(Void... params) {
            publishProgress(fromX, toX, fromY, toY);
            return true;
          }

          @Override protected void onProgressUpdate(Float... values) {
            super.onProgressUpdate(values);

            but.setVisibility(Button.INVISIBLE);

            butNil.setX(x1);
            butNil.setY(y1);
            but.setX(x);
            but.setY(y);

            animTranslate =
                new TranslateAnimation(Animation.ZORDER_NORMAL, values[0] - values[1], Animation.ZORDER_NORMAL, 0,
                    Animation.ZORDER_NORMAL, values[2] - values[3], Animation.ZORDER_NORMAL, 0);
            animTranslate.setAnimationListener(new Animation.AnimationListener() {

              @Override public void onAnimationStart(Animation animation) {
                but.setAnimate(false);
              }

              @Override public void onAnimationRepeat(Animation animation) {
              }

              @Override public void onAnimationEnd(Animation animation) {
                but.setVisibility(Button.VISIBLE);
                but.setAnimate(true);
              }
            });
            int timeAnimationValue = 0;
            switch (timeAnimation) {
              case 0: {
                timeAnimationValue = 0;
                break;
              }
              case 1: {
                timeAnimationValue = 200;
                break;
              }
              case 2: {
                timeAnimationValue = 500;
                break;
              }
              case 3: {
                timeAnimationValue = 800;
                break;
              }
              case 4: {
                timeAnimationValue = 1000;
                break;
              }
              case 5: {
                timeAnimationValue = 1500;
                break;
              }
              default: {
                timeAnimationValue = 0;
                break;
              }
            }

            AnimationSet aniSet = new AnimationSet(true);
            aniSet.setDuration(timeAnimationValue);
            animTranslate.setFillAfter(true);
            aniSet.setFillAfter(true);
            aniSet.addAnimation(animTranslate);

            if (but.getAnimate()) {
              butClick.setAnimation(aniSet);
              aniSet.startNow();
            }
            countClickText.setText(String.valueOf(countClick));
          }

          @Override protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (changeElem) {
              butClick.setAnimate(true);
            }
            if (checkWin()) {
              try {

                chronometer.stop();
                final String textchrome = chronometer.getText().toString();
                final int count = countClick;
                Intent intent = new Intent(GameActivity.this, Winner.class);
                intent.putExtra("time", textchrome);
                intent.putExtra("click", String.valueOf(count));

                datasource = new RecordsDataSource(getApplicationContext());
                datasource.open();
                datasource.createRecord(textchrome, count, gameManager.getUsername());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
              } catch (Exception e) {
                Log.e("eeeeeee", e.toString());
              }
            }

            // but.setBackgroundResource(R.drawable.button);

          }
        };

        myAsk.execute();
        countClick++;
        if (vibroEnabled == 1) vb.vibrate(35);
      }
    } catch (Exception e) {
      Log.d("error", e.toString());
    }
  }

  public class classBut implements OnClickListener {

    private ButtonPyatnashka but;
    public classBut(ButtonPyatnashka b) {
      but = b;
    }

    @Override public void onClick(View v) {
      Thread t = new Thread(new Runnable() {
        @Override public synchronized void run() {
          itemShaffle(but);
          Log.d("Button ", but.getText() + " " + but.getAnimate());
        }
      });
      t.start();
    }

  }
  private boolean checkWin() {
    boolean res = true;
    for (int i = 0; i < getSizeX(); i++) {
      for (int j = 0; j < getSizeY(); j++) {
        if (i == getSizeX() - 1 && j > (getSizeY() - 2)) {
          break;
        }
        if (numbers[i][j] != i * getSizeX() + j + 1) {
          res = false;
          break;
        }
      }
    }
    return res;
  }

  private static Random generator = new Random();

  public int getSizeX() {
    return sizeX;
  }

  public int getSizeY() {
    return sizeY;
  }

  private static void swap(int[] a, int i, int change) {
    int temp = a[i];
    a[i] = a[change];
    a[change] = temp;
  }

  public static void shuffleArray(int[] a) {
    int n = a.length;
    for (int i = 0; i < n; i++) {
      int change = i + generator.nextInt(n - i);
      swap(a, i, change);
    }
  }

  TextView countClickText;

  public void init() {
    countClick = 0;
    countClickText.setText("0");
    for (int i = 0; i < getSizeX(); i++) {
      for (int j = 0; j < getSizeY(); j++) {
        numbers[i][j] = i * getSizeX() + j;
        items[i * getSizeX() + j] = i * getSizeX() + j;
      }
    }
    shuffleArray(items);
    for (int i = 0; i < getSizeX(); i++) {
      for (int j = 0; j < getSizeY(); j++) {
        numbers[i][j] = items[i * sizeX + j];
      }
    }

    gr.removeAllViews();

    Point size = new Point();
    getWindowManager().getDefaultDisplay().getSize(size);
    int screenWidth = size.x;
    int screenHeight = size.y;

    int halfScreenWidth = (int) (screenWidth * 0.5f);//-15
    int quarterScreenWidth;//-15
    quarterScreenWidth = (int) (screenWidth / getSizeX()) - (int) (getSizeX());

    TableLayout tl = (TableLayout) findViewById(R.id.TableLayout1);
    screenHeight = screenHeight - tl.getHeight();
    int quarterScreenHeigth = (int) (screenHeight / getSizeX()) - (int) (getSizeX());

    GridLayout gridLayout = gr;
    gridLayout.setColumnCount(getSizeX());
    gridLayout.setRowCount(getSizeX());

    try {
      int row = 0;
      int col = 0;
      for (int i = 0; i < items.length; i++) {
        Log.d("item ", String.valueOf(i));

        Spec rowPlace = GridLayout.spec(row);
        Spec colPlace = GridLayout.spec(col++);
        ButtonPyatnashka b = new ButtonPyatnashka(this);

        // b.setBackgroundResource(R.drawable.button_blue);
        GridLayout.LayoutParams place = new GridLayout.LayoutParams(rowPlace, colPlace);
        place.width = quarterScreenWidth - 7;
        place.height = quarterScreenWidth - 6;
        place.setMargins(3, 3, 3, 3);
        b.setLayoutParams(place);
        // b.setBackgroundColor(Color.RED);
        //b.setText(String.valueOf(items[i]));
        b.number = items[i];
        switch (theme) {
          case 0: {
            b.setBackgroundResource(R.drawable.button_blue);
            b.setText(String.valueOf(b.number));
            break;
          }
          case 1: {
            b.setBackgroundResource(R.drawable.button_green);
            b.setText(String.valueOf(b.number));
            break;
          }
          case 2: {
            buttonImage(b);
            break;
          }
          default: {
            b.setBackgroundResource(R.drawable.button_blue);
            b.setText(String.valueOf(b.number));
            break;
          }
        }
        //b.setShadowLayer(10f, 5f, -5f, R.color.TransparentGrey);
        b.setOnClickListener(new classBut(b));

        if (items[i] == 0) {
          b.setVisibility(Button.INVISIBLE);
          butNil = b;
        }
        gridLayout.addView(b, place);
        if ((i + 1) % getSizeX() == 0) {
          row++;
          col = 0;
          Log.d("perenos", "true");
        }
      }
    } catch (Exception e) {
      //Log.e("Errror mother fucker", e.toString());
    }

    chronometer = (Chronometer) findViewById(R.id.chronometer2);
    chronometer.setBase(SystemClock.elapsedRealtime());
    chronometer.start();
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

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  public void closeAll() {
    this.finish();
    // Intent ???????? ?? ???????? ????? activity,
    // ????? ?? ???????????? ??????? ? ???? ??????? ????
    //Intent intent = new Intent(this, MainMenu.class);
    //startActivity(intent);
    overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
  }

  @Override public void onBackPressed() {
    AlertDialog.Builder exitDialog;
    exitDialog = new AlertDialog.Builder(GameActivity.this);
    exitDialog.setTitle("Выход");
    exitDialog.setMessage("Вы действительно хотите выйти?");
    exitDialog.setPositiveButton("Да", (dialog, which) -> {
      //finish();
      closeAll();
    });

    exitDialog.setNegativeButton("Нет", (dialog, which) -> {
      dialog.cancel();
    });
    exitDialog.setCancelable(false);
    exitDialog.show();
  }

  @Override protected void onResume() {
    super.onResume();
  }
}
