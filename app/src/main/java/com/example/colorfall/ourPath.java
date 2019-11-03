package com.example.colorfall;

import android.content.Context;
import android.graphics.Path;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

//WORK IN PROGRESS//
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


    public void save(String fileName) {
        Log.d("TAG", "starting ourPath save");//testing
        Log.d("TAG", "LL size = " + actions.size());//testing
        try {
            ObjectOutputStream saving = new ObjectOutputStream(new FileOutputStream(fileName));
            Log.d("TAG", "file made");//testing
            saving.writeObject(actions);
            Log.d("TAG", "write complete");//testing
            saving.close();
        } catch (IOException ioe) {
            Log.d("TAG", "Exception is caught");
        }
    }

    public void load(String fileName) {
        Log.d("TAG", "starting ourPath save");//testing
        try {
            ObjectInputStream loading = new ObjectInputStream(new FileInputStream(fileName));
            Log.d("TAG", "file opened ");//testing
            Object loadedList = loading.readObject();
            Log.d("TAG", "read complete");//testing
            actions = (LinkedList<Action>)loadedList;
            loading.close();
            Log.d("TAG", "LL saved locally; file closed");//testing
            Log.d("TAG", "LL size = " + actions.size());//testing

        } catch (Exception ld) {
            Log.d("TAG", "Exception is caught");
        }
    }

    public void testinhjt() {
        Log.d("TAG", "erhjklsldjgwehnwknwhjkg;wthjlakg");//testing
    }
    /******************reading obj/ re-drawing stored paths on load********************************/
    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //in.defaultReadObject();//errror here
        //Object loadedLL = in.readObject();
        Log.d("TAG","defaultReadObject passed");//testing

        //actions = (LinkedList<Action>)loadedLL; //Stores loaded LL into the local LL

        //Iterates throught local LL to redraw image
        ListIterator<Action> iter = actions.listIterator(0);

        while(iter.hasNext()==true){
            Action action = iter.next();
            Log.d("TAG","for loop went through this many times");
            action.perform(this);
            Log.d("TAG","after perform");
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
        Log.d("TAG","move 1. x=" + x + " y=" +y + " SIZE = " +  + actions.size());
    }

    @Override
    public void lineTo(float x, float y) {
        actions.add(new Line(x, y));
        super.lineTo(x, y);
        Log.d("TAG","line 1. x=" + x + " y=" +y + " SIZE = " +  + actions.size());
    }
    /*****************end overrides**********************/

    //Getter for linked list
    public List<Action> getLL()  {
        return actions;
    }

    /**************inner MOVE class*******************/
    private static final class Move implements Action, Serializable {

        private final float x, y;

        public Move(float x, float y) {
            this.x = x;
            this.y = y;
            Log.d("TAG","it moved. x=" + x + " y=" +y);
        }

        @Override
        public void perform(Path path) {
            path.moveTo(x, y);
        }
    }
    /***********End inner MOVE class*******************/





    /**************inner LINE class*******************/
    private static final class Line implements Action, Serializable {

        private final float x, y;

        public Line(float x, float y) {
            this.x = x;
            this.y = y;
            Log.d("TAG","it lined. x=" + x + " y=" +y);
        }

        @Override
        public void perform(Path path) {
            path.lineTo(x, y);
        }
    }
    /***********End inner LINE class*******************/

}
