package io.github.fusezion.skriptfishing.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fishing State")
@Description("The fishing state of a fishing event.")
@Examples("fishing state is failed or in ground")
@Since("1.0")
public class ExprFishingState extends EventValueExpression<State> {

	static {
		Skript.registerExpression(ExprFishingState.class, State.class, ExpressionType.SIMPLE, "[the] [evnet-]fish[ing]( |-)state");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
		if(!getParser().isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("fishing state expression can only be used within a fishing event", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return super.init(exprs, matchedPattern, isDelayed, parser);
	}

	public ExprFishingState() {
		super(State.class);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "the fishing state";
	}
}