package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
/*
Создаём класс FractalExplorer, который позволит нам исследовать
различные области фрактала, путем его создания, отображения через
графический интерфейс Swing и обработки событий, вызванных
взаимодействием приложения с пользователем.
Как видно их приведенных выше изображений пользовательского
интерфейса, FractalExplorer очень прост, он состоит из JFrame, который в свою
очередь содержит объект JImageDisplay, который отображает фрактал,и объект
JButton для сброса изображения, необходимый для отображения целого
фрактала. Данный макет можно создать, установив для фрейма BorderLayout,
затем поместив отображение в центр макета и кнопку сброса в "южной" части
макета
 */
public class FractalExplorer extends JFrame {
    private int size;
    private JImageDisplay imageDisplay;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(400);
        fractalExplorer.creatAndShowGUI();
        fractalExplorer.drawFractal();
    }


    FractalExplorer(int size){
        this.size = size;
        range = new Rectangle2D.Double();
        fractal = new Mandelbrot();
        fractal.getInitialRange(range);
        imageDisplay = new JImageDisplay(size,size);
    }

    private void creatAndShowGUI(){
        Mandelbrot mandelbrot = new Mandelbrot();
        Tricorn tricorn = new Tricorn();
        BurningShip burningShip = new BurningShip();

        JFrame frame = new JFrame("Fractal");
        JButton resetButton = new JButton("Reset");
        JButton saveButton = new JButton("Save Image");
        JLabel label = new JLabel("Fractal:");
        JComboBox comboBox = new JComboBox();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        comboBox.addItem(mandelbrot);
        comboBox.addItem(tricorn);
        comboBox.addItem(burningShip);

        resetButton.setActionCommand("reset");
        saveButton.setActionCommand("save");

        EventHandler eventHandler = new EventHandler();
        MouseHandler mouseHandler = new MouseHandler();

        imageDisplay.addMouseListener(mouseHandler);
        resetButton.addActionListener(eventHandler);
        saveButton.addActionListener(eventHandler);
        comboBox.addActionListener(eventHandler);

        panel1.add(label);
        panel1.add(comboBox);
        panel2.add(saveButton);
        panel2.add(resetButton);

        frame.add(imageDisplay,BorderLayout.CENTER);
        frame.add(panel1,BorderLayout.NORTH);
        frame.add(panel2,BorderLayout.SOUTH);
        frame.setSize(size,size + 100);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, size, i);
                double yCoord = FractalGenerator.getCoord(range.y,range.y + range.height, size, j);
                int iter = fractal.numIterations(xCoord,yCoord);
                if (iter == -1)imageDisplay.drawPixel(i,j,0);
                else {
                    float hue = 0.7f + (float)iter / 200f;
                    imageDisplay.drawPixel(i,j,Color.HSBtoRGB(hue,1f,1f));
                }
            }
        }
        imageDisplay.repaint();
    }
/*
  Следующая ваша задача - сохранение текущего изображения фрактала на
диск. Java API предоставляет несколько инструментов для реализации данной
задачи.
  Во-первых, нам нужно добавить кнопку «Save Image» в ваше окно.
Для этого мы можем  добавить обе кнопки «Save Image» и «Reset» в новую
Jpanel, а затем разместить эту панель в SOUTH части окна.
События от кнопки «Save Image» также должны обрабатываться
реализацией ActionListener. Назначьте кнопкам «Save Image» и «Reset» свои
значения команд (например, «save» и «reset») для того, чтобы обработчик
событий мог отличить события от этих двух разных кнопок.
  В обработчике кнопки «Save Image» нам необходимо реализовать
возможность указания пользователем, в какой файл он будет сохранять
изображение. Это можно сделать с помощью класса javax.swing.JFileChooser.
  Указанный класс предоставляет метод showSaveDialog(), который открывает
диалоговое окно «Save file», позволяя тем самым пользователю выбрать
директорию для сохранения.
 */
    private class EventHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object object = e.getSource();
            if(object instanceof JComboBox){
                fractal = (FractalGenerator) ((JComboBox) object).getSelectedItem();
                fractal.getInitialRange(range);
                drawFractal();
            }
            else if(object instanceof JButton) {
                JButton button = (JButton) object;
                if(button.getActionCommand().equals("reset")){
                    fractal.getInitialRange(range);
                    drawFractal();
                }
                else if (button.getActionCommand().equals("save")){
                    JFileChooser fileChooser = new JFileChooser();
                    FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                    fileChooser.setFileFilter(filter);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    if(fileChooser.showSaveDialog(button.getParent())
                            != JFileChooser.APPROVE_OPTION)return;
                    try {
                        ImageIO.write(imageDisplay.getBufferedImage(),"png",
                                fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(button.getParent(), ex.getMessage(),
                                "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private class MouseHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, size, x);
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, size, y);
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

}
