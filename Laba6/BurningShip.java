package com.company;

import java.awt.geom.Rectangle2D;
/*
2) Второй фрактал, который необходимо реализовать - это фрактал «Burning Ship»
который в реальности не похож на пылающий корабль. Данный фрактал
имеет следующие свойства:
 Уравнение имеет вид zn = (|Re(zn-1)| + i |Im(zn-1)|)2
+ c. Другими
словами, вы берете абсолютное значение каждого компонента zn-1 на каждой
итерации.
 Начальный диапазон для данного фрактала должен быть от (-2, -2.5)
до (2, 1.5).

 */

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    /*
    Сombo-boxe в Swing может управлять коллекцией объектов, но
    объекты должны предоставлять метод toString().  что в каждой
    реализации фракталов tcnm метод toString(), который возвращает имя,
    например  «Burning Ship».
     */
    public String toString() {
        return "BurningShip";
    }

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.height = 4;
        range.width = 4;
    }

    @Override
    public int numIterations(double x, double y) {
        int iteraion = 0;
        double x1 = 0;
        double y1 = 0;
        while (x1*x1 + y1*y1 < 4 && iteraion < MAX_ITERATIONS) {
            double temp1 = x1 * x1 - y1 * y1 + x;
            double temp2 = Math.abs(2 * x1 * y1) + y;
            x1 = temp1;
            y1 = temp2;
            iteraion++;
        }
        if (iteraion >= MAX_ITERATIONS)return -1;
        return iteraion;
    }
}
