package com.whirvis.mc.text.java;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * A container for translated Minecraft text.
 *
 * @see PlainText
 * @see KeybindText
 */
public class TranslatedText extends MinecraftText {

	private Object[] with;

	/**
	 * Constructs a new instance of {@code TranslatedText}.
	 * 
	 * @param translate
	 *            the translation key.
	 * @param with
	 *            the parameters to format this text with.
	 * @throws NullPointerException
	 *             if {@code translate} is {@code null}.
	 */
	public TranslatedText(@Nonnull String translate, @Nullable Object... with) {
		super("translate", translate);
		this.setWith(with);
	}

	/**
	 * Sets the parameters to format this text with.
	 * 
	 * @param with
	 *            the parameters to format this text with.
	 */
	public void setWith(@Nullable Object... with) {
		this.with = with;
	}

	/**
	 * Sets the translation of this text.
	 * 
	 * @param translate
	 *            the translation key.
	 * @param with
	 *            the parameters to format this text with.
	 */
	public void setTranslation(@Nullable String translate,
			@Nullable Object... with) {
		this.setContent(translate);
		this.setWith(with);
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = super.toJson();
		if (with == null || with.length <= 0) {
			return json;
		}

		JsonArray jsonWith = new JsonArray();
		for (int i = 0; i < with.length; i++) {
			jsonWith.add(toJsonElement(with[i]));
		}
		json.add("with", jsonWith);
		return json;
	}

}
