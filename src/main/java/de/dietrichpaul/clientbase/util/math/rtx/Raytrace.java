package de.dietrichpaul.clientbase.util.math.rtx;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;

public record Raytrace(Entity target, HitResult hitResult) {
}
