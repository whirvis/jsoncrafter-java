package com.whirvis.mc.text;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * A container for text formatted using the Minecraft raw JSON text format.
 *
 * @param <T>
 *            the content type.
 * @see PlainText
 * @see TranslatedText
 * @see KeybindText
 * @see https://minecraft.fandom.com/wiki/Raw_JSON_text_format#Content_types
 */
public abstract class MinecraftText<T> {

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
	public static String toString(@Nullable Iterable<MinecraftText<?>> texts) {
		if (texts == null) {
			return null;
		}
		JsonArray jsonTexts = new JsonArray();
		Iterator<MinecraftText<?>> textsI = texts.iterator();
		while (textsI.hasNext()) {
			MinecraftText<?> text = textsI.next();
			if (text != null) {
				jsonTexts.add(text.json);
			}
		}
		return jsonTexts.toString();
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
	public static String toString(@Nullable MinecraftText<?>... texts) {
		return toString(texts != null ? Arrays.asList(texts) : null);
	}

	private final String type;
	protected final JsonObject json;

	/**
	 * Constructs a new instance of {@code MinecraftText} and sets its
	 * parameters based on the specified JSON object.
	 * 
	 * @param type
	 *            the content type of this text.
	 * @param json
	 *            the JSON to operate on.
	 * @throws NullPointerException
	 *             if {@code type} or {@code json} are {@code null}.
	 */
	public MinecraftText(@NonNull String type, @NonNull JsonObject json) {
		this.type = Objects.requireNonNull(type, "type");
		this.json = Objects.requireNonNull(json, "json");
	}

	/**
	 * Constructs a new instance of {@code MinecraftText} and sets its
	 * parameters to the in-game default values.
	 * 
	 * @param type
	 *            the content type of this text.
	 */
	public MinecraftText(String type) {
		this(type, new JsonObject());
	}

	/**
	 * Returns the content type of this text.
	 * 
	 * @return the content type of this text.
	 */
	public String getType() {
		return this.type;
	}

	protected String getString(String name, String fallback) {
		if (!json.has(name)) {
			return fallback;
		}
		return json.get(name).getAsString();
	}

	protected void setString(String name, String value) {
		if (value == null) {
			json.remove(name);
			return;
		}
		json.addProperty(name, value);
	}

	protected boolean getBoolean(String name, boolean fallback) {
		if (!json.has(name)) {
			return fallback;
		}
		return json.get(name).getAsBoolean();
	}

	protected void setBoolean(String name, Boolean value) {
		if (value == null) {
			json.remove(name);
			return;
		}
		json.addProperty(name, value);
	}

	/**
	 * Returns the content of this text.
	 * 
	 * @return the content of this text.
	 */
	public String getContent() {
		return this.getString(type, null);
	}

	/**
	 * Sets the content of this text.
	 * 
	 * @param content
	 *            the content.
	 */
	public void setContent(String content) {
		this.setString(type, content);
	}

	/**
	 * Sets the child text components of this text.
	 * <p>
	 * Child text components inherit all formatting and interactivity from the
	 * parent component, unless they explicitly override them.
	 * 
	 * @param extra
	 *            the child components.
	 * @throws IllegalArgumentException
	 *             if a child component is {@code this}.
	 */
	public void setExtra(@Nullable Iterable<MinecraftText<?>> extra) {
		if (extra == null) {
			json.remove("extra");
			return;
		}

		JsonArray jsonExtra = new JsonArray();
		Iterator<MinecraftText<?>> extraI = extra.iterator();
		while (extraI.hasNext()) {
			MinecraftText<?> text = extraI.next();
			if (text == this) {
				throw new IllegalArgumentException("parent cannot be child");
			} else if (text != null) {
				jsonExtra.add(text.json);
			}
		}

		if (jsonExtra.isEmpty()) {
			json.remove("extra");
		} else {
			json.add("extra", jsonExtra);
		}
	}

	/**
	 * Sets the child text components of this text.
	 * <p>
	 * Child text components inherit all formatting and interactivity from the
	 * parent component, unless they explicitly override them.
	 * 
	 * @param extra
	 *            the child components.
	 * @throws IllegalArgumentException
	 *             if a child component is {@code this}.
	 */
	public void setExtra(@Nullable MinecraftText<?>... extra) {
		this.setExtra(extra != null ? Arrays.asList(extra) : null);
	}

	/**
	 * Returns the text color.
	 * <p>
	 * In game, this value defaults to {@code white}. This method may return
	 * either a color name or a hexadecimal value formatted as {@code #RRGGBB}.
	 * 
	 * @return the text color.
	 */
	@NonNull
	public String getColor() {
		return this.getString("color", "white");
	}

	/**
	 * Sets the text color.
	 * 
	 * @param color
	 *            the color name.
	 */
	public void setColor(@Nullable String color) {
		this.setString("color", color);
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
	 *            the chat color.
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
	@NonNull
	public String getFont() {
		return this.getString("font", "minecraft:default");
	}

	/**
	 * Sets the font this text is rendered with.
	 * 
	 * @param font
	 *            the font to render this text with.
	 */
	public void setFont(@Nullable String font) {
		this.setString("font", font);
	}

	/**
	 * Returns if this text is bold.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is bold, {@code false} otherwise.
	 */
	public boolean isBold() {
		return this.getBoolean("bold", false);
	}

	/**
	 * Sets whether or not this text is bold.
	 * 
	 * @param italic
	 *            {@code true} if this text should be bold, {@code false}
	 *            otherwise.
	 */
	public void setBold(@Nullable Boolean bold) {
		this.setBoolean("bold", bold);
	}

	/**
	 * Returns if this text is italic.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is italic, {@code false} otherwise.
	 */
	public boolean isItalic() {
		return this.getBoolean("italic", false);
	}

	/**
	 * Sets whether or not this text is italic.
	 * 
	 * @param italic
	 *            {@code true} if this text should be italic, {@code false}
	 *            otherwise.
	 */
	public void setItalic(@Nullable Boolean italic) {
		this.setBoolean("italic", italic);
	}

	/**
	 * Returns if this text is underlined.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is underlined, {@code false} otherwise.
	 */
	public boolean isUnderlined() {
		return this.getBoolean("underlined", false);
	}

	/**
	 * Sets whether or not this text is underlined.
	 * 
	 * @param italic
	 *            {@code true} if this text should be underlined, {@code false}
	 *            otherwise.
	 */
	public void setUnderlined(@Nullable Boolean underlined) {
		this.setBoolean("underlined", underlined);
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
		return this.getBoolean("strikethrough", false);
	}

	/**
	 * Sets whether or not this text is striked through.
	 * 
	 * @param italic
	 *            {@code true} if this text should be striked through,
	 *            {@code false} otherwise.
	 */
	public void setStrikethrough(@Nullable Boolean strikethrough) {
		this.setBoolean("strikethrough", strikethrough);
	}

	/**
	 * Returns if this text is obfuscated.
	 * <p>
	 * In game, this value defaults to {@code false}.
	 * 
	 * @return {@code true} if this text is obfuscated, {@code false} otherwise.
	 */
	public boolean isObfuscated() {
		return this.getBoolean("obfuscated", false);
	}

	/**
	 * Sets whether or not this text is obfuscated.
	 * 
	 * @param italic
	 *            {@code true} if this text should be obfuscated, {@code false}
	 *            otherwise.
	 */
	public void setObfuscated(@Nullable Boolean obfuscated) {
		this.setBoolean("obfuscated", obfuscated);
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
	@NonNull
	public String getInsertion() {
		return this.getString("insertion", "");
	}

	/**
	 * Sets the text that will be inserted into the player's chatbar when they
	 * shift click this text.
	 * 
	 * @param insertion
	 *            the text to insert when shift clicked.
	 */
	public void setInsertion(@Nullable String insertion) {
		this.setString("insertion", insertion);
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

	@Override
	public String toString() {
		return json.toString();
	}

}
