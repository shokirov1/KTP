package com.company;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * Этот класс является настраиваемым компонентом Swing для представления одной ячейки карты в
 * 2D-карта. Ячейка имеет несколько различных состояний, но самое основное.
 * состояние: проходима клетка или нет.
 */
public class JMapCell extends JComponent
{
    private static final Dimension CELL_SIZE = new Dimension(12, 12);

    /** Истина указывает, что ячейка является конечной точкой, либо начала, либо конца.. **/
    boolean endpoint = false;


    /** Истина указывает, что ячейка проходима; ложь означает, что это не так. **/
    boolean passable = true;

    /**
     * Истина указывает, что эта ячейка является частью пути между началом и концом..
     **/
    boolean path = false;

    /**
     * Построить новую ячейку карты с указанной «проходимостью». Ввод
     * true означает, что ячейка проходима.
     **/
    public JMapCell(boolean pass)
    {
        // Устанавливаем предпочтительный размер ячейки, чтобы задать начальный размер окна.
        setPreferredSize(CELL_SIZE);

        setPassable(pass);
    }

    /** Создайте новую ячейку карты, которая по умолчанию является проходимой.. **/
    public JMapCell()
    {
        // Вызов другого конструктора, указав истину для "проходимо".
        this(true);
    }

    /** Помечает эту ячейку как начальную или конечную.. **/
    public void setEndpoint(boolean end)
    {
        endpoint = end;
        updateAppearance();
    }

    /**
     * Устанавливает эту ячейку как проходимую или непроходимую. Ввод истинных оценок
     * клетка как проходимая; ввод false помечает его как непроходимый.
     **/
    public void setPassable(boolean pass)
    {
        passable = pass;
        updateAppearance();
    }

    /** Возвращает true, если эта ячейка проходима, или false в противном случае.. **/
    public boolean isPassable()
    {
        return passable;
    }

    /** Переключает текущее "проходимое" состояние ячейки карты.. **/
    public void togglePassable()
    {
        setPassable(!isPassable());
    }

    /** Помечает эту ячейку как часть пути, обнаруженного алгоритмом A *. **/
    public void setPath(boolean path)
    {
        this.path = path;
        updateAppearance();
    }

    /**
     * Этот вспомогательный метод обновляет цвет фона в соответствии с текущим
     * внутреннее состояние клетки.
     **/
    private void updateAppearance()
    {
        if (passable)
        {
            // Проходимая ячейка. Обозначьте его состояние рамкой.
            setBackground(Color.WHITE);

            if (endpoint)
                setBackground(Color.CYAN);
            else if (path)
                setBackground(Color.GREEN);
        }
        else
        {
            // Непроходимая клетка. Сделайте все красным.
            setBackground(Color.RED);
        }
    }

    /**
     * Реализация метода рисования для рисования цвета фона в
     * ячейка карты.
     **/
    protected void paintComponent(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
