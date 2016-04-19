package data;

import static helpers.Artist.*;

public class Level {

	public static float xFloor = 0, yFloor = HEIGHT - 64, widthFloor = ((WIDTH * 3) / 4) + 64, heightFloor = 1;

	public static float xWallLeft = 0, yWallLeft = 0, widthWallLeft = 2, heightWallLeft = HEIGHT;

	public static float xWallRight = ((WIDTH * 3) / 4) + 64, yWallRight = 0, widthWallRight = 1,
			heightWallRight = HEIGHT;

	public static collisionBox floor = new collisionBox(xFloor, yFloor, widthFloor, heightFloor);

	public static collisionBox wallLeft = new collisionBox(xWallLeft, yWallLeft, widthWallLeft, heightWallLeft);

	public static collisionBox wallRight = new collisionBox(xWallRight, yWallRight, widthWallRight, heightWallRight);

	public Level() {

	}
}
