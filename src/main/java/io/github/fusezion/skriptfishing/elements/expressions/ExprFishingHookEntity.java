package io.github.fusezion.skriptfishing.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fishing Hooked Entity")
@Description("Returns the hooked entity of the fishing hook.")
@Examples({
		"on fish:",
		"\tif hooked entity of fishing hook is a player:",
		"\t\tteleport hooked entity of fishing hook to player"
})
@Since("INSERT VERSION")
public class ExprFishingHookEntity extends SimplePropertyExpression<FishHook, Entity> {

	static {
		register(ExprFishingHookEntity.class,Entity.class, "hook[ed] entity", "fishinghooks");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if(!getParser().isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("fishing hook entity expression can only be used within a fishing event", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return super.init(exprs, matchedPattern, isDelayed, parseResult);
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		if(mode == ChangeMode.SET && delta == null) return;

		FishHook[] hooks = getExpr().getArray(event);
		switch (mode) {
			case SET:
				for (FishHook fishHook : hooks)
					fishHook.setHookedEntity((Entity) delta[0]);
				break;
			case DELETE:
				for (FishHook fishHook : hooks)
					if(fishHook.getHookedEntity() != null && !(fishHook.getHookedEntity() instanceof Player))
						fishHook.getHookedEntity().remove();
				break;
			default:
				assert false;
		}
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch (mode) {
			case DELETE:
			case SET:
				return CollectionUtils.array(Entity.class);
			default:
				return null;
		}
	}

	@Override
	@Nullable
	public Entity convert(FishHook fishHook) {
		return fishHook.getHookedEntity();
	}

	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	@Override
	protected String getPropertyName() {
		return "hooked entity";
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "hooked entity of " + getExpr().toString(event, debug);
	}
}
