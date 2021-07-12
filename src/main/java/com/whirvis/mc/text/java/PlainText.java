package com.whirvis.mc.text.java;

import javax.annotation.Nonnull;

/**
 * Displays plain text.
 * <p>
 * More info on plain text can be found on the <a href=
 * "https://minecraft.fandom.com/wiki/Raw_JSON_text_format#Plain_Text">Minecraft
 * Wiki</a>.
 * 
 * @see TranslatedText
 * @see KeybindText
 */
public class PlainText extends MinecraftText {

	/**
	 * Constructs a new instance of {@code PlainText}.
	 * 
	 * @param value
	 *            the content of this text, will be converted into JSON via
	 *            {@link #toJsonElement(Object)}.
	 * @throws NullPointerException
	 *             if {@code text} is {@code null}.
	 */
	public PlainText(@Nonnull Object value) {
		super("text", value);
	}

}
