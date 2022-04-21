package main.java.model;

import main.java.model.exception.TeamIsAlreadyFullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Team {
    private final static int MAXTEAMMEMBER = 3;
    private Map<Integer, Monster> teamMember;


    public Team() {
        this.teamMember = new HashMap<>();
    }

    public void addMonsterToTeam(Monster newMonster) throws TeamIsAlreadyFullException {
        if(teamIsFull()) {
            throw new TeamIsAlreadyFullException("Team is already full");
        } else {
            for (int i=0; i<MAXTEAMMEMBER; i++) {
                if (!teamMember.containsKey(i)) {
                    teamMember.put(i, newMonster);
                }
            }
        }
    }

    /**
     * A function used to check if the team is full or not.
     *
     * @return true if the team is full. Otherwise, return false.
     */
    public boolean teamIsFull() {
        return this.teamMember.size() >= MAXTEAMMEMBER;
    }

    public Map<Integer, Monster> getTeamMember() {
        return this.teamMember;
    }
}
