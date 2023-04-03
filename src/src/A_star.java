package src;
import java.util.*;

import static src.Node.getGoal;
import static src.Node.getState;


class A_star {

    public PriorityQueue<Node> pq;
    public Heuristic h;
    public ArrayList<Node> visited;


    A_star(Heuristic heuristic, Node start)
    {
        PriorityQueue<Node> p = new PriorityQueue<Node>(new CompareNodes());
        p.add(start);
        this.pq = p;
        this.h = heuristic;
        this.visited = new ArrayList<Node>();
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

    public void search()
    {
        PriorityQueue<Node> queue = this.pq;
        int currentIndex = 0;
        while (!queue.isEmpty())
        {
            Node currentNode = queue.poll();
            System.out.println(Arrays.deepToString(currentNode.getState().toArray()));
            if(getState(currentNode).equals(getGoal(currentNode))) {
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
}

