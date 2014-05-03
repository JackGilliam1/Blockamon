package blockamon.input;

import blockamon.items.Item;

/**
 * User: Jack's Computer
 * Date: 5/3/2014
 * Time: 1:33 PM
 */
public class ActionObject {
    private Action _action;

    private Item _item;
    private boolean _canSetItem;

    private ActionObject(Action action) {
        _action = action;
        _canSetItem = action.equals(Action.ADDITEM);
    }

    public Item getItem() {
        return _item;
    }
    public void setItem(Item item) {
        if(_canSetItem) {
            _item = item;
        }
    }

    public boolean isItemAction() {
        return _action == Action.ADDITEM;
    }
    public boolean isHealAction() {
        return _action == Action.HEAL;
    }
}
