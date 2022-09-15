package io.github.fusezion.skriptfishing.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.Experience;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fishing Experience")
@Description("get/modify the experience gained from fishing")
@Examples("set fishing experience to 0")
@Events("fishing")
@Since("1.0")
public class ExprFishingExperience extends SimpleExpression<Experience> {

	static {
		Skript.registerExpression(ExprFishingExperience.class, Experience.class, ExpressionType.SIMPLE, "fish[ing] [e]xp[erience]");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if(!getParser().isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("fishing experience expression can only be used within a fishing event", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return true;
	}

	@Override
	@Nullable
	protected Experience[] get(Event event) {
		return new Experience[] {new Experience(((PlayerFishEvent) event).getExpToDrop())};
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		if(!(event instanceof PlayerFishEvent)) return;

		int value;
		if(delta[0] == null) {
			value = 0;
		} else {
			value = ((PlayerFishEvent) event).getExpToDrop();
			for (Object o : delta) {
				int newValue = o instanceof Experience ? ((Experience) o).getXP() : ((Number) o).intValue();
				switch (mode) {
					case ADD :
						value += newValue;
						break;
					case SET:
						value = newValue;
						break;
					case REMOVE:
					case REMOVE_ALL:
						value -= newValue;
						break;
					case RESET:
					case DELETE:
						assert false;
						break;
				}
			}
		}

		value = Math.max(0, Math.round(value));
		((PlayerFishEvent) event).setExpToDrop(value);

	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch (mode) {
			case ADD:
			case DELETE:
			case REMOVE:
			case REMOVE_ALL:
				return new Class[]{Experience.class, Number[].class};
			case SET:
				return new Class[]{Experience.class, Number.class};
			case RESET:
				return null;
		}
		return null;
	}

	@Override
	public Class<? extends Experience> getReturnType() {
		return Experience.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public String toString(Event e, boolean Debug) {
		return "fishing experience";
	}
}
