package blockamon.objects;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Blockamon extends IBoundable {

    //TODO: Fix information being displayed incorrectly

	private static final Random randomNumberGenerator = new Random();

	private boolean isLead;
	private static int healthRange = 10;
	private static int healthAddition = 10;
	private static final int CRITICAL_HIT_MAX = 100;
	private static final int CRITICAL_HIT_CHANCE = 6;
	private static final int HIT_MAX = 100;
	private static final int CRITICAL_HIT_MULTIPLIER = 2;
	private static int attackRange = 2;
	private static int attackAddition = 3;
	private static final int MISS_CHANCE = 10;
	private static final int NEGATED_VALUE = 0;
	private static final double HEALTH_MULTIPLIER = 1.5;
	private static final double HEALTH_ADDITION_MULTIPLIER = 1.2;
	private static final double EXPERIENCE_NEEDED_MULTIPLIER = 2.5;
	private static final double EXPERIENCE_GAIN_MULTIPLIER = 1.5;
	private int randomExperience, _currentLevel, _currentExp, soNotZero, _neededExp;

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_LEVEL = 1;
    private static final int DEFAULT_EXPERIENCE = 0;
    private static final int DEFAULT_EXPERIENCE_NEEDED = 50;
    private static final double DEFAULT_ATTACK_POWER = 1.0;
    private String _status = "NONE";

    public Blockamon(ElementType type, String name, int width, int height, int currentLevel, int experienceNeeded, Color color) {
        this(type, name, width, height, currentLevel, DEFAULT_EXPERIENCE, experienceNeeded, color);
    }
    public Blockamon(ElementType type, String name, int width, int height, int currentLevel, int experience, int experienceNeeded, Color color) {
        elementType(type);
        name(name);
        this.setSize(width, height);
        maxHp(randomNumberGenerator.nextInt(healthRange) + healthAddition);
        totalAttack(randomNumberGenerator.nextInt(attackRange) + attackAddition);
        this._currentLevel = currentLevel;
        this._currentExp = experience;
        this._neededExp = experienceNeeded;
        randomExperience = 15;
        soNotZero = 5;
        isLead(false);
        resetHealth();
        resetAttack();
        blockamon.shapes.Rectangle backGround = new blockamon.shapes.Rectangle(0, 0, this.getWidth(), this.getHeight());
        backGround.setBackground(color);
        this.add(backGround, 0);
    }

    public String name() {
        if(_name == null)
        {
            name(elementType().toString());
        }
        return _name;
    }
    public void name(String value) {
        _name = value;
    }
    public boolean hasFainted() {
        return currentHp() == 0;
    }
    public boolean isLead() {
        return isLead;
    }
    public void isLead(boolean isLead) {
        this.isLead = isLead;
    }

    /**
     * Heals the blockamon to full health
     */
    public void fullyHeal() {
        currentHp(maxHp());
    }

    /**
     * Heals the basic attack of the blockamon
     */
    public void healAttack() {
        currentAttack(totalAttack());
    }

    /**
     * Damages this blockamon for the specified amount, or to zero
     * @param value The amount to damage this blockamon for
     */
    public void damage(double value) {
        final double currentHealth = currentHp();
        final double newHealth = (currentHealth - value) + ((value%currentHealth)%value);
        currentHp(newHealth);
    }

	public void levelUp() {
        level(level() + 1);
		showInfo("Your blockamon is now level " + level(), "Level up", JOptionPane.INFORMATION_MESSAGE);
		addToStats();
	}
	private void showInfo(String text, String title, int type) {
		JOptionPane.showMessageDialog(null, text, title, type);
	}
    //TODO: Move this method out of this class, as it doesn't belong here
	public void gainExperience() {
		int experienceGain = randomNumberGenerator.nextInt(randomExperience) + soNotZero;
		_currentExp += experienceGain;
		if(_currentExp + experienceGain < _neededExp)
		{
			showInfo("Your Blockamon Gained " + experienceGain + " _experience\nOnly " +(_neededExp - _currentExp) + " needed for a level up", "Experience Gained", JOptionPane.INFORMATION_MESSAGE);
		}
		if(_currentExp >= _neededExp)
		{
			showInfo("Your isLead Blockamon has gained a level!", "Level Up", JOptionPane.INFORMATION_MESSAGE);
			levelUp();
			_neededExp *= EXPERIENCE_NEEDED_MULTIPLIER;
			_currentExp = 0;
			randomExperience *= EXPERIENCE_GAIN_MULTIPLIER;
//			healthRange *= HEALTH_MULTIPLIER;
//			healthAddition *= HEALTH_ADDITION_MULTIPLIER;
			attackRange *= HEALTH_MULTIPLIER;
			attackAddition *= HEALTH_ADDITION_MULTIPLIER;
		}
	}
	private void addToStats() {
		final int healthAddition = randomNumberGenerator.nextInt(10) + 5;
		final int attackAddition = randomNumberGenerator.nextInt(3) + 2;
		showInfo("Your Blockamon gained " + attackAddition + " attack and " + healthAddition + " health", "Stats Upgraded", JOptionPane.INFORMATION_MESSAGE);
        _totalAttack += attackAddition;
        _currentAttack += attackAddition;
		resetAttack();
		resetHealth();
	}
	public void setStats(double health, boolean isLead, double attack, String name, int level, int currentExp, int neededExp) {
		maxHp(health);
		isLead(isLead);
		currentAttack(attack);
		name(name);
		_currentLevel = level;
		_currentExp = currentExp;
	    _neededExp = neededExp;
		resetAttack();
		resetHealth();
	}
    /**
     * Heals this blockamon for the specified amount, or to the blockamons total health
     * @param damage The amount to heal the blockamon for
     */
	public void takeDamage(double damage, boolean isPlayersTurn) {
		int criticalHit = randomNumberGenerator.nextInt(CRITICAL_HIT_MAX);
		int hitMiss = randomNumberGenerator.nextInt(HIT_MAX);
		//chance for a critical hit
		if(criticalHit <= CRITICAL_HIT_CHANCE)
		{
			scoredCritical(isPlayersTurn);
			damage *= CRITICAL_HIT_MULTIPLIER;
		}
		//chance for a miss
		if(hitMiss < MISS_CHANCE)
		{
			missedAttack(isPlayersTurn);
			damage = NEGATED_VALUE;
		}
	    currentHp(currentHp() - damage);
	}
	private void scoredCritical(boolean isPlayersTurn) {
		String who = whoDidIt(isPlayersTurn);
		//tell the user a critical hit was scored
		showInfo(who + "scored a CriticalHit", "Yay a Critical", JOptionPane.INFORMATION_MESSAGE);
	}
	private void missedAttack(boolean isPlayersTurn) {
		String who = whoDidIt(isPlayersTurn);
		//informs the attack missed
		showInfo(who + "missed their attack", "Its a miss", JOptionPane.INFORMATION_MESSAGE);
	}
	private String whoDidIt(boolean isPlayersTurn) {
		String who = "The Foe ";
		if(isPlayersTurn)
		{
			who = "The Player ";
		}
		return who;
	}
    //resets the attack and attack power to their defaults
    public void resetAttack() {
        currentAttack(totalAttack());
        currentAttack(DEFAULT_ATTACK_POWER);
    }

    public void resetHealth() {
        currentHp(maxHp());//health = totalHealth;
    }

    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Level: ");
        stringBuilder.append(level());
        stringBuilder.append("\n");
        stringBuilder.append("Attack: ");
        stringBuilder.append(currentAttack());
        stringBuilder.append("/");
        stringBuilder.append(totalAttack());
        stringBuilder.append("\n");
        stringBuilder.append("Status: ");
        if(this.hasFainted()) {
            stringBuilder.append("Fainted");
        }
        else {
            stringBuilder.append("Current Health: ");
            stringBuilder.append(currentHp());
            stringBuilder.append("/");
            stringBuilder.append(maxHp());
        }
        stringBuilder.append("\n");
        stringBuilder.append("Type: ");
        stringBuilder.append(elementType());
        stringBuilder.append("\n");
        stringBuilder.append("Experience: ");
        stringBuilder.append(this.currentExp());
        stringBuilder.append("/");
        stringBuilder.append(neededExp());
        return stringBuilder.toString();
    }




    private ElementType _element;
    private double _maxHp;
    private double _currentHp;
    private String _name;
    private double _currentAttack;
    private double _totalAttack;
    private double _maxAttack;

    public Blockamon(ElementType type) {
        _element = type;
        this.name(type.name());
        int maxHp = randomNumberGenerator.nextInt(10);
        if(maxHp < 5) {
            maxHp = 5;
        }
        this.maxHp(maxHp);
        int totalAttack = randomNumberGenerator.nextInt(2);
        if(totalAttack < 1) {
            totalAttack = 1;
        }
        this.totalAttack(totalAttack);
    }

    public ElementType elementType() {
        return _element;
    }
    public void elementType(ElementType element) {
        _element = element;
    }

    public double maxHp() {
        return _maxHp;
    }
    public void maxHp(double maxHp) {
        if(_currentHp == 0.0 || _currentHp == _maxHp) {
            currentHp(maxHp);
        }
        else {
            currentHp(_currentHp + (_maxHp - maxHp));
        }
        _maxHp = maxHp;
    }

    public double currentHp() {
        return _currentHp;
    }
    public void currentHp(double currentHp) {
        _currentHp = currentHp;
    }

    public double currentAttack() {
        return _currentAttack;
    }
    public void currentAttack(double currentAttack) {
        _currentAttack = currentAttack;
    }
    public double totalAttack() {
        return _totalAttack;
    }
    public void totalAttack(double totalAttack) {
        _totalAttack = totalAttack;
        currentAttack(totalAttack);
    }
    public int currentExp() {
        return _currentExp;
    }
    public void currentExp(int currentExp) {
        _currentExp = currentExp;
    }
    public int neededExp() {
        return _neededExp;
    }
    public void neededExp(int neededExp) {
        _neededExp = neededExp;
    }
    public int level() {
        return _currentLevel;
    }
    public void level(int value) {
        _currentLevel = value;
    }

    public void takeDamage(int hp) {
        _currentHp -= hp;
        if(_currentHp < 0) {
            _currentHp = 0;
        }
    }

    public void heal(int hp) {
        _currentHp = (_currentHp += hp) - (_currentHp % _maxHp);
    }

    public void status(String status) { _status = status;}
    public String status() {
        return _status;
    }
}
