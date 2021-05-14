package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
/*
Создаём  класс JImageDisplay, производный от
javax.swing.JComponent. Класс должен иметь одно поле с типом доступа
private, экземпляп java.awt.image.BufferedImage. Класс BufferedImage управляет
изображением, содержимое которого можно записать
 */
public class JImageDisplay extends javax.swing.JComponent {
    private BufferedImage bufferedImage;
/*
Конструктор JImageDisplay должен принимать целочисленные
значения ширины и высоты, и инициализировать объект BufferedImage новым
изображением с этой шириной и высотой, и типом изображения
TYPE_INT_RGB.
 */
    JImageDisplay(int width, int height){
        bufferedImage = new BufferedImage(width,height,bufferedImage.TYPE_INT_RGB);
        this.setPreferredSize(new Dimension(width,height));
    }
    /*
    После вызова версии суперкласса, мы можете
    нарисовать изображение в компоненте, используя следующую операцию:
    g.drawImage (image, 0, 0, image.getWidth(), image.getHeight(), null);
    (Мы передаем значение null для ImageObserver, поскольку данная
    функциональность не требуется.)
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
    }

    public void clearImage(){
        for(int i = 0; i < bufferedImage.getHeight(); i++){
            for(int j = 0; j < bufferedImage.getWidth(); j++){
                bufferedImage.setRGB(i,j,0);
            }
        }
    }
    public void drawPixel(int x, int y, int rgbColor){
        bufferedImage.setRGB(x,y,rgbColor);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
