package net.whirvis.mc.jsoncrafter.java.event.hover;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.JsonObject;

import net.whirvis.mc.jsoncrafter.java.RichText;

/**
 * Shows an entity to the player when they hover their mouse over text.
 */
public class TooltipEntity extends HoverTooltip {

	private String name;
	private String type;
	private UUID id;

	/**
	 * Constructs a new {@code HoverShowEntity}.
	 * 
	 * @param name
	 *            the entity name. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @param type
	 *            the entity type. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @param id
	 *            the entity ID.
	 * @throws NullPointerException
	 *             if {@code id} is {@code null}.
	 */
	public TooltipEntity(@Nullable String name, @Nullable String type,
			@Nonnull UUID id) {
		super(HoverEvent.SHOW_ENTITY);
		this.setName(name);
		this.setType(type);
		this.setId(id);
	}

	/**
	 * Constructs a new {@code HoverShowEntity}.
	 * 
	 * @param type
	 *            the entity type. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @param id
	 *            the entity ID.
	 * @throws NullPointerException
	 *             if {@code id} is {@code null}.
	 */
	public TooltipEntity(@Nullable String type, @Nonnull UUID id) {
		this(null, type, id);
	}

	/**
	 * Constructs a new {@code HoverShowEntity}.
	 * 
	 * @param id
	 *            the entity ID.
	 * @throws NullPointerException
	 *             if {@code id} is {@code null}.
	 */
	public TooltipEntity(@Nonnull UUID id) {
		this(null, null, id);
	}

	/**
	 * Constructs a new {@code HoverShowEntity}.
	 * 
	 * @param name
	 *            the entity name. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @param type
	 *            the entity type. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @param uuid
	 *            the entity UUID string.
	 * @throws NullPointerException
	 *             if {@code uuid} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@code uuid} is not a valid UUID string.
	 */
	public TooltipEntity(@Nullable String name, @Nullable String type,
			@Nonnull String uuid) {
		super(HoverEvent.SHOW_ENTITY);
		this.setName(name);
		this.setType(type);
		this.setId(uuid);
	}

	/**
	 * Constructs a new {@code HoverShowEntity}.
	 * 
	 * @param type
	 *            the entity type. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @param uuid
	 *            the entity UUID string.
	 * @throws NullPointerException
	 *             if {@code uuid} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@code uuid} is not a valid UUID string.
	 */
	public TooltipEntity(@Nullable String type, @Nonnull String uuid) {
		this(null, type, uuid);
	}

	/**
	 * Constructs a new {@code HoverShowEntity}.
	 * 
	 * @param uuid
	 *            the entity UUID string.
	 * @throws NullPointerException
	 *             if {@code uuid} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@code uuid} is not a valid UUID string.
	 */
	public TooltipEntity(@Nonnull String uuid) {
		this(null, null, uuid);
	}

	/**
	 * Returns the entity name.
	 * 
	 * @return the entity name.
	 */
	@Nullable
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the entity name.
	 * 
	 * @param name
	 *            the entity name. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @return this tooltip.
	 */
	@Nonnull
	public TooltipEntity setName(@Nullable String name) {
		this.name = name;
		return this;
	}

	/**
	 * Returns the entity type.
	 * <p>
	 * In game, this value defaults to {@code minecraft:pig}.
	 * 
	 * @return the entity type.
	 */
	@Nonnull
	public String getType() {
		return RichText.nullFallback(type, "minecraft:pig");
	}

	/**
	 * Sets the entity type.
	 * 
	 * @param type
	 *            the entity type. May be {@code null} to have the parameter
	 *            left absent from the encoded JSON.
	 * @return this tooltip.
	 */
	@Nonnull
	public TooltipEntity setType(@Nullable String type) {
		this.type = type;
		return this;
	}

	/**
	 * Returns the entity ID.
	 * 
	 * @return the entity ID.
	 */
	@Nonnull
	public UUID getId() {
		return this.id;
	}

	/**
	 * Sets the entity ID.
	 * 
	 * @param id
	 *            the entity ID.
	 * @return this tooltip.
	 * @throws NullPointerException
	 *             if {@code id} is {@code null}.
	 */
	@Nonnull
	public TooltipEntity setId(@Nonnull UUID id) {
		this.id = Objects.requireNonNull(id, "id");
		return this;
	}

	/**
	 * Sets the entity ID.
	 * 
	 * @param uuid
	 *            the entity UUID string.
	 * @return this tooltip.
	 * @throws NullPointerException
	 *             if {@code uuid} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@code uuid} is not a valid UUID string.
	 */
	@Nonnull
	public TooltipEntity setId(@Nonnull String uuid) {
		Objects.requireNonNull(uuid, "uuid");
		UUID id = UUID.fromString(uuid);
		return this.setId(id);
	}

	@Override
	public JsonObject toJson() {
		JsonObject entityJson = new JsonObject();
		entityJson.addProperty("name", name);
		entityJson.addProperty("type", type);
		entityJson.addProperty("id", Objects.toString(id, null));
		return entityJson;
	}

}
