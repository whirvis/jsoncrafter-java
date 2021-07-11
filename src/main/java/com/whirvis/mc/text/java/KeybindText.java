package com.whirvis.mc.text.java;

import javax.annotation.Nonnull;

/**
 * A container for a player's keybind.
 * 
 * @see PlainText
 * @see TranslatedText
 */
public class KeybindText extends MinecraftText {

	/**
	 * Constructs a new instance of {@code KeybindText}.
	 * 
	 * @param keybind
	 *            the name of this keybind.
	 * @throws NullPointerException
	 *             if {@code key} is {@code null}.
	 */
	public KeybindText(@Nonnull String keybind) {
		super("keybind", keybind);
	}

}
