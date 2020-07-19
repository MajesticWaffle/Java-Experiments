package types;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int id;

    public Texture(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void Bind(){
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
