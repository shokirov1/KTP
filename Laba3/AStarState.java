package com.company;
import java.util.HashMap;
/**
 * Этот класс хранит базовое состояние, необходимое алгоритму A * для вычисления
 * путь по карте. Это состояние включает в себя набор «открытых путевых точек» и
 * еще один сборник «закрытых путевых точек». Кроме того, этот класс предоставляет
 * основные операции, необходимые алгоритму поиска пути A * для его выполнения
 * обработка.
 **/
public class AStarState
{
    /** Это ссылка на карту, по которой алгоритм A * перемещается.. **/
    private Map2D map2d;
    private HashMap<Location, Waypoint>openWaypoints;
    private HashMap<Location, Waypoint>closeWaypoints;

    /**
     * Инициализировать новый объект состояния для использования алгоритма поиска пути A *.
     **/
    public AStarState(Map2D map2d)
    {
        if (map2d == null)
            throw new NullPointerException("карта не может быть нулевой");

        this.map2d = map2d;
        this.openWaypoints = new HashMap<Location, Waypoint>();
        this.closeWaypoints = new HashMap<Location, Waypoint>();
    }

    /** Возвращает карту, по которой перемещается поисковик A *.. **/
    public Map2D getMap()
    {
        return map2d;
    }



    /** Возвращает текущее количество открытых путевых точек.. **/

    //------------------1
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }
    /**
     * Этот метод просматривает все открытые путевые точки и возвращает путевую точку.
     * с минимальной общей стоимостью. Если нет открытых путевых точек, этот метод
     * возвращает <code> null </code>.
     **/

    //---------------2
    public Waypoint getMinOpenWaypoint()
    {
        Waypoint minimumCostPoint = null;
        if (!openWaypoints.isEmpty()){
            double miniCost = Double.MAX_VALUE;
            for(Waypoint cWaypoint: openWaypoints.values())
                if (cWaypoint.getTotalCost()< miniCost){
                    miniCost = cWaypoint.getTotalCost();
                    minimumCostPoint = cWaypoint;
                }
        }
        return minimumCostPoint;
    }

    /**
     * Этот метод добавляет путевую точку (или потенциально обновляет уже путевую точку)
     * в) коллекция "открытые путевые точки". Если еще нет открытого
     * путевая точка в местоположении новой путевой точки, тогда новая путевая точка просто
     * добавлено в коллекцию. Однако, если в
     * местоположение новой путевой точки, новая путевая точка заменяет только старую <em>
     * если </em> значение "предыдущей стоимости" новой путевой точки меньше текущей
     * значение "предыдущей стоимости" путевой точки.
     **/

    //----------------3
    public boolean addOpenWaypoint(Waypoint currentWP)
    {
        if(openWaypoints.containsKey(currentWP.getLocation())){
            if(currentWP.getPreviousCost() <
                    openWaypoints.get(currentWP.getLocation()).getPreviousCost()){
                openWaypoints.put(currentWP.getLocation(),
                        openWaypoints.get(currentWP.getLocation()
                        )
                );
                return true;
            }
            return false;
        }
        else{
            openWaypoints.put(currentWP.getLocation(), currentWP);
            return true;
        }
    }

    /**
     * Возвращает истину, если коллекция закрытых путевых точек содержит путевую точку.
     * для указанного места.
     **/

    //----------------------4
    public boolean isLocationClosed(Location loc)
    {
        return closeWaypoints.keySet().contains(loc);
    }

    /**
     * Этот метод перемещает путевую точку в указанном месте из
     * открытый список к закрытому списку.
     **/

    //----------------5
    public void closeWaypoint(Location location)
    {
        closeWaypoints.put(location, openWaypoints.get(location));
        openWaypoints.remove(location);
    }

}

