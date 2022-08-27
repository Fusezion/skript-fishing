package io.github.fusezion.skriptfishing.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import org.bukkit.entity.FishHook;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingTypes {

	static {

		// Only register if no other addons have registered this class
		if (Classes.getExactClassInfo(PlayerFishEvent.State.class) == null) {
			EnumUtils<PlayerFishEvent.State> fishStateUtils = new EnumUtils<>(PlayerFishEvent.State.class, "fishingstate");
			Classes.registerClass(new ClassInfo<>(PlayerFishEvent.State.class, "fishingstate")
					.user("fish(ing)? ?states?")
					.name("Fishing State")
					.description("Represents the fishing state in a <a href='events.html#fishing'>fishing</a> event.")
					.usage(fishStateUtils.getAllNames())
					.since("1.0")
					.parser(new Parser<PlayerFishEvent.State>() {
						@Override
						public PlayerFishEvent.State parse(String s, ParseContext context) {
							return fishStateUtils.parse(s);
						}

						@Override
						public String toString(PlayerFishEvent.State o, int flags) {
							return fishStateUtils.toString(o, flags);
						}

						@Override
						public String toVariableNameString(PlayerFishEvent.State o) {
							return o.name();
						}
					})
					.serializer(new EnumSerializer<>(PlayerFishEvent.State.class)));
		}
		// Only register if no other addons have registered this class
		if (Classes.getExactClassInfo(FishHook.class) == null) {
			Classes.registerClass(new ClassInfo<>(FishHook.class, "fishinghook")
					.user("fish(ing)? ?hooks")
					.name("Fishing Hook")
					.description("Represents the fishing hook in a <a href='events.html#fishing'>fishing</a> event.")
					.defaultExpression(new EventValueExpression<>(FishHook.class))
					.since("1.0")
					.parser(new Parser<FishHook>() {
						@Override
						public boolean canParse(ParseContext context) {
							return false;
						}

						@Override
						public String toString(FishHook o, int flags) {
							return "fishing hook";
						}

						@Override
						public String toVariableNameString(FishHook o) {
							return "fish hook " + o.toString();
						}
					}));
		}

	}

}
