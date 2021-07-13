package net.whirvis.mc.jsoncrafter.java;

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
public class PlainText extends RichText {

	/**
	 * Constructs a new instance of {@code PlainText}.
	 * 
	 * @param value
	 *            the content of this text, must be serializable to JSON.
	 * @throws NullPointerException
	 *             if {@code text} is {@code null}.
	 */
	public PlainText(@Nonnull Object value) {
		super("text", value);
	}

}
