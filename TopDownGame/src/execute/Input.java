package execute;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback {
    private static boolean[] keys = new boolean[GLFW_KEY_LAST + 1];
    private static int[] actions = new int[GLFW_KEY_LAST + 1];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods){
        keys[key] = action != GLFW_RELEASE;
        actions[key] = action;
    }

    public static boolean GetKey(int keycode){
        return keys[keycode];
    }

    public static boolean GetKeyDown(int keycode){
        return actions[keycode] == GLFW_PRESS;
    }

    public static boolean GetKeyUp(int keycode){
        return actions[keycode] == GLFW_RELEASE;
    }

    protected static void Update()
    {
        Arrays.fill(actions, -1);
    }

}
