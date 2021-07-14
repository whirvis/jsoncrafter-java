package net.whirvis.mc.jsoncrafter.java;

import javax.annotation.Nonnull;

/**
 * Displays the name of the button that is currently bound to a certain
 * configurable control. This uses the client's own control scheme. As such, if
 * two players with different control schemes are logged into the same server,
 * each will see their own keybind.
 * <p>
 * Keybind text works by specifying a keybind identifier. These values represent
 * what key a configurable control is bound to for a client. For example, the
 * button a player uses to open their inventory. In game, these keys are also
 * rendered in lowercase.
 * <p>
 * We can accomplish keybinds with the following code:
 * 
 * <pre>
 * KeybindText text = new KeybindText("key.inventory");
 * System.out.println(text.toString());
 * </pre>
 * 
 * More info on keybinds can be found on the <a href=
 * "https://minecraft.fandom.com/wiki/Raw_JSON_text_format#Keybind">Minecraft
 * Wiki</a>.
 * 
 * @see PlainText
 * @see TranslatedText
 */
public class KeybindText extends RichText {

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
