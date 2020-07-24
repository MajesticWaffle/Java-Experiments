package execute;

import glTypes.*;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import types.Item;
import types.Player;
import types.Time;
import types.Resources;

import java.nio.DoubleBuffer;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Application {

    private final int renderDistance = 2;

    public void Start(){
        Renderer renderer = new Renderer();
        Player player = new Player();

        long window = renderer.InitOpenGLContext(640, 480);

        glfwSetKeyCallback(window, new Input());
        glfwSetMouseButtonCallback(window, new Mouse());
        Resources.LoadTileResources();

        //player.GenerateDebugInv(new Item("Jew Stick", "/res/ground/tree.png", null));
        PerlinGeneration.seed = new Random().nextInt(255);
        System.out.println(PerlinGeneration.seed);



        while(!glfwWindowShouldClose(window)){
            Time.Update();
            Input.Update();
            Mouse.Update();

            glfwPollEvents();

            renderer.DrawChunksInRenderDistance(player.ChunkPositionX(), player.ChunkPositionY(), renderDistance, window);

            DoubleBuffer posX = BufferUtils.createDoubleBuffer(1), posY = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(window, posX, posY);

            double mouseX = posX.get();
            double mouseY = posY.get();

            //renderer.DrawInventoryScreen(player.inventory, player.inventoryCount, mouseX, mouseY);
            //renderer.DrawCursor(mouseX, mouseY);


            Resources.font.Bind(0);


            player.y += ((Input.GetKey(GLFW_KEY_LEFT_SHIFT) ? player.runSpeed : player.walkSpeed) * Time.deltaTime)
                                        * ((Input.GetKey(GLFW_KEY_S) ? 1 : 0) - (Input.GetKey(GLFW_KEY_W) ? 1 : 0));

            player.x += ((Input.GetKey(GLFW_KEY_LEFT_SHIFT) ? player.runSpeed : player.walkSpeed) * Time.deltaTime)
                                        * ((Input.GetKey(GLFW_KEY_D) ? 1 : 0) - (Input.GetKey(GLFW_KEY_A) ? 1 : 0));


            //renderer.UpdateCameraPosition(player.x, player.y);

            glfwSwapBuffers(window);
        }

        glfwTerminate();
    }

    public static void main(String[] args){
        new Application().Start();
    }
}
