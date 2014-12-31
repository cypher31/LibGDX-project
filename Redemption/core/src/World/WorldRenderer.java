package World;

import Characters.Player;
import Environment.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class WorldRenderer {
	
	private final static float CAMERA_WIDTH = 10f;
	private final static float CAMERA_HEIGHT = 7f;
	
	private TestLvl testLvl;
	private OrthographicCamera cam;
	
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	public Texture playerTexture;
	private Texture enviroTexture;
	World world;
	Box2DDebugRenderer B2ddebugRenderer = new Box2DDebugRenderer();
	
	private SpriteBatch batch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; //pixels per unit on x axis
	private float ppuY; //pixels per unity on y axis
	
	public void setSize(int w, int h){
		this.width = w;
		this.height = h;
		ppuX = ((float)width / CAMERA_WIDTH);//used to distort the images slightly to get them to fit the screen correctly
		ppuY = ((float)height /CAMERA_HEIGHT);
	}
	
	public WorldRenderer(TestLvl testLvl, boolean debug){
		this.testLvl = testLvl;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		batch = new SpriteBatch();
		loadTextures();
	}

	private void loadTextures(){
		playerTexture = new Texture(Gdx.files.internal("Sprite-0001 54x51px.png")); //Weird here, didnt need assets/ for some reason
		enviroTexture = new Texture(Gdx.files.internal("Sprite-0001_108x102px.png"));//" "
	}
	
	public void render(){
		batch.begin();
		drawPlayer();
		drawEnviro();
		batch.end();
		if (debug){
			drawdebug();
		}
		B2ddebugRenderer.render(world, cam.combined);
	}	

	private void drawdebug() {
		// TODO Auto-generated method stub
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for(Block block : testLvl.getBlocks()){
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;
			debugRenderer.setColor(1,0,0,1);
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
			Player player = testLvl.getPlayer();
			Rectangle rect = player.getBounds();
			float x1 = player.getPosition().x + rect.x;
			float y1 = player.getPosition().y + rect.y;
			debugRenderer.setColor(0,0,1,1);
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			debugRenderer.end();
		}
	

	private void drawEnviro() {
		// TODO Auto-generated method stub
		for(Block block : testLvl.getBlocks()){
			batch.draw(enviroTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}

	private void drawPlayer() {
		// TODO Auto-generated method stub
		Player player = testLvl.getPlayer();
		batch.draw(playerTexture, player.getPosition().x * ppuX, player.getPosition().y * ppuY, Player.SIZE * ppuX, Player.SIZE * ppuY);
		
	}

	}

