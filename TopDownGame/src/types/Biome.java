package types;

public class Biome {
    public String name = "Planes";
    public int groundTileID = 0; //Grass
    public int[] scatterTiles;
    public float scatterDensity = 0.15f; // 0.0f - 1.0f;

    public int averageWaterPoolSize = 0;
    public float WaterPoolChance = 0f;

    public Biome(String name, int groundTileID, int[] scatterTiles, float scatterDensity, int averageWaterPoolSize, float WaterPoolChance){
        this.name = name;
        this.groundTileID = groundTileID;
        this.scatterTiles = scatterTiles;
        this.scatterDensity = scatterDensity;
        this.averageWaterPoolSize = averageWaterPoolSize;
        this.WaterPoolChance = WaterPoolChance;
    }
}
