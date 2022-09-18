package me.blazingtide.phoenix.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;

public interface ConfigButtonReference<T extends Event, K extends ConfigurationSection> {

    T getEvent();

    K getSection();

}
