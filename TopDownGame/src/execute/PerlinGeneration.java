package execute;

import external.Noise;

public class PerlinGeneration {

    public static float scale = 0.5f;
    public static float sliceLevel = 0.3f;

    public static int[][] GenerateChunkBiomeMap(int chunkX, int chunkY){
        int[][] biomeMap = new int[20][15];
        Noise.seed = 123321;
        for(int y = 0; y < 15; y++){
            for(int x = 0; x < 20; x++){
                float coordinateX = (((float)x / 20f) + chunkX) * scale;
                float coordinateY = (((float)y / 15f) + chunkY) * scale;
                biomeMap[x][y] = (float)Math.abs(Noise.noise(coordinateX, coordinateY)) >= sliceLevel ? 1 : 0;
            }
        }
        return biomeMap;
    }
}
