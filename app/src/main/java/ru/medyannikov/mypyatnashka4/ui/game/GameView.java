package ru.medyannikov.mypyatnashka4.ui.game;

import ru.medyannikov.mypyatnashka4.ui.base.BaseView;
import ru.medyannikov.mypyatnashka4.ui.base.ButtonPyatnashka;

public interface GameView extends BaseView {
  void showScore(String s);

  void initGameScene(int[] items);

  void animationChange(ButtonPyatnashka butNil, ButtonPyatnashka but2);
}
