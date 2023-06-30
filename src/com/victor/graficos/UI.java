package com.victor.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.victor.entities.Player;
import com.victor.main.Game;


public class UI {

	public void render(Graphics g) {
		
		// BARRA DE VIDA DO PLAYER
		g.setColor(Color.red);
		g.fillRect(10, 10, 200, 30);
		g.setColor(Color.green);
		g.fillRect(10, 10,(int)((Player.life / 100) * 200), 30);
		g.setColor(Color.white);
		g.drawRect(10,10,200, 30);
	}
	
}
