package com.whirvis.mc.text.event;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A container for an event that can occur with
 * {@link com.whirvis.mc.text.MinecraftText Minecraft} text in-game.
 */
public abstract class TextEvent {

	private final String type;
	private String action;

	/**
	 * Constructs a new {@code TextEvent} of the specified type.
	 * 
	 * @param type
	 *            the event type.
	 */
	public TextEvent(@Nonnull String type) {
		this.type = Objects.requireNonNull(type, "type");
	}

	/**
	 * Constructs a new {@code TextEvent} of the specified type with the given
	 * course of action.
	 * 
	 * @param type
	 *            the event type.
	 * @param action
	 *            the name of the action.
	 * @throws NullPointerException
	 *             if {@code type} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@code action} is not supported.
	 */
	public TextEvent(@Nonnull String type, @Nullable String action) {
		this(type);
		this.setAction(action);
	}

	/**
	 * Returns the event type name.
	 * 
	 * @return the event type name.
	 */
	@Nonnull
	public String getType() {
		return this.type;
	}

	/**
	 * Returns if this event supports a given action.
	 * 
	 * @param action
	 *            the name of the action.
	 * @return {@code true} if this events supports {@code action},
	 *         {@code false} otherwise.
	 */
	public abstract boolean supportsAction(@Nonnull String action);

	/**
	 * Returns the course of action to take in response to this event.
	 * 
	 * @return the course of action to take in response to this event.
	 */
	@Nullable
	public String getAction() {
		return this.action;
	}

	/**
	 * Sets the course of action to take in response to this event.
	 * 
	 * @param action
	 *            the name of the action to perform.
	 * @return this event.
	 * @throws IllegalArgumentException
	 *             if {@code action} is not supported.
	 */
	@Nonnull
	public TextEvent setAction(@Nullable String action) {
		if (action != null && !this.supportsAction(action)) {
			throw new IllegalArgumentException("unsupported action");
		}
		this.action = action;
		return this;
	}

	/**
	 * Encodes this event.
	 * 
	 * @param json
	 *            the JSON to encode to.
	 */
	protected abstract void encodeEvent(JsonObject json);

	/**
	 * Converts this event to JSON.
	 * 
	 * @return the encoded JSON.
	 */
	@Nonnull
	public JsonElement toJson() {
		JsonObject json = new JsonObject();
		if (action != null) {
			json.addProperty("action", action);
		}
		this.encodeEvent(json);
		return json;
	}

}
