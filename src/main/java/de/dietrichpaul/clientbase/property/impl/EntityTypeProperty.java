package de.dietrichpaul.clientbase.property.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.feature.command.argument.EntityTypeArgumentType;
import de.dietrichpaul.clientbase.feature.gui.api.*;
import de.dietrichpaul.clientbase.feature.gui.api.panel.BoxPanel;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanRBTreeMap;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EntityTypeProperty extends Property {

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

    public EntityTypeProperty(String name, boolean onlyAttackable, EntityType<?>... defaultEnabled) {
        super(name);
        Registries.ENTITY_TYPE.forEach(entityType -> {
            if (!onlyAttackable || !notAttackable.contains(entityType)) {
                entityTypes.put(entityType, ArrayUtils.contains(defaultEnabled, entityType));
            }
        });
    }

    /*
    Paul das hast du echt schlecht gemacht. Dafür brauche ich eine ComboBox, die andere Kompenenten überlappt.
    Bitte schnellstmöglich den Workaround entsorgen!!!! Das ist der schlechteste Workaround, den ich je geschrieben habe.
    Schande über mein Haupt. Wenn es bei dieser Lösung bleibt: BOXPANEL MUSS DIE HÖHE BEGRENZEN KÖNNEN!!!!!!
     */
    @Override
    public Component getClickGuiComponent() {
        Expandable selectionBox = new Expandable(new Button(Text.of(getName())));
        selectionBox.setOrientation(-1);
        selectionBox.getHeader().setBackground(0);
        selectionBox.getHeader().setMargin(0, 0);
        selectionBox.getHeader().addListener(new ActionListener() {
            @Override
            public void mouseClicked(float mouseX, float mouseY, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                    if (selectionBox.isExpanded()) selectionBox.collapse();
                    else selectionBox.expand();
                }
            }
        });
        selectionBox.setBackground(0xff3b3b3b);
        BoxPanel content = new BoxPanel();
        content.setAxis(BoxPanel.Axis.Y);
        content.setGap(0);
        content.setMargin(0, 0);
        content.setOrientation(0);

        for (Object2BooleanMap.Entry<EntityType<?>> entry : entityTypes.object2BooleanEntrySet()) {
            Checkbox checkbox = new Checkbox(Text.translatable(entry.getKey().getTranslationKey()));
            checkbox.getBox().setStateSupplier(() -> entityTypes.getBoolean(entry.getKey()));
            checkbox.addListener(new ActionListener() {
                @Override
                public void mouseClicked(float mouseX, float mouseY, int button) {
                    if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                        entityTypes.put(entry.getKey(), !entityTypes.removeBoolean(entry.getKey()));
                    }
                }
            });
            content.addComponent(checkbox);
        }

        selectionBox.setContent(content);
        return selectionBox;
    }

    public <T extends Entity> boolean filter(T entity) {
        return entityTypes.getBoolean(entity.getType());
    }

    @Override
    public JsonElement serialize() {
        JsonArray array = new JsonArray();
        entityTypes.object2BooleanEntrySet().stream()
                .filter(Object2BooleanMap.Entry::getBooleanValue)
                .forEach(type -> array.add(Registries.ENTITY_TYPE.getId(type.getKey()).toString()));
        return array;
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element == null) return;
        JsonArray array = element.getAsJsonArray();
        for (JsonElement jsonElement : array) {
            EntityType<?> next = Registries.ENTITY_TYPE.get(new Identifier(jsonElement.getAsString()));
            entityTypes.removeBoolean(next);
            entityTypes.put(next, true);
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
                                            reportChanges();
                                            ChatUtil.sendChatMessage(Text.of("Added " + I18n.translate(type.getTranslationKey()) + " to " + getName()));
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
                                            reportChanges();
                                            ChatUtil.sendChatMessage(Text.of("Removed " + I18n.translate(type.getTranslationKey()) + " from " + getName()));
                                            return 1;
                                        })
                        )
        );
    }
}
