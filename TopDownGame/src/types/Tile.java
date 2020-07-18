package types;

public class Tile {
    public Texture texture;
    public boolean hasCollision;
    public boolean transparent;

    public Tile(String fileName, boolean collision, boolean transparent, Texture fallbackTexture){
        texture = TextureLoader.loadTexture(fileName, fallbackTexture);
        hasCollision = collision;
        this.transparent = transparent;
    }
}
