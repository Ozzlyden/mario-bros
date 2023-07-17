package com.victor.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.victor.main.Game;
import com.victor.world.Camera;
import com.victor.world.World;


public class Player extends Entity {
	
	public boolean right, left, moved;
	
	public static double life = 100;
	
	public static int currentCoins = 0;
	public static int maxCoins = 0;	
	
	public boolean isJumping = false;
	public boolean jump = false;
	public int jumpHeight = 30, jumpFrames = 0;
	
	private int framesAnimation = 0;
	private int maxFrames = 15;
	private int maxSprite = 2;
	private int curSprite = 0;
	
	private double gravity = 0.3;
	private double vspd = 0;		// Altura do pulo gravidade avancada
	public int dir = 1;
	
	private BufferedImage[] PLAYER_RIGHT;
	private BufferedImage[] PLAYER_LEFT;
	
	
	public Player(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed, sprite);
	
		PLAYER_RIGHT = new BufferedImage[2];
		PLAYER_LEFT = new BufferedImage[2];
		
		PLAYER_RIGHT[0] = Game.spritesheet.getSprite(48, 0, 16, 16);
		PLAYER_RIGHT[1] = Game.spritesheet.getSprite(64, 0, 16, 16);
		PLAYER_LEFT[0] = Game.spritesheet.getSprite(48, 16, 16, 16);
		PLAYER_LEFT[1] = Game.spritesheet.getSprite(64, 16, 16, 16);

	}
	
	public void tick() {
		// CAMADA DE RENDER
		depth = 2;
		moved = false;
		
		vspd += gravity;
		
		
		//LOGICA GRAVIDADE AVANCADA
		if(!World.isFree((int)x, (int) (y + 1)) && jump) {
			vspd = -6;
			jump = false;
		}
		
		if(!World.isFree((int)x,(int)(y + vspd))) {
			
			int signVsp = 0;
			if(vspd >= 0) {
				signVsp = 1;
			}else {
				signVsp = -1;
			}
			while(World.isFree((int)x, (int) (y + signVsp))) {
				y = y + signVsp;
			}
			vspd = 0;
			
			//LOGICA DE DANO CAINDO
			for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy2) {
				if(Entity.isColliding(this, e)) {
					isJumping = true;
					//jumpHeight = 40;
					vspd = -3;
					((Enemy2) e).vida--;
					if(((Enemy2) e).vida == 0) {
						Game.entities.remove(i);
						break;
					}
				}
			}
			}
			
			
		}
		
		y = y + vspd;
		
		/*
		//LOGICA GRAVIDADE SIMPLES
		if(World.isFree((int) x, (int) (y + gravity)) && isJumping == false) {
			y += gravity;
			
			//LOGICA DE DANO CAINDO
			for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy2) {
				if(Entity.isColliding(this, e)) {
					isJumping = true;
					jumpHeight = 40;
					((Enemy2) e).vida--;
					if(((Enemy2) e).vida == 0) {
						Game.entities.remove(i);
						break;
					}
				}
			}
			}
		}
		*/
		
		//MOVIMENTACAO
		if(right && World.isFree((int) (x + speed),(int) y)) {
			x+=speed;
			dir = 1;
			moved = true;
		}else if(left && World.isFree((int) (x - speed),(int) y)) {
			x-=speed;
			dir = -1;
			moved = true;
		}
		
		//LOGICA DE PULO
		if(jump) {
			if(!World.isFree(this.getX(), this.getY() + 1)) {
				//System.out.println("Pulando");
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
		
		if(moved == true) {
			framesAnimation++;
			if(framesAnimation == maxFrames) {
				curSprite++;
				framesAnimation = 0;
				if(curSprite == maxSprite) {
					curSprite = 0;
				}
			}
			
		}
		
		// SISTEMA DE DANO NA COLISAO
		for(int i = 0; i < Game.entities.size(); i++) {
		Entity e = Game.entities.get(i);
		if(e instanceof Enemy2) {
			if(Entity.isColliding(this, e)) {
				if(Entity.rand.nextInt(100) < 50) {
					life--;
					}
				}
			}
		}
		
		// COLETAR COIN
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Coin) {
				if(Entity.isColliding(this, e)) {
					Game.entities.remove(i);
					Player.currentCoins++;
					break;
				}
			}
		}
		
		//SISTEMA DE CAMERA
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
	}
		

	
	public void render(Graphics g) { 
		
		if(moved == false) {
			sprite = PLAYER_RIGHT[0];
		}
			
			if(dir == 1) {
				sprite = PLAYER_RIGHT[curSprite];
			}else if (dir == -1) {
				sprite = PLAYER_LEFT[curSprite];
			}
		super.render(g);
	}
}
