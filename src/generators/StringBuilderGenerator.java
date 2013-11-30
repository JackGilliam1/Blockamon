package generators;

/**
 * Created with IntelliJ IDEA.
 * User: jgilliam
 * Date: 1/6/13
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class StringBuilderGenerator
{
    private static final StringBuilder STRING_BUILDER = new StringBuilder();
    
    public static StringBuilder getStringBuilder() {
        clearStringBuilder();
        return STRING_BUILDER;
    }
    
    private static void clearStringBuilder() {
        STRING_BUILDER.delete(0, STRING_BUILDER.length());
    }
}
