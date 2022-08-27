package io.github.fusezion.skriptfishing.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.entity.FishHook;
import org.bukkit.event.player.PlayerFishEvent;

@Name("Fish hook is in Open Water")
@Description("Check whether or not the fish hook is in open water.")
@Examples({
	"on player fishing:",
	"\tif fish hook is in open water:",
	"\t\tsend \"You will catch a shark soon!\""
})
@Events("fishing")
@Since("1.0")
public class CondIsInOpenWater extends PropertyCondition<FishHook> {

	static {
		register(CondIsInOpenWater.class, PropertyType.BE, "in open water", "fishinghooks");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if(!getParser().isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("in open water condition can only be used within a fishing event", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		return super.init(exprs, matchedPattern, isDelayed, parseResult);
	}

	@Override
	public boolean check(FishHook fishHook) {
		return fishHook.isInOpenWater();
	}

	@Override
	protected String getPropertyName() {
		return "in open water";
	}
}
