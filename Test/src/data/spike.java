package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import org.newdawn.slick.opengl.Texture;

public class spike {

	private Texture texture;
	private float x, y;
	private int damage, height, width;

	public spike(Texture texture, float x, float y, int damage) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.height = 250;
		this.width = 139;

	}

	public void update() {
		draw();
	}

	public boolean checkIfVisible() {
		if (x < 0) {
			return true;
		} else if (x > WIDTH) {
			return true;
		}
		if (y < 0) {
			return true;
		} else if (y > HEIGHT) {
			return true;
		}
		return false;
	}

	public void draw() {
		DrawQuadTex(texture, x, y, width, height);
	}
}
