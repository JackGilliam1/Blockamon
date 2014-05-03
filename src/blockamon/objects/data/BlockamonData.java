package blockamon.objects.data;

import blockamon.objects.ElementType;

/**
 * User: Jack's Computer
 * Date: 5/3/2014
 * Time: 10:48 AM
 */
public class BlockamonData {
    public ElementType type;
    public int maxHP;
    public int currentHP;
    public int currentLevel;
    public int experience;
    public int neededExperience;
    public int totalAttack;
    public boolean isLead = false;

    public BlockamonData() {
        type = ElementType.NORMAL;
        maxHP = 15;
        currentHP = 5;
        currentLevel = 1;
        experience = 0;
        neededExperience = 200;
        totalAttack = 10;
    }
}
