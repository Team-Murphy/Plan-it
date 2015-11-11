package com.plan_it.mobile.plan_it;

import android.graphics.Bitmap;

/**
 * Created by Kevin on 17-Oct-2015.
 */
enum IsAttending{INVITED, ATTENDING, DECLINED, LEFT, OWNER}
public class Event {
    String name;
    String owner;
    String description;
    String location;
    String fromDate;
    String toDate;
    String fromTime;
    String toTime;
    Bitmap photoId;
    IsAttending isAttending;
    boolean itemList;
    boolean messageBoard;

    Event(String name, String owner, String description,String location, Bitmap photoId, String fromDate, String toDate, String fromTime, String toTime, IsAttending isAttending, boolean itemList, boolean messageBoard) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.location = location;
        this.photoId = photoId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.toTime = toTime;
        this.fromTime = fromTime;
        this.isAttending = isAttending;
        this.itemList = itemList;
        this.messageBoard = messageBoard;
    }
}
