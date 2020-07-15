package types;

public class Player {
    public float x;
    public float y;

    public final float walkSpeed = 256f;
    public final float runSpeed = 512f;

    public int ChunkPositionX(){
        return (int)x / (20 * 32);
    }

    public int ChunkPositionY(){
        return (int)y / (15 * 32);
    }
}
