package com.company;

import java.awt.geom.Rectangle2D;
/*
   Следующая ваша задача: написать код для вычисления фрактала
Мандельброта.
   Для создания фракталов используйте следующий исходный файл
FractalGenerator.java, от которого будут унаследованы все наши фрактальныегенераторы. Как мы могли заметить данный файл предоставляет также
некоторые полезные операции для перевода из экранных координат в систему
координат вычисляемого фрактала.
 */

public class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;
    /*
    Создаём подкласс FractalGenerator с именем Mandelbrot. в нем нам
    необходимо будет обеспечить только два метода: getInitialRange() и
    numIterations().

     */
    public String toString() {
        return "Mandelbrot";
    }
/*
 getInitialRange (Rectangle2D.Double) - метод позволяет генератору
фракталов определить наиболее «интересную» область комплексной плоскости
для конкретного фрактала. Обратите внимание на то, что методу в качестве
аргумента передается прямоугольный объект, и метод должен изменить поля
прямоугольника для отображения правильного начального диапазона для
фрактала. (Пример можно увидеть в методе
FractalGenerator.recenterAndZoomRange().) В классе Mandelbrot этот метод
должен установить начальный диапазон в (-2 - 1.5i) - (1 + 1.5i). Т.е. значения x
и y будут равны -2 и -1.5 соответственно, а ширина и высота будут равны 3.
 */
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;
    }
/*
Метод numIterations(double, double) реализует итеративную
функцию для фрактала Мандельброта. Константу с максимальным количеством
итераций можно определить
 */
    @Override
    public int numIterations(double x, double y) {
        int iteraion = 0;
        double x1 = 0;
        double y1 = 0;
        while (x1*x1 + y1*y1 < 4 && iteraion < MAX_ITERATIONS) {
            double temp1 = x1 * x1 - y1 * y1 + x;
            double temp2 = 2 * x1 * y1 + y;
            x1 = temp1;
            y1 = temp2;
            iteraion++;
        }
        if (iteraion >= MAX_ITERATIONS)return -1;
        return iteraion;
    }
}
