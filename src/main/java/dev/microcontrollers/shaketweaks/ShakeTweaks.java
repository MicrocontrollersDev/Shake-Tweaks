package dev.microcontrollers.shaketweaks;

import dev.microcontrollers.shaketweaks.config.ShakeTweaksConfig;
import net.fabricmc.api.ModInitializer;

public class ShakeTweaks implements ModInitializer {
	@Override
	public void onInitialize() {
		ShakeTweaksConfig.CONFIG.load();
	}

}