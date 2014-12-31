package World;

import Characters.Player;
import Environment.Block;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class TestLvl {
	
	World world;
	Body playerBody;
	WorldRenderer renderer;
	
	Array<Block> blocks = new Array<Block>();
	public Player player;
	
	public Array<Block> getBlocks(){
		return blocks;
	}
	public Player getPlayer(){
		return player;
	}
	
	public TestLvl(){
		
		world = new World(new Vector2(0,-10), true);
		createDemoWorld();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		
		this.playerBody = world.createBody(bodyDef);
	
		this.playerBody.setUserData(this);
		
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(50f, 50f);
		
		FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = playerBody.createFixture(fixtureDef);
        
        shape.dispose();
	}
	
	private void createDemoWorld(){
		player = new Player(new Vector2(7, 2));
		
		for(int i = 0; i< 10; i++){
			blocks.add(new Block(new Vector2(i,0)));
			blocks.add(new Block(new Vector2(i,6)));
			if(i>2)
			blocks.add(new Block(new Vector2(i,1)));
		}
		blocks.add(new Block(new Vector2(9,2)));
		blocks.add(new Block(new Vector2(9,3)));
		blocks.add(new Block(new Vector2(9,4)));
		blocks.add(new Block(new Vector2(9,5)));
		
		blocks.add(new Block(new Vector2(6,3)));
		blocks.add(new Block(new Vector2(6,4)));
		blocks.add(new Block(new Vector2(6,5)));
	}
	
	

}
