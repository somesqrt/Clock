
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.lwjgl.BufferUtils;

public class cv03 {
	private int sirka = 800;
	private int vyska = 800;
	private int rozmer = Math.min(sirka, vyska)/2; //max. polomer kruhu
	private int sx = sirka/2, sy = vyska/2; //stred obrazovky
	private ByteBuffer buffer = BufferUtils.createByteBuffer(sirka*vyska*4); //4 bpp (bytes per pixel), RGBA
	private int rgba_WHITE = 0xFFFFFFFF; //-1
	private int rgba_BLACK = 0x00000000;
	private ArrayList<Integer> l1 = new ArrayList<Integer>();
	private ArrayList<Integer> l2 = new ArrayList<Integer>();
	private ArrayList<Integer> l3 = new ArrayList<Integer>();
	private ArrayList<Integer> l4 = new ArrayList<Integer>();
	private ArrayList<Integer> ly1 = new ArrayList<Integer>();
	private ArrayList<Integer> ly2 = new ArrayList<Integer>();
	private ArrayList<Integer> ly3 = new ArrayList<Integer>();
	private ArrayList<Integer> ly4 = new ArrayList<Integer>();
	private void vypln(int x, int y) {
  	if(zistiFarbu(x,y) == rgba_BLACK) {
  		bod(x,y);
  		vypln(x-1,y);
  		vypln(x+1,y);
  		vypln(x,y-1);
  		vypln(x,y+1);
  	}

	}
	private void vyplnRiadok(int x, int y) {
			//System.out.println(x+" "+y);
			int xL = x-1;
			//System.out.println(xL+" ");
	  		while(xL>= 0 && zistiFarbu(xL,y) == rgba_BLACK) xL--;
	  		xL++;
	  		int xR = x+1;
	  		while(xR <= sirka-1 && zistiFarbu(xR,y) == rgba_BLACK) xR++;
	  		xR--;
	  		//System.out.println(xL+" "+xR);
	  		riadok(y,xL,xR);
	  		for (x=xL; x<=xR; x++) {
	  			if(y>0 && zistiFarbu(x,y-1) == rgba_BLACK) {
  				vyplnRiadok(x,y-1);
	  			}
  				if(y<vyska-1 && zistiFarbu(x,y+1) == rgba_BLACK) {
  	  				vyplnRiadok(x,y+1);
  				}
	  		}


		}



	private void trojuholniki(int x1, int y1,int x2, int y2,int x3, int y3) {
		l1.clear();
		l2.clear();
		l3.clear();
		l4.clear();
		ly1.clear();
		ly2.clear();
		ly3.clear();
		ly4.clear();

		glBegin(GL_POINTS);

		glColor3f((int)(Math.random()*((1-0)+1))+0,(int)(Math.random()*((1-0)+1))+0,(int)(Math.random()*((1-0)+1))+0);
		Bressenham(x1, y1, x2, y2);

		ly1.addAll(ly4);
		l1.addAll(l4);
		l4.clear();
		ly4.clear();

		Bressenham(x2, y2, x3, y3);

		ly2.addAll(ly4);
		l2.addAll(l4);
		l4.clear();
		ly4.clear();

		Bressenham(x3, y3, x1, y1);

		ly3.addAll(ly4);
		l3.addAll(l4);
		l4.clear();
		ly4.clear();

		/*for(int i = 0; i< l1.size(); i++) {
			System.out.println(Arrays.toString(l1.get(i)));
			System.out.println(Arrays.toString(l2.get(i)));
			System.out.println(Arrays.toString(l3.get(i)));
		}*/



		//System.out.println(l1.size()+" " + ""+l2.size()+" "+ l3.size());

		int max1 =Math.abs(l1.get(0) - l1.get(l1.size()-1));
		int max2 =Math.abs(l2.get(0) - l2.get(l2.size()-1));
		int max3 =Math.abs(l3.get(0) - l3.get(l3.size()-1));

		System.out.println(max1 + " " + max2 + " " + max3);
		if(max1<max2){
			l4.clear();
			l4.addAll(l1);
			l1.clear();
			l1.addAll(l2);
			l2.clear();
			l2.addAll(l4);
			l4.clear();

			ly4.clear();
			ly4.addAll(ly1);
			ly1.clear();
			ly1.addAll(ly2);
			ly2.clear();
			ly2.addAll(ly4);
			ly4.clear();
			if(max1<max3){
				l4.clear();
				l4.addAll(l2);
				l2.clear();
				l2.addAll(l3);
				l3.clear();
				l3.addAll(l4);
				l4.clear();

				ly4.clear();
				ly4.addAll(ly2);
				ly2.clear();
				ly2.addAll(ly3);
				ly3.clear();
				ly3.addAll(ly4);
				ly4.clear();
				if(max2<max3){
					l4.clear();
					l4.addAll(l1);
					l1.clear();
					l1.addAll(l2);
					l2.clear();
					l2.addAll(l4);
					l4.clear();

					ly4.clear();
					ly4.addAll(ly1);
					ly1.clear();
					ly1.addAll(ly2);
					ly2.clear();
					ly2.addAll(ly4);
					ly4.clear();

				}
			}
		}else{
			l4.clear();
			l4.addAll(l2);
			l2.clear();
			l2.addAll(l3);
			l3.clear();
			l3.addAll(l4);
			l4.clear();

			ly4.clear();
			ly4.addAll(ly2);
			ly2.clear();
			ly2.addAll(ly3);
			ly3.clear();
			ly3.addAll(ly4);
			ly4.clear();
			if(max1< max3){
				l4.clear();
				l4.addAll(l1);
				l1.clear();
				l1.addAll(l2);
				l2.clear();
				l2.addAll(l4);
				l4.clear();

				ly4.clear();
				ly4.addAll(ly1);
				ly1.clear();
				ly1.addAll(ly2);
				ly2.clear();
				ly2.addAll(ly4);
				ly4.clear();

				if(max1<max2){
					l4.clear();
					l4.addAll(l2);
					l2.clear();
					l2.addAll(l3);
					l3.clear();
					l3.addAll(l4);
					l4.clear();

					ly4.clear();
					ly4.addAll(ly2);
					ly2.clear();
					ly2.addAll(ly3);
					ly3.clear();
					ly3.addAll(ly4);
					ly4.clear();
				}
			}

		}
		max1 =Math.abs(l1.get(0) - l1.get(l1.size()-1));
		max2 =Math.abs(l2.get(0) - l2.get(l2.size()-1));
		max3 =Math.abs(l3.get(0) - l3.get(l3.size()-1));
		System.out.println(max1 + " " + max2 + " " + max3);
		try{
		for (int i = 0; i <l1.size(); i++) {


			int koordiantex1, koordiantex2,  koordiantex3;
			koordiantex1 = l1.get(i);
			for (int j = 0; j <l2.size(); j++) {

				koordiantex2 = l2.get(j);

				for (int k = 0; k <l3.size(); k++) {

					koordiantex3 = l3.get(k);
					try {
						if (koordiantex1 == koordiantex2) {
							Bressenham2(koordiantex1, ly1.get(i), koordiantex2,ly2.get(j));
						} else if (koordiantex1 == koordiantex3) {
							Bressenham2(koordiantex1, ly1.get(i), koordiantex3, ly3.get(k));
						} else if (koordiantex3 == koordiantex1) {
							Bressenham2(koordiantex3, ly3.get(k), koordiantex1, ly1.get(i));
						}
					}catch (IndexOutOfBoundsException e){

						//System.out.println(koordiantex3[0]+" " + koordiantex3[1]+" " + koordiantex1[0]+" " + koordiantex1[1]);

					}
				}
			}
			/*int[] koordiantey, koordiantey2, koordiantey3 = new int[2];
			koordiantey = l1.get(i);
			for (int j = 0; j <l2.size(); j++) {

				koordiantey2 = l2.get(j);

				for (int k = 0; k <l3.size(); k++) {

					koordiantey3 = l3.get(k);
					try {
						if (koordiantey[1] == koordiantey2[1]) {
							Bressenham(koordiantey[0], koordiantey[1], koordiantey2[0], koordiantey2[1]);
						} else if (koordiantey[1] == koordiantey3[1]) {
							Bressenham(koordiantey[0], koordiantey[1], koordiantey3[0], koordiantey3[1]);
						} else if (koordiantex3[0] == koordiantex1[0]) {
							Bressenham(koordiantex3[0], koordiantex3[1], koordiantex1[0], koordiantex1[1]);
						}
					}catch (IndexOutOfBoundsException e){

						System.out.println(koordiantey3[0]+" " + koordiantey3[1]+" " + koordiantey[0]+" " + koordiantey[1]);
						break;
					}
				}
			}*/


		}}catch (OutOfMemoryError e){

		}



	}

	void Bressenham(int x1, int y1, int x2, int y2) {
		//System.out.println(x1+" "+y1+" "+x2+" "+y2);
		boolean vymena = Math.abs(y2 - y1) > Math.abs(x2 - x1);
		if(vymena)
		{
			int pomoc = x1;x1=y1; y1 = pomoc;
			pomoc = x2;x2=y2; y2 = pomoc;
		}
		if(x1>x2)
		{
			int pomoc = x2;
			x2 = x1;
			x1 = pomoc;
			pomoc = y2;
			y2 = y1;
			y1 = pomoc;
		}
		int dX = x2 - x1;
		int dY = Math.abs(y2 - y1);
		int y = y1;
		int k1 = 2 * dY;
		int k2 = 2 * dY - 2 * dX;
		int d = 2 * dY - dX;
		int krok = y2 >= y1 ? 1 : -1;
		for (int i = x1; i <= x2; i++) {
			if(vymena) {
				//bod(y, i);
				l4.add(y);
				ly4.add(i);
			}
			else
			{
				//bod(i, y);
				l4.add(i);
				ly4.add(y);
			}
			if (d < 0) {
				d = d + k1;
			} else {
				d = d + k2;
				y = y + krok;
			}
		}
	}
	void Bressenham2(int x1, int y1, int x2, int y2) {
		//System.out.println(x1+" "+y1+" "+x2+" "+y2);
		boolean vymena = Math.abs(y2 - y1) > Math.abs(x2 - x1);
		if(vymena)
		{
			int pomoc = x1;x1=y1; y1 = pomoc;
			pomoc = x2;x2=y2; y2 = pomoc;
		}
		if(x1>x2)
		{
			int pomoc = x2;
			x2 = x1;
			x1 = pomoc;
			pomoc = y2;
			y2 = y1;
			y1 = pomoc;
		}
		int dX = x2 - x1;
		int dY = Math.abs(y2 - y1);
		int y = y1;
		int k1 = 2 * dY;
		int k2 = 2 * dY - 2 * dX;
		int d = 2 * dY - dX;
		int krok = y2 >= y1 ? 1 : -1;
		for (int i = x1; i <= x2; i++) {
			if(vymena) {
				bod(y, i);

			}
			else
			{
				bod(i, y);

			}
			if (d < 0) {
				d = d + k1;
			} else {
				d = d + k2;
				y = y + krok;
			}
		}
	}


	void vykresliGL() {
		// Clear the screen and depth buffer
		glLoadIdentity();

//		//vonkajsia lomena ciara
//		glBegin(GL_LINE_LOOP);
//		glColor3f(1, 1, 0);
//		lomenaCiaraKruh(0.75, 0.25);
//		glEnd();
//
//		//vnutorna lomena ciara
//		glBegin(GL_LINE_LOOP);
//		glColor3f(1, 0, 0);
//		lomenaCiaraKruh(0.2, 0.18);
//		glEnd();
//
//		takeScreenShot();
//
//		glBegin(GL_POINTS);
//		glColor3f(1, 0, 0);
//		vypln(sx, sy);
//		glEnd();
//
//		glBegin(GL_POINTS);
//		glColor3f(1, 1, 0);
//		vyplnRiadok(sx+(int)(0.4*rozmer), sy);
//		glColor3f(1, 0, 1);
//		vyplnRiadok(sirka-1, sy);
//		glEnd();
		for(int i = 0; i <20; i++) {


		Random random = new Random();
		int x1 = random.nextInt(sirka);
		int x2 = random.nextInt(sirka);
		int x3 = random.nextInt(sirka);

		int y1 = random.nextInt(vyska);
		int y2 = random.nextInt(vyska);
		int y3 = random.nextInt(vyska);
		trojuholniki(x1,y1, x2,y2, x3,y3);

		int tmp_x =(x1+x2)/2;
		int tmp_y =(y1+y2)/2;

		//vyplnRiadok((tmp_x +x3)/2, (tmp_y + y3) /2);



		}


		glEnd();
		//System.out.println("fse");



	}

	long window;
	GLFWErrorCallback errorCallback;
	GLFWKeyCallback   keyCallback;

	void spusti() {
		try {
			init();
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

		window = glfwCreateWindow(sirka, vyska, "UGR1", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Chyba pri vytvoreni GLFW okna!!!");

		glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key,
					int scancode, int action, int mods) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, true);
			}
		});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - sirka) / 2, (vidmode.height() - vyska) / 2);

		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);

		GL.createCapabilities();
		glReadBuffer(GL_BACK); //select a color buffer source for pixels
	}

	private void takeScreenShot() {
		glReadPixels(0, 0, sirka, vyska, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	}

	private void riadok(int y, int x1, int x2) {
        if (x1 > x2) {
         int pom=x1; x1=x2; x2=pom;
        }
		for (int x=x1; x <=x2; x++) {
			bod(x, y);
		}
	}

	private void bod(int x, int y) {
		glVertex2i(x, y);
		y = vyska-1-y;
		buffer.asIntBuffer().put(y*sirka+x, rgba_WHITE);
	}

	private int zistiFarbu(int x, int y) {
		y = vyska-1-y;
		return buffer.asIntBuffer().get(sirka*y+x);
	}

	private void lomenaCiaraKruh(double stred, double odchylka) {
		double r, znam = -1;
		for (double uhol=0; uhol < 1.999*Math.PI; uhol += 20*Math.PI/180) {
			r = stred*rozmer+znam*odchylka*Math.random()*rozmer;
			glVertex2d(sx+r*Math.cos(uhol), sy+r*Math.sin(uhol));
			znam = -znam;
		}
	}

	void loop() {
		glViewport(0,0,sirka,vyska);

		glMatrixMode( GL_PROJECTION );
		glLoadIdentity();
		glOrtho( -0.5, sirka-0.5, vyska-0.5, -0.5, 0, 1);

		glMatrixMode( GL_MODELVIEW );
		glLoadIdentity();

		glClearColor( 0.f, 0.f, 0.f, 1.f ); //Initialize clear color

		vykresliGL();

		glfwSwapBuffers(window);

		while ( !glfwWindowShouldClose(window) ) {
			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new cv03().spusti();
	}
}
