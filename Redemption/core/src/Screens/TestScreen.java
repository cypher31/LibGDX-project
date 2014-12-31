package Screens;

import Characters.Player;
import World.TestLvl;
import World.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cypherredemption.controller.WorldController;



public class TestScreen implements Screen, InputProcessor {
	
	private TestLvl testLvl;
	private WorldRenderer renderer;
	private WorldController controller;
	World world;
	Player player;
	Body playerBody;
	
	private int width, height;
	
	
	
	@Override
	public void show() {
		world = new World(new Vector2(0,-10), true);
		testLvl = new TestLvl();//Need to have this to actually create the world (placeholder)
		renderer = new WorldRenderer(testLvl, false);
		controller = new WorldController(testLvl);
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
		world.step(1/60f, 6, 2);

	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public Body getBodies(){
		return playerBody;
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		world.dispose();
	}
	
	@Override
	public boolean keyDown(int keycode){
		if(keycode == Keys.LEFT)
			controller.leftPressed();
		if(keycode == Keys.RIGHT)
			controller.rightPressed();
		if(keycode == Keys.Z)
			controller.jumpPressed();
		if(keycode == Keys.X)
			controller.jumpPressed();
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.LEFT)
			controller.leftReleased();
		if(keycode == Keys.RIGHT)
			controller.rightReleased();
		if(keycode == Keys.Z)
			controller.jumpReleased();
		if(keycode == Keys.X)
			controller.jumpReleased();
		return true;
	}
	
	@Override
	public boolean keyTyped(char character){
		return false;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button){
		if(x < width/2 && y > height/2){
			controller.leftPressed();
		}
		if(x > width/2 && y < height/2){
			controller.rightPressed();
		}
		return true;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button){
		if(x < width/2 && y > height/2){
			controller.leftReleased();
		}
		if(x > width/2 && y < height/2){
			controller.rightReleased();
		}
		return true;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer){
		return false;
	}
	
	@Override
	public boolean mouseMoved(int x, int y){
		return false;
	}
	
	@Override
	public boolean scrolled(int amount){
		return false;
	}

}
