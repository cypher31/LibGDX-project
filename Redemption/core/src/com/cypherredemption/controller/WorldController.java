package com.cypherredemption.controller;

import java.util.HashMap;
import java.util.Map;

import Characters.Player;
import Characters.Player.State;
import World.TestLvl;


public class WorldController {
	
	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}
	
	private Player player;
	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
		
	}
	
	public WorldController (TestLvl testLvl){
		this.player = testLvl.getPlayer();
	}


	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}
	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}
	public void jumpPressed() {
		keys.get(keys.put(Keys.JUMP, true));
	}
	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}
	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}
	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}
	public void jumpReleased() {
		keys.get(keys.put(Keys.JUMP, false));
	}
	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}
	
	public void update(float delta) {
		processInput();
		player.update(delta);
	}

	//changes player states and parameters based on the input controls
	private void processInput() {
		if(keys.get(Keys.LEFT)){
			player.setFacingLeft(true);
			player.setState(State.WALKING);
			player.getVelocity().x = -Player.SPEED;
		}

		if(keys.get(Keys.RIGHT)){
			player.setFacingLeft(false);
			player.setState(State.WALKING);
			player.getVelocity().x = Player.SPEED;
		}
		
		if((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) 
				|| (!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT))){
			player.setState(State.IDLE);
			player.getAcceleration().x = 0;
			player.getVelocity().x = 0;
		}
			
	}


}
