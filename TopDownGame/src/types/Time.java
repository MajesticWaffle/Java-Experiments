package types;

import org.lwjgl.glfw.GLFWWindowRefreshCallback;

public class Time{
    private static double lastFrame;
    public static double deltaTime;

    public static void Update(){
        deltaTime = ((System.nanoTime() - lastFrame) / 1000000000f);
        lastFrame = System.nanoTime();
    }
}
