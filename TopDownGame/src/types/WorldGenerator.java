package types;

import execute.PerlinGeneration;

import java.io.IOException;

public class WorldGenerator {

    public static Tile[] tiles;
    public static Texture cursor;
    public static Texture numberFont;

    public static Biome[] biomes = {
            new Biome("Plains", 0, new int[]{4}, 0.01f, 5, 0.1f),
            new Biome("Desert", 1, new int[]{5}, 0.01f, 0, 0f),
    };

    public static void LoadTileResources(){
        tiles = new Tile[255];

        //Create fallback texture
        Texture fallback = null;
        try {
            fallback = TextureLoader.loadTextureNoFallback("/res/dev/error.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load textures
        tiles[0] = new Tile("/res/ground/grass.png", false, false, fallback);
        tiles[1] = new Tile("/res/ground/sand.png", false, false, fallback);

        tiles[2] = new Tile("/res/ground/water.png", false, false, fallback);
        tiles[3] = new Tile("/res/ground/water_edge.png", false, true, fallback);

        tiles[4] = new Tile("/res/ground/tree.png", true, true, fallback);
        tiles[5] = new Tile("/res/ground/cactus.png", true,true, fallback);
        cursor = TextureLoader.loadTexture("/res/ui/cursor.png", fallback);
        numberFont = TextureLoader.loadTexture("/res/ui/numberfont.png", fallback);
    }

    public static Chunk LoadChunk(int chunkPosX, int chunkPosY) {

        //Attempt to load world


        //meh, l8r


        //Generate new chunk if an existing file cannot be found.

        return PerlinGeneration.GenerateChunk(chunkPosX, chunkPosY, WorldGenerator.biomes);
    }
}
