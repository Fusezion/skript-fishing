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
		Version minimumSupportedVersion = new Version(2,6,1);
		if (skript == null) {
			// Skript doesn't exist within the server plugins folder
			getLogger().severe("Could not find Skript! Make sure you have it installed. Disabling...");
			getLogger().severe("skript-fishing requires Skript " + minimumSupportedVersion + " or newer! Download Skript releases at https://github.com/SkriptLang/Skript/releases");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else if (!skript.isEnabled()) {
			// Skript is disabled on the server
			getLogger().severe("Skript failed to properly enable and is disabled on the server. Disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else if (Skript.getVersion().isSmallerThan(minimumSupportedVersion)) {
			// Current Skript version is below minimum required version
			getLogger().severe("You're running an unsupported Skript version (" + Skript.getVersion() + ")! Disabling...");
			getLogger().severe("skript-fishing requires Skript " + minimumSupportedVersion + " or newer! Download Skript releases at https://github.com/SkriptLang/Skript/releases");
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
