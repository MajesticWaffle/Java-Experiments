package types;

import org.lwjgl.glfw.GLFWWindowRefreshCallback;

public class Time{
    private static float lastFrame;
    public static float deltaTime;

    public static void Update(){
        deltaTime = ((System.nanoTime() - lastFrame) / 1000000000f);
        lastFrame = System.nanoTime();
    }
}
