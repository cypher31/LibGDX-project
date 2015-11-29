package com.physics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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

public class Physics3point5 extends ApplicationAdapter{
	
	SpriteBatch batch;
	Sprite sprite, sprite2;
	Texture img;
	World world;
	Body body, body2;
	Body bodyEdgeScreen;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;
	BitmapFont font;

	final float PIXELS_TO_METERS = 100f;
	
	@Override
	public void create(){
		
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		sprite = new Sprite(img);
		
		sprite.setPosition(-sprite.getWidth() / 2, - sprite.getHeight() / 2 + 200);
		
		sprite2 = new Sprite(img);
		
		sprite2.setPosition(-sprite2.getWidth() / 2 + 20, - sprite2.getHeight() / 2 + 400);
		
		world = new World(new Vector2(0, -9.8f), true);
		
		//sprite1 physics body
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) / PIXELS_TO_METERS, 
				(sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);
		
		body = world.createBody(bodyDef);
		
		//sprite1 physics body
		
		BodyDef bodyDef2 = new BodyDef();
		bodyDef2.type = BodyDef.BodyType.DynamicBody;
		bodyDef2.position.set((sprite2.getX() + sprite2.getWidth()/2) / PIXELS_TO_METERS, 
				(sprite2.getY() + sprite2.getHeight()/2) / PIXELS_TO_METERS);
				
		body2 = world.createBody(bodyDef2);		
		
		//Same shape for both sprites
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth() / 2 / PIXELS_TO_METERS, 
				sprite.getHeight() / 2 / PIXELS_TO_METERS);
		
		//Sprite 1
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = .1f;
		fixtureDef.restitution = .5f;
		
		body.createFixture(fixtureDef);

		//Sprite 2
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.shape = shape;
		fixtureDef2.density = .1f;
		fixtureDef2.restitution = .5f;
				
		body.createFixture(fixtureDef2);
		
		shape.dispose();
		
		BodyDef bodyDef3 = new BodyDef();
		bodyDef3.type = BodyDef.BodyType.StaticBody;
		float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
		
		//Set height to 50 px above bottom of screen
		
		float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS - 50/PIXELS_TO_METERS;
		
		bodyDef3.position.set(0,0);
		FixtureDef fixtureDef3 = new FixtureDef();
		
		EdgeShape edgeShape = new EdgeShape();
		edgeShape.set(-w/2, -h/2, w/2, -h/2);
		fixtureDef3.shape = edgeShape;
		
		bodyEdgeScreen = world.createBody(bodyDef3);
		bodyEdgeScreen.createFixture(fixtureDef3);
		edgeShape.dispose();

		debugRenderer = new Box2DDebugRenderer();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), 
				Gdx.graphics.getHeight());
		
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
				if((contact.getFixtureA().getBody() == bodyEdgeScreen &&
						contact.getFixtureB().getBody() == body2)
						|| (contact.getFixtureA().getBody() == body2 &&
						contact.getFixtureB().getBody() == bodyEdgeScreen)) {
					body.applyForceToCenter(0, MathUtils.random(20, 50), true);
					body2.applyForceToCenter(0,  MathUtils.random(20 ,50), true);
				}
				
			}
		});
		
	}
	
	private float elapsed = 0;
	
	@Override
	public void render(){
		camera.update();
		world.step(.1f/60f, 6, 2);

		sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) 
				- sprite.getWidth() / 2, (body.getPosition().y * PIXELS_TO_METERS)
				- sprite.getHeight() / 2);
		
		sprite.setRotation((float)Math.toDegrees(body.getAngle()));
		
		sprite2.setPosition((body2.getPosition().x * PIXELS_TO_METERS) 
				- sprite2.getWidth() / 2, (body2.getPosition().y * PIXELS_TO_METERS)
				- sprite2.getHeight() / 2);
		
		sprite2.setRotation((float)Math.toDegrees(body2.getAngle()));
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
				PIXELS_TO_METERS, 0);
		
		batch.begin();
	
		batch.draw(sprite, sprite.getX(), sprite.getY(), sprite.getOriginX(),
				sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(),
				sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
		
		batch.draw(sprite2, sprite2.getX(), sprite2.getY(), sprite2.getOriginX(),
				sprite2.getOriginY(), sprite2.getWidth(), sprite2.getHeight(),
				sprite2.getScaleX(), sprite2.getScaleY(), sprite2.getRotation());
	
	font.draw(batch, "Restitution: " + body.getFixtureList().first().getRestitution(),
			-Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		
		batch.end();
		
		debugRenderer.render(world, debugMatrix);
		
	}
	@Override
	public void dispose(){
		img.dispose();
		world.dispose();
		
	}

}
