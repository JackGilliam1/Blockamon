package generators;

import blockamon.objects.WildBlockamon;
import blockamon.objects.BlockamonType;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/5/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockamonGenerator
{
    public static WildBlockamon generateRandomBlockamon()
    {
        return new WildBlockamon(BlockamonType.getRandomType());
    }
    
    public static WildBlockamon generateBlockamonOfType(BlockamonType type)
    {
        return new WildBlockamon(type);
    }
}
