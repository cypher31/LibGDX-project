package com.cypherredemption.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.cypherredemption.game.RedemptionMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		 new LwjglApplication(new RedemptionMain(), "Redemption", 1080, 720);

	}
}
