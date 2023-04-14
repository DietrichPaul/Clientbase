package de.dietrichpaul.clientbase.feature.hack;

import de.dietrichpaul.clientbase.feature.hack.combat.KillAuraHack;
import de.dietrichpaul.clientbase.feature.hack.movement.FlightHack;
import de.dietrichpaul.clientbase.feature.hack.movement.SprintHack;
import de.dietrichpaul.clientbase.feature.hack.render.ClickGuiHack;
import de.dietrichpaul.clientbase.feature.hack.render.HudHack;
import de.dietrichpaul.clientbase.feature.hack.world.XRayHack;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class HackList {
    private final Map<String, Hack> hacks = new TreeMap<>();

    // combat
    public KillAuraHack killAura = new KillAuraHack();

    // movement
    public FlightHack flight = new FlightHack();
    public SprintHack sprint = new SprintHack();

    // render
    public ClickGuiHack clickGui = new ClickGuiHack();
    public HudHack hud = new HudHack();

    // world
    public XRayHack xRay = new XRayHack();

    public void registerBuiltIn() {
        // combat
        register(killAura);

        // movement
        register(flight);
        register(sprint);

        // render
        register(clickGui);
        register(hud);

        // world
        register(xRay);
    }

    public void register(Hack hack) {
        hacks.put(hack.getName(), hack);
    }

    public Hack getHack(String name) {
        return hacks.get(name);
    }

    public Collection<Hack> getHacks() {
        return hacks.values();
    }
}
