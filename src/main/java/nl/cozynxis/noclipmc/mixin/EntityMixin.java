package nl.cozynxis.noclipmc.mixin;

import nl.cozynxis.noclipmc.NoClipClient;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "baseTick", at = @At("HEAD"))
    private void noclipmc$enableCollisionBypass(CallbackInfo ci) {
        if ((Object) this instanceof LocalPlayer player && NoClipClient.isEnabled()) {
            player.noPhysics = true;
            player.setNoGravity(true);
        }
    }
}
