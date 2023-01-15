package com.mygdx.game.levels;

import static com.mygdx.game.helpers.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.KeyboardInput;
import com.mygdx.game.Platformer;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.playable.Player;
import com.mygdx.game.helpers.MapHelper;
import com.mygdx.game.hud.Hud;
import java.util.ArrayList;

public abstract class Level extends ScreenAdapter {

  protected final OrthographicCamera camera;
  protected Stage stage;
  // Set the position and size of each layer
  SpriteBatch batch;
  private Hud hud;
  private Platformer app;
  private Viewport viewport;

  private TiledMap map;
  private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
  private MapHelper mapHelper;
  Player player;
  KeyboardInput INPUT;
  Rectangle boundary;
  private World world;
  private Box2DDebugRenderer box2DDebugRenderer;

  protected String mapFile;

  private final ArrayList<Entity> entities = new ArrayList<>();

  public Level(OrthographicCamera camera, String mapFile) {

    this.camera = camera;
    this.batch = new SpriteBatch();
    this.mapFile = mapFile;
    // part of the YouTube Tutorial
    this.world = new World(new Vector2(0, -50f), false);
    this.box2DDebugRenderer = new Box2DDebugRenderer();
    this.mapHelper = new MapHelper(this);
    this.orthogonalTiledMapRenderer = mapHelper.setupMap();
    // calls the hud
    hud = new Hud(batch, player);
    // Create a SpriteBatch object
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);
    boundary = new Rectangle(0, 0, Gdx.graphics.getWidth(), 50);

    INPUT = new KeyboardInput(player);
  }

  public String getMapFile() {
    return mapFile;
  }

  public World getWorld() {
    return world;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  private void cameraUpdate() {
    Vector3 position = camera.position;
    position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
    position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
    camera.position.set(position);
    camera.update();
  }

  protected void update() {
    world.step(1 / 60f, 6, 2);
    cameraUpdate();
    batch.setProjectionMatrix(camera.combined);
    orthogonalTiledMapRenderer.setView(camera);
    // player.updatePosition();
    for (Entity entity : entities) {
      entity.updatePosition();
    }
  }

  @Override
  public void dispose() {
    map.dispose();
    batch.dispose();
  }

  @Override
  public void render(float delta) {

    update();
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    orthogonalTiledMapRenderer.render();
    // calls movement for character
    INPUT.keyboardMovement();
    batch.begin();
    for (Entity entity : entities) {
      batch.draw(
          entity.getCurrentFrame(),
          entity.getX() - entity.getSpriteWidth() / 2f * entity.getScaleFactor(),
          entity.getY() - entity.getSpriteHeight() / 2f * entity.getScaleFactor(),
          entity.getSpriteWidth() * entity.getScaleFactor(),
          entity.getSpriteHeight() * entity.getScaleFactor());
    }
    batch.end();

    // From the tutorial for map collision
    box2DDebugRenderer.render(world, camera.combined.scl(PPM));

    // TODO: change collision system to use map geometry

    batch.setProjectionMatrix(
        hud.getStage()
            .getCamera()
            .combined); // set the spriteBatch to draw what our stageViewport sees
    hud.getStage().act(delta); // act the Hud
    hud.getStage().draw(); // draw the Hud

    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height, true);
  }

  public void addEntity(Entity entity) {
    entities.add(entity);
  }
}
