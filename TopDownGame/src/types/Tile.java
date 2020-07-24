package types;

import de.matthiasmann.twl.renderer.Resource;

public class Tile {
    public boolean hasCollision;
    public boolean transparent;

    public float textureCoordinateX, textureCoordinateY;

    public Tile(int TextureIndex, boolean collision, boolean transparent){
        int indexX = TextureIndex % Resources.tileTextureMap.TileCountX;
        int indexY = TextureIndex / Resources.tileTextureMap.TileCountY;

        textureCoordinateX = indexX / (float)Resources.tileTextureMap.TileCountX;
        textureCoordinateY = indexY / (float)Resources.tileTextureMap.TileCountY;

        hasCollision = collision;
        this.transparent = transparent;
    }
}
