package com.victor.entities;

import java.awt.image.BufferedImage;

import com.victor.main.Game;

public class Coin extends Entity{
	
	public BufferedImage[] COIN;

	public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
		
		
		COIN = new BufferedImage [1];
		
		COIN[0] = Game.spritesheet.getSprite(32, 48, 16, 16);
		//COIN[1] = Game.spritesheet.getSprite(48, 48, 16, 16);
	}

}
