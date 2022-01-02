package net.whirvis.mc.jsoncrafter.java;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Displays a translated piece of text from the currently selected language.
 * This uses the client's selected language. As such, if two players have their
 * games set to different languages on the same server, each will see the
 * message in their own language.
 * <p>
 * Translated text works by specifying a translation key (which can be found in
 * the game's JSON language files), and then specifying "with" values. These are
 * values which are not tied to a language, but rather context for the message.
 * <p>
 * For example, when a player joins the server, the following is shown:
 * {@code [Player] joined the game}. The raw JSON text of this message in
 * English is {@code %s joined the game}. By setting the "with" value to
 * {@code Whirvis}, the output becomes: {@code Whirvis joined the game}.
 * Depending on the client language, the message could also be:
 * {@code Whirvis a rejoint la partie},
 * {@code Whirvis se ha unido a la partida}, etc.
 * <p>
 * Regular text can also be used in place of a translation key. Shall a
 * translation key be absent from Minecraft's current language file, it will be
 * displayed as given. When this happens, format specifiers (such as {@code %s})
 * are still functional.
 * <p>
 * We can accomplish translation with the following code:
 * 
 * <pre>
 * TranslatedText text = new TranslatedText("multiplayer.player.joined");
 * text.setWith("Whirvis");
 * System.out.println(text.toString());
 * </pre>
 * 
 * More info on translations can be found on the <a href=
 * "https://minecraft.fandom.com/wiki/Raw_JSON_text_format#Translated_Text">Minecraft
 * Wiki</a>.
 * 
 * @see PlainText
 * @see KeybindText
 */
public class TranslatedText extends RichText {

	private Object[] with;

	/**
	 * Constructs a new instance of {@code TranslatedText}.
	 * 
	 * @param translate
	 *            the translation key.
	 * @param with
	 *            the parameters to format this text with, must be serializable
	 *            to JSON.
	 * @throws NullPointerException
	 *             if {@code translate} is {@code null}.
	 */
	public TranslatedText(@NotNull String translate, @Nullable Object... with) {
		super("translate", translate);
		this.setWith(with);
	}

	/**
	 * Sets the parameters to format this text with.
	 * 
	 * @param with
	 *            the parameters to format this text with, must be serializable
	 *            to JSON.
	 * @return this text.
	 */
	@NotNull
	public TranslatedText setWith(@Nullable Object... with) {
		this.with = with;
		return this;
	}

	/**
	 * Sets the translation of this text.
	 * <p>
	 * This method is a shorthand for {@link #setContent(Object)} and
	 * {@link #setWith(Object...)}, with {@code translate} being set to the
	 * content of this text.
	 * 
	 * @param translate
	 *            the translation key.
	 * @param with
	 *            the parameters to format this text with, must be serializable
	 *            to JSON.
	 * @return this text.
	 */
	public final TranslatedText setTranslation(@Nullable String translate,
			@Nullable Object... with) {
		this.setContent(translate);
		this.setWith(with);
		return this;
	}

	@Override
	protected void serializeText(JsonObject json) {
		if (with == null || with.length <= 0) {
			return;
		}
		JsonArray jsonWith = new JsonArray();
		for (int i = 0; i < with.length; i++) {
			jsonWith.add(GSON.toJsonTree(with[i]));
		}
		json.add("with", jsonWith);
	}

}
