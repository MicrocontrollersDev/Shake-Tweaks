package dev.microcontrollers.shaketweaks.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ShakeTweaksConfig {
    public static final ConfigClassHandler<ShakeTweaksConfig> CONFIG = ConfigClassHandler.createBuilder(ShakeTweaksConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("shaketweaks.json"))
                    .build())
            .build();

    @SerialEntry public boolean disableScreenBobbing = true;
    @SerialEntry public boolean disableHandBobbing = false;
    @SerialEntry public boolean disableMapBobbing = true;
    @SerialEntry public boolean disableHandDamage = false;
    @SerialEntry public boolean disableScreenDamage = false;

    @SuppressWarnings("deprecation")
    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.translatable("shake-tweaks.shake-tweaks"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("shake-tweaks.shake-tweaks"))
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("shake-tweaks.disable-screen-bobbing"))
                                .description(OptionDescription.of(Text.translatable("shake-tweaks.disable-screen-bobbing.description")))
                                .binding(defaults.disableScreenBobbing, () -> config.disableScreenBobbing, newVal -> config.disableScreenBobbing = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("shake-tweaks.disable-hand-bobbing"))
                                .description(OptionDescription.of(Text.translatable("shake-tweaks.disable-hand-bobbing.description")))
                                .binding(defaults.disableHandBobbing, () -> config.disableHandBobbing, newVal -> config.disableHandBobbing = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("shake-tweaks.disable-map-bobbing"))
                                .description(OptionDescription.of(Text.translatable("shake-tweaks.disable-map-bobbing.description")))
                                .binding(defaults.disableMapBobbing, () -> config.disableMapBobbing, newVal -> config.disableMapBobbing = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("shake-tweaks.disable-screen-damage-tilt"))
                                .description(OptionDescription.of(Text.translatable("shake-tweaks.disable-screen-damage-tilt.description")))
                                .binding(defaults.disableScreenDamage, () -> config.disableScreenDamage, newVal -> config.disableScreenDamage = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.translatable("shake-tweaks.disable-hand-damage-tilt"))
                                .description(OptionDescription.of(Text.translatable("shake-tweaks.disable-hand-damage-tilt.description")))
                                .binding(defaults.disableHandDamage, () -> config.disableHandDamage, newVal -> config.disableHandDamage = newVal)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
        )).generateScreen(parent);
    }

}
