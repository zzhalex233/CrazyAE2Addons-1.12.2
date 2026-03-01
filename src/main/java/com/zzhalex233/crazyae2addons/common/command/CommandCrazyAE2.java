package com.zzhalex233.crazyae2addons.common.command;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.api.util.AEPartLocation;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.IActionSource;

import java.util.Optional;
import com.zzhalex233.crazyae2addons.common.ae2.mob.cell.item.ItemMobCell;
import com.zzhalex233.crazyae2addons.common.ae2.mob.channel.AEMobStack;
import com.zzhalex233.crazyae2addons.common.ae2.mob.channel.IMobStorageChannel;
import com.zzhalex233.crazyae2addons.common.ae2.mob.stack.MobKey;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class CommandCrazyAE2 extends CommandBase {

    @Override
    public String getName() {
        return "crazyae2";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/crazyae2 mobcell <minecraft:entity_id> <count>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(getUsage(sender));
        }

        if ("mobcell".equalsIgnoreCase(args[0])) {
            handleMobCell(sender, args);
            return;
        }

        throw new WrongUsageException(getUsage(sender));
    }

    private static final class PlayerActionSource implements IActionSource {
        private final EntityPlayerMP player;

        private PlayerActionSource(EntityPlayerMP player) {
            this.player = player;
        }

        @Override
        public Optional<net.minecraft.entity.player.EntityPlayer> player() {
            return Optional.of(this.player);
        }

        @Override
        public Optional<IActionHost> machine() {
            return Optional.empty();
        }

        @Override
        public <T> Optional<T> context(Class<T> contextClass) {
            return Optional.empty();
        }
    }

    private void handleMobCell(ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            throw new CommandException("This command can only be used by a player.");
        }

        if (args.length != 3) {
            throw new WrongUsageException("/crazyae2 mobcell <minecraft:entity_id> <count>");
        }

        EntityPlayerMP player = (EntityPlayerMP) sender;

        ResourceLocation entityId = new ResourceLocation(args[1]);
        long count = parseLong(args[2], 1L, Long.MAX_VALUE);

        if (!EntityList.isRegistered(entityId)) {
            throw new CommandException("Unknown entity id: " + entityId);
        }

        ItemStack held = player.getHeldItemMainhand();
        if (held.isEmpty() || !(held.getItem() instanceof ItemMobCell)) {
            throw new CommandException("You must hold a Mob Storage Cell in your main hand.");
        }

        IStorageChannel<AEMobStack> channel =
                AEApi.instance().storage().getStorageChannel(IMobStorageChannel.class);

        ICellInventoryHandler<AEMobStack> inv =
                AEApi.instance().registries().cell().getCellInventory(held, null, channel);

        if (inv == null) {
            throw new CommandException("Failed to access mob cell inventory.");
        }

        MobKey key = new MobKey(entityId, null);
        AEMobStack toInsert = new AEMobStack(key, count);

        IAEStack remainder = inv.injectItems(
                toInsert,
                Actionable.MODULATE,
                new PlayerActionSource(player)
        );

        long inserted = count;
        if (remainder != null) {
            inserted -= remainder.getStackSize();
        }

        sender.sendMessage(new TextComponentString(
                "[CrazyAE2Addons] Inserted " + inserted + " / " + count + " "
                        + entityId.toString() + " into mob cell."
        ));
    }
}
