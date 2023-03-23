package com.victor.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.victor.main.Game;
import com.victor.world.World;

public class Enemy2 extends Entity{
	
	public boolean right = true, left = false;
	
	private int frames = 0, maxFrames = 25, index = 0, maxIndex = 1;
	
	public BufferedImage[] ENEMY2_RIGHT;
	public BufferedImage[] ENEMY2_LEFT;


	public Enemy2(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
		
		ENEMY2_RIGHT = new BufferedImage [2];
		ENEMY2_LEFT = new BufferedImage [2];
		
		ENEMY2_RIGHT[0] = Game.spritesheet.getSprite(0, 80, 16, 16);
		ENEMY2_RIGHT[1] = Game.spritesheet.getSprite(16, 80, 16, 16);
		ENEMY2_LEFT[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
		ENEMY2_LEFT[1] = Game.spritesheet.getSprite(16, 96, 16, 16);
		
	}

	public void tick() {
		
		//LOGICA GAVIDADE
		if(World.isFree((int) x, (int) (y + 1))) {
			y += 1;
		}
		
		//LOGICA DE MOVIMENTACAO	
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			if(World.isFree((int) (x+16),(int) y+1)){
				right = false;
				left = true;
			}
		}else if(left && World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			if(World.isFree((int) (x-16),(int) y+1)){
				right = true;
				left = false;
			}
		}
		
		//LOGICA DE ANIMACAO
		frames++;
		if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
	}
	
	public void render(Graphics g) {
		if(right) {
			g.drawImage(ENEMY2_RIGHT[index],this.getX(), this.getY(), null);
		}else if(left) {
			g.drawImage(ENEMY2_LEFT[index],this.getX(), this.getY(), null);
		}
	super.render(g);
	}
}
