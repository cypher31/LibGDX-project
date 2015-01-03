package com.physics.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Physics1 extends ApplicationAdapter {
	
	SpriteBatch batch; //Draws the sprite
	Sprite sprite; //Puts the sprite on the screen with the correct coordinates
	Texture img; //Find out what a texture is
	World world; //BOX2D world where the magic happens
	Body body; //Body upon which we apply the magic
	
	@Override
	public void create () {
		batch = new SpriteBatch(); //calls the sprite batch on create
		img = new Texture("badlogic.jpg"); //loads our image from the disk
		sprite = new Sprite(img); //places the texture onto the sprite
		
		//Position the sprite
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
		
		//Create the physics world where the simulation happens
		world = new World(new Vector2(0, -9.8f), true); //Vector2(A,B) A = Gravity X, B = Gravity Y
		
		//Create our body definition which we attach our sprite to
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		//Set position of the body at our sprite origin
		bodyDef.position.set(sprite.getX(), sprite.getY());
		
		//Add the body to the world
		body = world.createBody(bodyDef);
		
		//Define the shape of the body we are creating
		PolygonShape shape = new PolygonShape();
		//Set the size of the polygon as the same size as the sprite
		shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
		
		//Fixture def is where we put density and stuffs
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		
		Fixture fixture = body.createFixture(fixtureDef);
		
		shape.dispose();
		
	}

	@Override
	public void render () {
		//Move the world forward by the amount of time passed since the last frame
		//this should not be done in the render loop typically
		world.step(Gdx.graphics.getDeltaTime(),6,2);
		
		//update the sprite position 
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
		
		//Standard
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(sprite, sprite.getX(), sprite.getY());
		batch.end();
	}
	
	@Override
	public void dispose(){
		img.dispose();
		world.dispose();
	}
}
