package com.victor.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.victor.main.Game;
import com.victor.world.Camera;
import com.victor.world.World;


public class Player extends Entity {
	
	public boolean right, left;
	
	public boolean isJumping = false;
	public boolean jump = false;
	public int jumpHeight = 48, jumpFrames = 0;
	
	private int framesAnimation = 0;
	private int maxFrames = 15;
	private int maxSprite = 2;
	private int curSprite = 0;
	
	private double gravity = 2;
	public int dir = 1;
	
	
	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
	

	}
	
	public void tick() {
		// CAMADA DE RENDER
		depth = 2;
		
		//LOGICA GRAVIDADE
		if(World.isFree((int) x, (int) (y + gravity)) && isJumping == false) {
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
		
		
		//LOGICA DE PULO
		if(jump) {
			if(!World.isFree(this.getX(), this.getY() + 1)) {
				isJumping = true;			
			}else {
				jump = false;
			}
		}
		if (isJumping) {
			if(World.isFree(this.getX(), this.getY() - 2)) {
				y-=2;
				jumpFrames+=2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping =false;
				jump = false;
				jumpFrames = 0;
			}
		}
		
		//SISTEMA DE CAMERA
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
		

	
	public void render(Graphics g) { 
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
		}else if (dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
		}
		super.render(g);
	}
}
