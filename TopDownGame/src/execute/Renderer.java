package execute;

import org.lwjgl.opengl.*;

import java.io.IOException;
import types.Texture;
import types.TextureLoader;
import types.Tile;
import types.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Renderer{
    private static final int TileSize = 32;

    private Tile[] tiles;
    private Texture cursor;

    private float cameraPositionX = 0;
    private float cameraPositionY = 0;



    public long InitOpenGLContext(int windowX, int windowY){
        if(!glfwInit()){
            System.err.println("GLFW Window failed to initialize!");
            System.exit(1);
        }

        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        long window = glfwCreateWindow(windowX, windowY, "Title", 0, 0);


        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(1);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, windowX, windowY, 0, 1, -1);

        return window;
    }

    public void LoadTextureResources(){
        tiles = new Tile[2];

        //Create fallback texture
        Texture fallback = null;
        try {
             fallback = TextureLoader.loadTextureNoFallback("res/dev/error.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load textures
        tiles[0] = new Tile("res/ground/grass.png", false, fallback);
        tiles[1] = new Tile("res/ground/sand.png", false, fallback);

        cursor = TextureLoader.loadTexture("res/ui/cursor.png", fallback);

    }

    public void DrawChunk(int chunkPosX, int chunkPosY){
        int chunkOffsetX = (20 * 32) * chunkPosX;
        int chunkOffsetY = (15 * 32) * chunkPosY;

        int[][] localBiomeMap = PerlinGeneration.GenerateChunkBiomeMap(chunkPosX, chunkPosY);

        for(int tileY = 0; tileY < 15; tileY++){
            for(int tileX = 0; tileX < 20; tileX++){
                tiles[localBiomeMap[tileX][tileY]].texture.Bind();
                glBegin(GL_QUADS);
                    glTexCoord2i(0,0);glVertex2i((TileSize * tileX) + chunkOffsetX, (TileSize * tileY) + chunkOffsetY);
                    glTexCoord2i(1,0);glVertex2i((TileSize * tileX) + TileSize + chunkOffsetX, (TileSize * tileY) + chunkOffsetY);
                    glTexCoord2i(1,1);glVertex2i((TileSize * tileX) + TileSize + chunkOffsetX, (TileSize * tileY) + TileSize + chunkOffsetY);
                    glTexCoord2i(0,1);glVertex2i((TileSize * tileX) + chunkOffsetX, (TileSize * tileY) + TileSize + chunkOffsetY);
                glEnd();
            }
        }
    }

    public void DrawChunksInRenderDistance(int chunkPositionX, int chunkPositionY, int renderDistance) {
        for(int y = chunkPositionY - renderDistance; y < chunkPositionY + renderDistance; y++){
            for(int x = chunkPositionX - renderDistance; x < chunkPositionX + renderDistance; x++){
                DrawChunk(x, y);
            }
        }
    }

    public void DrawCursor(double mouseX, double mouseY){
        cursor.Bind();
        glBegin(GL_QUADS);
            glTexCoord2i(0,0);glVertex2d(mouseX + cameraPositionX, mouseY + cameraPositionY);
            glTexCoord2i(1,0);glVertex2d(mouseX + cameraPositionX + 16, mouseY + cameraPositionY);
            glTexCoord2i(1,1);glVertex2d(mouseX + cameraPositionX + 16, mouseY + cameraPositionY + 16);
            glTexCoord2i(0,1);glVertex2d(mouseX + cameraPositionX, mouseY + cameraPositionY + 16);
        glEnd();
    }

    public void UpdateCameraPosition(float x, float y) {
        glTranslatef(cameraPositionX - x, cameraPositionY - y, 0f);
        cameraPositionX = x;
        cameraPositionY = y;
    }
}
