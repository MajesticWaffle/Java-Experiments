package com.MJWaffle.LWJGLTestProject;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Run {
	public static float x = 35f;

	 public static void main(String[] args) throws LWJGLException
     {
      Run r = new Run();
             r.Start();
     }
	 public void Start(){
		 try {
		            createWindow();
		 InitGL();
		     Run();
		 } catch (Exception e) {
		 
		 e.printStackTrace();
		 }
		 }
	    DisplayMode displayMode;

	    private void createWindow() throws Exception {
	         Display.setFullscreen(false);
	         DisplayMode d[] = Display.getAvailableDisplayModes();
	         for (int i = 0; i < d.length; i++) {
	             if (d[i].getWidth() == 640
	                 && d[i].getHeight() == 480
	                 && d[i].getBitsPerPixel() == 32) {
	                 displayMode = d[i];
	                 break;
	             }
	         }
	         Display.setDisplayMode(displayMode);
	         Display.setTitle("LWJGL Voxel engine");
	         Display.create();
	     }
	    private void InitGL() {
	         GL11.glEnable(GL11.GL_TEXTURE_2D);
	         GL11.glShadeModel(GL11.GL_SMOOTH);
	         GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
	         GL11.glClearDepth(1.0); 
	         GL11.glEnable(GL11.GL_DEPTH_TEST);
	         GL11.glDepthFunc(GL11.GL_LEQUAL); 

	         GL11.glMatrixMode(GL11.GL_PROJECTION); 
	         GL11.glLoadIdentity();

	         
	         GLU.gluPerspective(
	           45.0f,
	           (float)displayMode.getWidth()/(float)      displayMode.getHeight(),
	           0.1f,
	           100.0f);

	         GL11.glMatrixMode(GL11.GL_MODELVIEW);
	         GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	     }
	    private void Run() {
	    	 while (!Display.isCloseRequested()) {
	    	 try{
	         if (x>=360) {x = 0f;}
	    	 Render();
	    	                Display.update();
	    	                Display.sync(60);
	    	 }catch(Exception e){
	    	 
	    	 }
	    	 }
	    	 Display.destroy();
	    	 }
	    private void Render() {
	    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	         GL11.glLoadIdentity();  
	         x++;
	         GL11.glTranslatef(0f,0.0f,-7f);             
	         GL11.glRotatef(x,0.0f,1.0f,0.0f);               
	         GL11.glColor3f(0.5f,0.5f,1.0f);  
	              
	         GL11.glBegin(GL11.GL_QUADS);    
	            GL11.glColor3f(1.0f,1.0f,0.0f);           
	            GL11.glVertex3f( 1.0f, 1.0f,-1.0f);        
	            GL11.glVertex3f(-1.0f, 1.0f,-1.0f);        
	            GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
	            GL11.glVertex3f( 1.0f, 1.0f, 1.0f);  
	            GL11.glColor3f(1.0f,0.5f,0.0f);            
	            GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
	            GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
	            GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
	            GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
	            GL11.glColor3f(1.0f,0.0f,0.0f);
	            GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
	            GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
	            GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
	            GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
	            GL11.glColor3f(1.0f,1.0f,0.0f);
	            GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
	            GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
	            GL11.glVertex3f(-1.0f, 1.0f,-1.0f);
	            GL11.glVertex3f( 1.0f, 1.0f,-1.0f);
	            GL11.glColor3f(0.0f,0.0f,1.0f);
	            GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
	            GL11.glVertex3f(-1.0f, 1.0f,-1.0f);
	            GL11.glVertex3f(-1.0f,-1.0f,-1.0f);
	            GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
	            GL11.glColor3f(1.0f,0.0f,1.0f);
	            GL11.glVertex3f( 1.0f, 1.0f,-1.0f);
	            GL11.glVertex3f( 1.0f, 1.0f, 1.0f);
	            GL11.glVertex3f( 1.0f,-1.0f, 1.0f);
	            GL11.glVertex3f( 1.0f,-1.0f,-1.0f);
	        GL11.glEnd();    

	    	 }
}
