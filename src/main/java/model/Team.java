package main.java.model;

import main.java.model.exception.TeamIsAlreadyFullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class represent a team of monster.
 */
public class Team {
    private final int maxTeamMember;
    // eg.. Map{0: Monster1, 1: Monster2, ...}
    private final Map<Integer, Monster> teamMember;

    /**
     * Team's constructor. The default teamSize is three.
     * This constructor will initial a hashMap for storing the teamMember.
     */
    public Team() {
        this.maxTeamMember = 3;
        this.teamMember = new HashMap<>();
    }

    /**
     * Add a new monster to the team. An TeamIsAlreadyFullException will be thrown if the team is full.
     *
     * @param newMonster a monster instance
     * @throws TeamIsAlreadyFullException if team is already full.
     */
    public void addMonsterToTeam(Monster newMonster) throws TeamIsAlreadyFullException {
        if(teamIsFull()) {
            throw new TeamIsAlreadyFullException("Team is already full");
        } else {
            for (int i=0; i<this.maxTeamMember; i++) {
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
        return this.teamMember.size() >= this.maxTeamMember;
    }

    public Map<Integer, Monster> getTeamMember() {
        return this.teamMember;
    }
}
