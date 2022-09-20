package me.blazingtide.phoenix.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;

public interface ConfigButtonReference<T extends Event, K extends ConfigurationSection> {

    T getEvent();

    K getSection();

    static <T extends Event, K extends ConfigurationSection> ConfigButtonReference<T, K> of(T event, K section) {
        return new ConfigButtonReference<T, K>() {
            @Override
            public T getEvent() {
                return event;
            }

            @Override
            public K getSection() {
                return section;
            }
        };
    }

}
