package blockamon.controllers;

import blockamon.World;

/**
 * User: Jack's Computer
 * Date: 4/20/2014
 * Time: 12:11 PM
 */
public class BlockamonGame {

    private World _world;

    public BlockamonGame() {
        _world = new World();
    }

    public void start() {
        _world.run();
    }
}
