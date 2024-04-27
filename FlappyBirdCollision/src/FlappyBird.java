import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    int playerPoint = 0;
    JLabel pointLabel = new JLabel(""+playerPoint);
    Player player;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;

    boolean gameover = false;
    int currentPipe = 0;

    public FlappyBird(){
        setPreferredSize(new Dimension(360, 640));
        setFocusable(true);
        addKeyListener(this);
        add(pointLabel);

        // set image
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bintang.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();

        // set object
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage, playerPoint);
        pipes = new ArrayList<Pipe>();

        // set timer
        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        // menggambar image pada frame
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void move(){
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);

            // handle collision
            if(((player.getPosX() >= pipe.getPosX() && player.getPosX() <= pipe.getPosX() + pipe.getWidth())
            || (player.getPosX() + player.getWidth() >= pipe.getPosX() && player.getPosX() + player.getWidth() <= pipe.getPosX() + pipe.getWidth()))
            && ((player.getPosY() >= pipe.getPosY() && player.getPosY() <= pipe.getPosY() + pipe.getHeight())
            || (player.getPosY() + player.getHeight() >= pipe.getPosY() && player.getPosY() + player.getHeight() <= pipe.getPosY() + pipe.getHeight()))){
                player.setVelocityY(0);
                player.setPosX(player.getPosX() + pipe.getVelocityX());

                // buat state menjadi gameover
                gameover = true;
                gameLoop.stop();;
            }

            // +1 poin ketika melewati bagian tengah pipa
            if(player.getPosX() + player.getWidth()/2 >= pipe.getPosX() && i == currentPipe){
                player.setPoint(player.getPoint()+1);
                pointLabel.setText("" + player.getPoint());
                currentPipe += 2;
            }

            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());
        }

        // handle player melewati batas bawah
        if(player.getPosY() > frameHeight){
            // buat state menjadi gameover
            gameover = true;
            gameLoop.stop();;
        }
    }

    public void placePipes(){
        // menentukan posisi pipa
        int randomPipePosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;

        // menambahkan pipa atas
        Pipe upperPipe = new Pipe(pipeStartPosX, randomPipePosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        // menambahkan pipa bawah
        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPipePosY + pipeHeight + openingSpace, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        // gerak keatas jika space bar ditekan
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            player.setVelocityY(-10);
        }

        // jika menekan 'r' saat gameover
        if(e.getKeyCode() == KeyEvent.VK_R && gameover){
            // reset game
            player.setPosX(playerStartPosX);
            player.setPosY(playerStartPosY);
            player.setPoint(0);
            player.setVelocityY(0);

            pipes.clear();

            gameover = false;
            currentPipe = 0;
            pointLabel.setText("" + player.getPoint());

            pipesCooldown.start();
            gameLoop.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }
}
