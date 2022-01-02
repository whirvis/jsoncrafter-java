package net.whirvis.mc.jsoncrafter.java.event;

import java.net.MalformedURLException;
import java.net.URL;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Allows for events to occur when the player clicks on text. Usually, this
 * works only with chat messages and written books.
 * <p>
 * More information on click events can be found on the <a href=
 * "https://minecraft.fandom.com/wiki/Raw_JSON_text_format#Java_Edition">Minecraft
 * Wiki</a>.
 */
public class ClickEvent extends TextEvent {

	public static final String OPEN_URL = "open_url";
	public static final String RUN_COMMAND = "run_command";
	public static final String SUGGEST_COMMAND = "suggest_command";
	public static final String CHANGE_PAGE = "change_page";
	public static final String COPY_TO_CLIPBOARD = "copy_to_clipboard";

	private Object value;

	/**
	 * Constructs a new {@code ClickEvent} with the given course of action.
	 * 
	 * @param action
	 *            the name of the action.
	 * @throws IllegalArgumentException
	 *             if {@code action} is not supported.
	 */
	public ClickEvent(@Nullable String action) {
		super("clickEvent", action);
	}

	/**
	 * Constructs a new {@code ClickEvent}.
	 */
	public ClickEvent() {
		this(null);
	}

	@Override
	public boolean supportsAction(@NotNull String action) {
		switch (action) {
			case OPEN_URL:
			case RUN_COMMAND:
			case SUGGEST_COMMAND:
			case CHANGE_PAGE:
			case COPY_TO_CLIPBOARD:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Returns the text that will be pasted into the chatbar.
	 * 
	 * @return the text, {@code null} if no text.
	 * @throws IllegalStateException
	 *             if the value of this event is not text.
	 */
	@Nullable
	public String getText() {
		try {
			return (String) this.value;
		} catch (ClassCastException e) {
			throw new IllegalStateException("value not text", e);
		}
	}

	/**
	 * Sets the text that will be pasted into the chatbar.
	 * 
	 * @param text
	 *            the text, may be {@code null} for no text.
	 * @return this event.
	 */
	@NotNull
	public ClickEvent setText(@Nullable String text) {
		this.value = text;
		return this;
	}

	/**
	 * Returns the URL that will be opened.
	 * 
	 * @return the URL, {@code null} if no URL.
	 * @throws IllegalStateException
	 *             if the value of this event is not a URL.
	 */
	@Nullable
	public URL getURL() {
		try {
			return (URL) this.value;
		} catch (ClassCastException e) {
			throw new IllegalStateException("value not a URL", e);
		}
	}

	/**
	 * Sets the URL that will be opened.
	 * 
	 * @param url
	 *            the URL, may be {@code null} for no URL.
	 * @return this event.
	 */
	@NotNull
	public ClickEvent setURL(@Nullable URL url) {
		this.value = url;
		return this;
	}

	/**
	 * Sets the URL that will be opened.
	 * <p>
	 * This method is a shorthand for {@link #setURL(URL)}, with {@code url}
	 * being parsed into a URL.
	 * 
	 * @param url
	 *            the URL, may be {@code null} for no URL.
	 * @return this event.
	 * @throws IllegalArgumentException
	 *             if {@code url} is malformed.
	 */
	@NotNull
	public final ClickEvent setURL(@Nullable String url) {
		try {
			return this.setURL(new URL(url));
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Returns the book page that will be flipped to.
	 * 
	 * @return the book page, {@code -1} if no page.
	 * @throws IllegalStateException
	 *             if the value of this event is not a book page.
	 */
	public int getPage() {
		try {
			return value != null ? (Integer) this.value : -1;
		} catch (ClassCastException e) {
			throw new IllegalStateException("value not a page", e);
		}
	}

	/**
	 * Sets the book page to flip to when clicked.
	 * 
	 * @param page
	 *            the book page, may be {@code null} for no page.
	 * @return this event.
	 * @throws IllegalArgumentException
	 *             if {@code page} is negative.
	 */
	@NotNull
	public ClickEvent setPage(@Nullable Integer page) {
		if (page != null && page < 0) {
			throw new IllegalArgumentException("page < 0");
		}
		this.value = page;
		return this;
	}

	@Override
	protected void serializeEvent(JsonObject json) {
		JsonElement valueJson = null;
		if (value instanceof String) {
			valueJson = new JsonPrimitive((String) value);
		} else if (value instanceof URL) {
			valueJson = new JsonPrimitive(value.toString());
		} else if (value instanceof Integer) {
			valueJson = new JsonPrimitive((Integer) value);
		}
		json.add("value", valueJson);
	}

}
