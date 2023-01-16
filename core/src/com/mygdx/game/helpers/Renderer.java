package com.mygdx.game.helpers;

import static com.mygdx.game.helpers.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.playable.Player;
import com.mygdx.game.hud.Hud;
import com.mygdx.game.levels.Level;

public class Renderer extends ScreenAdapter {
  private final OrthographicCamera camera;
  private final Stage stage;
  private final SpriteBatch batch;
  private final Level level;
  private final Hud hud;
  private final OrthogonalTiledMapRenderer mapRenderer;
  private final Box2DDebugRenderer box2DDebugRenderer;

  public Renderer(Level level) {
    this.camera = new OrthographicCamera();
    this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.level = level;
    this.stage = new Stage();
    this.batch = new SpriteBatch();
    this.mapRenderer = level.loadMap();
    this.hud = new Hud(batch, level.getPlayer());
    this.box2DDebugRenderer = new Box2DDebugRenderer();
  }

  @Override
  public void render(float dt) {
    // Clear screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update camera position
    final Player player = level.getPlayer();
    final Vector3 position = camera.position;
    position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
    position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
    camera.position.set(position);
    camera.update();

    // Draw map
    mapRenderer.setView(camera);
    mapRenderer.render();

    // Draw player & enemy sprites
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for (Entity entity : level.getEntities()) {
      batch.draw(
          entity.getCurrentFrame(),
          entity.getX() - entity.getSpriteWidth() / 2f * entity.getScaleFactor(),
          entity.getY() - entity.getSpriteHeight() / 2f * entity.getScaleFactor(),
          entity.getSpriteWidth() * entity.getScaleFactor(),
          entity.getSpriteHeight() * entity.getScaleFactor());
    }
    batch.end();

    // Draw debug bounding boxes
    box2DDebugRenderer.render(level.getWorld(), camera.combined.scl(PPM));

    // Draw HUD
    batch.setProjectionMatrix(hud.getStage().getCamera().combined);
    hud.getStage().act(dt);
    hud.getStage().draw();

    // Draw stage
    stage.act();
    stage.draw();
  }

  @Override
  public void dispose() {
    batch.dispose();
    stage.dispose();
    hud.dispose();
    mapRenderer.dispose();
    box2DDebugRenderer.dispose();
  }
}
