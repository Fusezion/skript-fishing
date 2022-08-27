package io.github.fusezion.skriptfishing;

import java.io.IOException;

import ch.njol.skript.util.Version;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class SkriptFishing extends JavaPlugin {

	@Nullable
	private static SkriptFishing instance;

	@Nullable
	private static SkriptAddon addonInstance;

	@Override
	public void onEnable() {
		Plugin skript = getServer().getPluginManager().getPlugin("Skript");
		Version MINIMAL_SKRIPT_VERSION = new Version(2,6,3);
		if (skript == null) {
			getLogger().severe("Could not find Skript! Make sure you have it installed and that it properly loaded, Disabling...");
			getLogger().severe("Download latest skript version: https://github/SkriptLang/Skript/releases");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else if (!skript.isEnabled()) { // Skript is not any version after 2.5.3 (aka 2.6)
			getLogger().severe("Could not load skript-fishing! Skript is disabled on the server, Disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else if (Skript.getVersion().isSmallerThan(MINIMAL_SKRIPT_VERSION)) { // Skript version is below, minimum requirement.
			getLogger().severe("You are running an unsupported version of Skript v" + Skript.getVersion() + ". Please update to at Skript " + MINIMAL_SKRIPT_VERSION + ", Disabling...");
			getLogger().severe("Download latest skript version: https://github/SkriptLang/Skript/releases");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		instance = this;

		try {
			getAddonInstance().loadClasses("io.github.fusezion.skriptfishing","elements");
			getAddonInstance().setLanguageFileDirectory("lang");
		} catch (IOException e) {
			getLogger().severe("An error occurred while trying to load the addon's elements. The addon will be disabled.");
			getLogger().severe("Printing StackTrace:");
			e.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
		}

	}

	public static SkriptFishing getInstance() {
		if(instance == null) {
			throw new IllegalStateException();
		}
		return instance;
	}
	public static SkriptAddon getAddonInstance() {
		if (addonInstance == null) {
			addonInstance = Skript.registerAddon(getInstance());
		}
		return addonInstance;
	}

}
