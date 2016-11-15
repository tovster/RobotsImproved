/**
 * Assignment: Assignment 4
 * Submitted by: Mark Tov
 * Program: Software Engineering and Network Engineering
 * Filename: TestRobotImproved.java
 * Description: A test of the new RobotImproved subclass to show off the
 * different new functionality
 */

import becker.robots.*;

public class TestRobotImproved {
    public static void main(String[] args) {

        // Set up the initial situation
        City ny = new City();

        //Set up oil station
        Wall oilStation0 = new Wall(ny, 1, 0, Direction.NORTH);
        Wall oilStation1 = new Wall(ny, 0, 1, Direction.WEST);
        Wall oilStation2 = new Wall(ny, 0, 2, Direction.WEST);
        Thing oilSign0 = new Thing(ny, 0, 0);
        oilSign0.getIcon().setLabel("Midas");

        //Things of the city
        Thing thing0 = new Thing(ny, 4, 4);
        Thing thing1 = new Thing(ny, 4, 4);
        Thing thing2 = new Thing(ny, 4, 4);
        Thing thing3 = new Thing(ny, 6, 2);
        Thing thing4 = new Thing(ny, 1, 3);
        Thing thing5 = new Thing(ny, 3, 5);
        Thing thing6 = new Thing(ny, 3, 5);
        Thing thing7 = new Thing(ny, 5, 5);

        //Walls of the city
        Wall wall0 = new Wall(ny, 3, 6, Direction.NORTH);
        Wall wall1 = new Wall(ny, 2, 3, Direction.WEST);
        Wall wall2 = new Wall(ny, 5, 5, Direction.WEST);
        Wall wall3 = new Wall(ny, 1, 6, Direction.NORTH);

        //Initializing Robot
        RobotImproved mark = new RobotImproved(ny, 3, 0, Direction.EAST,
                false, 25);
        mark.pickAllAtIntersection(4,4);
        mark.goToStation();
        mark.goToAvenue(2);
        mark.putAll();
        mark.pickAllAtIntersection(6,2);
        mark.goToStreet(0);
        mark.putAll();
        mark.goToStation();
        mark.pickAllAtIntersection(5,5);
        mark.goToStation();
        mark.goToAvenue(2);
        mark.goToStreet(0);
        mark.putAll();
    }
}