package types;

public class Tile {
    public Texture texture;
    public boolean hasCollision;

    public Tile(String fileName, boolean collision, Texture fallbackTexture){
        texture = TextureLoader.loadTexture(fileName, fallbackTexture);
        hasCollision = collision;
    }
}
