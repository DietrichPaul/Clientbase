package de.dietrichpaul.clientbase.properties.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.features.commands.argument.EntityTypeArgumentType;
import de.dietrichpaul.clientbase.properties.Property;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanRBTreeMap;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TargetProperty extends Property {

    private static List<EntityType<?>> notAttackable = Arrays.asList(
            EntityType.AREA_EFFECT_CLOUD,
            EntityType.ARROW,
            EntityType.EGG,
            EntityType.ENDER_PEARL,
            EntityType.EXPERIENCE_BOTTLE,
            EntityType.EXPERIENCE_ORB,
            EntityType.FALLING_BLOCK,
            EntityType.FIREWORK_ROCKET,
            EntityType.FISHING_BOBBER,
            EntityType.ITEM,
            EntityType.LIGHTNING_BOLT,
            EntityType.LLAMA_SPIT,
            EntityType.POTION,
            EntityType.TRIDENT,
            EntityType.SPECTRAL_ARROW
    );

    private Object2BooleanMap<EntityType<?>> entityTypes = new Object2BooleanRBTreeMap<>(Comparator.comparing(entityType
            -> I18n.translate(entityType.getTranslationKey())));

    public TargetProperty(String name, boolean onlyAttackable, EntityType<?>... defaultEnabled) {
        super(name);
        Registries.ENTITY_TYPE.forEach(entityType -> {
            if (!onlyAttackable || !notAttackable.contains(entityType)) {
                entityTypes.put(entityType, ArrayUtils.contains(defaultEnabled, entityType));
            }
        });
    }

    public <T extends Entity> boolean filter(T entity) {
        return entityTypes.getBoolean(entity.getType());
    }

    @Override
    public JsonElement serialize() {
        JsonObject object = new JsonObject();
        entityTypes.object2BooleanEntrySet().forEach(type
                -> object.addProperty(type.getKey().getTranslationKey(), type.getBooleanValue()));
        return object;
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element == null) return;
        JsonObject object = element.getAsJsonObject();
        for (EntityType<?> next : entityTypes.keySet()) {
            entityTypes.removeBoolean(next);
            entityTypes.put(next, object.get(next.getTranslationKey()).getAsBoolean());
        }
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                literal("add")
                        .then(
                                argument("type", EntityTypeArgumentType.entityType(e -> entityTypes.containsKey(e) && !entityTypes.getBoolean(e)))
                                        .executes(context -> {
                                            EntityType<?> type = EntityTypeArgumentType.getEntityType(context, "type");
                                            entityTypes.removeBoolean(type);
                                            entityTypes.put(type, true);
                                            // bitte components machen paul
                                            cb.sendChatMessage(Text.of("Added " + I18n.translate(type.getTranslationKey()) + " to " + getName()));
                                            return 1;
                                        })
                        )
        ).then(
                literal("remove")
                        .then(
                                argument("type", EntityTypeArgumentType.entityType(e -> entityTypes.containsKey(e) && entityTypes.getBoolean(e)))
                                        .executes(context -> {
                                            EntityType<?> type = EntityTypeArgumentType.getEntityType(context, "type");
                                            entityTypes.removeBoolean(type);
                                            entityTypes.put(type, false);
                                            System.out.println(entityTypes.getBoolean(type));
                                            // bitte components machen paul
                                            cb.sendChatMessage(Text.of("Removed " + I18n.translate(type.getTranslationKey()) + " from " + getName()));
                                            return 1;
                                        })
                        )
        );
    }
}
