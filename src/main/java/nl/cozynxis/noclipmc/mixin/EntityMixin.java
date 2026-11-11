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
        if ((Object) this instanceof LocalPlayer player && NoClipClient.isEnabled()) {
            player.noPhysics = true;
            player.setNoGravity(true);
        }
    }

    // Skip vanilla's collision resolution entirely while NoClip is active.
    // Merely setting noPhysics does not consistently bypass all player movement paths.
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void noclipmc$moveWithoutCollisions(MoverType type, Vec3 movement, CallbackInfo ci) {
        if ((Object) this instanceof LocalPlayer player && NoClipClient.isEnabled()) {
            player.noPhysics = true;
            player.setNoGravity(true);
            player.setPos(player.getX() + movement.x, player.getY() + movement.y, player.getZ() + movement.z);
            ci.cancel();
        }
    }
}
