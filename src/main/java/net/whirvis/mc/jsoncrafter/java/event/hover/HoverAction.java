package net.whirvis.mc.jsoncrafter.java.event.hover;

import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.gson.JsonObject;

/**
 * A container for data of a {@link HoverEvent} action.
 */
public abstract class HoverAction {

	private final String action;

	/**
	 * Constructs a new {@code HoverAction}.
	 * 
	 * @param action
	 *            the action name.
	 * @throws NullPointerException
	 *             if {@code action} is {@code null}.
	 */
	public HoverAction(@Nonnull String action) {
		this.action = Objects.requireNonNull(action, "action");
	}

	/**
	 * Returns the action name.
	 * 
	 * @return the action name.
	 */
	@Nonnull
	public final String getAction() {
		return this.action;
	}

	/**
	 * Converts this hover action to JSON.
	 * 
	 * @return the encoded JSON.
	 */
	@Nonnull
	protected abstract JsonObject toJson();

}
