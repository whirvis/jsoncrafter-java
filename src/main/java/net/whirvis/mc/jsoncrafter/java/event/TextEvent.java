package net.whirvis.mc.jsoncrafter.java.event;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import net.whirvis.mc.jsoncrafter.java.RichText;

/**
 * Allows for events to occur when the player interacts with text.
 * <p>
 * More information on events can be found on the <a href=
 * "https://minecraft.fandom.com/wiki/Raw_JSON_text_format#Java_Edition">Minecraft
 * Wiki</a>.
 */
public abstract class TextEvent {

	/**
	 * Wrapper lambda to make {@code TextEvent} and its children function with
	 * GSON type hierarchy adapters. To convert an instance of {@code TextEvent}
	 * to JSON without GSON, use {@link RichText#toJson()}.
	 */
	public static final JsonSerializer<TextEvent> SERIALIZER =
			(src, type, ctx) -> src.toJson();

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
	public final String getType() {
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
	 * Serializes the event.
	 * 
	 * @param json
	 *            the JSON to encode to.
	 */
	protected abstract void serializeEvent(JsonObject json);

	/**
	 * Serializes the event into JSON.
	 * 
	 * @return the encoded JSON.
	 */
	public final JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("action", action);
		this.serializeEvent(json);
		return json;
	}

}
