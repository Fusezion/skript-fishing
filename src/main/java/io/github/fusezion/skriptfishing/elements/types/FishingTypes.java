package io.github.fusezion.skriptfishing.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import org.bukkit.entity.FishHook;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.eclipse.jdt.annotation.Nullable;

public class FishingTypes {

	static {

		EnumUtils<State> fishStateUtils = new EnumUtils<>(State.class, "fishing states");
		Classes.registerClass(new ClassInfo<>(State.class, "fishingstate")
				.user("fish(ing)? ?states?")
				.name("Fishing State")
				.description("Represents the fishing state in a fishing event.")
				.usage(fishStateUtils.getAllNames())
				.since("1.0")
				.parser(new Parser<State>() {
					@Override
					@Nullable
					public State parse(String str, ParseContext context) {
						return fishStateUtils.parse(str);
						}

					@Override
					public String toString(State obj, int flags) {
							return fishStateUtils.toString(obj, flags);
						}

					@Override
					public String toVariableNameString(State obj) {
							return obj.name();
						}
				})
				.serializer(new EnumSerializer<>(State.class)));

		Classes.registerClass(new ClassInfo<>(FishHook.class, "fishinghook")
				.user("fish(ing)? ?hooks?")
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
					public String toString(FishHook obj, int flags) {
							return "fish hook";
						}

					@Override
					public String toVariableNameString(FishHook obj) {
							return "fish hook";
						}
				}));

	}

}
