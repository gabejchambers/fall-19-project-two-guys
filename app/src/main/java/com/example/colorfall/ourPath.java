package com.example.colorfall;

import android.content.Context;
import android.graphics.Path;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class ourPath extends Path implements Serializable {

    private static final long serialVersionUID = -5974912367682897467L;//used for serializing

    private List<Action> actions = new LinkedList<>();//list where all user actions are stored


    /***********************************Testing methods***************************/
    public void printList() {
        int i = 1;
        for (Action action : actions) {
            System.out.println(action);
            System.out.println(i);
            i++;
        }
    }
    /******************own write object testing********************************************/
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.actions);
    }//end readObject
    /*************End write object testing**********************************************/
    /*******************************end Testing methods***************************/



    /******************reading obj/ re-drawing stored paths on load********************************/
    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //in.defaultReadObject();//errror here
        in.readObject();
        System.out.println("defaultReadObject passed");//testing
        for (Action action : actions) {
            //System.out.println("for loop went through this many times");
            action.perform(this);
        }
    }//end readObject

    private interface Action extends Serializable {
        void perform(Path path);
    }//end Action
    /*************End redrawing stored paths on load**********************************************/



    /****************Overrides****************************/
    @Override
    public void moveTo(float x, float y) {
        actions.add(new Move(x, y));
        super.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        actions.add(new Line(x, y));
        super.lineTo(x, y);
    }
    /*****************end overrides**********************/




    /**************inner MOVE class*******************/
    private static final class Move implements Action {

        private final float x, y;

        public Move(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void perform(Path path) {
            path.moveTo(x, y);
        }
    }
    /***********End inner MOVE class*******************/





    /**************inner LINE class*******************/
    private static final class Line implements Action {

        private final float x, y;

        public Line(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void perform(Path path) {
            path.lineTo(x, y);
        }
    }
    /***********End inner LINE class*******************/

}