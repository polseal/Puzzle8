package src;
import java.io.*;
import java.lang.*;
import java.util.*;
import src.Node;

class CompareNodes implements Comparator<Node> {


    public int compare(Node a, Node b)
    {
        return a.getf()- b.getf();
    }
}