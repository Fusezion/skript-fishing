package io.github.fusezion.skriptfishing.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Events;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fishing Hook Apply Lure")
@Description("Returns whether the lure enchantment should be applied to reduce the wait time.")
@Examples({
	"on player fishing:",
	"\tset apply lure enchantment of fishing hook to false"
})
@Events("fishing")
@Since("1.0")
public class ExprFishingApplyLure extends SimplePropertyExpression<FishHook, Boolean> {

	static {
		register(ExprFishingApplyLure.class, Boolean.class, "apply lure [enchant[ment]]", "fishinghooks");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if(!getParser().isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("fishing apply lure expression can only be used within a fishing event", ErrorQuality.SEMANTIC_ERROR);
			return false;
		}
		setExpr((Expression<FishHook>) exprs[0]);
		return true;
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, ChangeMode mode) {
		if(delta[0] == null) return;
		for (FishHook fishHook : getExpr().getArray(e)
		     ) {
			fishHook.setApplyLure((Boolean) delta[0]);
		}
	}

	@Override
	public @Nullable Boolean convert(FishHook fishHook) {
		return fishHook.getApplyLure();
	}

	@Override
	public @Nullable Class<?>[] acceptChange(ChangeMode mode) {
		return mode == ChangeMode.SET ? CollectionUtils.array(Boolean.class) : null;
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}

	@Override
	protected String getPropertyName() {
		return "apply lure of fishing hook";
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "apply lure of " + getExpr().toString(e, debug);
	}
}
