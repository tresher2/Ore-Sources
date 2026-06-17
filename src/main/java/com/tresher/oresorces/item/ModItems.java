package com.tresher.oresorces.item;

import com.tresher.oresorces.OreSources;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OreSources.MOD_ID);

    public static final DeferredItem<Item> STRANGEITEM = ITEMS.register("strangeitem",
            () -> new Item(new Item.Properties()));
    //дублировать строчку выше, что бы создать ещё предмет, тут можн использовать _,
    // надо ещё прописать в меню,
    // в языках (через зяпятую) item.oresources.strangeitem = то же имя, что и в регистре
    // models/ если перетащить json в item, то можно создать дубликат и переименовать в тоже имя
    // в моделях указать текстуру



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
