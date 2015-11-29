package com.physics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Physics4 extends ApplicationAdapter implements InputProcessor {

	SpriteBatch batch;
	World world;
	Body body;
	Body floorBody;
	Body platformBody;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;

	final float PIXELS_TO_METERS = 100;
	public float playerX = 0, playerY = 0;
	public float speed = 5.0f;
	final public float playerHeight = 25f;
	final public float playerWidth = 25f;

	@Override
	public void create() {

		batch = new SpriteBatch();
		
		world = new World(new Vector2(0, -9.8f), true);

		// "Player body"
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0, 0);
		bodyDef.fixedRotation = true;

		body = world.createBody(bodyDef);

		// "Player Shape"
		PolygonShape shape1 = new PolygonShape();
		shape1.setAsBox(playerHeight / PIXELS_TO_METERS, playerWidth / PIXELS_TO_METERS);

		// Player Fixture
		FixtureDef fixtureDef1 = new FixtureDef();
		//fixtureDef1.filter.groupIndex = -1;
		fixtureDef1.shape = shape1;
		fixtureDef1.density = .1f;
		fixtureDef1.restitution = 0f;
		fixtureDef1.friction = 0;

		body.createFixture(fixtureDef1);

		// Ground body

		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.type = BodyDef.BodyType.StaticBody;
		bodyDef2.position.set(Gdx.graphics.getWidth() / 2 / PIXELS_TO_METERS,
				Gdx.graphics.getHeight() / 2 / PIXELS_TO_METERS);

		floorBody = world.createBody(bodyDef2);

		// "Ground Shape"
		EdgeShape shape2 = new EdgeShape();
		shape2.set(-Gdx.graphics.getWidth() / PIXELS_TO_METERS, -Gdx.graphics.getHeight() / PIXELS_TO_METERS + 50/PIXELS_TO_METERS,
				Gdx.graphics.getWidth() / PIXELS_TO_METERS, -Gdx.graphics.getHeight() / PIXELS_TO_METERS + 50/PIXELS_TO_METERS);

		// Ground Fixture
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.shape = shape2;
		fixtureDef2.density = 0;
		fixtureDef2.restitution = 0f;
		fixtureDef2.friction = 0;

		floorBody.createFixture(fixtureDef2);
		
		// Platform body

		BodyDef bodyDef3 = new BodyDef();
		bodyDef3.type = BodyDef.BodyType.DynamicBody;
		bodyDef3.position.set(0 / PIXELS_TO_METERS, 0 / PIXELS_TO_METERS - 25 / PIXELS_TO_METERS);
		bodyDef3.fixedRotation = true;

		platformBody = world.createBody(bodyDef3);

		// "Platform Shape"
		PolygonShape shape3 = new PolygonShape();
		shape3.setAsBox(playerHeight / PIXELS_TO_METERS, playerWidth / PIXELS_TO_METERS);

		// Platform Fixture
		FixtureDef fixtureDef3 = new FixtureDef();
		fixtureDef3.shape = shape3;
		//fixtureDef3.filter.groupIndex = -1;
		fixtureDef3.density = 0f;
		fixtureDef3.restitution = 0;
		fixtureDef3.friction = 0;

		platformBody.createFixture(fixtureDef3);
		
		debugRenderer = new Box2DDebugRenderer();
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight());

		shape1.dispose();
		shape2.dispose();
		shape3.dispose();
		
		Gdx.input.setInputProcessor(this);
		
		world.setContactListener(new ContactListener() {
			
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beginContact(Contact contact) {
			//	if((contact.getFixtureA().getBody() == floorBody &&
				//		contact.getFixtureB().getBody() == body)
				//		|| (contact.getFixtureA().getBody() == body &&
				//		contact.getFixtureB().getBody() == floorBody)) {
				//	body.applyForceToCenter(0, MathUtils.random(10, 10), true);
				//}
				
			}
		});
		
	}
	
	@Override
	public void render(){
		camera.update();
		
		world.step(1f/60f, 6, 2);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
				PIXELS_TO_METERS, 0);
		
		batch.begin();
		batch.end();
		
		debugRenderer.render(world, debugMatrix);
		
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		
		if(keycode == Input.Keys.RIGHT)
			body.applyLinearImpulse((playerWidth / PIXELS_TO_METERS) * .25f, 0, 0, 0, true);
		if(keycode == Input.Keys.LEFT)
			body.applyLinearImpulse(-(playerWidth / PIXELS_TO_METERS) * .25f, 0, 0, 0, true);
		
		
		if(keycode == Input.Keys.UP)
			body.applyLinearImpulse(0, (playerHeight / PIXELS_TO_METERS) * .5f, 0, 0, true);
		if(keycode == Input.Keys.DOWN)
			body.applyLinearImpulse(0, -(playerHeight / PIXELS_TO_METERS) * .5f, 0, 0, true);
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Input.Keys.RIGHT)
			body.applyLinearImpulse(-(playerWidth / PIXELS_TO_METERS) * .25f, 0, 0, 0, true);
		if(keycode == Input.Keys.LEFT)
			body.applyLinearImpulse((playerWidth / PIXELS_TO_METERS) * .25f, 0, 0, 0, true);


		if(keycode == Input.Keys.COMMA){
			body.getFixtureList().first().setRestitution(body.getFixtureList().
					first().getRestitution() - .1f);
		}
				
			
		if(keycode == Input.Keys.PERIOD){
			body.getFixtureList().first().setRestitution(body.getFixtureList().
					first().getRestitution() + .1f);	
		}		
			
		return true;
		
        }

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
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
	
	@Override
	public void dispose(){
		world.dispose();
		
	}

}
