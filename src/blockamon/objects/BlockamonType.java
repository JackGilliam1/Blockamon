package blockamon.objects;

import java.awt.*;
import java.util.*;

public enum BlockamonType {
	BUG(168, 184, 32), DARK(112, 88, 72), DRAGON(112, 56, 248), ELECTRIC(248, 208, 48), 
    FIGHTING(192, 48, 40), FIRE(240, 128, 48), FLYING(168, 144, 240), GHOST(112, 88, 152),
	GRASS(0, 200, 0), GROUND(227, 199, 106), ICE(152, 216, 216), NORMAL(152, 149, 116), 
    POISON(160, 64, 160), PSYCHIC(248, 88, 136), ROCK(184, 160, 56), STEEL(184, 184, 208), 
    WATER(0, 0, 200);
    
    private static final BlockamonType[] _TYPES = BlockamonType.values();
    private static final int _NUMBEROFTYPES = _TYPES.length;
    private static final Random TYPE_RANDOMIZER = new Random();
    
    private static final Map<BlockamonType, ArrayList<BlockamonType>> _weaknesses = new HashMap<BlockamonType, ArrayList<BlockamonType>>();
    private static final Map<BlockamonType, ArrayList<BlockamonType>> _resistances = new HashMap<BlockamonType, ArrayList<BlockamonType>>();
    
    /*
     * The left is negated by the right, left = attacker, right = defender
     */
    private static final Map<BlockamonType, BlockamonType> _negations = new HashMap<BlockamonType, BlockamonType>();
    
	private ArrayList<BlockamonType> resistance;
	private ArrayList<BlockamonType> negated;
    private Color _color;
    
	BlockamonType() {
        this(0, 0, 0);
	}
    
    BlockamonType(int red, int green, int blue) {
        _color = new Color(red, green, blue);
    }
    
    public Color getColor() {
        return _color;
    }
    
    public boolean isWeakAgainst(BlockamonType attackingType) {
        final ArrayList<BlockamonType> weaknesses = getWeaknesses(this);
        boolean weakAgainst = false;
        if(weaknesses != null) {
           weakAgainst = weaknesses.contains(attackingType);
        }
        return weakAgainst;
    }
    
    public boolean isResistantAgainst(BlockamonType attackingType) {
        ArrayList<BlockamonType> resistances = getResistances(this);
        boolean resistantAgainst = false;
        if(resistances != null) {
            resistantAgainst = resistances.contains(attackingType);
        }
        return resistantAgainst;
    }
    
    public boolean negatesAttackAgainst(BlockamonType attackingType) {
        BlockamonType defendType = getNegation(attackingType);
        boolean negatesAttack = defendType != null && defendType.equals(this);
        return negatesAttack;
    }
    
    private ArrayList<BlockamonType> getWeaknesses(BlockamonType attackingType) {
        if(_weaknesses == null) {
            setWeaknesses();
        }
        return _weaknesses.get(attackingType);
    }
    private void setWeaknesses() {
        ArrayList<BlockamonType> bugWeaknesses = toArrayList(BlockamonType.FIRE, BlockamonType.ROCK, BlockamonType.FLYING);
        ArrayList<BlockamonType> darkWeaknesses = toArrayList(BlockamonType.BUG, BlockamonType.FIGHTING);
        ArrayList<BlockamonType> dragonWeaknesses = toArrayList(BlockamonType.DRAGON, BlockamonType.ICE);
        ArrayList<BlockamonType> electricWeaknesses = toArrayList(BlockamonType.GROUND);
        ArrayList<BlockamonType> fightingWeaknesses = toArrayList(BlockamonType.FIGHTING, BlockamonType.PSYCHIC);
        ArrayList<BlockamonType> fireWeaknesses = toArrayList(BlockamonType.GROUND, BlockamonType.ROCK, BlockamonType.WATER);
        ArrayList<BlockamonType> flyingWeaknesses = toArrayList(BlockamonType.ELECTRIC, BlockamonType.ICE, BlockamonType.ROCK);
        ArrayList<BlockamonType> ghostWeaknesses = toArrayList(BlockamonType.GHOST, BlockamonType.DARK);
        ArrayList<BlockamonType> grassWeaknesses = toArrayList(BlockamonType.BUG, BlockamonType.FIRE, BlockamonType.FLYING, BlockamonType.ICE, BlockamonType.POISON);
        ArrayList<BlockamonType> groundWeaknesses = toArrayList(BlockamonType.GRASS, BlockamonType.ICE, BlockamonType.WATER);
        ArrayList<BlockamonType> iceWeaknesses = toArrayList(BlockamonType.FIGHTING, BlockamonType.FIRE, BlockamonType.ROCK, BlockamonType.STEEL);
        ArrayList<BlockamonType> normalWeaknesses = toArrayList(BlockamonType.FIGHTING);
        ArrayList<BlockamonType> poisonWeaknesses = toArrayList(BlockamonType.GROUND, BlockamonType.PSYCHIC);
        ArrayList<BlockamonType> psychicWeaknesses = toArrayList(BlockamonType.BUG, BlockamonType.DARK, BlockamonType.GHOST);
        ArrayList<BlockamonType> rockWeaknesses = toArrayList(BlockamonType.FIGHTING, BlockamonType.GRASS, BlockamonType.GROUND, BlockamonType.STEEL, BlockamonType.WATER);
        ArrayList<BlockamonType> steelWeaknesses = toArrayList(BlockamonType.FIGHTING, BlockamonType.FIRE, BlockamonType.GROUND);
        ArrayList<BlockamonType> waterWeaknesses = toArrayList(BlockamonType.ELECTRIC, BlockamonType.GRASS);
        _weaknesses.put(BlockamonType.BUG, bugWeaknesses);
        _weaknesses.put(BlockamonType.DARK, darkWeaknesses);
        _weaknesses.put(BlockamonType.DRAGON, dragonWeaknesses);
        _weaknesses.put(BlockamonType.ELECTRIC, electricWeaknesses);
        _weaknesses.put(BlockamonType.FIGHTING, fightingWeaknesses);
        _weaknesses.put(BlockamonType.FIRE, fireWeaknesses);
        _weaknesses.put(BlockamonType.FLYING, flyingWeaknesses);
        _weaknesses.put(BlockamonType.GHOST, ghostWeaknesses);
        _weaknesses.put(BlockamonType.GRASS, grassWeaknesses);
        _weaknesses.put(BlockamonType.GROUND, groundWeaknesses);
        _weaknesses.put(BlockamonType.ICE, iceWeaknesses);
        _weaknesses.put(BlockamonType.NORMAL, normalWeaknesses);
        _weaknesses.put(BlockamonType.POISON, poisonWeaknesses);
        _weaknesses.put(BlockamonType.PSYCHIC, psychicWeaknesses);
        _weaknesses.put(BlockamonType.ROCK, rockWeaknesses);
        _weaknesses.put(BlockamonType.STEEL, steelWeaknesses);
        _weaknesses.put(BlockamonType.WATER, waterWeaknesses);
    }
    
    private ArrayList<BlockamonType> getResistances(BlockamonType attackingType) {
        if(_resistances == null)
        {
            setResistances();
        }
        return _resistances.get(attackingType);
    }
    private void setResistances() {
        ArrayList<BlockamonType> bugResistances = toArrayList(BlockamonType.FIGHTING, BlockamonType.GRASS, BlockamonType.GROUND);
        ArrayList<BlockamonType> darkResistances = toArrayList(BlockamonType.DARK, BlockamonType.GHOST);
        ArrayList<BlockamonType> dragonResistances = toArrayList(BlockamonType.ELECTRIC, BlockamonType.FIRE, BlockamonType.GRASS, BlockamonType.WATER);
        ArrayList<BlockamonType> electricResistances = toArrayList(BlockamonType.ELECTRIC, BlockamonType.FLYING, BlockamonType.STEEL);
        ArrayList<BlockamonType> fightingResistances = toArrayList(BlockamonType.BUG,  BlockamonType.DARK, BlockamonType.ROCK);
        ArrayList<BlockamonType> fireResistances = toArrayList(BlockamonType.BUG, BlockamonType.FIRE, BlockamonType.GRASS, BlockamonType.ICE, BlockamonType.STEEL);
        ArrayList<BlockamonType> flyingResistances = toArrayList(BlockamonType.BUG, BlockamonType.FIGHTING, BlockamonType.GRASS);
        ArrayList<BlockamonType> ghostResistances = toArrayList(BlockamonType.BUG, BlockamonType.POISON);
        ArrayList<BlockamonType> grassResistances = toArrayList(BlockamonType.ELECTRIC, BlockamonType.GRASS, BlockamonType.GROUND, BlockamonType.WATER);
        ArrayList<BlockamonType> groundResistances = toArrayList(BlockamonType.POISON, BlockamonType.ROCK);
        ArrayList<BlockamonType> iceResistances = toArrayList(BlockamonType.ICE);
        ArrayList<BlockamonType> normalResistances = toArrayList();
        ArrayList<BlockamonType> poisonResistances = toArrayList(BlockamonType.BUG, BlockamonType.FIGHTING, BlockamonType.GRASS, BlockamonType.POISON);
        ArrayList<BlockamonType> psychicResistances = toArrayList(BlockamonType.FIGHTING, BlockamonType.PSYCHIC);
        ArrayList<BlockamonType> rockResistances = toArrayList(BlockamonType.FIRE, BlockamonType.FLYING, BlockamonType.NORMAL, BlockamonType.POISON);
        ArrayList<BlockamonType> steelResistances = toArrayList(BlockamonType.BUG, BlockamonType.DARK, BlockamonType.DRAGON, BlockamonType.FLYING, BlockamonType.GHOST, BlockamonType.GRASS, BlockamonType.ICE, BlockamonType.NORMAL, BlockamonType.PSYCHIC, BlockamonType.ROCK, BlockamonType.STEEL);
        ArrayList<BlockamonType> waterResistances = toArrayList(BlockamonType.FIRE, BlockamonType.ICE, BlockamonType.STEEL, BlockamonType.WATER);
        _resistances.put(BlockamonType.BUG, bugResistances);
        _resistances.put(BlockamonType.DARK, darkResistances);
        _resistances.put(BlockamonType.DRAGON, dragonResistances);
        _resistances.put(BlockamonType.ELECTRIC, electricResistances);
        _resistances.put(BlockamonType.FIGHTING, fightingResistances);
        _resistances.put(BlockamonType.FIRE, fireResistances);
        _resistances.put(BlockamonType.FLYING, flyingResistances);
        _resistances.put(BlockamonType.GHOST, ghostResistances);
        _resistances.put(BlockamonType.GRASS, grassResistances);
        _resistances.put(BlockamonType.GROUND, groundResistances);
        _resistances.put(BlockamonType.ICE, iceResistances);
        _resistances.put(BlockamonType.NORMAL, normalResistances);
        _resistances.put(BlockamonType.POISON, poisonResistances);
        _resistances.put(BlockamonType.PSYCHIC, psychicResistances);
        _resistances.put(BlockamonType.ROCK, rockResistances);
        _resistances.put(BlockamonType.STEEL, steelResistances);
        _resistances.put(BlockamonType.WATER, waterResistances);
        
    }
    
    private BlockamonType getNegation(BlockamonType attackingType) {
        if(_negations == null) {
            setNegations();
        }
        return _negations.get(attackingType);
    }
    private void setNegations(){
        _negations.put(BlockamonType.PSYCHIC, BlockamonType.DARK);
        _negations.put(BlockamonType.GROUND, BlockamonType.FLYING);
        _negations.put(BlockamonType.NORMAL, BlockamonType.GHOST);
        _negations.put(BlockamonType.FIGHTING, BlockamonType.GHOST);
        _negations.put(BlockamonType.ELECTRIC, BlockamonType.GROUND);
        _negations.put(BlockamonType.GHOST, BlockamonType.NORMAL);
        _negations.put(BlockamonType.POISON, BlockamonType.STEEL);
    }
    
    private <T> ArrayList<T> toArrayList(T... values) {
        ArrayList<T> savedValues = new ArrayList<T>();
        for(T value : values)
        {
            savedValues.add(value);
        }
        return savedValues;
    }
    private ArrayList<BlockamonType> weaknesses;
	public void setValues() {
		switch(this)
		{
            case BUG:
                this.setWeaknesses(FIRE, FLYING, ROCK);
                this.setResistance(FIGHTING, GRASS, GROUND);
                this.setNegated();
                break;
            case DARK:
                this.setWeaknesses(BUG, FIGHTING);
                this.setResistance(DARK, GHOST);
                this.setNegated(PSYCHIC);
                break;
            case DRAGON:
                this.setWeaknesses(DRAGON, ICE);
                this.setResistance(ELECTRIC, FIRE, GRASS, WATER);
                this.setNegated();
                break;
            case ELECTRIC:
                this.setWeaknesses(GROUND);
                this.setResistance(ELECTRIC, FLYING, STEEL);
                this.setNegated();
                break;
            case FIGHTING:
                this.setWeaknesses(FLYING, PSYCHIC);
                this.setResistance(BUG, DARK, ROCK);
                this.setNegated();
                break;
            case FIRE:
                this.setWeaknesses(GROUND, ROCK, WATER);
                this.setResistance(BUG, FIRE, GRASS, ICE, STEEL);
                this.setNegated();
                break;
            case FLYING:
                this.setWeaknesses(ELECTRIC, ICE, ROCK);
                this.setResistance(BUG, FIGHTING, GRASS);
                this.setNegated(GROUND);
                break;
            case GHOST:
                this.setWeaknesses(GHOST, DARK);
                this.setResistance(BUG, POISON);
                this.setNegated(NORMAL, FIGHTING);
                this.setNegated();
                break;
            case GRASS:
                this.setWeaknesses(BUG, FIRE, FLYING, ICE, POISON);
                this.setResistance(ELECTRIC, GRASS, GROUND, WATER);
                this.setNegated();
                break;
            case GROUND:
                this.setWeaknesses(GRASS, ICE, WATER);
                this.setResistance(POISON, ROCK);
                this.setNegated(ELECTRIC);
                break;
            case ICE:
                this.setWeaknesses(FIGHTING, FIRE, ROCK, STEEL);
                this.setResistance(ICE);
                this.setNegated();
                break;
            case NORMAL:
                this.setWeaknesses(FIGHTING);
                this.setResistance();
                this.setNegated(GHOST);
                break;
            case POISON:
                this.setWeaknesses(GROUND, PSYCHIC);
                this.setResistance(BUG, GRASS, FIGHTING, POISON);
                this.setNegated();
                break;
            case PSYCHIC:
                this.setWeaknesses(BUG, DARK, GHOST);
                this.setResistance(FIGHTING, PSYCHIC);
                this.setNegated();
                break;
            case ROCK:
                this.setWeaknesses(FIGHTING, GRASS, GROUND, STEEL, WATER);
                this.setResistance(FIRE, FLYING, NORMAL, POISON);
                this.setNegated();
                break;
            case STEEL:
                this.setWeaknesses(FIGHTING, FIRE, GROUND);
                this.setResistance(BUG, DARK, DRAGON, FLYING, GHOST, GRASS, ICE, NORMAL, PSYCHIC, ROCK, STEEL);
                this.setNegated(POISON);
                break;
            case WATER:
                this.setWeaknesses(ELECTRIC, GRASS);
                this.setResistance(WATER, STEEL, ICE, FIRE);
                this.setNegated();
                break;
		}
	}
	public void setNegated(BlockamonType...negate) {
		negated = new ArrayList<BlockamonType>();
		for(BlockamonType neg: negate)
		{
			negated.add(neg);
		}
	}
	public void setResistance(BlockamonType... resist) {
		resistance = new ArrayList<BlockamonType>();
		for(BlockamonType res: resist)
		{
			resistance.add(res);
		}
	}
	public void setWeaknesses(BlockamonType... weakness) {
		weaknesses = new ArrayList<BlockamonType>();
		for(BlockamonType weak: weakness)
		{
			weaknesses.add(weak);
		}
	}
    public static BlockamonType getTypeAt(int index) {
        return _TYPES[index];
    }
    public static int getNumOfTypes() {
        return _NUMBEROFTYPES;
    }
    public static BlockamonType getRandomType() {
        final int index = TYPE_RANDOMIZER.nextInt(getNumOfTypes());
        return getTypeAt(index);
    }
}
