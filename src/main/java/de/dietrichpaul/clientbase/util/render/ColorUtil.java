package de.dietrichpaul.clientbase.util.render;

import org.lwjgl.glfw.GLFW;

public class ColorUtil {

    public static int getRainbow(float offset, float saturation, float brightness) {
        double timing = GLFW.glfwGetTime() * (50.0/360) + offset / 360.0F;
        timing = Math.abs(timing) % 1.0F;
        return java.awt.Color.HSBtoRGB((float) timing, saturation, brightness);
    }

}
