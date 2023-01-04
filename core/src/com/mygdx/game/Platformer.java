package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.animations.CharacterSelectionScreen;
import com.mygdx.game.entities.playable.Mage;

public class Platformer extends Game {

  private static Screen screen;
  private static Level_1 level1Screen;

  KeyboardInput INPUT;
  Mage testcharacter;

  @Override
  public void create() {
    screen = new CharacterSelectionScreen(this);
    this.setScreen(screen);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    super.render();
  }

  @Override
  public void dispose() {}
}
