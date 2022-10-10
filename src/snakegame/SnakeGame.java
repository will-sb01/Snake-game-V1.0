package snakegame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class SnakeGame extends Canvas implements Runnable, KeyListener{
    
    public Node[] nodeSnake = new Node[10];
    public boolean left, right, up, down;
    
    public int score = 0;
    public int macaX = 0;
    public int macaY = 0;
    
    public static void main(String[] args) {
        SnakeGame jogo = new SnakeGame();
        JFrame jf = new JFrame();
        jf.add(jogo);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setLocationRelativeTo(null);
        
        jf.setVisible(true);
        new Thread(jogo).start();
    }

    public SnakeGame(){
        this.setPreferredSize(new Dimension(480,480));
        for(int i =0; i< nodeSnake.length; i++){
            nodeSnake[i] = new Node(0,0);
        }
        this.addKeyListener(this);
    }
    
    public void tick(){
        
        for(int i = nodeSnake.length- 1; i>0; i--){
            nodeSnake[i].x = nodeSnake[i-1].x;
            nodeSnake[i].y = nodeSnake[i-1].y;
        }
        
        if(right){
            nodeSnake[0].x++;
        }else if(up){
            nodeSnake[0].y--;
        }else if(down){
            nodeSnake[0].y++;
        }else if (left){
            nodeSnake[0].x--;
        }
        
        if(new Rectangle(nodeSnake[0].x, nodeSnake[0].y,10,10).intersects(new Rectangle(macaX, macaY,10,10))){
            macaX = new Random().nextInt(480-10);
            macaY = new Random().nextInt(480-10);
            score++;
            System.out.println("Pontos: "+ score);
    }
    }
    
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 480,480);
        for(int i = 0; i < nodeSnake.length; i++){
            g.setColor(Color.BLUE);
            g.fillRect(nodeSnake[i].x, nodeSnake[i].y, 10, 10);
        }
        g.setColor(Color.RED);
        g.fillRect(macaX, macaY, 10, 10);
        
        g.dispose();
        bs.show();
    }
    
    @Override
    public void run() {
        while(true){
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            right = true;
            left = false;
            up = false;
            down = false;
        }else if(e.getKeyCode()== KeyEvent.VK_LEFT){
            left = true;
            up = false;
            right = false;
            down = false;
        }else if(e.getKeyCode()== KeyEvent.VK_UP){
            up = true;
            left = false;
            right = false;
            down = false;
        }else if(e.getKeyCode()== KeyEvent.VK_DOWN){
            down = true;
            up = false;
            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
