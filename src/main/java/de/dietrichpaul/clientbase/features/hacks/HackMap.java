package de.dietrichpaul.clientbase.features.hacks;

import de.dietrichpaul.clientbase.features.hacks.combat.KillAuraHack;
import de.dietrichpaul.clientbase.features.hacks.movement.FlightHack;
import de.dietrichpaul.clientbase.features.hacks.movement.SprintHack;
import de.dietrichpaul.clientbase.features.hacks.render.ClickGuiHack;
import de.dietrichpaul.clientbase.features.hacks.render.HudHack;
import de.dietrichpaul.clientbase.features.hacks.world.XRayHack;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class HackMap {

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

    public void registerBuiltInHacks() {
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
