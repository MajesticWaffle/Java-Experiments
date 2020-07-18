package execute;

import org.lwjgl.opengl.*;
import java.io.IOException;
import types.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static types.WorldGenerator.*;

public class Renderer{
    private static final int TileSize = 32;

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

    public void DrawChunk(int chunkPosX, int chunkPosY){
        int chunkOffsetX = (20 * 32) * chunkPosX;
        int chunkOffsetY = (15 * 32) * chunkPosY;

        Chunk chunk = LoadChunk(chunkPosX, chunkPosY);

        for(int tileY = 0; tileY < 15; tileY++){
            for(int tileX = 0; tileX < 20; tileX++){
                //render ground tile under transparent tile
                if(tiles[chunk.tileMap[tileX][tileY]].transparent){
                    tiles[biomes[chunk.biomeMap[tileX][tileY]].groundTileID].texture.Bind();
                    glBegin(GL_QUADS);
                    glTexCoord2i(0,0);glVertex2i((TileSize * tileX) + chunkOffsetX, (TileSize * tileY) + chunkOffsetY);
                    glTexCoord2i(1,0);glVertex2i((TileSize * tileX) + TileSize + chunkOffsetX, (TileSize * tileY) + chunkOffsetY);
                    glTexCoord2i(1,1);glVertex2i((TileSize * tileX) + TileSize + chunkOffsetX, (TileSize * tileY) + TileSize + chunkOffsetY);
                    glTexCoord2i(0,1);glVertex2i((TileSize * tileX) + chunkOffsetX, (TileSize * tileY) + TileSize + chunkOffsetY);
                    glEnd();
                }
                tiles[chunk.tileMap[tileX][tileY]].texture.Bind();
                //Render tile
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
        int mouseXTile = (int)Math.floor((mouseX + cameraPositionX) / 32f);
        int mouseYTile = (int)Math.floor((mouseY + cameraPositionY) / 32f);
        
        glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
        glBegin(GL_QUADS);
            glVertex2i((mouseXTile * TileSize), (mouseYTile * TileSize));
            glVertex2i((mouseXTile * TileSize) + 32, (mouseYTile * TileSize));
            glVertex2i((mouseXTile * TileSize) + 32, (mouseYTile * TileSize) + 32);
            glVertex2i((mouseXTile * TileSize), (mouseYTile * TileSize) + 32);
        glEnd();

        glColor3f(1f,1f,1f);
        cursor.Bind();
        glBegin(GL_QUADS);
            glTexCoord2i(0,0);glVertex2d(mouseX + cameraPositionX, mouseY + cameraPositionY);
            glTexCoord2i(1,0);glVertex2d(mouseX + cameraPositionX + 16, mouseY + cameraPositionY);
            glTexCoord2i(1,1);glVertex2d(mouseX + cameraPositionX + 16, mouseY + cameraPositionY + 16);
            glTexCoord2i(0,1);glVertex2d(mouseX + cameraPositionX, mouseY + cameraPositionY + 16);
        glEnd();
    }

    public void DrawInventoryScreen(Item[] items, int[] counts){

    }

    public void DrawNumber(int value, int posX, int posY, int scale, boolean worldPosition){
        char[] digits = String.valueOf(value).toCharArray();

        float finalPositionX = posX + (worldPosition ? 0 : cameraPositionX);
        float finalPositionY = posY + (worldPosition ? 0 : cameraPositionY);

        int charSize = 16 * scale;
        //Display each character
        for(int charIndex = 0; charIndex < digits.length; charIndex++){
            int digit = Integer.parseInt(String.valueOf(digits[charIndex]));

            float textureOffsetX = digit / 10f;

            WorldGenerator.numberFont.Bind();
            glBegin(GL_QUADS);
                glTexCoord2f(textureOffsetX,0); glVertex2f(finalPositionX + (charIndex * charSize), finalPositionY);
                glTexCoord2f((1f / 10f) + textureOffsetX,0); glVertex2f(finalPositionX + (charIndex * charSize) + charSize, finalPositionY);
                glTexCoord2f((1f / 10f) + textureOffsetX,1); glVertex2f(finalPositionX + (charIndex * charSize) + charSize, finalPositionY + charSize);
                glTexCoord2f(textureOffsetX,1); glVertex2f(finalPositionX + (charIndex * charSize), finalPositionY + charSize);
            glEnd();
        }
    }
    public void UpdateCameraPosition(float x, float y) {
        glTranslatef(cameraPositionX - x, cameraPositionY - y, 0f);
        cameraPositionX = x;
        cameraPositionY = y;
    }
}
