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

    /**
     * Add the monster to the monsterBag. This is an overloading method
     *
     * @param m a monster instance
     */
    public void addItemToMonsterBag(Monster m) {
        this.monsterBag.add(m);
    }

    /**
     * Add the weapon to the weaponBag. This is an overloading method
     *
     * @param w a weapon instance
     */
    public void addItemToWeaponBag(Weapon w) {
        this.weaponBag.add(w);
    }

    /**
     * Add shield to the shieldBag. This is an overloading method.
     *
     * @param s a shield instance
     */
    public void addItemToShieldBag(Shield s) {
        this.shieldBag.add(s);
    }

    /**
     * Add medicine to the medicineBag. This is an overloading method.
     *
     * @param m a medicine instance
     */
    public void addItemToMedicineBag(Medicine m) {
        this.medicineBag.add(m);
    }

    /**
     * Return the player's monsterBag.
     *
     * @return an arrayList represent the monsterBag of the player.
     */
    public ArrayList<Monster> getMonsterBag() {
        return this.monsterBag;
    }

    /**
     * Return the player's weaponBag
     *
     * @return an arrayList represent the weaponBag of the player
     */
    public ArrayList<Weapon> getWeaponBag() {
        return this.weaponBag;
    }

    /**
     * Return the shieldBag of the player.
     *
     * @return an arrayList represent the shieldBag of the player
     */
    public ArrayList<Shield> getShieldBag() {
        return this.shieldBag;
    }

    /**
     * Return the medicineBag of the player.
     *
     * @return an arrayList represent the medicineBag of the player.
     */
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

    /**
     * Heal all monster. All monster's health will be healed to its full health.
     */
    public void healAllMonster() {
        for (Monster m: this.teamMember) {
            m.recover();
        }
    }

    /**
     * Rename the monster base on the given index.
     *
     * @param monsterIndex the index of the monster in the team
     * @param newName the new name of the monster.
     */
    public void renameMonsterByIndex(int monsterIndex, String newName) {
        this.teamMember.get(monsterIndex).setName(newName);
    }

    /**
     * Return the maximum teamMember of the team
     *
     * @return an integer represent the maximum number of monsters we can have in the team
     */
    public int getMaxTeamMember() {
        return this.maxTeamMember;
    }

    /**
     * Remove the teamMonster in the team by index.
     *
     * @param index the index of the monster in the team
     * @return true if we change its name successfully. Otherwise, return false.
     */
    public boolean removeTeamMemberByIndex(int index) {
        if (index > 0 && index < this.teamMember.size()) {
            this.teamMember.remove(index);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the monster which has the lease health in the team
     *
     * @return the least monsterIndex in the team
     */
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

    /**
     * Apply the effect of the item to the monster. This is an overloading method.
     *
     * @param w a weapon instance
     * @param monsterIndex the index of the monster in the team
     */
    public void useItemForMonster(Weapon w, int monsterIndex) {
        this.teamMember.get(monsterIndex).increaseDamage(w.getDmg());
    }

    /**
     * Apply the effect of the item to the monster. This is an overloading method.
     *
     * @param m a medicine instance
     * @param monsterIndex the index of the monster in the team
     */
    public void useItemForMonster(Medicine m, int monsterIndex) {
        this.teamMember.get(monsterIndex).healBy(m);
    }

    /**
     * Apply the effect of the item to the monster. This is an overloading method.
     *
     * @param s a shield instance
     * @param monsterIndex the index of the monster in the team
     */
    public void useItemForMonster(Shield s, int monsterIndex) {
        this.teamMember.get(monsterIndex).increaseMaxHealth(s.getShield());
    }

    /**
     * Take the monster from the bag and put it into to the team.
     *
     * @param bagMonsterIndex a weapon instance
     */
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

    /**
     * Empty the bag. Remove everything in the bag.
     */
    public void emptyBag() {
    	this.monsterBag.clear();
    	this.medicineBag.clear();
    	this.monsterBag.clear();
    	this.weaponBag.clear();
    }

    /**
     * Remove the monster from the team base on the given index
     *
     * @param index an integer represent the index of the monster in the team
     */
    public void removeMonsterFromTeam(int index) {
    	Monster monster = teamMember.get(index);
    	teamMember.remove(index);
    	monsterBag.add(monster);
    	
    }
}
