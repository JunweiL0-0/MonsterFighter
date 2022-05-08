package main.java.model;

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
     * Add a new monster to the team.
     *
     * @param newMonster a monster instance
     */
    public boolean addMonsterToTeam(Monster newMonster) {
        if(teamIsFull()) {
            return false;
        } else {
            for (int i=0; i<this.maxTeamMember; i++) {
                if (!teamMember.containsKey(i)) {
                    teamMember.put(i, newMonster);
                }
            }
            return true;
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
