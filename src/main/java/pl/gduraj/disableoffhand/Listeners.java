package pl.gduraj.disableoffhand;


import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {

    private DisableOffHand plugin;
    private final String PERM = "disableoffhand.bypass";
    private int SLOT;

    public Listeners() {
        this.plugin = DisableOffHand.getIntance();
        this.SLOT = plugin.getSLOT();
    }


    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event){
        if(event.getPlayer().hasPermission(PERM)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event){
        if((event.getInventory().getType() != InventoryType.CRAFTING) || !(event.getInventorySlots().contains(SLOT)))
            return;

        if(event.getWhoClicked().hasPermission(PERM)) return;

        if(event.getOldCursor().getType() != Material.AIR){
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClickOffHand(final InventoryClickEvent e) {

        if (e.getWhoClicked().hasPermission(PERM)) return;

        if(actionCheck(e.getAction()) && !e.getClick().equals(ClickType.NUMBER_KEY) ) e.setCancelled(true);

        if (e.getInventory().getType() != InventoryType.CRAFTING || e.getSlot() != SLOT) return;

        if ((e.getClick().equals(ClickType.NUMBER_KEY) && e.getSlot() == SLOT) || itemCheck(e.getCursor())) {
            e.setResult(Event.Result.DENY);
            e.setCancelled(true);
        }


    }

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
        if (!e.getPlayer().hasPermission(PERM)) {
            if(e.getInventory().getType().equals(InventoryType.CRAFTING)){
                final ItemStack offhand = e.getPlayer().getInventory().getItemInOffHand();
                if (offhand.getType() != Material.AIR) {
                    e.getPlayer().getInventory().setItemInOffHand(null);
                    e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), offhand);
                    e.getPlayer().sendMessage(plugin.getConfigManager().getString("messages.dropItem"));
                }
            }
        }
    }

    private boolean itemCheck(ItemStack itemStack){
        return itemStack != null && itemStack.getType() != Material.AIR;
    }

    private boolean actionCheck(InventoryAction action){
        return action != null && action.equals(InventoryAction.HOTBAR_SWAP);
    }

}
