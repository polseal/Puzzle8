package src;
import java.io.IOException;
import java.util.*;

import static java.util.Collections.*;
import static src.Node.*;


public class A_star {

    public PriorityQueue<Node> pq;
    public Heuristic h;
    public ArrayList<Node> visited;


    public A_star(Heuristic heuristic, Node start)
    {
        PriorityQueue<Node> p = new PriorityQueue<>(new CompareNodes());
        p.add(start);
        this.pq = p;
        this.h = heuristic;
        this.visited = new ArrayList<>();
    }

    public boolean checkIfInVisited(ArrayList<Node> visited, Node currentNode)
    {
        for (int i = 0; i < visited.size(); i++)
        {
            if(getState(visited.get(i)) == getState(currentNode))
            {
                return true;
            }

        }
        return false;
    }

    public void search() throws IOException {
        PriorityQueue<Node> queue = this.pq;
        int currentIndex = 0;
        while (!queue.isEmpty())
        {
            Node currentNode = queue.poll();
            if(getState(currentNode).equals(getGoal(currentNode)))
            {
                visited.add(currentNode);
               break;
            }
            for (Direction d : Direction.values()) {
                if (currentNode.allowedState(d)) {
                    Node newNode = currentNode.newNode(d);
                    if ((checkIfDuplicate(queue, newNode) == false) && (checkIfInVisited(this.visited, newNode) == false)) {
                        queue.add(newNode);
                    }
                }
            }
            this.visited.add(currentNode);
            currentIndex++;
        }
        HTMLupdate.updateFinal(reverse(reconstruct_path(visited)));
    }

    public static boolean checkIfDuplicate(PriorityQueue<Node> pq, Node e)
    {
        if(pq.size() > 0) {
            Iterator value = pq.iterator();
            while (value.hasNext()) {
                if (getState((Node) value.next()) == getState(e)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static ArrayList<Node> reconstruct_path(ArrayList<Node> visited)
    {
        ArrayList<Node> path = new ArrayList<>();
        Node f = visited.get(visited.size()-1);
        while(f!=visited.get(0))
        {
            path.add(f);
            f = getParent(f);
        }
        path.add(visited.get(0));
        return path;
    }
    public ArrayList<Node> reverse(ArrayList<Node> list) {
        for(int i = 0, j = list.size() - 1; i < j; i++) {
            list.add(i, list.remove(j));
        }
        return list;
    }
}

