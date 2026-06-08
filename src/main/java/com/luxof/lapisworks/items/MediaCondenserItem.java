package com.luxof.lapisworks.items;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.utils.MediaHelper;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.items.magic.ItemMediaHolder;

import com.luxof.lapisworks.init.ModBlocks;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class MediaCondenserItem extends BlockItem {

    public MediaCondenserItem(Settings settings) {
        super(ModBlocks.MEDIA_CONDENSER, settings);
    }

    static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static MutableText mediaTooltip(Long media, Long max) {
        return Text.translatable(
                "hexcasting.tooltip.media_amount.advanced",
                Text.literal(decimalFormat.format((double)media / MediaConstants.DUST_UNIT))
                        .styled(s -> s.withColor(ItemMediaHolder.HEX_COLOR)),
                Text.translatable("hexcasting.tooltip.media",
                        Text.literal(decimalFormat.format((double)max / MediaConstants.DUST_UNIT))
                            .styled(s -> s.withColor(ItemMediaHolder.HEX_COLOR))),
                Text.literal(String.format("%d%%", media * 100 / max ))
                        .styled(s -> s.withColor(TextColor.fromRgb(MediaHelper.mediaBarColor(media, max))))
        );
    }
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        var media = NBTHelper.getLong(stack, "media", 0L);
        var max = NBTHelper.getLong(stack, "max", 64 * MediaConstants.DUST_UNIT);
        tooltip.add(mediaTooltip(media, max).formatted(Formatting.LIGHT_PURPLE));
    }
}
