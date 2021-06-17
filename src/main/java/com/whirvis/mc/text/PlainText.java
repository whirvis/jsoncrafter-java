package com.whirvis.mc.text;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A container for plain Minecraft text.
 *
 * @see TranslatedText
 * @see KeybindText
 */
public class PlainText extends MinecraftText<String> {

	/**
	 * Constructs a new instance of {@code PlainText}.
	 * 
	 * @param text
	 *            the content of this text.
	 */
	public PlainText(@Nullable String text) {
		super("text");
		this.setContent(text);
	}

	/**
	 * Constructs a new instance of {@code PlainText} with no content.
	 */
	public PlainText() {
		this(null);
	}

}
