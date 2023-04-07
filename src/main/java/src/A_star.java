package src;
import java.io.IOException;
import java.util.*;

import static src.Node.getGoal;
import static src.Node.getState;


class A_star {

    public PriorityQueue<Node> pq;
    public Heuristic h;
    public ArrayList<Node> visited;


    A_star(Heuristic heuristic, Node start)
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
            //System.out.println(Arrays.deepToString(currentNode.getState().toArray()));
            //toString(currentNode.getState().toArray());
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
        HTMLupdate.updateFinal(visited);
    }
   /* public void toString(Object[] array)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]+" ");
            if ((i + 1) % 3 == 0 && i != array.length - 1) {
                sb.append("\n");
            }
        }

        String arrayString = sb.toString() + "\n";
        System.out.println(arrayString);
        //return arrayString;
    }*/

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

