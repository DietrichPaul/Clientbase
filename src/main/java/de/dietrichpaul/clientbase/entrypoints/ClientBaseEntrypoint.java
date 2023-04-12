package de.dietrichpaul.clientbase.entrypoints;

import de.dietrichpaul.clientbase.ClientBase;
import net.fabricmc.api.ClientModInitializer;

public class ClientBaseEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientBase.getInstance().init();
    }
}
