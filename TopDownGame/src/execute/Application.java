package execute;

import types.Player;
import types.Time;

import static org.lwjgl.glfw.GLFW.*;

public class Application {

    private final int renderDistance = 2;

    public void Start(){
        Renderer renderer = new Renderer();
        Player player = new Player();

        long window = renderer.InitOpenGLContext(640, 480);

        renderer.LoadTextureResources();


        while(!glfwWindowShouldClose(window)){
            glfwPollEvents();
            Time.Update();
            //renderer.DrawChunk(1,0);
            renderer.DrawChunksInRenderDistance(player.ChunkPositionX(), player.ChunkPositionY(), renderDistance);
            player.x += 32f * Time.deltaTime;
            renderer.UpdateCameraPosition(player.x, player.y);
            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    public static void main(String[] args){
        new Application().Start();
    }
}
