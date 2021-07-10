package com.whirvis.mc.text;

import javax.annotation.Nonnull;

/**
 * A container for plain Minecraft text.
 *
 * @see TranslatedText
 * @see KeybindText
 */
public class PlainText extends MinecraftText {

	/**
	 * Constructs a new instance of {@code PlainText}.
	 * 
	 * @param text
	 *            the content of this text.
	 * @throws NullPointerException
	 *             if {@code text} is {@code null}.
	 */
	public PlainText(@Nonnull String text) {
		super("text", text);
	}

}
