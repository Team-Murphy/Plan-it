package com.plan_it.mobile.plan_it;

/**
 * Created by Kevin on 29-Oct-2015.
 */
public class Item {
    int listID;
    String item;
    String providing;
    double estCost;
    double actCost;

    Item(int listID, String item, String providing, double estCost, double actCost){
    this.listID = listID;
        this.item = item;
        this.providing = providing;
        this.estCost = estCost;
        this.actCost = actCost;
    }
}
