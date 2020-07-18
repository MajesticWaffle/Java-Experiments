package types;

public class Item {
    public String displayName;
    public Texture texture;

    public Item(String displayName, String fileName, Texture fallbackTexture){
        texture = TextureLoader.loadTexture(fileName, fallbackTexture);
        this.displayName = displayName;
    }
}
