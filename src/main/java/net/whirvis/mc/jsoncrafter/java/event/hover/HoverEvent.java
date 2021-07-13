package net.whirvis.mc.jsoncrafter.java.event.hover;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.whirvis.mc.jsoncrafter.java.RichText;
import net.whirvis.mc.jsoncrafter.java.event.TextEvent;

/**
 * Allows for a tooltip to be displayed when the player hovers their mouse over
 * text.
 * <p>
 * More information on hover events can be found on the <a href=
 * "https://minecraft.fandom.com/wiki/Raw_JSON_text_format#Java_Edition">Minecraft
 * Wiki</a>.
 */
public class HoverEvent extends TextEvent {

	public static final String SHOW_TEXT = "show_text";
	public static final String SHOW_ITEM = "show_item";
	public static final String SHOW_ENTITY = "show_entity";

	private Object value;

	/**
	 * Constructs a new {@code HoverEvent}.
	 */
	public HoverEvent() {
		super("hoverEvent");
	}

	@Override
	public boolean supportsAction(@Nonnull String action) {
		switch (action) {
			case SHOW_TEXT:
			case SHOW_ITEM:
			case SHOW_ENTITY:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Sets the tooltip text that will be shown.
	 * <p>
	 * The appropriate call to {@link #setAction(String)} will be called
	 * automatically to update event's action to {@value #SHOW_TEXT}.
	 * 
	 * @param values
	 *            the text to show in the tooltip, {@code null} values are
	 *            ignored. Values are persuaded into rich text via
	 *            {@link RichText#persuade(Object)}.
	 * @return this event.
	 */
	@Nonnull
	public HoverEvent show(@Nullable Iterable<?> values) {
		this.setAction(SHOW_TEXT);
		List<RichText> texts = RichText.persuade(values);
		this.value = !texts.isEmpty() ? (RichText[]) texts.toArray() : null;
		return this;
	}

	/**
	 * Sets the tooltip text that will be shown.
	 * <p>
	 * The appropriate call to {@link #setAction(String)} will be called
	 * automatically to update event's action to {@value #SHOW_TEXT}.
	 * <p>
	 * This method is a shorthand for {@link #show(Iterable)}, with
	 * {@code values} being converted to a list.
	 * 
	 * @param values
	 *            the text to show in the tooltip, {@code null} values are
	 *            ignored. Values are persuaded into rich text via
	 *            {@link RichText#persuade(Object)}.
	 * @return this event.
	 */
	@Nonnull
	public final HoverEvent show(@Nullable Object... values) {
		return this.show(values != null ? Arrays.asList(values) : null);
	}

	/**
	 * Sets the tooltip that will be shown.
	 * <p>
	 * The appropriate call to {@link #setAction(String)} will be called for
	 * {@code tooltip} automatically, based on the value it returns for
	 * {@link HoverTooltip#getAction()}.
	 * 
	 * @param tooltip
	 *            the tooltip to show.
	 * @return this event.
	 */
	@Nonnull
	public HoverEvent show(@Nullable HoverTooltip tooltip) {
		this.setAction(tooltip.getAction());
		this.value = tooltip;
		return this;
	}

	@Override
	protected void serializeEvent(JsonObject json) {
		JsonElement contentsJson = null;
		if (value instanceof RichText[]) {
			RichText[] texts = (RichText[]) value;
			if (texts.length == 1) {
				contentsJson = texts[0].toJson();
			} else if (texts.length > 1) {
				JsonArray textsJson = new JsonArray();
				for (RichText text : texts) {
					textsJson.add(text.toJson());
				}
				contentsJson = textsJson;
			}
		} else if (value instanceof HoverTooltip) {
			contentsJson = ((HoverTooltip) value).toJson();
		}
		json.add("contents", contentsJson);
	}

}
