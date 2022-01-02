package net.whirvis.mc.jsoncrafter.java.event.hover;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonObject;

/**
 * A tooltip shown to the player.
 */
public abstract class HoverTooltip {

	private final String action;

	/**
	 * Constructs a new {@code HoverAction}.
	 * 
	 * @param action
	 *            the action name.
	 * @throws NullPointerException
	 *             if {@code action} is {@code null}.
	 */
	public HoverTooltip(@NotNull String action) {
		this.action = Objects.requireNonNull(action, "action");
	}

	/**
	 * Returns the action name.
	 * 
	 * @return the action name.
	 */
	@NotNull
	public final String getAction() {
		return this.action;
	}

	/**
	 * Serializes the tooltip.
	 * 
	 * @return the encoded JSON.
	 */
	@NotNull
	protected abstract JsonObject toJson();

}
