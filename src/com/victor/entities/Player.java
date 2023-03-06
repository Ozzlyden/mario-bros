package com.victor.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.victor.main.Game;
import com.victor.world.World;


public class Player extends Entity {
	
	public boolean right, left, jump;
	
	private double gravity = 2;
	public int dir = 1;
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 2;
	
	
	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
	

	}
	
	public void tick() {
		// CAMADA DE RENDER
		depth = 2;
		
		//LOGICA GRAVIDADE
		if(World.isFree((int) x, (int) (y + gravity))) {
			y += gravity;
		}
		//MOVIMENTACAO
		if(right && World.isFree((int) (x + speed),(int) y)) {
			x+=speed;
			dir = 1;
		}else if(left && World.isFree((int) (x - speed),(int) y)) {
			x-=speed;
			dir = -1;
		}
		
	}

	
	public void render(Graphics g) {  
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT;
		}else if (dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT;
		}
		super.render(g);
	}
}
