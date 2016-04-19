package data;

public class collisionBox {

	private float x, width;

	public collisionBox(float x, float y, float width, float height) {
		this.x = x;
		this.width = width;
	}

	public boolean checkCollision(collisionBox box) {
		if (x < box.x + box.width && x + width > box.x) {
			return true;
		} else {
			return false;
		}
	}

	public void update(float x, float width) {
		this.x = x;
		this.width = width;
	}

}
