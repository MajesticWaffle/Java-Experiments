package execute;

import org.apache.commons.lang.ArrayUtils;
import org.lwjgl.opengl.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import types.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static types.Resources.*;

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

        glColor3f(1f,1f,1f);
        cursor.Bind();
        glBegin(GL_QUADS);
            glTexCoord2i(0,0);glVertex2d(mouseX + cameraPositionX, mouseY + cameraPositionY);
            glTexCoord2i(1,0);glVertex2d(mouseX + cameraPositionX + 16, mouseY + cameraPositionY);
            glTexCoord2i(1,1);glVertex2d(mouseX + cameraPositionX + 16, mouseY + cameraPositionY + 16);
            glTexCoord2i(0,1);glVertex2d(mouseX + cameraPositionX, mouseY + cameraPositionY + 16);
        glEnd();
    }

    public void DrawInventoryScreen(Item[] items, int[] counts, double mouseX, double mouseY){
        //Display background
        glDisable(GL_TEXTURE_2D);
        glColor3f(0.5f,0.5f,0.5f);
        glBegin(GL_QUADS);
            glVertex2f(0 + cameraPositionX,0 + cameraPositionY);
            glVertex2f(306 + cameraPositionX,0 + cameraPositionY);
            glVertex2f(306 + cameraPositionX,154 + cameraPositionY);
            glVertex2f(0 + cameraPositionX,154 + cameraPositionY);
        glEnd();

        for(int i = 0; i < 32; i++){
            int posX = i % 8;
            int posY = i / 8;

            //Icon border
            glColor3f(0.25f,0.25f,0.25f);
            glBegin(GL_QUADS);
                glVertex2f((36 * posX) + (2 * (posX + 1)) + cameraPositionX,(36 * posY) + (2 * (posY + 1)) + cameraPositionY);
                glVertex2f((36 * posX) + (2 * (posX + 1)) + 36 + cameraPositionX,(36 * posY) + (2 * (posY + 1)) + cameraPositionY);
                glVertex2f((36 * posX) + (2 * (posX + 1)) + 36 + cameraPositionX,(36 * posY) + (2 * (posY + 1)) + 36 + cameraPositionY);
                glVertex2f((36 * posX) + (2 * (posX + 1)) + cameraPositionX,(36 * posY) + (2 * (posY + 1)) + 36 + cameraPositionY);
            glEnd();

            //Icon background
            glColor3f(0.75f,0.75f,0.75f);
            glBegin(GL_QUADS);
                glVertex2f((32 * posX) + (6 * posX) + 4 + cameraPositionX,(32 * posY) + (6 * posY) + 4 + cameraPositionY);
                glVertex2f((32 * posX) + (6 * posX) + 4 + 32 + cameraPositionX,(32 * posY) + (6 * posY) + 4 + cameraPositionY);
                glVertex2f((32 * posX) + (6 * posX) + 4 + 32 + cameraPositionX,(32 * posY) + (6 * posY) + 4 + 32 + cameraPositionY);
                glVertex2f((32 * posX) + (6 * posX) + 4 + cameraPositionX,(32 * posY) + (6 * posY) + 4 + 32 + cameraPositionY);
            glEnd();

            glColor3f(1f,1f,1f);
            try {
                items[i].texture.Bind();
                glEnable(GL_TEXTURE_2D);
                glBegin(GL_QUADS);
                    glTexCoord2i(0, 0); glVertex2f((32 * posX) + (6 * posX) + 4 + cameraPositionX, (32 * posY) + (6 * posY) + 4 + cameraPositionY);
                    glTexCoord2i(1, 0); glVertex2f((32 * posX) + (6 * posX) + 4 + 32 + cameraPositionX, (32 * posY) + (6 * posY) + 4 + cameraPositionY);
                    glTexCoord2i(1, 1); glVertex2f((32 * posX) + (6 * posX) + 4 + 32 + cameraPositionX, (32 * posY) + (6 * posY) + 4 + 32 + cameraPositionY);
                    glTexCoord2i(0, 1); glVertex2f((32 * posX) + (6 * posX) + 4 + cameraPositionX, (32 * posY) + (6 * posY) + 4 + 32 + cameraPositionY);
                glEnd();
                glDisable(GL_TEXTURE_2D);

            }catch(NullPointerException e){

            }
        }

        try {
            //Display item name and count
            int mouseXIndex = (int) Math.floor((mouseX) / 38f);
            int mouseYIndex =  (int) Math.floor((mouseY) / 38f);
            if((mouseXIndex >= 0 && mouseXIndex < 8) && (mouseYIndex >= 0 && mouseYIndex < 4)) {
                int mouseIndex = mouseXIndex + (mouseYIndex * 8);
                DrawText(items[mouseIndex].displayName + " x" + counts[mouseIndex], (int) mouseX + 16, (int) mouseY + 16, 1, false);
            }
        }
        catch(NullPointerException e){

        }
    }

    public void DrawText(String text, int posX, int posY, int scale, boolean isWorldPosition){
        char[] charArray = text.toCharArray();

        float finalPositionX = posX + (isWorldPosition ? 0 : cameraPositionX);
        float finalPositionY = posY + (isWorldPosition ? 0 : cameraPositionY);

        int charSize = 16 * scale;

        for(int charIndex = 0; charIndex < charArray.length; charIndex++){
            char[] chars = fontIndices.toCharArray();
            int indexLinear = ArrayUtils.indexOf(chars, charArray[charIndex]);

            int indexX = indexLinear % 10;
            int indexY = indexLinear / 10;

            float textureOffsetX = indexX / 10f;
            float textureOffsetY = indexY / 10f;

            font.Bind();
            glBegin(GL_QUADS);
                glTexCoord2f(textureOffsetX,textureOffsetY);glVertex2f(finalPositionX + (charIndex * charSize), finalPositionY);
                glTexCoord2f((1f / 10f) + textureOffsetX,textureOffsetY);glVertex2f(finalPositionX + (charIndex * charSize) + charSize, finalPositionY);
                glTexCoord2f((1f / 10f) + textureOffsetX,(1 / 10f) + textureOffsetY);glVertex2f(finalPositionX + (charIndex * charSize) + charSize, finalPositionY + charSize);
                glTexCoord2f(textureOffsetX,(1 / 10f) + textureOffsetY);glVertex2f(finalPositionX + (charIndex * charSize), finalPositionY + charSize);
            glEnd();
        }
    }

    public void DrawNumber(int value, int posX, int posY, int scale, boolean worldPosition){
        DrawText(String.valueOf(value), posX, posY, scale, worldPosition);
    }

    public void UpdateCameraPosition(float x, float y) {
        glTranslatef(cameraPositionX - x, cameraPositionY - y, 0f);
        cameraPositionX = x;
        cameraPositionY = y;
    }
}
