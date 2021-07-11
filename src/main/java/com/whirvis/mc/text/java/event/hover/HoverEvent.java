package com.whirvis.mc.text.java.event.hover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.whirvis.mc.text.java.MinecraftText;
import com.whirvis.mc.text.java.event.TextEvent;

/**
 * A container for a text hover event.
 */
public class HoverEvent extends TextEvent {

	public static final String SHOW_TEXT = "show_text";
	public static final String SHOW_ITEM = "show_item";
	public static final String SHOW_ENTITY = "show_entity";

	private Object value;

	/**
	 * Constructs a new {@code HoverEvent}.
	 */
	public HoverEvent() {
		super("hoverEvent");
	}

	@Override
	public boolean supportsAction(@Nonnull String action) {
		switch (action) {
			case SHOW_TEXT:
			case SHOW_ITEM:
			case SHOW_ENTITY:
				return true;
		}
		return false;
	}

	/**
	 * Sets the text that will be shown.
	 * 
	 * @param values
	 *            the values to persuade into {@link MinecraftText} objects
	 *            using {@link MinecraftText#persuade(Object)}, {@code null}
	 *            values are ignored.
	 * @return this event.
	 */
	@Nonnull
	public HoverEvent show(@Nullable Iterable<?> values) {
		List<MinecraftText> texts = new ArrayList<>();
		for (Object value : values) {
			if (value != null) {
				texts.add(MinecraftText.persuade(value));
			}
		}
		this.value = !texts.isEmpty()
				? texts.toArray(new MinecraftText[texts.size()])
				: null;
		return this;
	}

	/**
	 * Sets the text that will be shown.
	 * 
	 * @param values
	 *            the values to persuade into {@link MinecraftText} objects
	 *            using {@link MinecraftText#persuade(Object)}, {@code null}
	 *            values are ignored.
	 * @return this event.
	 */
	@Nonnull
	public HoverEvent show(@Nullable Object... values) {
		return this.show(values != null ? Arrays.asList(values) : null);
	}

	/**
	 * Sets what will be shown.
	 * <p>
	 * The appropriate call to {@link #setAction(String)} will be called for
	 * {@code action} automatically, based on the value it returns for
	 * {@link HoverAction#getAction()}.
	 * 
	 * @param action
	 *            the action to perform.
	 * @return this event.
	 */
	@Nonnull
	public HoverEvent show(@Nullable HoverAction action) {
		this.setAction(action.getAction());
		this.value = action;
		return this;
	}

	@Override
	protected void encodeEvent(JsonObject json) {
		JsonElement contentsJson = null;
		if (value instanceof MinecraftText[]) {
			MinecraftText[] texts = (MinecraftText[]) value;
			if (texts.length == 1) {
				contentsJson = texts[0].toJson();
			} else if (texts.length > 1) {
				JsonArray textsJson = new JsonArray();
				for (MinecraftText text : texts) {
					textsJson.add(text.toJson());
				}
				contentsJson = textsJson;
			}
		} else if (value instanceof HoverAction) {
			contentsJson = ((HoverAction) value).toJson();
		}
		json.add("contents", contentsJson);
	}

}
