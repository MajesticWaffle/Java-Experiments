package types;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Texture {
    private int id;

    public Texture(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void Bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
