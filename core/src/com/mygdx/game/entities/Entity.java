package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
  // Combat stats
  protected int currentHp, maxHp, attackPower;

  // Position & movement stats
  public enum Direction {
    LEFT,
    RIGHT
  }

  protected Rectangle bounds = new Rectangle();
  protected Vector2 position = new Vector2();
  protected Vector2 speed = new Vector2();
  protected Direction facing;
  protected Animation<TextureRegion> animation;
  static int frameCols, frameRows = 1;

  public Entity(
      Vector2 position,
      Direction facing) {
    // Spawn with max HP
    this.position = position;
    this.facing = facing;
  }

  public static Animation<TextureRegion> CreateAnimation(String fileName, int numFrames) {
    Texture sprite1 = new Texture(Gdx.files.internal(fileName));
    frameCols = numFrames;
    TextureRegion[][] tmp =
        TextureRegion.split(
            sprite1, sprite1.getWidth() / frameCols, sprite1.getHeight() / frameRows);

    TextureRegion[] spriteIdle = new TextureRegion[frameCols * frameRows];
    int index = 0;
    for (int i = 0; i < frameRows; i++) {
      for (int j = 0; j < frameCols; j++) {
        spriteIdle[index++] = tmp[i][j];
      }
    }
    return new Animation<TextureRegion>(0.045f, spriteIdle);
  }

  public void attack(Entity target) {
    target.setCurrentHp(target.getCurrentHp() - this.attackPower);
  }

  public void turnAround() {
    switch (this.facing) {
      case LEFT:
        this.facing = Direction.RIGHT;
      case RIGHT:
        this.facing = Direction.LEFT;
    }
  }

  public float getX() {
    return position.x;
  }

  public float getY() {
    return position.y;
  }

  public void setX(float X) {
    position.x = X;
  }
  ;

  public void setY(float Y) {
    position.y = Y;
  }
  ;

  public int getCurrentHp() {
    return currentHp;
  }

  public void setCurrentHp(int currentHp) {
    this.currentHp = currentHp;
  }

  public int getMaxHp() {
    return maxHp;
  }

  public void setMaxHp(int maxHp) {
    this.maxHp = maxHp;
  }

  public int getAttackPower() {
    return attackPower;
  }

  public void setAttackPower(int attackPower) {
    this.attackPower = attackPower;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  public void setBounds(Rectangle bounds) {
    this.bounds = bounds;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public Vector2 getSpeed() {
    return speed;
  }

  public void setSpeed(Vector2 speed) {
    this.speed = speed;
  }

  public Direction getFacing() {
    return facing;
  }

  public void setFacing(Direction facing) {
    this.facing = facing;
  }
}
