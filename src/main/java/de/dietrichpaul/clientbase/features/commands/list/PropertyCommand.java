package de.dietrichpaul.clientbase.features.commands.list;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.features.commands.Command;
import de.dietrichpaul.clientbase.features.hacks.Hack;
import de.dietrichpaul.clientbase.properties.Property;
import de.dietrichpaul.clientbase.properties.PropertyGroup;
import net.minecraft.command.CommandSource;

import java.util.Map;

public class PropertyCommand extends Command {

    public PropertyCommand() {
        super("property");
    }

    public void buildGroup(PropertyGroup group, LiteralArgumentBuilder<CommandSource> base, String name) {
        if (!group.hasProperties())
            return;
        LiteralArgumentBuilder<CommandSource> literal = literal(name);

        for (Map.Entry<String, PropertyGroup> propertyGroup : group.getPropertyGroups().entrySet()) {
            buildGroup(propertyGroup.getValue(), literal, propertyGroup.getKey());
        }

        for (Property property : group.getProperties()) {
            LiteralArgumentBuilder<CommandSource> propertyLiteral = literal(property.getName().replace(' ', '_'));
            property.buildCommand(propertyLiteral);
            literal.then(propertyLiteral);
        }

        base.then(literal);
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) { // TODO
        for (Hack hack : cb.getHackMap().getHacks()) {
            buildGroup(hack, root, hack.getName());
        }
    }
}
