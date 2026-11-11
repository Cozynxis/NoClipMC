package nl.cozynxis.noclipmc.mixin;

import nl.cozynxis.noclipmc.NoClipClient;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "baseTick", at = @At("HEAD"))
    private void noclipmc$baseTick(CallbackInfo ci) {
        noclipmc$apply();
    }

    @Inject(method = "move", at = @At("HEAD"))
    private void noclipmc$beforeMove(MoverType type, Vec3 movement, CallbackInfo ci) {
        noclipmc$apply();
    }

    private void noclipmc$apply() {
        if ((Object) this instanceof LocalPlayer player && NoClipClient.isEnabled()) {
            player.noPhysics = true;
            player.setNoGravity(true);
        }
    }
}
