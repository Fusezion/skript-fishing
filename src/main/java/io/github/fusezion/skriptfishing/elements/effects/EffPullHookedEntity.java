package io.github.fusezion.skriptfishing.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.eclipse.jdt.annotation.Nullable;

@Name("Pull Hooked Entity")
@Description("Pull the hooked entity to the caster of this fish hook.")
@Examples({
		"on player fishing",
		"\tif fishing state is caught_entity",
		"\t\tpull hooked entity"
})
@Events("fishing")
@Since("1.1")
public class EffPullHookedEntity extends Effect {

	static {
		Skript.registerEffect(EffPullHookedEntity.class, "pull hook[ed] entity [1:of %fishinghooks%]");
	}

	private Expression<FishHook> fishHook;


	@Override
	@SuppressWarnings({"null", "unchecked"})
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (!getParser().isCurrentEvent(PlayerFishEvent.class) && parseResult.mark == 0) {
			Skript.error("\"The 'pull hooked entity' effect can either be used in the fishing event or by providing a fishing hook\"");
			return false;
		}
		fishHook = (Expression<FishHook>) exprs[0];
		return true;
	}

	@Override
	protected void execute(Event event) {
		for (FishHook fh : fishHook.getArray(event)) {
			fh.pullHookedEntity();
		}
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "pull hooked entity of " + fishHook.toString(event, debug);
	}
}
