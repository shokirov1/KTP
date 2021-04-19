package com.company;
import java.util.HashMap;
import java.util.HashSet;


/**
 * Этот класс содержит реализацию алгоритма поиска пути A *. В
 * алгоритм реализован как статический метод, поскольку алгоритм поиска пути
 * действительно не нужно поддерживать какое-либо состояние между вызовами
 * алгоритм.
 */
public class AStarPathfinder{
    /**
     * Эта константа содержит максимальный предел отсечения для стоимости путей. Если
     * конкретная путевая точка превышает этот предел стоимости, путевая точка
     * исключено.
     **/
    public static final float COST_LIMIT = 1e6f;


    /**
     * Попытки вычислить путь, который проходит между началом и концом
     * локации указанной карты. Если путь может быть найден, путевая точка
     * возвращается <em> последний </em> шаг пути; эта путевая точка может быть
     * раньше ходил назад к исходной точке. Если путь не может быть найден,
     * Возвращается <code> null </code>.
     **/
    public static Waypoint computePath(Map2D map)
    {
        // Переменные, необходимые для поиска A *.
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Устанавливаем начальную точку для начала поиска A *.
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, estimateTravelCost(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;

        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // Находим "лучшую" (т.е. самую дешевую) путевую точку на данный момент.
            Waypoint best = state.getMinOpenWaypoint();

            // Если лучшее место - это место финиша, то все готово!
            if (best.getLocation().equals(finishLoc))
            {
                finalWaypoint = best;
                foundPath = true;
            }

            // Добавить / обновить всех соседей текущего лучшего местоположения. Это
            // эквивалентно выполнению всех "следующих шагов" из этого места.
            takeNextStep(best, state);

            // Наконец, переместите это место из "открытого" списка в "закрытый"
            // список.
            state.closeWaypoint(best.getLocation());
        }

        return finalWaypoint;
    }

    /**
     * Этот статический вспомогательный метод принимает путевую точку и генерирует все действительные "следующие".
     * шаги "от этой путевой точки. Новые путевые точки добавляются в" открытую "
     * waypoints »коллекция переданного объекта состояния A *.
     **/
    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();

        for (int y = loc.yCoord - 1; y <= loc.yCoord + 1; y++)
        {
            for (int x = loc.xCoord - 1; x <= loc.xCoord + 1; x++)
            {
                Location nextLoc = new Location(x, y);

                // Если «следующее местоположение» находится за пределами карты, пропустите его.
                if (!map.contains(nextLoc))
                    continue;

                // Если "следующее местоположение" - это это местоположение, пропустите его.
                if (nextLoc == loc)
                    continue;

                // Если это место уже находится в "закрытом" наборе
                // затем переходим к следующему местоположению.
                if (state.isLocationClosed(nextLoc))
                    continue;

                // Создаем путевую точку для этого "следующего местоположения".

                Waypoint nextWP = new Waypoint(nextLoc, currWP);

                // Хорошо, мы обманываем и используем смету для вычисления фактического
                // стоимость из предыдущей ячейки. Затем мы добавляем стоимость из
                // ячейка карты, на которую мы ступаем, чтобы установить барьеры и т. д.

                float prevCost = currWP.getPreviousCost() +
                        estimateTravelCost(currWP.getLocation(),
                                nextWP.getLocation());

                prevCost += map.getCellValue(nextLoc);

                // Пропускаем это «следующее место», если оно слишком дорогое.
                if (prevCost >= COST_LIMIT)
                    continue;

                nextWP.setCosts(prevCost,
                        estimateTravelCost(nextLoc, map.getFinish()));

                // Добавляем путевую точку в набор открытых путевых точек. Если здесь
                // оказывается уже путевой точкой для этого местоположения, новый
                // путевая точка заменяет старую путевую точку только в том случае, если она менее затратна
                // чем старый.
                state.addOpenWaypoint(nextWP);
            }
        }
    }

    /**
     * Оценивает стоимость поездки между двумя указанными местами.
     * Фактическая стоимость рассчитывается как расстояние по прямой между
     * два места.
     **/
    private static float estimateTravelCost(Location currLoc, Location destLoc)
    {
        int dx = destLoc.xCoord - currLoc.xCoord;
        int dy = destLoc.yCoord - currLoc.yCoord;

        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
