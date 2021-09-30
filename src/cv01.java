import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.time.LocalTime;
import java.util.Iterator;

public class cv01 {
	int width = 600;
	int height = 600;
	

	long window;
	GLFWErrorCallback errorCallback;
	GLFWKeyCallback keyCallback;

	void spusti() {
		try {
			init();
			GL.createCapabilities();
			loop();

			glfwDestroyWindow(window);
			keyCallback.free();
		} finally {
			glfwTerminate();
			errorCallback.free();
		}
	}

	void init() {
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		if (!glfwInit())
			throw new IllegalStateException("Chyba pri inicializacii GLFW!!!");

		window = glfwCreateWindow(width, height, "UGR1", NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Chyba pri vytvoreni GLFW okna!!!");

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, true);
			}
		});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);

	}

	void vykresliGL() {
//		glBegin(GL_POINTS);
//		glVertex2i(10, 10);
//		glVertex2i(0, 0);
//		glVertex2i(width-1, height-1);
//		glEnd();

		int  sx= width/2, sy = height/2;
		double r = 0.7*width/2;
		//for(int uhol =0; uhol < 360; uhol +=10) {
		//useckaBresenahm(sx, sy , sx+ (int) (r*Math.cos(Math.toRadians(uhol))) ,
		//						 sy+ (int) (r*Math.sin(Math.toRadians(uhol))));
		//}
		glPointSize(2);
		
		//glColor3d (0, 0, 1);
		//kruh(180,160, (int) (0.3*height/2));
		//glColor3d (0, 0, 0);
		//kruh(320,160, (int) (0.3*height/2));
		//glColor3d (1, 0, 0);
		//kruh(460,160, (int) (0.3*height/2));
		//glColor3d (1, 1, 0);
		//kruh(250,220, (int) (0.3*height/2));
		//glColor3d (0, 1, 0);
		//kruh(390,220, (int) (0.3*height/2));
		
		glColor3d (1, 1, 1);
		cas();
		int secunds = LocalTime.now().getSecond();
		int minutes = LocalTime.now().getMinute();
		int hodins = LocalTime.now().getHour();

		glColor3d (0, 0, 1);
		int uhol_pre_second  = (secunds*6)-90;

		glPointSize(3);
		useckaBresenahm(width/2, height/2 , sx+ (int) (r*Math.cos(Math.toRadians(uhol_pre_second))) ,
								 sy+ (int) (r*Math.sin(Math.toRadians(uhol_pre_second))));
		r = 0.5*width/2;
		glPointSize(3);
		glColor3d (0, 1, 0);
		int uhol_pre_minute  = ((minutes*6)+(secunds/6)-90);

		useckaBresenahm(width/2, height/2 , sx+ (int) (r*Math.cos(Math.toRadians(uhol_pre_minute))) ,
				sy+ (int) (r*Math.sin(Math.toRadians(uhol_pre_minute))));

		r = 0.5*width/2;
		glPointSize(3);
		glColor3d (1, 0, 0);
		int temp = 0;
		if( hodins <=12){
			temp = hodins;
		}else{
			temp = hodins - 12;
		}

		int uhol_pre_hodiny  = ((temp*30)+(minutes/2)-90);
		System.out.println(temp);
		useckaBresenahm(width/2, height/2 , sx+ (int) (r*Math.cos(Math.toRadians(uhol_pre_hodiny))) ,
				sy+ (int) (r*Math.sin(Math.toRadians(uhol_pre_hodiny))));
		glColor3d (1, 1, 1);
		int r1 = (int) (0.73*width/2);
		int r2 = (int) (0.79*width/2);
		int r3 = (int) (0.67*width/2);

		for(int uhol =0; uhol < 360; uhol +=6) {
			if(uhol%30 == 0 ) {
				useckaBresenahm(sx + (int) (r2 * Math.cos(Math.toRadians(uhol))), sy + (int) (r2 * Math.sin(Math.toRadians(uhol))), sx + (int) (r3 * Math.cos(Math.toRadians(uhol))),
						sy + (int) (r3 * Math.sin(Math.toRadians(uhol))));
			}else{
				useckaBresenahm(sx + (int) (r2 * Math.cos(Math.toRadians(uhol))), sy + (int) (r2 * Math.sin(Math.toRadians(uhol))), sx + (int) (r1 * Math.cos(Math.toRadians(uhol))),
						sy + (int) (r1 * Math.sin(Math.toRadians(uhol))));
			}
		}
	}
	
	void kruh( int pod_x , int pod_y, int r) {
		
		int x = 0; int y = r;
		int d = 1-r;
		
		glBegin(GL_POINTS);
		while (x <= y) {
			
			glVertex2i(x+pod_x, y+pod_y);
			glVertex2i(-x+pod_x, y+pod_y);
			glVertex2i(x+pod_x, -y+pod_y);
			glVertex2i(-x+pod_x, -y+pod_y);
			
			glVertex2i(y+pod_x, x+pod_y);
			glVertex2i(-y+pod_x, x+pod_y);
			glVertex2i(y+pod_x, -x+pod_y);
			glVertex2i(-y+pod_x, -x+pod_y);
		 if (d < 0) {
		  d = d+(2*x)+3;
		 }
		 else {
		  d = d+(2*(x-y))+5;
		  y = y-1;
		 }
	
		 x = x+1;
	}
		glEnd();
	}
	
	void cas() {
		LocalTime time = LocalTime.now();
		
		int x = 0; int y = (int)(height*0.4);
		int d = 1-(int)(height*0.3);
		
		int pod_x = height/2;
		int pod_y = width/2;
		
		glBegin(GL_POINTS);
		while (x <= y) {
			
			glVertex2i(x+pod_x, y+pod_y);
			glVertex2i(-x+pod_x, y+pod_y);
			glVertex2i(x+pod_x, -y+pod_y);
			glVertex2i(-x+pod_x, -y+pod_y);
			
			glVertex2i(y+pod_x, x+pod_y);
			glVertex2i(-y+pod_x, x+pod_y);
			glVertex2i(y+pod_x, -x+pod_y);
			glVertex2i(-y+pod_x, -x+pod_y);
		 if (d < 0) {
		  d = d+(2*x)+3;
		 }
		 else {
		  d = d+(2*(x-y))+5;
		  y = y-1;
		 }
	
		 x = x+1;
	}
		glEnd();
	}
	
	
	
	void useckaDDA(int x1, int y1, int x2, int y2) {
		double y = y1;
		double k = 1.0*(y2-y1)/(x2-x1);
		System.out.print(k);
		glBegin(GL_POINTS);
		for(int x=x1; x<=x2; x++) {
			y += k;
			glVertex2i(x, (int) y);
		}
	}
	
	void useckaBresenahm(int x1, int y1, int x2, int y2) {
		boolean vymena = Math.abs(x2-x1)<Math.abs(y2-y1);
		if(vymena) {
			int pom=x1; x1=y1; y1=pom;
			int pom2=x2; x2=y2; y2=pom2;
		}
		
		if(x1>x2) {
			int pom=x1; x1=x2; x2=pom;
			int pom2=y1; y1=y2; y2=pom2;
		}
		
		
		int dx = x2-x1, dy = Math.abs(y2-y1);
		int y = y1;
		int k1 = 2*dy;
		int k2 = 2*dy-2*dx;
		int d = 2*dy-dx;
		int krok  = y2 >= y1 ? 1 : -1;
		
		glBegin(GL_POINTS);
		for(int x=x1; x<= x2; x++) {
			if(vymena) {
				glVertex2i(y, x);
			}else {
				glVertex2i(x, y);	
			
			}
		 if (d < 0) {
			d = d+k1; 
		 }
		  
		 else {
		  d = d+k2;
		  y = y+krok;
		 }
		}
		  glEnd();
		
		
	
	}

	void loop() {
		glViewport(0, 0, width, height);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-0.5, width - 0.5, height - 0.5, -0.5, 0, 1);

		glShadeModel(GL_FLAT);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// rgb (0,0,0) je cierne pozadie
		//glClearColor(255.f, 255.f, 255.f, 1.f); // Initialize clear color
		glClearColor(0.f, 0.f, 0.f, 1.f);
		

		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			vykresliGL();

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new cv01().spusti();
	}
}
