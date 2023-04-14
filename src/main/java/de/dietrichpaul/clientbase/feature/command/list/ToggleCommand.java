package de.dietrichpaul.clientbase.feature.command.list;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.feature.command.argument.HackArgumentType;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import net.minecraft.command.CommandSource;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("t");
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(argument("hack", HackArgumentType.hack()).executes(ctx -> {
                    Hack hack = HackArgumentType.getHack(ctx, "hack");
                    hack.toggle();
                    return 1;
        }));
    }
}
