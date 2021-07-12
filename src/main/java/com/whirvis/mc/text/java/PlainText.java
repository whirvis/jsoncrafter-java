package com.whirvis.mc.text.java;

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
	 * @param value
	 *            the content of this text.
	 * @throws NullPointerException
	 *             if {@code text} is {@code null}.
	 */
	public PlainText(@Nonnull Object value) {
		super("text", value);
	}

}
