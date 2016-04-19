package data;

import static helpers.Artist.*;
import static helpers.Clock.*;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.Artist;

public class Player {

	private int currentFrame, index, frameSpeed;

	private float y, width, height, startHealth, velx, vely, velxGoal, gravity;
	public float x, health;

	private boolean jumping, movingRight;
	public boolean moving, die;

	private Texture healthBackround, healthForeground, healthBorder, playerWalking4, playerWalking3, playerWalking2,
			playerWalking1, playerStanding, spikeTex = QuickLoad("spike");
	
	private ArrayList<spike> spikes;

	public static final int PLAYERHEIGHT = 64;
	public static final int PLAYERWIDTH = 32;

	public collisionBox box;
	
	Random random = new Random();

	public Player() {
		this.die = false;
		this.health = 100;
		this.gravity = 120;
		this.startHealth = health;
		this.x = WIDTH / 2;
		this.y = 0;
		this.velx = 0;
		this.vely = 0;
		this.width = 32;
		this.height = 64;
		this.velxGoal = 0;
		this.frameSpeed = 5;
		this.moving = false;
		this.jumping = false;
		this.movingRight = false;
		this.index = 0;
		this.currentFrame = 0;
		this.startHealth = 100;
		this.box = new collisionBox(x, y, width, height);
		this.healthBackround = QuickLoad("red");
		this.healthForeground = QuickLoad("green");
		this.healthBorder = QuickLoad("border");
		this.playerStanding = QuickLoad("playerStanding");
		this.playerWalking1 = QuickLoad("playerWalking1");
		this.playerWalking2 = QuickLoad("playerWalking2");
		this.playerWalking3 = QuickLoad("playerWalking3");
		this.playerWalking4 = QuickLoad("playerWalking4");
	}

	public void update() {
		if (die) {
			reset();
		}
		updatePlayer();
		drawHealthBar();

	}

	public void updatePlayer() {
		drawMap();
		box.update(x, width);
		checkCollision(Level.wallRight, Level.wallLeft);

		Mouse.isButtonDown(0);

		while (Keyboard.next()) {

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !jumping || Keyboard.isKeyDown(Keyboard.KEY_W) && !jumping) {
				vely = -60;
				jumping = true;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_K)) {
				health -= 10;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				velxGoal = -80;
				moving = true;
				movingRight = false;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				velxGoal = 80;
				moving = true;
				movingRight = true;
			} else if (!Keyboard.isKeyDown(Keyboard.KEY_A) || !Keyboard.isKeyDown(Keyboard.KEY_D)) {
				velxGoal = 0;
				moving = false;
			}

		}

		if (jumping) {

			if (y + vely < Level.yFloor - 64) {
				y += vely * Delta() * 20;
				vely += gravity * Delta();
			} else {
				y = Level.yFloor - height;
				jumping = false;
			}
		} else {
			if (y + vely - 20 < Level.yFloor - 64) {
				y += vely * Delta() * 20;
				vely += gravity * Delta();
			} else {
				y = Level.yFloor - height;
			}

		}

		velx = approach(velxGoal, velx, Delta() * 250);

		x += velx * Delta() * 8;

		animate();

	}

	private void checkCollision(collisionBox wallRight, collisionBox wallLeft) {
		if (box.checkCollision(wallRight)) {
			x = Level.xWallRight - width - 1;
			velx = 0;
		}
		if (box.checkCollision(wallLeft)) {
			x = Level.xWallLeft + 2;
			velx = 0;
		}

	}

	private float approach(float Goal, float Current, float dt) {
		double Difference = Goal - velx;
		if (Difference > dt) {
			return Current + dt;
		}
		if (Difference < -dt) {
			return Current - dt;
		}
		return Goal;
	}

	public void drawHealthBar() {
		float healthPercentage = health / startHealth;
		DrawQuadTex(healthBackround, 8, 8, 128, 16);
		DrawQuadTex(healthForeground, 8, 8, 128 * healthPercentage, 16);
		DrawQuadTex(healthBorder, 8, 8, 128, 16);
		if (health <= 0) {
			die();
		}
	}
	
	public void drawMap(){
		spike s = new spike(spikeTex, random.nextInt((WIDTH - WIDTH / 2) + 1) + WIDTH / 2, HEIGHT - 64, 10);
		System.out.println(s);
		System.out.println(spikes);
		spikes.add(s);
	}
	
	public void reset() {
		this.die = false;
		this.health = 100;
		this.gravity = 120;
		this.startHealth = health;
		this.x = Artist.WIDTH / 2;
		this.y = 0;
		this.velx = 0;
		this.vely = 0;
		this.width = 32;
		this.height = 64;
		this.velxGoal = 0;
		this.frameSpeed = 5;
		this.moving = false;
		this.jumping = false;
		this.movingRight = false;
		this.index = 0;
		this.currentFrame = 0;
		this.startHealth = 100;
	}

	private void animate() {
		if (index >= (frameSpeed - 1)) {
			currentFrame = (currentFrame + 1) % 4;
		}
		index = (index + 1) % frameSpeed;

		if (!moving) {
			DrawQuadTex(playerStanding, x, y, PLAYERWIDTH, PLAYERHEIGHT);
		} else if (movingRight) {

			if (currentFrame == 0) {

				DrawQuadTex(playerWalking1, x, y, PLAYERWIDTH, PLAYERHEIGHT);
			}
			if (currentFrame == 1) {
				DrawQuadTex(playerWalking2, x, y, PLAYERWIDTH, PLAYERHEIGHT);
			}
			if (currentFrame == 2) {
				DrawQuadTex(playerWalking3, x, y, PLAYERWIDTH, PLAYERHEIGHT);
			}
			if (currentFrame == 3) {
				DrawQuadTex(playerWalking4, x, y, PLAYERWIDTH, PLAYERHEIGHT);

			}

		} else if (!movingRight) {
			if (currentFrame == 0) {
				DrawQuadTexFlip(playerWalking1, x, y, PLAYERWIDTH, PLAYERHEIGHT);
			}
			if (currentFrame == 1) {
				DrawQuadTexFlip(playerWalking2, x, y, PLAYERWIDTH, PLAYERHEIGHT);
			}
			if (currentFrame == 2) {
				DrawQuadTexFlip(playerWalking3, x, y, PLAYERWIDTH, PLAYERHEIGHT);
			}
			if (currentFrame == 3) {
				DrawQuadTexFlip(playerWalking4, x, y, PLAYERWIDTH, PLAYERHEIGHT);

			}

		}
	}

	public void die() {
		die = true;
	}

}