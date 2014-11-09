package blockamon.generators;

import blockamon.objects.Blockamon;
import blockamon.objects.ElementType;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/5/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlockamonGenerator
{
    public static Blockamon generateRandomBlockamon()
    {
        return new Blockamon(ElementType.getRandomType());
    }

    public static Blockamon generateBlockamonOfType(ElementType type)
    {
        return new Blockamon(type);
    }
}
