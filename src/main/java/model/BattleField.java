package main.java.model;


/**
 * This is the BattleField class. Everytime the player enter a game, two team(Player, enemy) will be passed into this
 * class.
 */
public class BattleField {
    private int round;
    private int battleResult;
    private Team playerTeam;
    private Team enemyTeam;


    /**
     * This is the constructor of the BattleField. We set the round to one and create two empty teams.
     */
    public BattleField() {
        this.round = 1;
        this.battleResult = -1;
        // empty team
        this.playerTeam = new Team(1);
        this.enemyTeam = new Team(1);
    }

    /**
     * Everytime when player enter a battle. Two teams will be passed into this function.
     * This function will store two team inside this class and set the round to one.
     *
     * @param playerTeam a Team instance which represent the playerTeam
     * @param enemyTeam a Team instance which represent the enemyTeam
     */
    public void setBattle(Team playerTeam, Team enemyTeam) {
        this.round = 1;
        this.battleResult = -1;
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
    }

    /**
     * This function will emit a battle and return the result for each turn.
     * Returned value: 1:PlayerTeam won, -1:EnemyTeam won, 0:Nobody won yet
     *
     * @return an integer which represent the result of each turn
     */
    public int battle() {
        // 1: PlayerTeam won, -1: EnemyTeam won, 0: Nobody won yet
        // attackMonster: The monster with the least action counter in its team
        // defenceMonster: The very first monster which is not fainted
        int attackMonsterIndex;
        int defenceMonsterIndex;
        Monster attackMonster;
        Monster defenceMonster;
        boolean playerTurn = (this.round % 2) != 0;
        if (playerTurn) {
            // if this is player's turn.
            defenceMonsterIndex = enemyTeam.getFirstHealthMonsterIndex();
            attackMonsterIndex = playerTeam.getLeastActionCounterMonsterIndex();
            defenceMonster = enemyTeam.getMonsterByIndex(defenceMonsterIndex);
            attackMonster = playerTeam.getMonsterByIndex(attackMonsterIndex);
            defenceMonster.harmBy(attackMonster.getDamage());
        } else {
            // if this is enemy's turn
            defenceMonsterIndex = playerTeam.getFirstHealthMonsterIndex();
            attackMonsterIndex = enemyTeam.getLeastActionCounterMonsterIndex();
            defenceMonster = playerTeam.getMonsterByIndex(defenceMonsterIndex);
            attackMonster = enemyTeam.getMonsterByIndex(attackMonsterIndex);
            defenceMonster.harmBy(attackMonster.getDamage());
        }
        this.round++;
        if (this.playerTeam.isAllFainted()) {
            this.battleResult = -1;
        } else if (this.enemyTeam.isAllFainted()) {
            this.battleResult = 1;
        } else {
            this.battleResult = 0;
        }
        return this.battleResult;
    }

    public int getBattleResult() {
        return this.battleResult;
    }

    public void endBattle() {
        for (Monster m: this.playerTeam.getTeamMember()) {
            m.resetActionCounter();
        }
        for (Monster m: this.enemyTeam.getTeamMember()) {
            m.resetActionCounter();
        }
        this.round = 1;
        this.battleResult = -1;
        this.playerTeam = new Team(1);
        this.enemyTeam = new Team(1);
    }

    /**
     * Get the monster from playerTeam which is ready to attack/defence in this turn
     *
     * @return a monster instance from player team which is ready to attack/defence in this turn
     */
    public Monster getPlayerTeamReadyMonster() {
        int index;
        boolean playerTurn = (this.round % 2) != 0;
        if (playerTurn) {
            index = playerTeam.getLeastActionCounterMonsterIndex();
        } else {
            index = playerTeam.getFirstHealthMonsterIndex();
        }
        return playerTeam.getMonsterByIndex(index);
    }

    /**
     * Get the monster from enemyTeam which is ready to attack/defence in this turn
     *
     * @return a monster instance from enemy team which is ready to attack/defence in this turn
     */
    public Monster getEnemyTeamReadyMonster() {
        int index;
        boolean enemyTurn = (this.round % 2) == 0;
        if (enemyTurn) {
            index = enemyTeam.getLeastActionCounterMonsterIndex();
        } else {
            index = enemyTeam.getFirstHealthMonsterIndex();
        }
        return enemyTeam.getMonsterByIndex(index);
    }

    /**
     * This function is exported to outside for checking whether now is the playerTurn
     *
     * @return a boolean value shows whether now is the playerTurn
     */
    public boolean isPlayerTurn() {
        return (this.round % 2) != 0;
    }
}
