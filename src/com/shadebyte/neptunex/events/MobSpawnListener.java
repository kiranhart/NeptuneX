package com.shadebyte.neptunex.events;

import com.shadebyte.neptunex.Core;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class MobSpawnListener implements Listener {

    @EventHandler
    public void onMobSpawnFromSpawner(SpawnerSpawnEvent e) {
        EntityType type = e.getEntity().getType();
        if (Core.getInstance().getConfig().contains("custom-mob-health.mobs." + type.name())) {
            if (Core.getInstance().getConfig().contains("custom-mob-health.enabled")) {
                LivingEntity livingEntity = (LivingEntity) e.getEntity();
                livingEntity.setHealth(Core.getInstance().getConfig().getDouble("custom-mob-health.mobs." + type.name()));
            }
        }
    }
}
