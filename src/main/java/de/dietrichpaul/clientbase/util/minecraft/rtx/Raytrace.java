package de.dietrichpaul.clientbase.util.minecraft.rtx;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;

public record Raytrace(Entity target, HitResult hitResult) {
}
