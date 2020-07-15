package execute;

import org.lwjgl.BufferUtils;
import types.Player;
import types.Time;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Application {

    private final int renderDistance = 2;

    public void Start(){
        Renderer renderer = new Renderer();
        Player player = new Player();

        long window = renderer.InitOpenGLContext(640, 480);

        glfwSetKeyCallback(window, new Input());
        glfwSetMouseButtonCallback(window, new Mouse());
        renderer.LoadTextureResources();


        while(!glfwWindowShouldClose(window)){
            Time.Update();
            Input.Update();
            Mouse.Update();

            glfwPollEvents();

            renderer.DrawChunksInRenderDistance(player.ChunkPositionX(), player.ChunkPositionY(), renderDistance);

            DoubleBuffer posX = BufferUtils.createDoubleBuffer(1), posY = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, posX, posY);
            renderer.DrawCursor(posX.get(), posY.get());

            player.y += ((Input.GetKey(GLFW_KEY_LEFT_SHIFT) ? player.runSpeed : player.walkSpeed) * Time.deltaTime)
                                        * ((Input.GetKey(GLFW_KEY_S) ? 1 : 0) - (Input.GetKey(GLFW_KEY_W) ? 1 : 0));

            player.x += ((Input.GetKey(GLFW_KEY_LEFT_SHIFT) ? player.runSpeed : player.walkSpeed) * Time.deltaTime)
                                        * ((Input.GetKey(GLFW_KEY_D) ? 1 : 0) - (Input.GetKey(GLFW_KEY_A) ? 1 : 0));

            renderer.UpdateCameraPosition(player.x, player.y);
            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    public static void main(String[] args){
        new Application().Start();
    }
}
