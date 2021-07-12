package com.whirvis.mc.text.java;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * A container for translated Minecraft text.
 * <p>
 * Translated text works by specifying a translation key (which can be found in
 * the game's JSON language files), and then specifying "with" values. These are
 * values which are not tied to a language, but rather context for the message.
 * <p>
 * For example, when a player joins the server, the following is shown:
 * {@code [Player] joined the game}. The raw JSON text of this message in
 * English is {@code %s joined the game}. By setting the "with" value to
 * {@code Whirvis}, the output becomes: {@code Whirvis joined the game}.
 * <p>
 * We can accomplish translation with the following code:
 * 
 * <pre>
 * TranslatedText text = new TranslatedText("multiplayer.player.joined");
 * text.setWith("Whirvis");
 * System.out.println(text.toString());
 * </pre>
 * 
 * Depending on the client language, the message could be:
 * {@code "Whirvis joined the game"}, {@code "Whirvis a rejoint la partie"},
 * {@code "Whirvis se ha unido a la partida"}, etc.
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
