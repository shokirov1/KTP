package com.company;
/**
 * Этот класс представляет собой конкретное место на 2D-карте. Координаты
 * целочисленные значения.
 **/
public class Location
{
    /** Координата X этого места. **/
    public int xCoord;

    /** Координата Y этого места. **/
    public int yCoord;


    /** Создает новое местоположение с указанными целыми координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создает новое местоположение с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
    public boolean equals(Object obj){
        if(obj instanceof Location)
            return ((Location)obj).xCoord == this.xCoord &&
                    ((Location)obj).yCoord == this.yCoord;
        else
            return false;
    }

    public int hashCode(){
        return 123 * (123*6 + this.xCoord) + this.yCoord;
    }
}
