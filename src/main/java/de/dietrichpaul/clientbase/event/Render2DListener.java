package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import net.minecraft.client.util.math.MatrixStack;

public interface Render2DListener extends Listener {
    void onRender2D(final MatrixStack matrices, final float tickDelta);

    class Render2DEvent extends AbstractEvent<Render2DListener> {
        private final EventExecutor<Render2DListener> eventExecutor;

        public Render2DEvent(final MatrixStack matrices, final float tickDelta) {
            this.eventExecutor = listener -> listener.onRender2D(matrices, tickDelta);
        }

        @Override
        public EventExecutor<Render2DListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<Render2DListener> getListenerType() {
            return Render2DListener.class;
        }
    }
}
