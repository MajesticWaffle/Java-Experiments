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


    public static Chunk GenerateChunk(int chunkX, int chunkY, Biome[] Biomes){
        Chunk newChunk = new Chunk();
        int averageBiome = 0;

        int[][] biomeMap = new int[20][15];
        int[][] tileMap = new int[20][15];
        if(seed == 0) {
            System.err.println("Seed not set!");
        }

        //Generate Biomes
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 20; x++){
                float coordinateX = (((float)(x + seed) / 20f) + chunkX) * scale;
                float coordinateY = (((float)(y + seed) / 15f) + chunkY) * scale;
                biomeMap[x][y] = (float)Math.abs(Noise.noise(coordinateX, coordinateY)) >= sliceLevel ? 1 : 0;
                averageBiome += biomeMap[x][y];
            }
        }
        averageBiome = averageBiome / (15 * 20);

        newChunk.chunkSeed = seed * (chunkX + chunkY);

        //Generate Tiles
        Random chunkSeedGen = new Random(newChunk.chunkSeed);
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 20; x++){
                //Generate a scatter
                if(chunkSeedGen.nextFloat() <= Biomes[biomeMap[x][y]].scatterDensity){
                    tileMap[x][y] = Biomes[biomeMap[x][y]].scatterTiles[chunkSeedGen.nextInt(Biomes[biomeMap[x][y]].scatterTiles.length)];
                }else{
                    tileMap[x][y] = Biomes[biomeMap[x][y]].groundTileID;
                }
            }
        }


        if(chunkSeedGen.nextFloat() <= biomes[averageBiome].WaterPoolChance){
            int waterSize =  biomes[averageBiome].averageWaterPoolSize + chunkSeedGen.nextInt(5);
            int waterX = chunkSeedGen.nextInt(20 - waterSize);
            int waterY = chunkSeedGen.nextInt(15 - waterSize);
            for(int x = 0; x < waterSize; x++){
                int offsetY = chunkSeedGen.nextInt(2);
                for(int y = 0; y < waterSize; y++) {
                    tileMap[waterX + x][waterY + y + offsetY] = (y == 0) ? 3 : 2; //Water
                }
            }
        }


        newChunk.tileMap = tileMap;
        newChunk.biomeMap = biomeMap;
        return newChunk;
    }
}
