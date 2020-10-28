package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.Tile;
import world.WallTile;
import world.World;

public class BulletShoot extends Entity {
	
	private double dx;
	private double dy;
	private double speed = 4;
	private int life = 60, curLife = 0;
	
	public BulletShoot(double x, double y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}
	
	public void collidingTile() {
		
		for(int i=0; i < World.tiles.length; i++) {
			Tile t =  World.tiles[i];
			if(t instanceof WallTile) {
				if(Entity.isColiddingTile(this, t)) {
					Game.bulletShootes.remove(this);
				}
			}
		}
	}

	public void tick() {
		
		x += dx * speed;
		y += dy * speed;
		curLife++;
		if(curLife == life) {
			Game.bulletShootes.remove(this);
			return;	
		}
		
		collidingTile();
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
	}

}