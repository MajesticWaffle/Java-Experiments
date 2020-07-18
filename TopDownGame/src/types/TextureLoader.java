package types;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {

    //Loads a texture with no fallback, program will crash if the file does not exist
    public static Texture loadTextureNoFallback(String fileName) throws NullPointerException, IOException {
        PNGDecoder decoder = new PNGDecoder(TextureLoader.class.getResourceAsStream(fileName));
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);

        //flip the buffer because opengl is weird
        buffer.flip();

        int id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        //disable garbage filtering
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //upload image data into texture
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return new Texture(id);
    }

    //Loads a texture with no fallback
    public static Texture loadTexture(String fileName, Texture fallback) {
        try {
            return loadTextureNoFallback(fileName);
        } catch (Exception e) {
            System.err.println("Texture File: " + fileName + " does not exist, using fallback texture!");
            return fallback;
        }
    }
}
