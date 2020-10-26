package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	public boolean rigth, left, down, up;
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
	public int dir = rightDir;
	public int maskx = 3, masky = 0, maskw = 10, maskh = 14;
	
	public double speed = 1.4;
	public double life = 100, maxLife = 100;
	public int ammo = 0;
	
	private BufferedImage [] rightPlayer;
	private BufferedImage [] leftPlayer;
	private BufferedImage [] upPlayer;
	private BufferedImage [] downPlayer; 
	private BufferedImage [] rightPlayerDamage;
	private BufferedImage [] leftPlayerDamage;
	private BufferedImage [] upPlayerDamage;
	private BufferedImage [] downPlayerDamage;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	public boolean isDamage;
	private int damageFrames;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		rightPlayerDamage = new BufferedImage[4];
		leftPlayerDamage = new BufferedImage[4];
		upPlayerDamage = new BufferedImage[4];
		downPlayerDamage = new BufferedImage[4];
		
		for(int i = 0; i<4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 32, 16, 16);
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 48, 16, 16);
			
			rightPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 64, 16, 16);
			leftPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 80, 16, 16);
			upPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 96, 16, 16);
			downPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 112, 16, 16);
		}
	}
	
	public void checkCollisionLifePack() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof LifePack) {
				if(Entity.isColidding(this, atual)) {
					
					if(life <= 90) {
						life += 10;
						Game.entities.remove(atual);
					}
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					ammo += 10;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void tick() {
		
		moved = false;
		
		if(rigth && World.isFree((int)(x+speed), this.getY())) {
			moved =  true;
			dir = rightDir;
			x+=speed;
		}else if (left && World.isFree((int)(x-speed), this.getY())) {
			moved =  true;
			dir = leftDir;
			x-=speed;
		}
			
		if(up && World.isFree(this.getX(),(int)(y-speed))) {
			moved =  true;
			dir = upDir;
			y-=speed;
		}else if (down && World.isFree(this.getX(), (int)(y+speed))) {
			moved =  true;
			dir = downDir;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}else{
			index = 0;
			frames = 0;
		}
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		
		if(isDamage) {
			damageFrames++;
			if(this.damageFrames == 8) {
				damageFrames = 0;
				isDamage = false;
			}
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y =  Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	@Override
	public void render(Graphics g) {
	
		if(!isDamage) {
			if(dir == rightDir)
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else {
			if(dir == rightDir)
				g.drawImage(rightPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}
