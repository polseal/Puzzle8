package src;
import java.util.*;

public class Node
{
    private ArrayList<Integer> state;
    private ArrayList<Integer> goal;
    private Node parent;
    private Heuristic heuristic_type;
    private int h;
    private int g;
    private int f;
    
    public Node(ArrayList<Integer> state, ArrayList<Integer> goal, Heuristic h)
    {
        this.state = state;
        this.parent = null;
        this.goal = goal;
        this.heuristic_type = h;
        this.seth(h); /*set h*/
        this.g = 0; /*set g*/
        this.f = 0;/*set f*/
    }

    Node(ArrayList<Integer> state, Node parent, ArrayList<Integer> goal, Heuristic h)
    {
        this.state = state;
        this.parent = parent;
        this.goal = goal;
        this.heuristic_type = h;
        this.seth(h);
        this.setg(parent.getg() + 1);
        this.setf();

    }

    public int getEmpty(ArrayList<Integer> state)
    {
        return state.indexOf(0);
    }

    public ArrayList<Integer>  swap(ArrayList<Integer> state, int indexToReplace)
    {
        ArrayList<Integer> newState = new ArrayList<>(state);
        int emptyIndex = getEmpty(state);
        newState.set(emptyIndex, newState.get(indexToReplace));
        newState.set(indexToReplace, 0);
        return newState;
    }

    public static ArrayList<Integer> getState(Node currentNode)
    {
        return currentNode.state;
    }

    public static ArrayList<Integer> getGoal(Node currentNode)
    {
        return currentNode.goal;
    }

    public int getg()
    {
        return this.g;
    }

    public void setg(int val)
    {
        this.g = val;
    }

    public ArrayList<Integer> getState()
    {
        return this.state;
    }

    public Heuristic getHeuristic()
    {
        return this.heuristic_type;
    }

    public boolean allowedState(Direction direction)
    {       ArrayList<Integer> oldState = getState(this);
            int currentEmpty = getEmpty(oldState);
        switch(direction)
        {
            case DOWN:
                if(getEmpty(oldState) > 5) return false;
                break;
            case UP:
                if(getEmpty(oldState) < 3) return false;
                break;
            case LEFT:
                if(currentEmpty % 3 == 0) return false;
                break;
            case RIGHT:
                if(currentEmpty % 3 == 2) return false;
                break;
            default:
                return false;
        }
        return true;
    }

    public Node newNode(Direction direction)
    {   ArrayList<Integer> oldState = getState(this);
        ArrayList<Integer> newState;
        int currentEmpty = getEmpty(oldState);
        switch(direction)
        {
            case DOWN:
                newState = swap(oldState, currentEmpty+3);
                break;
            case UP:
                newState = swap(oldState, currentEmpty-3);
                break;
            case LEFT:
                newState = swap(oldState, currentEmpty-1);
                break;
            case RIGHT:
                newState = swap(oldState, currentEmpty+1);
                break;
            default:
                return null;
        }
        return new Node(newState,this, getGoal(this), this.getHeuristic());
    }
    public void setState(ArrayList<Integer> s)
    {
        this.state = s;
    }
    public int geth()
    {
        return this.h;
    }
    public void seth(Heuristic h)
    {
        switch(h)
        {
            case MAN:
                this.h = this.calculateManhattan();
                break;
            case DIS:
                this.h = this.calculateDisplaced();
                break;
        }
    }
    public void setf()
    {
        this.f = this.getg() + this.geth();
    }
    public int getf()
    {
        return this.f;
    }
    public int calculateManhattan()
    {
        int sum=0;
        ArrayList<Integer> state = getState(this);
        ArrayList<Integer> goal = getGoal(this);
        for (int i = 1; i < state.size(); i++)
        {
            sum += Math.abs((state.indexOf(i) % 3) - (goal.indexOf(i) % 3)) + Math.abs(Math.floorDiv(state.indexOf(i), 3)-Math.floorDiv(goal.indexOf(i), 3));
        }
        return sum;
    }
    public int calculateDisplaced()
    {
        int sum=0;
        ArrayList<Integer> state = getState(this);
        ArrayList<Integer> goal = getGoal(this);

        for (int i = 0; i < state.size(); i++)
        {
            if(state.get(i) != goal.get(i))
            {
                sum += 1;
            }
        }
        return sum;
    }


}
