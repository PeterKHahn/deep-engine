package game.map;

import game.entity.DynamicEntity;
import game.entity.Player;
import game.entity.StaticEntity;

import java.util.Collection;

public class GameMap {


    public final Collection<Player> players;
    // TODO change to private, and think of a better way to do this
    public final Collection<StaticEntity> staticEntities;
    public final Collection<DynamicEntity> dynamicEntities;

    public GameMap(Collection<Player> players, Collection<StaticEntity> staticEntities, Collection<DynamicEntity> dynamicEntities) {
        this.players = players;

        this.staticEntities = staticEntities;
        this.dynamicEntities = dynamicEntities;
    }
}
