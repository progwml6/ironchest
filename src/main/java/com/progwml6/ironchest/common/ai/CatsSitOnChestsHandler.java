package com.progwml6.ironchest.common.ai;

import com.progwml6.ironchest.IronChests;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.animal.Cat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.HashSet;

@EventBusSubscriber(modid = IronChests.MODID, bus = EventBusSubscriber.Bus.GAME)
public class CatsSitOnChestsHandler {

  @SubscribeEvent
  static void changeSittingTaskForOcelots(EntityTickEvent.Post evt) {
    if (evt.getEntity().tickCount < 5 && evt.getEntity() instanceof Cat cat) {
      HashSet<WrappedGoal> goals = new HashSet<>();

      for (WrappedGoal goal : cat.goalSelector.getAvailableGoals()) {
        if (goal.getGoal().getClass() == CatSitOnBlockGoal.class) {
          goals.add(goal);
        }
      }

      for (WrappedGoal goal : goals) {
        cat.goalSelector.removeGoal(goal.getGoal());
        cat.goalSelector.addGoal(goal.getPriority(), new IronChestCatSitOnBlockGoal(cat, 0.4F));
      }
    }
  }
}
