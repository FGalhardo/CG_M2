/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projeto;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 *
 * @author Galhardo
 */
public class M2
        implements GLEventListener,
        KeyListener {

    GLU glu = new GLU();
    GLUT glut = new GLUT();

    public static void main(String args[]) {
        new M2();
    }
    private double g;
    private double incG=0.2;
    private double gt=0;
    private double g2;
    private double g3;
    private double g4;
    private double g5= 10;
    private double g6 =2.6;
    private boolean w;
    private boolean s;
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;
    private float pos[] = {0,0,0,1};
    double r = 0;
    float posZ = -10;
    float incZ = 0.1f;
    
    public M2() {
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);

        JFrame frame = new JFrame("Exemplo01");
        frame.setSize(500, 500);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        frame.addKeyListener(this);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        System.exit(0);
                    }
                }).start();
            }
        });

    }

    @Override
    public void init(GLAutoDrawable glAuto) {
        Animator a = new Animator(glAuto);
        a.start();
        GL2 gl = glAuto.getGL().getGL2();
        gl.glClearColor(0.4f, 0.4f, 0.4f, 0.4f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT1);
  

        float luzEspecular[] = {1,1,1,1};
        float luzDifusa[]  ={1,1f,1f,1.1f};
    
        
       gl.glLightfv(GL2.GL_LIGHT1, 
                     GL2.GL_DIFFUSE, 
                     luzDifusa,0); 
        
        gl.glLightfv(GL2.GL_LIGHT1, 
                     GL2.GL_SPECULAR,
                     luzEspecular,0); 
        
    }

    @Override
    public void display(GLAutoDrawable glAuto) {

        GL2 gl = glAuto.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT
                | GL.GL_DEPTH_BUFFER_BIT
        );

        gl.glLoadIdentity();
        
         pos[2]=posZ;
         posZ = incZ;
         
         incZ = -13;
        
         if(posZ > -20)
            incZ += 0.1f;
         if(posZ < -30)
              incZ -= 0.1f;
             
         
          gl.glLightfv(GL2.GL_LIGHT1,
                     GL2.GL_POSITION,
                     pos,
                     0);
        
       
        float materialEspecular[] ={1,1,1,1};
        float brilho = 40;

  
        
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_SPECULAR,
                        materialEspecular,
                        0);
        
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,
                        GL2.GL_SHININESS,
                        brilho
                        );
        
        gl.glTranslated(0, 0, -10);
        gl.glRotated(g4, 1, 0, 0);
        gl.glRotated(-360, 0,0 , 1);
        //gl.glRotated(90,0,1,0);
        gl.glRotated(180,1,0,0);

        g2 = g2 + 0.2;
        gl.glColor3d(1,0,0);
        gl.glPushMatrix();
        gl.glTranslated(-5,-0.5,0);
        gl.glTranslated(0, 0, g3);
        desenhaBraco(gl);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslated(2,5,0);
        desenhaEsteira(gl);
        gl.glPopMatrix();
        
       
        gl.glPushMatrix();
        if(g5 >= 5)
           g5 = g5 - 0.01f;
        gl.glTranslated(g5,g6,g3);
        
        desenhaTeapot(gl);
        gl.glPopMatrix();
        
        if(gt > - 8)
            gt= gt - 0.01;
        
        if(down && g <= 80)
            g = g + incG;
        else
            if(up && g >= 0)
                g = g - incG;
        
        if(left && g3 <= 5)
            g3 = g3+ 0.01;
        else
            if(right && g3 >= -5)
             g3 = g3 - 0.01;
        
        
        if(g >= 80 || g <= 0 ){
            
            if(up){
            g6 = g - incG;
            g5 = 7;
        }else{
                g5=7;
            }
                if(down){
            g6 = 2.6;
            }   
            
        }
        
        if(w && g4 <= 0)
            g4 = g4 + 1;
        else 
            if(s && g4 >= -90)
              g4 = g4 - 1;

    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {

        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, 1, 1, 300);
        gl.glViewport(0, 0, w, h);
        gl.glTranslated(0, 0, -7.5);
        if (w <= h) {
            gl.glOrtho(-1.5, 1.5,
                    -1.5 * (float) h / (float) w,//
                    1.5 * (float) h / (float) w,//
                    -10.0, 10.0
            );
        } else {
            gl.glOrtho(-1.5 * (float) w / (float) h, //
                    1.5 * (float) w / (float) h, //
                    -1.5, 1.5, -10.0, 10.0
            );
        }
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
               
    }

    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {

    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }
    
    private void desenhaEsteira(GL2 gl){
        gl.glPushMatrix();
        gl.glScaled(20, 3, 4);
        glut.glutSolidCube(1);
        gl.glPopMatrix();       
}
     private void desenhaTeapot(GL2 gl){
        gl.glPushMatrix();
        gl.glTranslated(gt, 0,0);
        gl.glScaled(1, 1, 1);
        gl.glRotated(180,0,0,1);
        glut.glutSolidTeapot(1);
        gl.glPopMatrix();       
}

    private void desenhaDedo(GL2 gl) {
        gl.glPushMatrix();
        //Dedo 1a
        gl.glTranslated(1, 0.5, 0);
        gl.glRotated(g, 0,0 , 1);
        gl.glTranslated(0.25, -0.125, 0);
        gl.glPushMatrix();
        gl.glScaled(0.5, 0.25, 0.25);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        //Dedo 1b
        gl.glTranslated(0.25, -0.125, 0);
        gl.glRotated(-g * 2, 0, 0, 1);
        gl.glTranslated(0.25, 0.125, 0);
        gl.glPushMatrix();
        gl.glScaled(0.5, 0.25, 0.25);
        glut.glutSolidCube(1);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    private void desenhaBraco(GL2 gl) {
        gl.glPushMatrix();
        //gl.glTranslated(0, 0, g3);
        gl.glPushMatrix();
        gl.glScaled(2, 1, 1);
        glut.glutSolidCube(1);
        gl.glPopMatrix();

        gl.glTranslated(1, 0.5, 0);
        gl.glRotated(g, 0, 0, 0.5);
        //gl.glRotated(g3,0.2,0,0);
        gl.glTranslated(1, -0.5, 0);

        gl.glPushMatrix();
        gl.glScaled(2, 1, 1);
        glut.glutSolidCube(1);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glRotated(180, 1, 0, 0);
        gl.glPushMatrix();
        desenhaDedo(gl);
        gl.glPushMatrix();
        gl.glTranslated(0, 0, 0.5 - 0.125);
        desenhaDedo(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0, 0, -0.5 + 0.125);
        desenhaDedo(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotated(180, 1, 0, 0);
        desenhaDedo(gl);
        gl.glPopMatrix();
        gl.glPopMatrix();
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
            
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            w = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            s = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }  else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }else if (e.getKeyCode() == KeyEvent.VK_W) {
            w = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            s = false;
        }
    }
    
}