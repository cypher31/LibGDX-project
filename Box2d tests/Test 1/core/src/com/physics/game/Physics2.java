package com.physics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Physics2 extends ApplicationAdapter implements InputProcessor{
	
	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	World world;
	Body body;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;
	
	float torque = 0.0f;
	boolean drawSprite = true;
	
	final float PIXELS_TO_METERS = 100f;
	
	@Override
	public void create() {
		
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		world = new World(new Vector2(0, 0f),true);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) / PIXELS_TO_METERS, 
				(sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, 
				sprite.getHeight() / 2 / PIXELS_TO_METERS);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.1f;
		
		body.createFixture(fixtureDef);
		shape.dispose();
		
		Gdx.input.setInputProcessor(this);
		
		//Create the Debug renderer so we can see the physics behind the scenes
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight());
		
	}
	
	private float elapsed = 0;
	
	@Override
	public void render(){
		camera.update();
		//step the physics simulation forward at 60hz
		world.step(1f/60f, 6, 2);
		
		//Apply torque to the physics body
		//Torque is applied per frame instead of just once
		body.applyTorque(torque, true);
		
		//Set sprites position from the updated physics body location
		sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.getWidth() / 2,
				(body.getPosition().y * PIXELS_TO_METERS) - sprite.getHeight() / 2);
		
		//Same for rotation
		sprite.setRotation((float)Math.toDegrees(body.getAngle()));
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		//Scale down the sprite batches projection matrix to box2D size
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, 
				PIXELS_TO_METERS, 0);
		
		batch.begin();
		
		if(drawSprite)
			batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
					sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(),
					sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
		
		batch.end();
		
		//Render the physics world using the scaled down matrix
		//this is optional and used for debugging
		debugRenderer.render(world, debugMatrix);
				
	}
	
	@Override
	public void dispose(){
		img.dispose();
		world.dispose();
	}
	

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		//On right or left arrow set the velocity at a fixed rate in that direction
		if(keycode == Input.Keys.RIGHT)
			body.setLinearVelocity(1f, 0f);
		if(keycode == Input.Keys.LEFT)
			body.setLinearVelocity(-1f, 0f);
		
		if(keycode == Input.Keys.UP)
			body.setLinearVelocity(0f, 1f);
		if(keycode == Input.Keys.DOWN)
			body.setLinearVelocity(0f, -1f);
		
		//On brackets apply torque
		if(keycode == Input.Keys.RIGHT_BRACKET)
			torque += 0.1f;
		if(keycode == Input.Keys.LEFT_BRACKET)
			torque -= 0.1f;
		
		//Remove torque with /
		if(keycode == Input.Keys.BACKSLASH)
			torque = 0.0f;
		
		//if user hits space set it all to 0
		if(keycode == Input.Keys.SPACE){
			body.setLinearVelocity(0f, 0f);
			body.setAngularVelocity(0f);
			torque = 0f;
			sprite.setPosition(0f, 0f);
			body.setTransform(0f, 0f, 0f);
		}
		
		//Toggle visability of sprite 
		if(keycode == Input.Keys.ESCAPE)
			drawSprite = !drawSprite;
		
		return true;
	
	
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	//On touch we apply force from the direction of the users touch
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		body.applyForce(1f, 1f, screenX, screenY, true);
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
