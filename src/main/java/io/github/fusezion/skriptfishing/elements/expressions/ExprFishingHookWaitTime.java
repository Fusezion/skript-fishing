package io.github.fusezion.skriptfishing.elements.expressions;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fishing Hook Wait Time")
@Description({
		"Returns the minimum/maximum waiting time of the fishing hook. Default minimum value is 5 seconds and maximum is 30 seconds.",
		"NOTE: changing values in non sense results such as setting minimum higher than maximum will result in both min and max being set to the result value"
})
@Examples({
	"on fish:",
	"\tset max waiting time of fishing hook to 1 second # will also force set the minimum to 1 second"
})
@Events("fishing")
@Since("1.0")
public class ExprFishingHookWaitTime extends SimplePropertyExpression<FishHook, Timespan> {

	static {
		register(ExprFishingHookWaitTime.class, Timespan.class, "(max[imum]|min:min[imum]) wait[ing] time", "fishinghooks");
	}

	private static final int DEFAULT_MINIMUM_TIME = 100;
	private static final int DEFAULT_MAXIMUM_TIME = 600;

	private boolean isMin;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		isMin = parseResult.hasTag("min");
		return super.init(exprs, matchedPattern, isDelayed, parseResult);
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		if(mode != ChangeMode.RESET && delta[0] == null) return;

		int ticks = mode == ChangeMode.RESET ? (isMin ? DEFAULT_MINIMUM_TIME : DEFAULT_MAXIMUM_TIME) : (int) ((Timespan) delta[0]).getTicks_i();
		switch (mode) {
			case ADD:
				setWaitingTime(event, ticks, false);
				break;
			case REMOVE:
				setWaitingTime(event, (ticks * -1), false);
				break;
			case SET:
			case RESET:
				setWaitingTime(event, ticks, true);
				break;
		}
	}

	private void setWaitingTime(Event event, int value, boolean isSet) {
		for (FishHook hook : getExpr().getAll(event)) {
			if(isMin) {
				int newValue = Math.max((isSet ? 0 : hook.getMinWaitTime()) + value, 0);
				if (hook.getMaxWaitTime() < newValue)
					hook.setMaxWaitTime(newValue);
				hook.setMinWaitTime(newValue);
			} else {
				int newValue = Math.max((isSet ? 0 : hook.getMaxWaitTime()) + value, 0);
				if (hook.getMinWaitTime() > newValue)
					hook.setMinWaitTime(newValue);
				hook.setMaxWaitTime(newValue);
			}
		}
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch (mode) {
			case DELETE:
			case REMOVE_ALL:
				return null;
			default:
				return CollectionUtils.array(Timespan.class);
		}
	}

	@Override
	@Nullable
	public Timespan convert(FishHook fishHook) {
		return Timespan.fromTicks_i(isMin ? fishHook.getMinWaitTime() : fishHook.getMaxWaitTime());
	}

	@Override
	protected String getPropertyName() {
		return isMin ? "minimum" : "maximum" + " waiting time";
	}

	@Override
	public Class<? extends Timespan> getReturnType() {
		return Timespan.class;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return (isMin ? "minimum" : "maximum") + " waiting time of " + getExpr().toString(event, debug);
	}

}
