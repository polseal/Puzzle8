package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String args[]){
        ArrayList<Integer> state = new ArrayList<>(Arrays.asList(1,3,4,8,6,2,0,7,5));
        ArrayList<Integer> goal = new ArrayList<>(Arrays.asList(1,2,3,8,0,4,7,6,5));
        Heuristic m = Heuristic.MAN;
        Node start = new Node(state, goal, m);
        A_star a_star = new A_star(m, start);
        a_star.search();
    }
}
