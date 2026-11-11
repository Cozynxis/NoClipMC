package nl.cozynxis.noclipmc;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class NoClipClient implements ClientModInitializer {
    private static boolean enabled;
    private static KeyMapping toggleKey;
    private static boolean previousMayfly;
    private static boolean previousFlying;
    private static float previousFlySpeed;

    public static boolean isEnabled() {
        return enabled;
    }

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
                Identifier.fromNamespaceAndPath("noclipmc", "general"));

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.noclipmc.toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, category));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.level == null) {
                enabled = false;
                return;
            }

            while (toggleKey.consumeClick()) {
                if (enabled) {
                    disable(client);
                } else {
                    enable(client);
                }
            }

            if (enabled) {
                client.player.getAbilities().mayfly = true;
                client.player.getAbilities().flying = true;
                client.player.getAbilities().setFlyingSpeed(0.05F);
                client.player.setNoGravity(true);
                client.player.noPhysics = true;
            }
        });
    }

    private static void enable(Minecraft client) {
        previousMayfly = client.player.getAbilities().mayfly;
        previousFlying = client.player.getAbilities().flying;
        previousFlySpeed = client.player.getAbilities().getFlyingSpeed();
        enabled = true;
        client.player.getAbilities().mayfly = true;
        client.player.getAbilities().flying = true;
        client.player.noPhysics = true;
        client.player.setNoGravity(true);
        client.player.displayClientMessage(Component.translatable("noclipmc.enabled"), true);
    }

    private static void disable(Minecraft client) {
        enabled = false;
        client.player.noPhysics = false;
        client.player.setNoGravity(false);
        client.player.getAbilities().mayfly = previousMayfly;
        client.player.getAbilities().flying = previousFlying;
        client.player.getAbilities().setFlyingSpeed(previousFlySpeed);
        client.player.displayClientMessage(Component.translatable("noclipmc.disabled"), true);
    }
}
