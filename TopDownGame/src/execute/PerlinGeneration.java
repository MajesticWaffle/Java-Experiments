package execute;

import external.Noise;
import types.Biome;
import types.Chunk;

import java.util.Random;

import static types.Resources.*;

public class PerlinGeneration {

    public static int seed;
    public static float scale = 0.5f;
    public static float sliceLevel = 0.2f;
    public static int averageWaterSize = 5;
    public static float waterChance = 1f;

    public static Chunk GenerateChunk(int chunkX, int chunkY){
        Chunk newChunk = new Chunk();

        int[][] backgroundMap = new int[20][15];
        int[][] foregroundMap = new int[20][15];

        if(seed == 0) {
            System.err.println("Seed not set!");
        }

        newChunk.chunkSeed = seed * (chunkX + chunkY);

        //Generate Tiles
        Random chunkSeedGen = new Random(newChunk.chunkSeed);
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 20; x++){
                backgroundMap[x][y] = TileType.Grass.ordinal();
                foregroundMap[x][y] = 255;
            }
        }


        if(chunkSeedGen.nextFloat() <= waterChance){
            int waterSize =  averageWaterSize + chunkSeedGen.nextInt(5);
            int waterX = chunkSeedGen.nextInt(20 - waterSize);
            int waterY = chunkSeedGen.nextInt(15 - waterSize);

            for(int x = 0; x < waterSize; x++){
                int offsetY = chunkSeedGen.nextInt(2);
                for(int y = 0; y < waterSize; y++) {
                    if(y == 0){
                        backgroundMap[waterX + x][waterY + y + offsetY] = TileType.GrassEdge.ordinal();
                        foregroundMap[waterX + x][waterY + y + offsetY] = TileType.WaterEdge.ordinal();
                    }else {
                        backgroundMap[waterX + x][waterY + y + offsetY] = TileType.Dirt.ordinal();
                        foregroundMap[waterX + x][waterY + y + offsetY] = TileType.Water.ordinal();
                    }
                }
            }

        }

        newChunk.backgroundMap = backgroundMap;
        newChunk.foregroundMap = foregroundMap;
        return newChunk;
    }
}
