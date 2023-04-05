package de.dietrichpaul.clientbase.rotation;

import de.dietrichpaul.clientbase.ClientBase;

public interface RotationSpoof {

    RotationEngine engine = ClientBase.getInstance().getRotationEngine();

    /**
     * Suche target und gibt zurück, ob eins gefunden wurde.
     * Für die implementation: Es sei schlau, würde das Target gespeichert werden,
     * da folgend die #rotate(float[])-Methode aufgerufen wird.
     * @return ob ein Target gefunden wurde
     */
    boolean pickTarget();

    void rotate(float[] rotations, boolean tick, float partialTicks);

    boolean isToggled();

    int getPriority();

    boolean canInterpolate();

}
