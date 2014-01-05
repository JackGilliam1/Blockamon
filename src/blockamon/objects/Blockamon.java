package blockamon.objects;

import java.awt.*;

import javax.swing.*;
import java.util.*;
public class Blockamon extends IBoundable {

    //TODO: Fix information being displayed incorrectly
    private String _name;
    private ElementType _elementType;
    private double _currentAttack;
    private double _totalAttack;
    private double _totalHealth;
    private double _currentHealth;
    private double _attackPower;

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
	private int randomExperience, currentLevel, experience, soNotZero, experienceNeeded;

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_LEVEL = 1;
    private static final int DEFAULT_EXPERIENCE = 0;
    private static final int DEFAULT_EXPERIENCE_NEEDED = 50;
    private static final double DEFAULT_ATTACK_POWER = 1.0;
    private static final Color DEFAULT_COLOR = Color.WHITE;

    public Blockamon(ElementType type) {
        this(type, type.toString());
    }
    public Blockamon(ElementType type, String name) {
        this(type, name, DEFAULT_LEVEL);
    }
    public Blockamon(ElementType type, String name, int currentLevel) {
        this(type, name, DEFAULT_WIDTH, DEFAULT_HEIGHT, currentLevel, DEFAULT_EXPERIENCE_NEEDED);
    }
    public Blockamon(ElementType type, String name, int currentLevel, int experienceNeeded) {
        this(type, name, DEFAULT_WIDTH, DEFAULT_HEIGHT, currentLevel, experienceNeeded);
    }
    public Blockamon(ElementType type, String name, int width, int height, int currentLevel, int experienceNeeded) {
        this(type, name, width, height, currentLevel, experienceNeeded, DEFAULT_COLOR);
    }
    public Blockamon(ElementType type, String name, int width, int height, int currentLevel, int experienceNeeded, int red, int green, int blue) {
        this(type, name, width, height, currentLevel, experienceNeeded, new Color(red, green, blue));
    }
    public Blockamon(ElementType type, String name, int width, int height, int currentLevel, int experienceNeeded, Color color) {
        this(type, name, width, height, currentLevel, DEFAULT_EXPERIENCE, experienceNeeded, color);
    }
    public Blockamon(ElementType type, String name, int width, int height, int currentLevel, int experience, int experienceNeeded, Color color) {
        super();
        setElementType(type);
        setName(name);
        this.setSize(width, height);
        setTotalHealth(randomNumberGenerator.nextInt(healthRange) + healthAddition);
        setTotalAttack(randomNumberGenerator.nextInt(attackRange) + attackAddition);
        this.currentLevel = currentLevel;
        this.experience = experience;
        this.experienceNeeded = experienceNeeded;
        randomExperience = 15;
        soNotZero = 5;
        isLead(false);
        resetHealth();
        resetAttack();
        shapes.Rectangle backGround = new shapes.Rectangle(0, 0, this.getWidth(), this.getHeight());
        backGround.setBackground(color);
        this.add(backGround, 0);
    }

    //<editor-fold desc="Getters and Setters">
    /**
     * Retrieves the name of this blockamon
     * @return The name of this blockamon
     */
    public String getName() {
        if(_name == null)
        {
            setName(getElementType().toString());
        }
        return _name;
    }
    /**
     * Sets the name of this blockamon to the specified value
     * @param value The new name of this blockamon
     */
    public void setName(String value) {
        _name = value;
    }
    /**
     * Retrieves the element type of this blockamon
     * @return The element type of this blockamon
     */
    public ElementType getElementType() {
        return _elementType;
    }
    /**
     * Sets the element type of this blockamon to the specified value
     * @param value The new element type of this blockamon
     */
    private void setElementType(ElementType value) {
        _elementType = value;
    }
    /**
     * Retrieves the base attack of this blockamon
     * @return The base attack of this blockamon
     */
    public double getCurrentAttack() {
        return _currentAttack * getAttackPower();
    }
    /**
     * Sets the base attack of this blockamon to the specified value
     * @param value The new base attack of this blockamon
     */
    public void setCurrentAttack(double value) {
        _currentAttack = value;
    }
    /**
     * Retrieves the total attack of this blockamon
     * @return The total attack of this blockamon
     */
    public double getTotalAttack() {
        return _totalAttack;
    }
    /**
     * Sets the total attack of this blockamon to the specified value
     * @param value The new total attack of this blockamon
     */
    public void setTotalAttack(int value) {
        _totalAttack = value;
    }
    public void setAttackPower(double value) {
        _attackPower = value;
    }
    public double getAttackPower() {
        return _attackPower;
    }
    /**
     * Retrieves the total health of this blockamon
     * @return The total health of this blockamon
     */
    public double getTotalHealth() {
        return _totalHealth;
    }
    /**
     * Sets the total health of this blockamon to the specified value
     * @param value The new total health of this blockamon
     */
    public void setTotalHealth(double value) {
        _totalHealth = value;
    }
    /**
     * Retrieves the current health of this blockamon
     * @return The current health of this blockamon
     */
    public double getCurrentHealth() {
        return _currentHealth;
    }
    /**
     * Sets the current health of this blockamon to the specified value
     * @param value The new current health of this blockamon
     */
    public void setCurrentHealth(double value) {
        _currentHealth = value;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int value) {
        currentLevel = value;
    }
    public int getExperience() {
        return experience;
    }
    public void setExperience(int value) {
        experience = value;
    }
    public int getNeededExperience() {
        return experienceNeeded;
    }
    public void setNeededExperience(int value) {
        experienceNeeded = value;
    }
    /**
     * Indicates whether this blockamon has fainted (Health at Zero)
     * @return True if this blockamon has a health less than or equal to zero
     */
    public boolean hasFainted() {
        return getCurrentHealth() <= 0;
    }
    public boolean isLead() {
        return isLead;
    }
    public void isLead(boolean isLead) {
        this.isLead = isLead;
    }
    //</editor-fold>

    /**
     * Heals the blockamon to full health
     */
    public void fullyHeal() {
        setCurrentHealth(getTotalHealth());
    }

    /**
     * Heals the basic attack of the blockamon
     */
    public void healAttack() {
        setCurrentAttack(getTotalAttack());
    }

    /**
     * Damages this blockamon for the specified amount, or to zero
     * @param value The amount to damage this blockamon for
     */
    public void damage(double value) {
        final double currentHealth = getCurrentHealth();
        final double newHealth = (currentHealth - value) + ((value%currentHealth)%value);
        setCurrentHealth(newHealth);
    }

	public void levelUp() {
        setCurrentLevel(getCurrentLevel() + 1);
		showInfo("Your blockamon is now level " + getCurrentLevel(), "Level up", JOptionPane.INFORMATION_MESSAGE);
		addToStats();
	}
	private void showInfo(String text, String title, int type) {
		JOptionPane.showMessageDialog(null, text, title, type);
	}
    //TODO: Move this method out of this class, as it doesn't belong here
	public void gainExperience() {
		int experienceGain = randomNumberGenerator.nextInt(randomExperience) + soNotZero;
		experience += experienceGain;
		if(experience + experienceGain < experienceNeeded)
		{
			showInfo("Your Blockamon Gained " + experienceGain + " experience\nOnly " +(experienceNeeded - experience) + " needed for a level up", "Experience Gained", JOptionPane.INFORMATION_MESSAGE);
		}
		if(experience >= experienceNeeded)
		{
			showInfo("Your isLead Blockamon has gained a level!", "Level Up", JOptionPane.INFORMATION_MESSAGE);
			levelUp();
			experienceNeeded *= EXPERIENCE_NEEDED_MULTIPLIER;
			experience = 0;
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
        _totalHealth += healthAddition;
        _currentHealth = healthAddition;
		resetAttack();
		resetHealth();
	}


	public void setStats(double health, boolean isLead, double attack, String name, int level, int currentExp, int neededExp) {
		setTotalHealth(health);
		isLead(isLead);
		setCurrentAttack(attack);
		setName(name);
		currentLevel = level;
		experience = currentExp;
	    experienceNeeded = neededExp;
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
	    setCurrentHealth(getCurrentHealth() - damage);
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
        setCurrentAttack(getTotalAttack());
        setAttackPower(DEFAULT_ATTACK_POWER);
    }

    public void resetHealth() {
        setCurrentHealth(getTotalHealth());//health = totalHealth;
    }

    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Level: ");
        stringBuilder.append(getCurrentLevel());
        stringBuilder.append("\n");
        stringBuilder.append("Attack: ");
        stringBuilder.append(getCurrentAttack());
        stringBuilder.append("/");
        stringBuilder.append(getTotalAttack());
        stringBuilder.append("\n");
        stringBuilder.append("Status: ");
        if(this.hasFainted()) {
            stringBuilder.append("Fainted");
        }
        else {
            stringBuilder.append("Current Health: ");
            stringBuilder.append(getCurrentHealth());
            stringBuilder.append("/");
            stringBuilder.append(getTotalHealth());
        }
        stringBuilder.append("\n");
        stringBuilder.append("Type: ");
        stringBuilder.append(getElementType());
        stringBuilder.append("\n");
        stringBuilder.append("Experience: ");
        stringBuilder.append(getExperience());
        stringBuilder.append("/");
        stringBuilder.append(getNeededExperience());
        return stringBuilder.toString();
    }




    private ElementType _element;
    private int _maxHP;
    private int _currentHP;

    public Blockamon(int maxHP) {
        maxHitPoints(maxHP);
        currentHitPoints(maxHP);
        _element = ElementType.NORMAL;
    }

    public void element(ElementType element) {
        _element = element;
    }

    public ElementType element() {
        return _element;
    }

    public void maxHitPoints(int maxHP) {
        _maxHP = maxHP;
    }

    public void currentHitPoints(int currentHP) {
        _currentHP = currentHP;
    }

    public void takeDamage(int hp) {
        _currentHP -= hp;
        if(_currentHP < 0) {
            _currentHP = 0;
        }
    }

    public int maxHP() {
        return _maxHP;
    }

    public int currentHitPoints() {
        return _currentHP;
    }

    public void heal(int hp) {
        _currentHP = (_currentHP += hp) - (_currentHP % _maxHP);
    }


}
