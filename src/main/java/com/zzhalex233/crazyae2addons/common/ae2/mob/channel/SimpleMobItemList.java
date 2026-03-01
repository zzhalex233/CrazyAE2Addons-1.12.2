package com.zzhalex233.crazyae2addons.common.ae2.mob.channel;

import appeng.api.config.FuzzyMode;
import appeng.api.storage.data.IItemList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

final class SimpleMobItemList implements IItemList<AEMobStack> {

    private final Map<AEMobStack, AEMobStack> map = new HashMap<>();

    @Override
    public void add(AEMobStack option) {
        if (option == null) return;
        AEMobStack existing = map.get(option);
        if (existing == null) {
            AEMobStack c = option.copy();
            map.put(c, c);
        } else {
            existing.add(option);
        }
    }

    @Override
    public void addStorage(AEMobStack option) {
        add(option);
    }

    @Override
    public void addCrafting(AEMobStack option) {
        if (option == null) return;
        AEMobStack c = option.copy();
        c.setCraftable(true);
        add(c);
    }

    @Override
    public void addRequestable(AEMobStack option) {
        if (option == null) return;
        add(option);
    }

    @Override
    public AEMobStack findPrecise(AEMobStack option) {
        if (option == null) return null;
        AEMobStack existing = map.get(option);
        return existing;
    }

    @Override
    public Collection<AEMobStack> findFuzzy(AEMobStack option, @Nonnull FuzzyMode fuzzy) {
        if (option == null) return Collections.emptyList();
        List<AEMobStack> out = new ArrayList<>();
        for (AEMobStack v : map.values()) {
            if (v.fuzzyComparison(option, fuzzy)) {
                out.add(v.copy());
            }
        }
        return out;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void resetStatus() {
        for (AEMobStack v : map.values()) {
            v.setCraftable(false);
            v.setCountRequestable(0);
        }
    }

    @Override
    public Iterator<AEMobStack> iterator() {
        final Iterator<AEMobStack> it = map.values().iterator();
        return new Iterator<AEMobStack>() {
            @Override public boolean hasNext() { return it.hasNext(); }
            @Override public AEMobStack next() { return it.next().copy(); }
        };
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    @Nullable
    public AEMobStack getFirstItem() {
        Iterator<AEMobStack> it = map.values().iterator();
        return it.hasNext() ? it.next().copy() : null;
    }
}
