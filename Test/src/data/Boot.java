package data;

import static helpers.Artist.LoadTexture;
import static helpers.Artist.WIDTH;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import helpers.Artist;
import helpers.Clock;

public class Boot {

	public int X = 0, X2 = WIDTH, i = 0;

	public Boot() {
		Artist.BeginSession();

		Texture background = LoadTexture("res/background.png", "png");

		Player p = new Player();

		while (!Display.isCloseRequested()) {
			update(60, p, background);

			checkIfDead(p);
		}

		Display.destroy();

	}

	public static void main(String[] args) {
		new Boot();
	}

	private void repeat(Player p) {
		if (p.moving) {
			if (p.x > (WIDTH * 3) / 4) {
				if (i != 6) { // one i = one restart. Ends on X2.
					X -= 16;
					X2 -= 16;
				} else {
					p.health -= 100;
				}

				if (X == (WIDTH * -1)) {
					X = 0;
					X2 = WIDTH;
					i++;
					System.out.println("Restart");
				}
			}
		}
	}

	private void checkIfDead(Player p) {
		if (p.die) {
			restart(p);
		}
	}

	private void restart(Player p) {
		X = 0;
		X2 = WIDTH;
		i = 0;
	}

	private void update(int FPS, Player p, Texture bg) {
		drawBackground(bg, p);
		p.update();
		Clock.update();
		Display.update();
		Display.sync(FPS);
	}

	private void drawBackground(Texture background, Player p) {
		Artist.DrawQuadTex(background, X, 0, Artist.WIDTH * 1.075f, Artist.HEIGHT * 2);
		Artist.DrawQuadTex(background, X2, 0, Artist.WIDTH * 1.075f, Artist.HEIGHT * 2);
		repeat(p);
	}

}