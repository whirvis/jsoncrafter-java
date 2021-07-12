package com.whirvis.mc.text.java;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Displays a translated piece of text from the currently selected language.
 * This uses the client's selected language, so if players with their games set
 * to different languages are logged into the same server, each will see the
 * component in their own language.
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
public class TranslatedText extends MinecraftText {

	private Object[] with;

	/**
	 * Constructs a new instance of {@code TranslatedText}.
	 * 
	 * @param translate
	 *            the translation key.
	 * @param with
	 *            the parameters to format this text with. Values will be
	 *            converted to JSON via {@link #toJsonElement(Object)}.
	 * @throws NullPointerException
	 *             if {@code translate} is {@code null}.
	 */
	public TranslatedText(@Nonnull String translate, @Nullable Object... with) {
		super("translate", translate);
		this.setWith(with);
	}

	/**
	 * Sets the parameters to format this text with.
	 * 
	 * @param with
	 *            the parameters to format this text with. Values will be
	 *            converted to JSON via {@link #toJsonElement(Object)}.
	 * @return this text.
	 */
	@Nonnull
	public TranslatedText setWith(@Nullable Object... with) {
		this.with = with;
		return this;
	}

	/**
	 * Sets the translation of this text.
	 * 
	 * @param translate
	 *            the translation key.
	 * @param with
	 *            the parameters to format this text with. Values will be
	 *            converted to JSON via {@link #toJsonElement(Object)}.
	 * @return this text.
	 */
	public TranslatedText setTranslation(@Nullable String translate,
			@Nullable Object... with) {
		this.setContent(translate);
		this.setWith(with);
		return this;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = super.toJson();
		if (with == null || with.length <= 0) {
			return json;
		}

		JsonArray jsonWith = new JsonArray();
		for (int i = 0; i < with.length; i++) {
			jsonWith.add(toJsonElement(with[i]));
		}
		json.add("with", jsonWith);
		return json;
	}

}
