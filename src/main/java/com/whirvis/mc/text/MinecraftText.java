package com.whirvis.mc.text;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.ChatColor;

import com.c05mic.generictree.Tree;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.whirvis.mc.text.event.TextEvent;

/**
 * A container for text formatted using the
 * <a href="https://minecraft.fandom.com/wiki/Raw_JSON_text_format">Minecraft
 * raw JSON text format</a>.
 *
 * @see PlainText
 * @see TranslatedText
 * @see KeybindText
 */
public abstract class MinecraftText {

	/*
	 * We're required to create our own GSON instance stop null values from
	 * being serialized. For whatever reason, the toString() built into JSON
	 * objects will serialize null values by default.
	 */
	public static final Gson GSON = new GsonBuilder().create();

	/**
	 * Converts an iterable of {@code MinecraftText} instances into a JSON
	 * string, accounting for the fact there may only one or multiple.
	 * 
	 * @param texts
	 *            the minecraft texts.
	 * @return a JSON array string containing all not {@code null} values of
	 *         {@code texts}.
	 */
	@Nullable
	public static JsonArray toJson(@Nullable Iterable<MinecraftText> texts) {
		if (texts == null) {
			return null;
		}
		JsonArray jsonTexts = new JsonArray();
		Iterator<MinecraftText> textsI = texts.iterator();
		while (textsI.hasNext()) {
			MinecraftText text = textsI.next();
			if (text != null) {
				jsonTexts.add(text.toJson());
			}
		}
		return jsonTexts;
	}

	/**
	 * Converts an array of {@code MinecraftText} instances into a JSON string,
	 * accounting for the fact there may only one or multiple.
	 * 
	 * @param texts
	 *            the minecraft texts.
	 * @return a JSON array string containing all not {@code null} values of
	 *         {@code texts}.
	 */
	@Nullable
	public static JsonArray toJson(@Nullable MinecraftText... texts) {
		return toJson(texts != null ? Arrays.asList(texts) : null);
	}

	/**
	 * Takes in a value and returns it, unless it is {@code null}. In which
	 * case, a given fallback value is returned instead.
	 * 
	 * @param <T>
	 *            the value type.
	 * @param value
	 *            the original value.
	 * @param fallback
	 *            the value to return if {@code value} is {@code null}.
	 * @return {@code value} if not {@code null}, {@code fallback} otherwise.
	 */
	@Nullable
	public static <T> T nullFallback(@Nullable T value, @Nullable T fallback) {
		return value != null ? value : fallback;
	}

	/**
	 * Attempts to convert an object to a {@link JsonElement}.
	 * 
	 * @param obj
	 *            the object to convert.
	 * @return the converted object as a JSON element.
	 * @throws IllegalArgumentException
	 *             if {@code obj} is of an unsupported type.
	 */
	@Nonnull /* assuming JsonNull doesn't count */
	protected static JsonElement toJsonElement(@Nullable Object obj) {
		if (obj == null) {
			return JsonNull.INSTANCE;
		} else if (obj instanceof JsonElement) {
			return (JsonElement) obj;
		} else if (obj instanceof Boolean) {
			return new JsonPrimitive((Boolean) obj);
		} else if (obj instanceof Character) {
			return new JsonPrimitive((Character) obj);
		} else if (obj instanceof Number) {
			return new JsonPrimitive((Number) obj);
		} else if (obj instanceof String) {
			return new JsonPrimitive((String) obj);
		}
		throw new IllegalArgumentException("unsupported type");
	}

	private String type;
	private Object content;
	private Tree<MinecraftText> extra;
	private String color;
	private String font;
	private Boolean bold;
	private Boolean italic;
	private Boolean strikethrough;
	private Boolean underlined;
	private Boolean obfuscated;
	private String insertion;
	private HashMap<String, TextEvent> events;

	/**
	 * Constructs a new instance of {@code MinecraftText} and sets its
	 * parameters to the in-game default values.
	 * 
	 * @param type
	 *            the content type of this text.
	 * @param json
	 *            the JSON to operate on.
	 * @throws NullPointerException
	 *             if {@code type} or {@code content} are {@code null}.
	 */
	public MinecraftText(@Nonnull String type, @Nonnull Object content) {
		this.setType(type);
		this.setContent(content);
		this.extra = new Tree<>(null);
		this.events = new HashMap<>();
	}

	/**
	 * Returns the content type of this text.
	 * 
	 * @return the content type of this text.
	 */
	@Nonnull
	public String getType() {
		return this.type;
	}

	/**
	 * Sets the content type of this text.
	 * 
	 * @param type
	 *            the content type.
	 * @throws NullPointerException
	 *             if {@code type} is {@code null}.
	 */
	public void setType(@Nonnull String type) {
		this.type = Objects.requireNonNull(type, "type");
	}

	/**
	 * Returns the content of this text.
	 * 
	 * @return the content of this text, may be {@code null} if the content has
	 *         yet to be set.
	 */
	@Nullable
	public Object getContent() {
		return this.content;
	}

	/**
	 * Sets the content of this text.
	 * 
	 * @param content
	 *            the content.
	 * @throws NullPointerException
	 *             if {@code content} is {@code null}.
	 */
	public void setContent(@Nonnull Object content) {
		this.content = Objects.requireNonNull(content, "content");
	}

	/**
	 * Sets the child text components of this text.
	 * <p>
	 * Child text components inherit all formatting and interactivity from the
	 * parent component, unless they explicitly override them.
	 * 
	 * @param extra_
	 *            the child components, {@code null} values are ignored.
	 * @throws IllegalArgumentException
	 *             if a child component is {@code this}.
	 */
	/* TODO */

	/**
	 * Returns the text color.
	 * <p>
	 * In game, this value defaults to {@code white}. This method may return
	 * either a color name or a hexadecimal value formatted as {@code #RRGGBB}.
	 * 
	 * @return the text color.
	 */
	@Nonnull
	public String getColor() {
		return nullFallback(color, "white");
	}

	/**
	 * Sets the text color.
	 * 
	 * @param color
	 *            the color name. May be {@code null} to have the parameter left
	 *            absent from the encoded JSON.
	 */
	public void setColor(@Nullable String color) {
		this.color = color;
	}

	/**
	 * Sets the text color.
	 * 
	 * @param rgb
	 *            the RGB color value.
	 */
	public void setColor(int rgb) {
		this.setColor("#" + Integer.toHexString(rgb));
	}

	/**
	 * Sets the text color.
	 * 
	 * @param color
	 *            the chat color. May be {@code null} to have the parameter left
	 *            absent from the encoded JSON.
	 * @throws IllegalArgumentException
	 *             if {@code color} is not a color.
	 */
	public void setColor(@Nullable ChatColor color) {
		if (color == null) {
			this.setColor((String) null);
			return;
		} else if (!color.isColor()) {
			throw new IllegalArgumentException("not a color");
		}
		this.setColor(color.name().toLowerCase());
	}

	/**
	 * Returns the font this text is rendered with.
	 * <p>
	 * In game, this value defaults to {@code minecraft:default}.
	 * 
	 * @return the font this text is rendered with.
	 */
	@Nonnull
	public String getFont() {
		return nullFallback(font, "minecraft:default");
	}

	/**
	 * Sets the font this text is rendered with.
	 * 
	 * @param font
	 *            the font to render this text with. May be {@code null} to have
	 *            the parameter left absent from the encoded JSON.
	 */
	public void setFont(@Nullable String font) {
		this.font = font;
	}

	/**
	 * Returns if this text is bold.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is bold, {@code false} otherwise.
	 */
	public boolean isBold() {
		return nullFallback(bold, false);
	}

	/**
	 * Sets whether or not this text is bold.
	 * 
	 * @param bold
	 *            {@code true} if this text should be bold, {@code false}
	 *            otherwise. May be {@code null} to have the parameter left
	 *            absent from the encoded JSON.
	 */
	public void setBold(@Nullable Boolean bold) {
		this.bold = bold;
	}

	/**
	 * Returns if this text is italic.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is italic, {@code false} otherwise.
	 */
	public boolean isItalic() {
		return nullFallback(italic, false);
	}

	/**
	 * Sets whether or not this text is italic.
	 * 
	 * @param italic
	 *            {@code true} if this text should be italic, {@code false}
	 *            otherwise. May be {@code null} to have the parameter left
	 *            absent from the encoded JSON.
	 */
	public void setItalic(@Nullable Boolean italic) {
		this.italic = italic;
	}

	/**
	 * Returns if this text is underlined.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is underlined, {@code false} otherwise.
	 */
	public boolean isUnderlined() {
		return nullFallback(underlined, false);
	}

	/**
	 * Sets whether or not this text is underlined.
	 * 
	 * @param underlined
	 *            {@code true} if this text should be underlined, {@code false}
	 *            otherwise. May be {@code null} to have the parameter left
	 *            absent from the encoded JSON.
	 */
	public void setUnderlined(@Nullable Boolean underlined) {
		this.underlined = underlined;
	}

	/**
	 * Returns if this text is striked through.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is striked through, {@code false}
	 *         otherwise.
	 */
	public boolean isStrikethrough() {
		return nullFallback(strikethrough, false);
	}

	/**
	 * Sets whether or not this text is striked through.
	 * 
	 * @param strikethrough
	 *            {@code true} if this text should be striked through,
	 *            {@code false} otherwise. May be {@code null} to have the
	 *            parameter left absent from the encoded JSON.
	 */
	public void setStrikethrough(@Nullable Boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	/**
	 * Returns if this text is obfuscated.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is obfuscated, {@code false} otherwise.
	 */
	public boolean isObfuscated() {
		return nullFallback(obfuscated, false);
	}

	/**
	 * Sets whether or not this text is obfuscated.
	 * 
	 * @param obfuscated
	 *            {@code true} if this text should be obfuscated, {@code false}
	 *            otherwise. May be {@code null} to have the parameter left
	 *            absent from the encoded JSON.
	 */
	public void setObfuscated(@Nullable Boolean obfuscated) {
		this.obfuscated = obfuscated;
	}

	/**
	 * Returns the text that will be inserted into the player's chatbar when
	 * they shift click this text.
	 * <p>
	 * In game, this value defaults to an empty string.
	 * 
	 * @return the text that will be inserted into the player's chatbar when
	 *         they shift click this text.
	 */
	@Nonnull
	public String getInsertion() {
		return nullFallback(insertion, "");
	}

	/**
	 * Sets the text that will be inserted into the player's chatbar when they
	 * shift click this text.
	 * 
	 * @param insertion
	 *            the text to insert when shift clicked. May be {@code null} to
	 *            have the parameter left absent from the encoded JSON.
	 */
	public void setInsertion(@Nullable String insertion) {
		this.insertion = insertion;
	}

	/**
	 * Returns if this text has any events.
	 * 
	 * @return {@code true} if this text has any events, {@code false}
	 *         otherwise.
	 */
	public boolean hasEvents() {
		return !events.isEmpty();
	}

	/**
	 * Returns if this text has an event of a given type.
	 * 
	 * @param type
	 *            the event type.
	 * @return {@code true} if this text has an event of {@code type},
	 *         {@code false} otherwise.
	 */
	public boolean hasEvent(@Nullable String type) {
		return events.containsKey(type);
	}

	/**
	 * Returns the text events.
	 * 
	 * @return the text events.
	 */
	@Nonnull
	public Collection<TextEvent> getEvents() {
		return Collections.unmodifiableCollection(events.values());
	}

	/**
	 * Adds a text event.
	 * <p>
	 * Keep in mind that only one event type can be present in text at a time.
	 * If another event of the same type is already present, it will be
	 * overriden by the one being added.
	 * 
	 * @param event
	 *            the text event.
	 * @throws NullPointerException
	 *             if {@code event} is {@code null}.
	 */
	public void addEvent(@Nonnull TextEvent event) {
		Objects.requireNonNull(event, "event");
		events.put(event.getType(), event);
	}

	/**
	 * Removes a text event.
	 * 
	 * @param event
	 *            the text event.
	 */
	public void removeEvent(@Nullable TextEvent event) {
		if (event != null) {
			events.remove(event.getType(), event);
		}
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		String objStr = Objects.toString(obj);
		return Objects.equals(objStr, this.toString());
	}

	/**
	 * Encodes the content into a JSON element.
	 * <p>
	 * By default, the content of this text will be encoded via the
	 * {@link #toJsonElement(Object)} method. If the text content is a more
	 * complex type, the extending class should override this method.
	 * 
	 * @return the encoded content.
	 */
	@Nonnull
	public JsonElement encodeContent() {
		return toJsonElement(content);
	}

	/**
	 * Converts this text to JSON.
	 * 
	 * @return the encoded JSON.
	 */
	@Nonnull
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.add(type, this.encodeContent());
		if (!extra.isEmpty()) {
			/* TODO */
		}

		json.addProperty("color", color);
		json.addProperty("font", font);
		json.addProperty("bold", bold);
		json.addProperty("italic", italic);
		json.addProperty("strikethrough", strikethrough);
		json.addProperty("underlined", underlined);
		json.addProperty("obfuscated", obfuscated);
		json.addProperty("insertion", insertion);

		for (TextEvent event : events.values()) {
			json.add(event.getType(), event.toJson());
		}
		return json;
	}

	@Override
	public String toString() {
		return GSON.toJson(this.toJson());
	}

}
