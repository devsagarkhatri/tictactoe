/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Sagar
 */
public final class TicTac extends JPanel implements KeyListener{

    /**
     * @param args the command line arguments
     */
        
    boolean Circle=true;
    boolean won=false;
    boolean enemyWon=false;
    boolean tie=false;
    
    int space=212;
    int border=82;
    int errors;
    int firstSpot=-1,secondSpot=-1;
    int port;
    int blueX=0,blueY=0;
    int redX=0,redY=0;
    int wins[][]={
        {0,1,2},
        {3,4,5},
        {6,7,8},
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,6}
    };
    
    Font font=new Font("Verdana",Font.BOLD,32);
    Font smallFont=new Font("Verdana",Font.BOLD,20);
    Font largeFont= new Font("Verdana",Font.BOLD,60);

    String CirclePlayer="Player 1";
    String CrossPlayer= "Player 2";
    String WonString="You Won";    
    String tieString="It's a tie";
    String OpponentWonString="Opponent Won";
    
    String board[][]={
                      {"","",""},
                      {"","",""},
                      {"","",""}
                    };
    String blankSpace[]=new String[9];  
    BufferedImage back,cross,zero,redbar,bluebar;
    Graphics g;
    
    
    public TicTac()
    {  
       this.setName("TicTacToe");
       this.setSize(760,760);
       this.setVisible(true);
       this.addKeyListener(this);
       this.setFocusable(true);
       loadImages();
       
    }
    
    private void loadImages()
    {
        try
        {
            back    =   ImageIO.read(new File("src/tictac/board.png"));
            cross   =   ImageIO.read(new File("src/tictac/redX.png"));
            zero    =   ImageIO.read(new File("src/tictac/blueO.png"));
            bluebar =   ImageIO.read(new File("src/tictac/bluebar.png"));
            redbar  =   ImageIO.read(new File("src/tictac/redbar.png"));
            System.out.println("Images loaded");            
        }
        catch(Exception e)
        {
            System.out.println("Images could not be loaded");
        }
    }
        
    boolean checkXWin()
    {
        for(int i=0;i<8;i++)
        {
           if(blankSpace[wins[i][0]].equals("X")  &&  blankSpace[wins[i][1]].equals("X") && blankSpace[wins[i][2]].equals("X"))
           {
               firstSpot=wins[i][0];
               secondSpot=wins[i][2];
               
               won=true;
               System.out.println("match at "+wins[i][0]+" and "+wins[i][1]+" and "+wins[i][2]);        
               return true;
           }
        }
        return false;
    }
    
    boolean checkYWin()
    { 
        for(int i=0;i<8;i++)
        {
           if(blankSpace[wins[i][0]].equals("O")  &&  blankSpace[wins[i][1]].equals("O") && blankSpace[wins[i][2]].equals("O"))
           {
               firstSpot=wins[i][0];
               secondSpot=wins[i][2];
               
               won=true;
               System.out.println("match at "+wins[i][0]+" and "+wins[i][1]+" and "+wins[i][2]);        
               return true;
           }
        }
        return false;
    }
    
    boolean checkTie()
    {
       
        for(int i=0;i<9;i++)
        {   
            if(blankSpace[i]=="")
                return false;
        }
        return true;
    }
    
     /**
     *
     * @param g
     */
    
    
    @Override
    public void paintComponent(Graphics g)
    {    
        
        super.paintComponent(g);
        for(int i=0,k=0;i<3;i++)
        {
            for(int j=0;j<3;j++,k++)
            {
                blankSpace[k]=board[j][i];
            }
        }        
        
        if(true)
        {
            try
            {
                g.drawImage(back, 0, 0, this);  
                  
                for(int i=0;i<3;i++)
                {
                    for(int j=0;j<3;j++)
                    {
                        if(board[i][j].equals("X"))
                        {   
                            g.drawImage(cross,border+i*space ,border+j*space , this);
                        }
                        else if(board[i][j]=="O")
                        {
                            g.drawImage(zero, border+i*space, border+j*space, this);
                        }
                    }
                }
                g.setFont(font);
                g.drawString(CrossPlayer +" V/s "+CirclePlayer, 190, 70);
                g.setFont(largeFont);
                
                if(checkXWin())
                {
                   g.setFont(largeFont); 
                   g.setColor(Color.green);
                   Graphics2D g2 = (Graphics2D) g;
                   g2.setStroke(new BasicStroke(6));
                   g2.drawLine((firstSpot%3)*space+2*border, (firstSpot/3)*space+2*border,(secondSpot%3)*space+2*border,(secondSpot/3)*space+2*border);
                   g.setColor(Color.black);
                   //g.drawString(WonString,this.getWidth()/2-WonString.length()*10,this.getWidth()/2); 
                }                
                else if(checkYWin())
                {
                   g.setFont(largeFont); 
                   g.setColor(Color.green);
                   Graphics2D g2 = (Graphics2D) g;
                   g2.setStroke(new BasicStroke(6));
                   g2.drawLine((firstSpot%3)*space+2*border, (firstSpot/3)*space+2*border,(secondSpot%3)*space+2*border,(secondSpot/3)*space+2*border);
                   g.setColor(Color.black);
                   //g.drawString(OpponentWonString,this.getWidth()/2-OpponentWonString.length()*10,this.getWidth()/2); 
                }
                else if(checkTie())
                {
                   g.setFont(largeFont); 
                   g.setColor(Color.BLACK);
                   
                   g.drawString(tieString,this.getWidth()/2-tieString.length()*10,this.getWidth()/2); 
                }
                
                if(!won)
                {
                    if(Circle){
                        g.drawImage(bluebar, border+blueX*space,(blueY+1)*space+border-bluebar.getHeight()-8, this);  
                        g.setColor(Color.blue);                    
                        g.drawString(CirclePlayer +"'s Turn", 150,740);
                    }
                    else{
                        g.drawImage( redbar, border+redX*space,(redY+1)*space+border-redbar.getHeight()-8, this);           
                        g.setColor(Color.red);
                        g.drawString(CrossPlayer +"'s Turn", 150,740);
                    }            
                }
            }
            catch(NullPointerException e){}
        }       
        repaint();  
    }  
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(Circle)
        {
            int a=e.getKeyCode();
            switch(a)
            {   
                case KeyEvent.VK_A:     if(blueX>0){
                                            blueX--;
                                            }
                                            break;
                case KeyEvent.VK_D:     if(blueX<2){
                                            blueX++;
                                            }
                                            break;
                case KeyEvent.VK_W:     if(blueY>0){
                                            blueY--;
                                            }
                                            break;
                case KeyEvent.VK_S:     if(blueY<2){
                                            blueY++;
                                            }
                                            break;
                case KeyEvent.VK_SPACE: if(board[blueX][blueY].equals(""))
                                        {
                                            board[blueX][blueY]="O";
                                            Circle=false;
                                        }
                                        break;
                case KeyEvent.VK_ESCAPE:board[redX][redY]="";                                            
            }
            redX=0;
            redY=0;
        }
        else
        {
            int a=e.getKeyCode();
            switch(a)
            {
                case KeyEvent.VK_LEFT:if(redX>0){
                                            redX--;
                                            }
                                            break;
                case KeyEvent.VK_RIGHT:if(redX<2){
                                            redX++;
                                            }
                                            break;
                case KeyEvent.VK_UP:    if(redY>0){
                                            redY--;
                                            }
                                            break;
                case KeyEvent.VK_DOWN:  if(redY<2){
                                            redY++;
                                            }
                                            break;
                case KeyEvent.VK_ENTER: if(board[redX][redY].equals(""))
                                        {
                                            board[redX][redY]="X";
                                            Circle=true;
                                        }
                                        break;
                case KeyEvent.VK_ESCAPE:board[redX][redY]="";
            }
            blueX=0;
            blueY=0;           
        }
        repaint();

    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    
    public static void framing()
    {
        JFrame frame=new JFrame("TicTacToe");
        
        TicTac tic=new TicTac();
        frame.add(tic);
        frame.setSize(775,780);
        frame.setResizable(false);
        frame.setVisible(true); 
        frame.setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        framing();
        
    }  
}
