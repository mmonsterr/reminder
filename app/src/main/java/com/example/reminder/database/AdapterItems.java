package com.example.reminder.database;

public class AdapterItems
{
    public  long ID;
    public  String DateTime;
    public  String Title;
    public  String Description;
    public  String Time;
    public  String Date;
    public  String Priority;

    public AdapterItems(long ID, String DateTime, String Title, String Description, String Time, String Date, String Priority)
    {
        this.ID = ID;
        this.DateTime = DateTime;
        this.Title = Title;
        this.Description = Description;
        this.Time = Time;
        this.Date = Date;
        this.Priority = Priority;
    }
}