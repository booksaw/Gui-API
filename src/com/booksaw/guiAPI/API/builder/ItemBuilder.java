package com.booksaw.guiAPI.API.builder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Used to build items for your plugin quickly
 * 
 * @author James McNair
 *
 */
public class ItemBuilder {

	private ItemStack item;
	private ItemMeta meta;

	public ItemBuilder(Material material) {
		item = new ItemStack(material);
		meta = item.getItemMeta();
	}

	public ItemBuilder(ItemStack item) {
		this.item = item;
		meta = item.getItemMeta();
	}

	public ItemStack getItem() {
		item.setItemMeta(meta);
		return item;
	}

	public ItemBuilder setName(String name) {
		meta.setDisplayName(getMessage(name));
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		List<String> messageLore = new ArrayList<>();

		for (String str : lore) {
			messageLore.add(getMessage(str));
		}

		meta.setLore(messageLore);
		return this;
	}

	public ItemBuilder addLoreLine(String line) {
		List<String> lore = meta.getLore();
		if (lore == null) {
			lore = new ArrayList<>();
		}
		lore.add(getMessage(line));
		meta.setLore(lore);

		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {
		meta.setUnbreakable(unbreakable);
		return this;
	}

	public ItemBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;
	}

	public ItemBuilder setDurability(int damage) {
		if (meta instanceof Damageable) {
			((Damageable) meta).setDamage(damage);
		}
		return this;
	}

	public ItemBuilder setType(Material type) {
		item.setItemMeta(meta);
		item.setType(type);
		meta = item.getItemMeta();
		return this;
	}

	/**
	 * Used if a custom item builder is wanted with a message manager for example
	 * 
	 * @param message The message to find
	 * @return The message
	 */
	protected String getMessage(String message) {
		return message;
	}

}
