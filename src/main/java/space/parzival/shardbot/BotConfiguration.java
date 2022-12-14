package space.parzival.shardbot;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import space.parzival.shardbot.properties.ClientProperties;
import space.parzival.discord.shared.base.types.Command;
import space.parzival.discord.shared.base.types.EventListener;
import space.parzival.discord.shared.base.utils.Commands;

@Slf4j
@Configuration
@Profile("!test")
public class BotConfiguration implements InitializingBean {
    
    @Autowired
    private ClientProperties clientProperties;

    @Autowired
    private List<? extends Command> commands;

    @Autowired
    private List<? extends EventListener> events;

    @Override
    public void afterPropertiesSet() throws Exception {

        JDA client = JDABuilder.createDefault(clientProperties.getToken())
                .build();

        // remove previously registered commands
        Commands.registerCommands(commands, client);

        // register new commands & event handlers
        events.forEach(client::addEventListener);
        log.info("Registered {} events and {} commands.", events.size(), commands.size());

    }

}
