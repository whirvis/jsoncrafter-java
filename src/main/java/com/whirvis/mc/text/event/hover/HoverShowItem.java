package com.whirvis.mc.text.event.hover;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.whirvis.mc.text.MinecraftText;

/**
 * The show item action, which shows an item stack to the player.
 */
public class HoverShowItem extends HoverAction {

	private String id;
	private Integer count;
	private String tag;

	/**
	 * Constructs a new {@code HoverShowItem}.
	 * 
	 * @param id
	 *            the namespaced item ID. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 * @param count
	 *            the item stack count. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 * @param tag
	 *            the item NBT tag string. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 */
	public HoverShowItem(@Nullable String id, @Nullable Integer count,
			@Nullable String tag) {
		super(HoverEvent.SHOW_ITEM);
		this.setId(id);
		this.setCount(count);
		this.setTag(tag);
	}

	/**
	 * Constructs a new {@code HoverShowItem}.
	 * 
	 * @param id
	 *            the namespaced item ID. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 * @param count
	 *            the item stack count. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 */
	public HoverShowItem(@Nullable String id, @Nullable Integer count) {
		this(id, count, null);
	}

	/**
	 * Constructs a new {@code HoverShowItem}.
	 * 
	 * @param id
	 *            the namespaced item ID. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 */
	public HoverShowItem(@Nullable String id) {
		this(id, null, null);
	}

	/**
	 * Constructs a new {@code HoverShowItem}.
	 */
	public HoverShowItem() {
		this(null, null, null);
	}

	/**
	 * Returns the item namespace ID.
	 * <p>
	 * In game, this value defaults to {@code minecraft:air}.
	 * 
	 * @return the item namesapce ID.
	 */
	@Nonnull
	public String getId() {
		return MinecraftText.nullFallback(id, "minecraft:air");
	}

	/**
	 * Sets the item namespace ID.
	 * 
	 * @param id
	 *            the item namespace ID. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 * @return this hover action.
	 */
	@Nonnull
	public HoverShowItem setId(@Nullable String id) {
		this.id = id;
		return this;
	}

	/**
	 * Returns the item stack count.
	 * <p>
	 * In game, this value defaults to {@code 1}.
	 * 
	 * @return the item stack count.
	 */
	public int getCount() {
		return MinecraftText.nullFallback(count, 1);
	}

	/**
	 * Sets the item stack count.
	 * 
	 * @param count
	 *            the item stack count. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 * @return this hover action.
	 */
	@Nonnull
	public HoverShowItem setCount(@Nullable Integer count) {
		this.count = count;
		return this;
	}

	/**
	 * Returns the item NBT tag string.
	 * 
	 * @return the item NBT tag string.
	 */
	@Nullable
	public String getTag() {
		return this.tag;
	}

	/**
	 * Sets the item NBT tag string.
	 * 
	 * @param tag
	 *            the item NBT tag string. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 * @return this hover action.
	 */
	@Nonnull
	public HoverShowItem setTag(@Nullable String tag) {
		this.tag = tag;
		return this;
	}

	@Override
	protected JsonObject toJson() {
		JsonObject itemJson = new JsonObject();
		itemJson.addProperty("id", id);
		itemJson.addProperty("count", count);
		itemJson.addProperty("tag", tag);
		return itemJson;
	}

}
