package main.java.model;

public class BattleField {
    private int round;
    private Team playerTeam;
    private Team enemyTeam;

    public BattleField() {
        this.round = 1;
        // empty team
        this.playerTeam = new Team(1);
        this.enemyTeam = new Team(1);
    }

    public void setBattle(Team playerTeam, Team enemyTeam) {
        this.round = 1;
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
    }

    public int battle() {
            // 1: PlayerTeam won, -1: EnemyTeam won, 0: Nobody won yet
            int attackMonsterIndex;
            int defenceMonsterIndex;
            Monster attackMonster;
            Monster defenceMonster;
            boolean playerTurn = (this.round % 2) != 0;
            boolean enemyTurn = (this.round % 2) == 0;
            if (playerTurn) {
                defenceMonsterIndex = enemyTeam.getFirstHealthMonsterIndex();
                attackMonsterIndex = playerTeam.getLeastActionCounterMonsterIndex();
                defenceMonster = enemyTeam.getMonsterByIndex(defenceMonsterIndex);
                attackMonster = playerTeam.getMonsterByIndex(attackMonsterIndex);
                defenceMonster.harmBy(attackMonster.getDamage());
            } else if (enemyTurn) {
                defenceMonsterIndex = playerTeam.getFirstHealthMonsterIndex();
                attackMonsterIndex = enemyTeam.getLeastActionCounterMonsterIndex();
                defenceMonster = playerTeam.getMonsterByIndex(defenceMonsterIndex);
                attackMonster = enemyTeam.getMonsterByIndex(attackMonsterIndex);
                defenceMonster.harmBy(attackMonster.getDamage());
            }
            this.round++;
            if (this.playerTeam.isAllFainted()) {
                return -1;
            } else if (this.enemyTeam.isAllFainted()) {
                return 1;
            } else {
                return 0;
            }
    }

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

    public boolean isPlayerTurn() {
        return (this.round % 2) != 0;
    }
}
