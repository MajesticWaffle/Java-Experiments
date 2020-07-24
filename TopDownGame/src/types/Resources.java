package types;

import execute.PerlinGeneration;
import glTypes.Texture;

import java.awt.*;
import java.io.IOException;

public class Resources {

    public static Tile[] tiles;

    public static Texture tileTextureMap;
    public static Texture cursor;
    public static Texture font;

    public static final String fontIndices = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!\"#$%&\'()*+,-./:;<=>?@[\\]^_ {|}~0123456789";

    public static Color[] textColors = {
            new Color(0, 0, 0),
            new Color(0,0,127),
            new Color(0,127,0),
            new Color(0, 170, 170),
            new Color(170,0,0),
            new Color(170,0, 170),
            new Color(255, 170,0),
            new Color(170,170,170),
            new Color(85,85,85),
            new Color(85,85,255),
            new Color(85,255,85),
            new Color(85,255,255),
            new Color(255,85,85),
            new Color(255, 85,255),
            new Color(255,255, 85),
            new Color(255,255,255),};

    public static void LoadTileResources(){
        tiles = new Tile[255];

        //Create fallback texture
        Texture fallback = null;
        try {
            fallback = TextureLoader.loadTextureNoFallback("/res/dev/colorspace.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        tileTextureMap = TextureLoader.loadTexture("/res/tilemap.png", fallback);

        //Ground tiles
        tiles[0] = new Tile(0, false, false); //Grass
        tiles[1] = new Tile(2, false, false); //Grass Edge
        tiles[2] = new Tile(1, false, false); // Dirt

        tiles[3] = new Tile(3, false, true); //water
        tiles[4] = new Tile(4, false, true); //Water Edge

        cursor = TextureLoader.loadTexture("/res/ui/cursor.png", fallback);
        font = TextureLoader.loadTexture("/res/ui/font.png", fallback);
    }

    public static Chunk LoadChunk(int chunkPosX, int chunkPosY) {

        //Attempt to load world


        //meh, l8r


        //Generate new chunk if an existing file cannot be found.

        return PerlinGeneration.GenerateChunk(chunkPosX, chunkPosY);
    }

    public enum TileType{
        Grass,
        GrassEdge,
        Dirt,
        Water,
        WaterEdge,
    }
}
