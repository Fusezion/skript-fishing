package io.github.fusezion.skriptfishing.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fishing Hook")
@Description("The <a href='classes.html#entity'>fishing hook</a> in a fishing event.")
@Examples({
		"on fishing:",
		"\tteleport player to fishing hook"
})
@Events("fishing")
@Since("1.0")
public class ExprFishingHook extends EventValueExpression<FishHook> {

	static {
		Skript.registerExpression(ExprFishingHook.class, FishHook.class, ExpressionType.SIMPLE, "[the] [event-]fish[ing]( |-)hook");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
		if(!getParser().isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("fishing hook expression can only be used within a fishing event", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return super.init(exprs, matchedPattern, isDelayed, parser);
	}

	public ExprFishingHook() {
		super(FishHook.class);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "the fishing hook";
	}

}
