package net.momirealms.customnameplates.api.manager;

import net.momirealms.customnameplates.api.mechanic.nameplate.CachedNameplate;
import net.momirealms.customnameplates.api.mechanic.nameplate.Nameplate;
import net.momirealms.customnameplates.api.mechanic.nameplate.TagMode;
import net.momirealms.customnameplates.api.mechanic.tag.NameplatePlayer;
import net.momirealms.customnameplates.common.team.TeamColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NameplateManager {

    /**
     * Get the default nameplate
     */
    @NotNull
    String getDefaultNameplate();

    /**
     * Put an entity's ID to map
     * This map is used for quickly getting the entity instance
     * Removal from the map is necessary when the entity is invalid
     * Otherwise it would cause memory leak
     *
     * @param entityID entityID
     * @param entity entity
     */
    boolean putEntityIDToMap(int entityID, Entity entity);

    /**
     * Remove the entity from map
     *
     * @param entityID entityID
     * @return the removed entity
     */
    @Nullable
    Entity removeEntityIDFromMap(int entityID);

    /**
     * Nameplates are cached in memory so they would not be frequently updated
     * The update rate is decided by "refresh-frequency" in nameplate.yml
     *
     * @param uuid player uuid
     * @param nameplate cached nameplate
     */
    boolean putCachedNameplateToMap(UUID uuid, CachedNameplate nameplate);

    /**
     * Remove CachedNameplate from map
     *
     * @param uuid player uuid
     * @return removed CachedNameplate
     */
    @Nullable
    CachedNameplate removeCachedNameplateFromMap(UUID uuid);

    /**
     * Get player by entityID from the cache
     *
     * @param id entityID
     * @return player
     */
    @Nullable
    Player getPlayerByEntityID(int id);

    /**
     * Get entity by entityID from the cache
     *
     * @param id entityID
     * @return entity
     */
    @Nullable
    Entity getEntityByEntityID(int id);

    /**
     * Update a player's cached nameplate
     * The nameplate is affected by "prefix" and "suffix" option
     *
     * @param player player
     * @return if the nameplate is updated
     */
    boolean updateCachedNameplate(Player player);

    /**
     * Update a player's cached nameplate
     * The nameplate is affected by "prefix" and "suffix" option
     *
     * @param player player
     * @return if the nameplate is updated
     */
    boolean updateCachedNameplate(Player player, Nameplate nameplate);

    /**
     * This should not be null when player's data is loaded (async process)
     *
     * @param player player
     * @return cached nameplate
     */
    @Nullable
    CachedNameplate getCacheNameplate(Player player);

    /**
     * Create a name tag for a player, the tag type is decided by the mode in nameplate.yml
     *
     * @param player player
     */
    void createNameTag(Player player);

    void putNameplatePlayerToMap(NameplatePlayer player);

    NameplatePlayer removeNameplatePlayerFromMap(UUID uuid);

    String getNameplatePrefix(Player player);

    String getNameplateSuffix(Player player);

    String getFullNameTag(Player player);

    boolean registerNameplate(String key, Nameplate nameplate);

    void unEquipNameplate(Player player);

    boolean unregisterNameplate(String key);

    boolean isProxyMode();

    TagMode getTagMode();

    Nameplate getNameplate(String key);

    Collection<Nameplate> getNameplates();

    boolean containsNameplate(String key);

    List<String> getAvailableNameplates(Player player);

    /**
     * If player has permission for a certain nameplate
     */
    boolean hasNameplate(Player player, String nameplate);

    /**
     * Return false if nameplate doesn't exist
     */
    boolean equipNameplate(Player player, String nameplate);

    TeamColor getTeamColor(Player player);

    TeamTagManager getTeamTagManager();

    UnlimitedTagManager getUnlimitedTagManager();
}
