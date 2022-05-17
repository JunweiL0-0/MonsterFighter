package main.java.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class represent a team of monster.
 */
public class Team {
    private final int maxTeamMember;
    private final ArrayList<Monster> teamMember;

    /**
     * Team's constructor. The default teamSize is three.
     * This constructor will initial a hashMap for storing the teamMember.
     */
    public Team(int maxTeamMember) {
        this.maxTeamMember = maxTeamMember;
        this.teamMember = new ArrayList<>();
    }

    /**
     * Add a new monster to the team. Return true if added successfully, return false otherwise.
     *
     * @param newMonster a monster instance
     */
    public boolean addMonsterToTeam(Monster newMonster) {
        if (this.teamMember.size() <= this.maxTeamMember) {
            this.teamMember.add(newMonster);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return monster member(s) in the team.
     *
     * @return monster member(s) in the team.
     */
    public ArrayList<Monster> getTeamMember() {
        return this.teamMember;
    }

    /**
     * Receive the incoming damage from monster.
     * The first not fainted monster in this team will receive the damage.
     *
     * @param incomingDamage an integer value
     */
    public void receiveDamage(int incomingDamage) {
        // The first not fainted monster
        Monster firstNotFaintedMonster = teamMember.get(getFirstHealthMonsterIndex());
        // Receive damage
        firstNotFaintedMonster.harmBy(incomingDamage);
    }

    /**
     * Return true if we can't find any not fainted monster
     *
     * @return a boolean value. True: AllFainted. False: Not allFainted
     */
    public boolean isAllFainted() {
        return getFirstHealthMonsterIndex() == -1;
    }

    public Monster getMonsterByIndex(int i) {
        return this.teamMember.get(i);
    }

    public int size() {
        return this.teamMember.size();
    }

    public void swapMonster(int monsterIndex1, int monsterIndex2) {
        Collections.swap(this.teamMember, monsterIndex1, monsterIndex2);
    }

    public int getFirstHealthMonsterIndex() {
        for (int i=0; i<teamMember.size(); i++) {
            // return the index value if monster is not fainted
            if (!(teamMember.get(i).isFainted())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Loop through the teamMember and trying to find a not fainted monster and return the index of that monster.
     *
     * @return an integer value which represent the index of the first not fainted monster in the team.
     */
    public int getLeastActionCounterMonsterIndex() {
        // maximum integer value
        int minAction = Integer.MAX_VALUE;
        // set default index to -1
        int index = -1;
        // loop through the team and calculate the minimum action counter value.
        for (int i=0; i<this.teamMember.size(); i++) {
            // current selected monster
            Monster m = this.teamMember.get(i);
            // if not fainted and has lower action counter value
            if (!(m.isFainted()) && m.getActionCounter() < minAction) {
                minAction = m.getActionCounter();
                // store the index
                index = i;
            }
        }
        if (index != -1) {
            this.teamMember.get(index).incrementActionCounter();
        }
        return index;
    }
}
