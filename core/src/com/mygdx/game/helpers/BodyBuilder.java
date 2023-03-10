package com.mygdx.game.helpers;

import static com.mygdx.game.helpers.Constants.PPM;

import com.badlogic.gdx.physics.box2d.*;

public class BodyBuilder {

  float width, height;

  public static Body createBody(
      float x, float y, float width, float height, BodyDef.BodyType bodyType, World world) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = bodyType;
    bodyDef.position.set(x / PPM, y / PPM);
    bodyDef.fixedRotation = true;
    Body body = world.createBody(bodyDef);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.friction = 0;
    body.createFixture(fixtureDef);
    shape.dispose();
    return body;
  }

  public static Body createSensor(float x, float y, float width, float height, World world) {
    BodyDef bodyDef = new BodyDef();
    bodyDef.position.set(x / PPM, y / PPM);
    bodyDef.fixedRotation = true;
    Body body = world.createBody(bodyDef);
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.isSensor = true;
    body.createFixture(fixtureDef);
    shape.dispose();
    return body;
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }
}
