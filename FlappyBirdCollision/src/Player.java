import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityY;
    private int velocityX;
    private int point;

    public Player(int posX, int posY, int width, int height, Image image, int point){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.point = 0;

        this.velocityY = -0;
        this.velocityX = 0;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
