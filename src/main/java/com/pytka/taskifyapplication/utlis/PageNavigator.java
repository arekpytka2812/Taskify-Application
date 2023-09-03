package com.pytka.taskifyapplication.utlis;

import java.util.Stack;

import com.pytka.taskifyapplication.controllers.ICenterPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class PageNavigator {

    private StackPane root;

    private final Stack<ICenterPane> pagesStack;

    private static PageNavigator instance = null;

    private PageNavigator(){
        pagesStack = new Stack<>();
    }

    public static PageNavigator getInstance(){

        if(instance == null){
            instance = new PageNavigator();
        }

        return instance;
    }

    public void setRoot(StackPane root){

        if(this.root != null){
            return;
        }

        this.root = root;
    }


    public void push(ICenterPane newParent){

        this.pagesStack.push(newParent);

        this.reload();

    }

    public void pop(){

        this.pagesStack.pop();

        if(this.pagesStack.size() == 0){
            return;
        }

        this.reload();

        this.pagesStack.peek().refresh();
    }

    private void reload(){

        this.root.getChildren().clear();
        this.root.getChildren().add((Node) this.pagesStack.peek());
    }

}
