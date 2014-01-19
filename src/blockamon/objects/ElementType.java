package blockamon.objects;

import blockamon.ExtensionMethods;

import java.util.*;

public enum ElementType {
	BUG(ElementColor.BUG), DARK(ElementColor.DARK), DRAGON(ElementColor.DRAGON), ELECTRIC(ElementColor.ELECTRIC),
    FAIRY(ElementColor.FAIRY), FIGHTING(ElementColor.FIGHTING), FIRE(ElementColor.FIRE), FLYING(ElementColor.FLYING),
    GHOST(ElementColor.GHOST), GRASS(ElementColor.GRASS), GROUND(ElementColor.GROUND), ICE(ElementColor.ICE),
    NORMAL(ElementColor.NORMAL), POISON(ElementColor.POISON), PSYCHIC(ElementColor.PSYCHIC), ROCK(ElementColor.ROCK),
    STEEL(ElementColor.STEEL), WATER(ElementColor.WATER);

    static {
        _weaknesses = new Hashtable<ElementType, ArrayList<ElementType>>();
        _resistances = new Hashtable<ElementType, ArrayList<ElementType>>();
        _negations = new Hashtable<ElementType, ArrayList<ElementType>>();
        setUpDummyElementTypes();
        setUpWeaknesses();
        setUpResistances();
        setUpNegations();
    }

    private ElementColor _elementColor;

    private ElementType(ElementColor elementColor) {
        _elementColor = elementColor;
    }

    public ElementColor getColor() {
        return _elementColor;
    }

    private static Dictionary<ElementType, ArrayList<ElementType>> _weaknesses;
    private static Dictionary<ElementType, ArrayList<ElementType>> _resistances;
    private static Dictionary<ElementType, ArrayList<ElementType>> _negations;

    public boolean isWeakAgainst(ElementType type) {
        ArrayList<ElementType> weaknesses = _weaknesses.get(this);
        return weaknesses.contains(type);
    }

    public boolean resists(ElementType type) {
        ArrayList<ElementType> resistances = _resistances.get(this);
        return resistances.contains(type);
    }

    public boolean negates(ElementType type) {
        ArrayList<ElementType> negation = _negations.get(this);
        return negation.contains(type);
    }

    private static void setUpWeaknesses() {
        addWeaknesses(ElementType.NORMAL, ElementType.FIGHTING);
        addWeaknesses(ElementType.FIGHTING, ElementType.FLYING, ElementType.PSYCHIC);
        addWeaknesses(ElementType.FLYING, ElementType.ROCK, ElementType.ELECTRIC, ElementType.ICE);
        addWeaknesses(ElementType.POISON, ElementType.GROUND, ElementType.PSYCHIC);
        addWeaknesses(ElementType.GROUND, ElementType.WATER, ElementType.GRASS, ElementType.ICE);
        addWeaknesses(ElementType.ROCK, ElementType.FIGHTING, ElementType.GROUND, ElementType.STEEL, ElementType.WATER, ElementType.GRASS);
        addWeaknesses(ElementType.BUG, ElementType.FLYING, ElementType.ROCK, ElementType.FIRE);
        addWeaknesses(ElementType.GHOST, ElementType.GHOST, ElementType.DARK);
        addWeaknesses(ElementType.STEEL, ElementType.FIGHTING, ElementType.GROUND, ElementType.FIRE);
        addWeaknesses(ElementType.FIRE, ElementType.GROUND, ElementType.ROCK, ElementType.WATER);
        addWeaknesses(ElementType.WATER, ElementType.GRASS, ElementType.ELECTRIC);
        addWeaknesses(ElementType.GRASS, ElementType.FLYING, ElementType.POISON, ElementType.BUG, ElementType.FIRE, ElementType.ICE);
        addWeaknesses(ElementType.ELECTRIC, ElementType.GROUND);
        addWeaknesses(ElementType.PSYCHIC, ElementType.BUG, ElementType.GHOST, ElementType.DARK);
        addWeaknesses(ElementType.ICE, ElementType.FIGHTING, ElementType.ROCK, ElementType.STEEL, ElementType.FIRE);
        addWeaknesses(ElementType.DRAGON, ElementType.ICE, ElementType.DRAGON, ElementType.FAIRY);
        addWeaknesses(ElementType.DARK, ElementType.FIGHTING, ElementType.BUG, ElementType.FAIRY);
        addWeaknesses(ElementType.FAIRY, ElementType.POISON, ElementType.STEEL);
    }

    private static void addWeaknesses(ElementType element, ElementType... weaknesses) {
        _weaknesses.put(element, ExtensionMethods.toArrayList(weaknesses));
    }

    private static void setUpResistances() {
        addResistance(ElementType.FIGHTING, ElementType.BUG,  ElementType.DARK, ElementType.ROCK);
        addResistance(ElementType.FLYING, ElementType.BUG, ElementType.FIGHTING, ElementType.GRASS);
        addResistance(ElementType.POISON, ElementType.BUG, ElementType.FAIRY, ElementType.FIGHTING, ElementType.GRASS, ElementType.POISON);
        addResistance(ElementType.GROUND, ElementType.POISON, ElementType.ROCK);
        addResistance(ElementType.ROCK, ElementType.FIRE, ElementType.FLYING, ElementType.NORMAL, ElementType.POISON);
        addResistance(ElementType.BUG, ElementType.FIGHTING, ElementType.GRASS, ElementType.GROUND);
        addResistance(ElementType.GHOST, ElementType.BUG, ElementType.POISON);
        addResistance(ElementType.STEEL, ElementType.BUG, ElementType.DARK, ElementType.DRAGON,
                ElementType.FAIRY, ElementType.FLYING, ElementType.GHOST, ElementType.GRASS,
                ElementType.ICE, ElementType.NORMAL, ElementType.PSYCHIC, ElementType.ROCK, ElementType.STEEL);
        addResistance(ElementType.FIRE, ElementType.BUG, ElementType.FIRE, ElementType.GRASS, ElementType.ICE, ElementType.STEEL);
        addResistance(ElementType.WATER, ElementType.FIRE, ElementType.ICE, ElementType.STEEL, ElementType.WATER);
        addResistance(ElementType.GRASS, ElementType.ELECTRIC, ElementType.GRASS, ElementType.GROUND, ElementType.WATER);
        addResistance(ElementType.ELECTRIC, ElementType.ELECTRIC, ElementType.FLYING, ElementType.STEEL);
        addResistance(ElementType.PSYCHIC, ElementType.FIGHTING, ElementType.PSYCHIC);
        addResistance(ElementType.ICE, ElementType.ICE);
        addResistance(ElementType.DRAGON, ElementType.ELECTRIC, ElementType.FIRE, ElementType.GRASS, ElementType.WATER);
        addResistance(ElementType.DARK, ElementType.DARK, ElementType.GHOST);
        addResistance(ElementType.FAIRY, ElementType.FIGHTING, ElementType.BUG, ElementType.DARK);
    }

    private static void addResistance(ElementType element, ElementType... resistances) {
        _resistances.put(element, ExtensionMethods.toArrayList(resistances));
    }

    private static void setUpNegations(){
        addNegation(ElementType.NORMAL, ElementType.GHOST);
        addNegation(ElementType.FLYING, ElementType.GROUND);
        addNegation(ElementType.GROUND, ElementType.ELECTRIC);
        addNegation(ElementType.GHOST, ElementType.NORMAL, ElementType.FIGHTING);
        addNegation(ElementType.STEEL, ElementType.POISON);
        addNegation(ElementType.DARK, ElementType.PSYCHIC);
        addNegation(ElementType.FAIRY, ElementType.DRAGON);
    }

    private static void addNegation(ElementType element, ElementType... negations) {
        _negations.put(element, ExtensionMethods.toArrayList(negations));
    }

    private static void setUpDummyElementTypes() {
        ArrayList<ElementType> dummyList = new ArrayList<ElementType>();
        ElementType[] elementTypes = ElementType.values();
        for(ElementType elementType : elementTypes) {
            _negations.put(elementType, dummyList);
            _weaknesses.put(elementType, dummyList);
            _resistances.put(elementType, dummyList);
        }
    }







    private static final ElementType[] _TYPES = ElementType.values();
    private static final int _NUMBEROFTYPES = _TYPES.length;
    private static final Random TYPE_RANDOMIZER = new Random();

    public boolean negatesAttackAgainst(ElementType attackingType) {
        return false;
    }

    public static ElementType getTypeAt(int index) {
        return _TYPES[index];
    }
    public static int getNumOfTypes() {
        return _NUMBEROFTYPES;
    }
    public static ElementType getRandomType() {
        final int index = TYPE_RANDOMIZER.nextInt(getNumOfTypes());
        return getTypeAt(index);
    }
}
