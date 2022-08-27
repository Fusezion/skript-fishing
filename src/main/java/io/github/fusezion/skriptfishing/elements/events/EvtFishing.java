package io.github.fusezion.skriptfishing.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.util.StringUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.eclipse.jdt.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvtFishing extends SkriptEvent {

	static {

		Skript.registerEvent("Fishing", EvtFishing.class, PlayerFishEvent.class, "[player] fish[ing] [state[s] [of] %-fishingstates%]");

		EventValues.registerEventValue(PlayerFishEvent.class, FishHook.class, new Getter<FishHook, PlayerFishEvent>() {
			@Override
			public FishHook get(PlayerFishEvent event) {
				return event.getHook();
			}
		}, EventValues.TIME_NOW);

		EventValues.registerEventValue(PlayerFishEvent.class, State.class, new Getter<State, PlayerFishEvent>() {
			@Override
			public State get(PlayerFishEvent event) {
				return event.getState();
			}
		}, EventValues.TIME_NOW);

		EventValues.registerEventValue(PlayerFishEvent.class, Entity.class, new Getter<Entity, PlayerFishEvent>() {
			@Override
			@Nullable
			public Entity get(PlayerFishEvent event) {
				return event.getCaught();
			}
		}, EventValues.TIME_NOW);

	}

	private List<State> states = new ArrayList<>();

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		if (args[0] != null) states = Arrays.asList(((Literal<State>) args[0]).getAll());
		return true;
	}

	@Override
	public boolean check(Event event) {
		return states.isEmpty() || states.contains(((PlayerFishEvent) event).getState());
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return states.isEmpty() ? "fishing" : "fishing states of " + StringUtils.join(states);
	}
}
