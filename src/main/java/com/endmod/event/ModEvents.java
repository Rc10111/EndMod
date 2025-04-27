package com.endmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ModEvents {
    //全局效果注册
    public static void registerevents() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            // 全局事件逻辑（如冷却计时）
        });
    }
}