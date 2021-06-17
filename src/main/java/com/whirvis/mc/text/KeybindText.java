package com.whirvis.mc.text;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A container for a player's keybind.
 * 
 * @see PlainText
 * @see TranslatedText
 */
public class KeybindText extends MinecraftText<String> {

	/**
	 * Constructs a new instance of {@code KeybindText}.
	 * 
	 * @param key
	 *            the content of this text, that being the name of this keybind.
	 */
	public KeybindText(@Nullable String key) {
		super("keybind");
		this.setContent(key);
	}

	/**
	 * Constructs a new instance of {@code KeybindText} with no content.
	 */
	public KeybindText() {
		this(null);
	}

}
