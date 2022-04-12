package main.java.model;

import java.util.ArrayList;

public class Team {
    private ArrayList<Monster> teamMember;


    public void addMonsterToTeam(Monster newMonster) {
        // we will need to set and check the max teamMember
        this.teamMember.add(newMonster);
    }

    /**
     * A function used to check if the team is full or not.
     *
     * @return true if the team is full. Otherwise, return false.
     */
    public boolean teamIsFull() {
        return this.teamMember.size() >= 3;
    }

    public ArrayList<Monster> getTeamMember() {
        return this.teamMember;
    }


}
