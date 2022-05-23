package main.java.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class represent a team of monster.
 */
public class Team {
    private final int maxTeamMember;
    private final ArrayList<Monster> teamMember;
    private final ArrayList<Monster> monsterBag;
    private final ArrayList<Weapon> weaponBag;
    private final ArrayList<Shield> shieldBag;
    private final ArrayList<Medicine> medicineBag;

    /**
     * Team's constructor. The default teamSize is three.
     * This constructor will initial a hashMap for storing the teamMember.
     */
    public Team(int maxTeamMember) {
        this.maxTeamMember = maxTeamMember;
        this.teamMember = new ArrayList<>();
        this.monsterBag = new ArrayList<>();
        this.weaponBag = new ArrayList<>();
        this.shieldBag = new ArrayList<>();
        this.medicineBag = new ArrayList<>();
    }

    public void addItemToMonsterBag(Monster m) {
        this.monsterBag.add(m);
    }

    public void addItemToWeaponBag(Weapon w) {
        this.weaponBag.add(w);
    }

    public void addItemToShieldBag(Shield s) {
        this.shieldBag.add(s);
    }

    public void addItemToMedicineBag(Medicine m) {
        this.medicineBag.add(m);
    }

    public ArrayList<Monster> getMonsterBag() {
        return this.monsterBag;
    }

    public ArrayList<Weapon> getWeaponBag() {
        return this.weaponBag;
    }
    public ArrayList<Shield> getShieldBag() {
        return this.shieldBag;
    }
    public ArrayList<Medicine> getMedicineBag() {
        return this.medicineBag;
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

    /**
     * Get a monster from team by using the index.
     *
     * @param i index of the monster in team
     * @return a monster instance
     */
    public Monster getMonsterByIndex(int i) {
        return this.teamMember.get(i);
    }

    /**
     * Return how many monster do we have in the team.
     *
     * @return an integer represent the amount of monsters in the team
     */
    public int size() {
        return this.teamMember.size();
    }

    /**
     * Swap two monsters' positions.
     *
     * @param monsterIndex1 the first index of the monster you want to swap
     * @param monsterIndex2 the second index of the monster you want to swap
     */
    public void swapMonster(int monsterIndex1, int monsterIndex2) {
        Collections.swap(this.teamMember, monsterIndex1, monsterIndex2);
    }

    /**
     * Loop through the team and return the first health(current!=0) monster's index.
     * Return -1 if we can't find any health monster.
     * @return an integer represent the index of the very first health monster. Return -1 if we can't find any health monster.
     */
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

    public void healAllMonster() {
        for (Monster m: this.teamMember) {
            m.recover();
        }
    }

    public void renameMonsterByIndex(int monsterIndex, String newName) {
        this.teamMember.get(monsterIndex).setName(newName);
    }

    public int getMaxTeamMember() {
        return this.maxTeamMember;
    }

    public boolean removeTeamMemberByIndex(int index) {
        if (index > 0 && index < this.teamMember.size()) {
            this.teamMember.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public int getLeastHealthMonsterIndex() {
        int index = -1;
        int minHealth = Integer.MAX_VALUE;
        for (int i=0; i<this.teamMember.size(); i++) {
            if (this.teamMember.get(i).getCurrentHealth() < minHealth) {
                index = i;
                minHealth = this.teamMember.get(i).getCurrentHealth();
            }
        }
        return index;
    }

    public void useItemForMonster(Weapon w, int monsterIndex) {
        this.teamMember.get(monsterIndex).increaseDamage(w.getDmg());
    }
    public void useItemForMonster(Medicine m, int monsterIndex) {
        this.teamMember.get(monsterIndex).healBy(m);
    }
    public void useItemForMonster(Shield s, int monsterIndex) {
        this.teamMember.get(monsterIndex).increaseMaxHealth(s.getShield());
    }

    public void addBagMonsterToTeam(int bagMonsterIndex) {
        if (this.teamMember.size() < this.maxTeamMember) {
            this.teamMember.add(this.monsterBag.get(bagMonsterIndex));
            this.monsterBag.remove(bagMonsterIndex);
        } else {
            int teamMonsterIndex = getLeastHealthMonsterIndex();
            Monster monster = this.teamMember.get(teamMonsterIndex);
            Monster readyToUseMonster = this.monsterBag.get(bagMonsterIndex);
            this.teamMember.remove(teamMonsterIndex);
            this.teamMember.add(readyToUseMonster);
            this.monsterBag.add(monster);
            this.monsterBag.remove(bagMonsterIndex);
        }
    }
    public void emptybag() {
    	this.monsterBag.clear();
    	this.medicineBag.clear();
    	this.monsterBag.clear();
    	this.weaponBag.clear();
    }
}
