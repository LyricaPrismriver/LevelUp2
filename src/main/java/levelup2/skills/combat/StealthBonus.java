package levelup2.skills.combat;

import levelup2.skills.BaseSkill;
import levelup2.util.Library;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StealthBonus extends BaseSkill {

    @Override
    public String getSkillName() {
        return "levelup:stealth";
    }

    @Override
    public byte getSkillType() {
        return 2;
    }

    @Override
    public boolean hasSubscription() {
        return true;
    }

    @Override
    public ItemStack getRepresentativeStack() {
        ItemStack stack = new ItemStack(Items.POTIONITEM);
        PotionUtils.addPotionToItemStack(stack, PotionType.getPotionTypeForName("invisibility"));
        return stack;
    }

    @SubscribeEvent
    public void onTargetSet(LivingSetAttackTargetEvent evt) {
        if (!isActive()) return;
        if (evt.getTarget() instanceof EntityPlayer && evt.getEntityLiving() instanceof EntityMob) {
            if (evt.getTarget().isSneaking() && !StealthLib.entityHasVisionOf(evt.getEntityLiving(), (EntityPlayer)evt.getTarget())
                    && evt.getEntityLiving().getRevengeTimer() != ((EntityMob) evt.getEntityLiving()).ticksExisted) {
                ((EntityMob) evt.getEntityLiving()).setAttackTarget(null);
            }
        }
    }
}
