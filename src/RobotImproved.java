/**
 * Assignment: Assignment 4
 * Submitted by: Mark Tov
 * Program: Software Engineering and Network Engineering
 * Filename: RobotImproved.java
 * Description: Subclass of Robot to add functionality to the default Robot
 * class
 */

import becker.robots.*;
import javax.swing.JOptionPane;


public class RobotImproved extends Robot {

    public boolean useOil = useOilDialog();
    public int oilCounter = oilCapacity();
    public int oilRefill = oilCounter;

    public RobotImproved(City city, int street, int avenue, Direction direction,
                         boolean useOil, int oilCounter) {
        super(city, street, avenue, direction);
    }

    //MOVEMENT METHODS - Makes the robot move with different params
    //Makes the robot move to a street (avoids crashing into walls)
    public boolean goToStreet(int street) {
        //Defaults the robot to face North
        faceNorth();
        //Makes the robot turn around if it is on a lower street
        if (getStreet() < street) {
            turnAround();
        }
        //Makes the robot move until it gets to its destination
        //Scales a wall if there is one in the way
        while (getStreet() != street) {
            move();
            if (frontIsClear() == false) {
                scaleWall();
            }
        }
        //Returns true upon completion
        return true;
    }
    //Makes the robot move to a avenue (avoids crashing into walls)
    public boolean goToAvenue(int avenue) {
        //Makes the robot face East by default
        faceEast();
        //If the robot is has to go the other direction, turn around
        if (getAvenue() > avenue) {
            turnAround();
        }
        //While the robot is not at the requested avenue, keep moving
        while (getAvenue() != avenue) {
            move();
            //Scales the wall if there is one in the way
            if (frontIsClear() == false) {
                scaleWall();
            }
        }
        return true;
    }
    //If the robot can move forward without crashing, do so
    public void move() {
        //Overwrites the regular robot move method
        //If it won't crash moving forward, move forward
        if (frontIsClear()) {
            //Call upon the original robot move method
            super.move();
            //Subtract the oil counter (only matters if the user designates that
            //robot will crash with no oil)
            oilCounter--;
        }
        //Refills the oil of the robot when it lands in the oil refill station
        //intersection, in this case 0,1
        if(getAvenue()==1 && getStreet()==0){
            oilCounter = oilRefill;
        }
        //Breaks the robot if it runs out of oil while moving
        breakRobotNoOil();
    }
    //Makes the robot scale a wall and then return to its previous direction
    public void scaleWall() {
        //Saves the current intersection to resume on the current track after
        int currentAve = getAvenue();
        int currentSt = getStreet();
        //Goes around the wall
        while (frontIsClear() == false) {
            turnLeft();
            move();
            for (int i = 0; i < 3; i++) {
                turnLeft();
            }
        }
        move();
        //Returns the robot back on track depending on its direction
        if (getDirection() == Direction.EAST) {
            goToStreet(currentSt);
            faceEast();
        } else if (getDirection() == Direction.WEST) {
            goToStreet(currentSt);
            faceWest();
        } else if (getDirection() == Direction.NORTH) {
            goToAvenue(currentAve);
            faceNorth();
        } else if (getDirection() == Direction.SOUTH) {
            goToAvenue(currentAve);
            faceSouth();
        }
    }

    //INVENTORY MANAGEMENT METHODS - Makes robot pick or put things
    //Makes robot pick everything at its current intersection while it can
    public void pickAll() {
        //While the robot can pick something up at its intersection, picks
        while (canPickThing()) {
            pickThing();
        }
    }
    //Makes robot drop everything it has in backpack at current intersection
    public void putAll() {
        //While the robot has things in backpack, drop things
        while (countThingsInBackpack() > 0) {
            putThing();
        }
    }
    //Makes the robot pick up everything at an intersection
    public boolean pickAllAtIntersection(int street, int avenue) {
        //Go to an intersection
        goToStreet(street);
        goToAvenue(avenue);
        //If it can pick things at the intersection call pickAll method, returns
        //true if it can, otherwise, return false
        if (canPickThing()) {
            pickAll();
            return true;
        } else {
            return false;
        }
    }

    //DIRECTION METHODS - Makes robot face a certain direction
    //Makes the robot face east
    public void faceEast() {
        while (getDirection() != Direction.EAST) {
            turnLeft();
        }
    }
    //Makes the robot face west
    public void faceWest() {
        while (getDirection() != Direction.WEST) {
            turnLeft();
        }
    }
    //Makes the robot face north
    public void faceNorth() {
        while (getDirection() != Direction.NORTH) {
            turnLeft();
        }
    }
    //Makes the robot face south
    public void faceSouth() {
        while (getDirection() != Direction.SOUTH) {
            turnLeft();
        }
    }
    //Makes the robot turn around
    public void turnAround() {
        for (int i = 0; i < 2; i++) {
            turnLeft();
        }
    }

    //OIL METHOD - Makes the robot rely on oil
    //Brings up a dialog if the user wants to make the robot rely on oil
    public boolean useOilDialog() {
        //Brings up dialog
        int useOilDecision = JOptionPane.showConfirmDialog(null, "Would you" +
                " like your robot to use oil?", "Oil",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        //If the user selects yes, returns yes to use oil
        if (useOilDecision == JOptionPane.YES_OPTION) {
            return true;
        }
        //If the user selects no, returns no to use oil
        else if (useOilDecision == JOptionPane.NO_OPTION) {
            return false;
        }
        //Defaults to no by default
        else {
            return false;
        }
    }
    //Brings up a dialog to let the user between 15 & 25 moves before the robot
    //breaks
    public int oilCapacity() {
        //Array featuring the options for the user
        Object moveOptionArray[] = {"15", "25"};
        //Dialog for the user to pick which option
        int oilCapacitySet = JOptionPane.showOptionDialog(null,
                "How many moves per oil refill would you like?" +
                "(If you're not using oil, input does not matter"
                , "Oil", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                , null, moveOptionArray, moveOptionArray[1]);
        //If the user selects 15(yes), then return 15 moves
        if (oilCapacitySet == JOptionPane.YES_OPTION) {
            return 15;
        }
        //If the user selects 25(no), then return 25 moves
        else if (oilCapacitySet == JOptionPane.NO_OPTION) {
            return 25;
        }
        //Defaults to 25 moves by default
        else {
            return 25;
        }
    }
    //Breaks the robot if it runs out of oil, and the user wants to rely on oil
    public void breakRobotNoOil() {
        //Checks params
        if (oilCounter == 0 && useOil == true) {
            //Brings up the error message
            JOptionPane.showMessageDialog(null, "The robot ran out of oil"
                    , "Oh no!", JOptionPane.ERROR_MESSAGE);
            //Kills the robot :-(
            breakRobot(null);
        }
    }
    //Makes the robot go to the station to oil up
    public void goToStation(){
        goToStreet(0);
        goToAvenue(1);
    }
}