package possibletriangle.skygrid.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import possibletriangle.skygrid.util.loading.DimensionConfig;
import possibletriangle.skygrid.util.loading.DimensionLoader;

import java.util.concurrent.CompletableFuture;

public class DimensionConfigArgument implements ArgumentType<DimensionConfig> {

    private DimensionConfigArgument() {}

    public static final DynamicCommandExceptionType NOT_FOUND = new DynamicCommandExceptionType(name -> new TranslationTextComponent("argument.skygrid.config.invalid", name));

    public DimensionConfig parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation name = ResourceLocation.read(reader);
        return DimensionLoader.find(name).orElseThrow(() -> NOT_FOUND.create(name));
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.func_212476_a(DimensionLoader.configKeys(), builder);
    }

    public static DimensionConfigArgument getConfig() {
        return new DimensionConfigArgument();
    }

    public static DimensionConfig getConfigArgument(CommandContext<CommandSource> context, String key) {
        return context.getArgument(key, DimensionConfig.class);
    }

}
