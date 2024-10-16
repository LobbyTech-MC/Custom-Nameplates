/*
 *  Copyright (C) <2024> <XiaoMoMi>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.momirealms.customnameplates.bukkit.compatibility;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.expansion.Relational;
import net.momirealms.customnameplates.api.CNPlayer;
import net.momirealms.customnameplates.api.CustomNameplates;
import net.momirealms.customnameplates.api.feature.TimeStampData;
import net.momirealms.customnameplates.api.placeholder.Placeholder;
import net.momirealms.customnameplates.api.placeholder.PlayerPlaceholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class NameplatesExpansion extends PlaceholderExpansion implements Relational {

    private final CustomNameplates plugin;

    public NameplatesExpansion(CustomNameplates plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "nameplates";
    }

    @Override
    public @NotNull String getAuthor() {
        return "XiaoMoMi";
    }

    @Override
    public @NotNull String getVersion() {
        return "3.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        Placeholder placeholder = plugin.getPlaceholderManager().getRegisteredPlaceholder("%np_" + params + "%");
        if (placeholder instanceof PlayerPlaceholder playerPlaceholder) {
            CNPlayer cnPlayer = player == null ? null : plugin.getPlayer(player.getUniqueId());
            if (placeholder.children().isEmpty()) {
                return playerPlaceholder.request(cnPlayer);
            }
            if (cnPlayer != null) {
                cnPlayer.forceUpdatePlaceholders(Set.of(placeholder), Collections.emptySet());
                return cnPlayer.getData(placeholder);
            } else {
                try {
                    return playerPlaceholder.request(null);
                } catch (NullPointerException e) {
                    plugin.getPluginLogger().warn("%nameplates_" + params + "% contains a placeholder that requires a player as the parameter");
                }
            }
        }
        return null;
    }

    @Override
    public String onPlaceholderRequest(Player p1, Player p2, String params) {
        CNPlayer cnPlayer1 = p1 == null ? null : plugin.getPlayer(p1.getUniqueId());
        CNPlayer cnPlayer2 = p2 == null ? null : plugin.getPlayer(p2.getUniqueId());
        if (p1 == null || p2 == null) {
            return null;
        }
        Placeholder placeholder = plugin.getPlaceholderManager().getRegisteredPlaceholder("%rel_np_" + params + "%");
        if (placeholder != null) {
            cnPlayer1.forceUpdatePlaceholders(Set.of(placeholder), Set.of(cnPlayer2));
            return Optional.ofNullable(cnPlayer1.getRelationalValue(placeholder, cnPlayer2)).map(TimeStampData::data).orElse(placeholder.id());
        }
        return null;
    }
}
