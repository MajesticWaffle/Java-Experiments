package main;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
 
public class Run {
	
	/** The texture that will hold the image details */
	public Texture Textures[] = new Texture[9];
	public Texture Nothing;
	public int Blockdata[][] = new int[16][12];
	public int Inventory[] = new int[9];
	public int InventoryQ[] = new int[9];
	public int SelectedSlot = 0;
	public String Blockname[] = new String[]{"Air","Dirt","Cobblestone","Log","Plank","Bricc","Grass","Leaves","Stone"};
	
	/**
	 * Start the example
	 */
	TrueTypeFont font;
	TrueTypeFont font2;
	 
	public void initTT() {
		// load a default java font
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
	}
	
	public void start() {
		Blockdata = GenerateWorld.Generate();
		Inventory[0] = 2;
		InventoryQ[0] = 10;
		initGL(800,600);
		init();
		initTT();
 
		while (true) {
			if (InventoryQ[SelectedSlot] == 0) {Inventory[SelectedSlot] = 0;}
			controls();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			render();
 
			Display.update();
			Display.sync(100);
 
			if (Display.isCloseRequested()) {
				Display.destroy();
				System.exit(0);
			}
		}
	}
 
	/**
	 * Initialise the GL display
	 * 
	 * @param width The width of the display
	 * @param height The height of the display
	 */
	public void controls(){
		while (Keyboard.next()) {
			Keyboard.enableRepeatEvents(false);
			if (Keyboard.getEventKey() == Keyboard.KEY_1) {
			    SelectedSlot = 0;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_2) {
			    SelectedSlot = 1;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_3) {
			    SelectedSlot = 2;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_4) {
			    SelectedSlot = 3;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_5) {
			    SelectedSlot = 4;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_6) {
			    SelectedSlot = 5;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_7) {
			    SelectedSlot = 6;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_8) {
			    SelectedSlot = 7;
			} 
			if (Keyboard.getEventKey() == Keyboard.KEY_9) {
			    SelectedSlot = 8;
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_U) {
				ExecuteCommand(JOptionPane.showInputDialog("Command: ?"));
			} 
			
			
		}
		int x = Mouse.getX();
		int y = Mouse.getY();
		boolean leftButtonDown = Mouse.isButtonDown(0); // is left mouse button down.
		boolean rightButtonDown = Mouse.isButtonDown(1); // is right mouse button down.
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0 && (!(Blockdata[(x/50)][((600-y)/50)] == 0))) {
		        	TrySave(Blockdata[(x/50)][((600-y)/50)]);
		        	Blockdata[(x/50)][((600-y)/50)] = 0;
		        	
		        }
		    }
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 1) {
		    			if(InventoryQ[SelectedSlot] > 0 && Blockdata[(x/50)][((600-y)/50)] == 0 && (!(InventoryQ[SelectedSlot] == 0))){	
		    				Blockdata[(x/50)][((600-y)/50)] = Inventory[SelectedSlot];
		    				InventoryQ[SelectedSlot] = InventoryQ[SelectedSlot] - 1;
		    			}else{System.out.println("Slot Occupied Or Nothing Selected");}
		        }
		    }
		}
	}

	private void TrySave(int blocksave) {
		int i = 0;
		while(i < 9){
			if(Inventory[i] == blocksave){
				InventoryQ[i]++;
				System.out.println("Added To Slot: " + i);
				System.out.println("New Quantity: " + InventoryQ[i]);
				break;
			}else if(Inventory[i] == 0){
				Inventory[i] = blocksave;
				InventoryQ[i] = 1;
				System.out.println("Saved As New Slot: " + i);
				System.out.println("New Quantity: s" + InventoryQ[i]);
				break;
				
			}else{
				i++;
				System.out.println("Failed To Save In Slot:" + i);
			}
		}
		
	}

	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
 
		GL11.glEnable(GL11.GL_TEXTURE_2D);               
 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
 
        	// enable alpha blending
        	GL11.glEnable(GL11.GL_BLEND);
        	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
 
        	GL11.glViewport(0,0,width,height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
 
	/**
	 * Initialise resources
	 */
	public void init() {
 
		try {
			Nothing = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/nothing.png"));
			int x = 0;
			while (x<9){
				Textures[x] = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/"+ x +".png"));
				x++;
				System.out.println("did it:" + x);
			}
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * draw a quad with the image on it
	 */
	public void render() {
		Color.white.bind();
		
		int x = 0;
		int y = 0;
		while(x<16){
			while(y<12){
				
				Textures[Blockdata[x][y]].bind();
				if(Blockdata[x][y] == 7){Color.green.bind();}else{Color.white.bind();}
		//System.out.println(Blockdata[x][y]);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(x*50,y*50);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(x*50+50,y*50);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(x*50+50,y*50+50);
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(x*50,y*50+50);
		GL11.glEnd();
			y++;
			}
			y = 0;
			x++;
		}
		
		//render bar
	int i = 0;
	while (i < 9){
	Textures[Inventory[i]].bind();
	if(SelectedSlot == i) {Color.white.bind();}else {Color.gray.bind();}
	GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(i*25,0);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(i*25+25,0);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(i*25+25,0+25);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(i*25,0+25);
	GL11.glEnd();
	Nothing.bind();
	Color.white.bind();
	if(SelectedSlot == i) {font.drawString(i*25, 0, Integer.toString(InventoryQ[i]), Color.yellow);}else {font.drawString(i*25, 0, Integer.toString(InventoryQ[i]), Color.white);}
	i++;
	}
	
	}
 
	/**
	 * Main Class
	 */
	public static void main(String[] argv) {
		Run textureExample = new Run();
		textureExample.start();
	}
	public void ExecuteCommand(String command) {
		if(command.equals("giveitem")) {
		TrySave(Integer.parseInt(JOptionPane.showInputDialog("Enter Item ID: ")));	
		
	}else if(command.equals("regenworld -force")) {
		Blockdata = GenerateWorld.Generate();
		
	}else if(command.equals("regenworld")) {
		if(Blockdata == null) {Blockdata = GenerateWorld.Generate();}else {System.out.println("World has already been generated. Use regenworld -force to force the world to regenerate");}
	
	}
	}
}