package com.whirvis.mc.text;

import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * A container for translated Minecraft text.
 *
 * @see PlainText
 * @see KeybindText
 */
public class TranslatedText extends MinecraftText<String> {

	private static int getArgCount(String format) {
		int count = 0;
		char[] c = format.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] != '%' || i + 1 >= c.length) {
				continue;
			}
			char type = c[i++];
			if (!Character.isWhitespace(type) && type != '%') {
				count++;
			}
		}
		return count;
	}

	private String translate;
	private Object[] with;

	/**
	 * Constructs a new instance of {@code TranslatedText}.
	 * 
	 * @param translate
	 *            the content of this text, that being the translation key.
	 * @param with
	 *            the parameters to format this text with.
	 */
	public TranslatedText(@Nullable String translate,
			@Nullable Object... with) {
		super("translate");
		this.setTranslation(translate, with);
	}

	/**
	 * Constructs a new instance of {@code TranslatedText} with no content.
	 */
	public TranslatedText() {
		this(null);
	}

	/**
	 * Returns the content of this text, that being the translation key.
	 * 
	 * @return the content of this text, that being the translation key.
	 */
	@Override
	public String getContent() {
		return this.translate;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param translate
	 *            the content of this text, that being the translation key.
	 */
	@Override
	public void setContent(String translate) {
		this.translate = translate;
		super.setContent(translate);
	}

	/**
	 * Returns the parameters this text is being formatted with.
	 * <p>
	 * If a parameters is missing at translation time, the in game default is to
	 * leave it as an empty string.
	 * 
	 * @return the parameters this text is being formatted with.
	 */
	@Nullable
	public Object[] getWith() {
		return this.with;
	}

	/**
	 * Sets the parameters to format this text with.
	 * 
	 * @param with
	 *            the parameters to format this text with.
	 */
	public void setWith(@Nullable Object... with) {
		this.with = with;
		if (with == null || with.length == 0) {
			json.remove("with");
			return;
		}

		JsonArray jsonWith = new JsonArray();
		for (int i = 0; i < with.length; i++) {
			Object param = with[i];
			if (param instanceof Boolean) {
				jsonWith.add((Boolean) param);
			} else if (param instanceof Character) {
				jsonWith.add((Character) param);
			} else if (param instanceof JsonElement) {
				jsonWith.add((JsonElement) param);
			} else if (param instanceof Number) {
				jsonWith.add((Number) param);
			} else if (param instanceof String) {
				jsonWith.add((String) param);
			} else {
				throw new IllegalArgumentException("unsupported type");
			}
		}
		json.add("with", jsonWith);
	}

	/**
	 * Returns the formatted translation of this text.
	 * <p>
	 * If a parameters is missing at translation time, the in game default is to
	 * leave it as an empty string.
	 * 
	 * @return the formatted translation of this text.
	 */
	public String getTranslation() {
		int argCount = getArgCount(translate);
		Object[] args = new Object[argCount];
		for (int i = 0; i < args.length; i++) {
			if (i < with.length) {
				args[i] = with[i];
			} else {
				args[i] = new String();
			}
		}
		return String.format(translate, args);
	}

	/**
	 * Sets the translation of this text.
	 * 
	 * @param translate
	 *            the content of this text, that being the translation key.
	 * @param with
	 *            the parameters to format this text with.
	 */
	public void setTranslation(@Nullable String translate,
			@Nullable Object... with) {
		this.setContent(translate);
		this.setWith(with);
	}

}
