package com.komodin.sonar.slacknotifier.extension.task;

import com.komodin.sonar.slacknotifier.SlackNotifierPlugin;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.CONFIG;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.DEFAULT_CHANNEL;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.ENABLED;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.HOOK;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.SONARQUBE_URL;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.INCLUDE_BRANCH;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.INCLUDE_GATE;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.NOTIFICATION_TEMPLATE;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.PROXY_IP;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.PROXY_PORT;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.PROXY_PROTOCOL;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.SERVER_TOKEN;
import static com.komodin.sonar.slacknotifier.common.SlackNotifierProp.USER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SlackNotifierPluginTest {

    private final SlackNotifierPlugin plugin = new SlackNotifierPlugin();

    @Test
    public void define_expectedExtensionsAdded() {

        final Plugin.Context mockContext = mock(Plugin.Context.class);
        this.plugin.define(mockContext);
        final ArgumentCaptor<List> arg = ArgumentCaptor.forClass(List.class);
        verify(mockContext, times(1)).addExtensions(arg.capture());

        final List extensions = arg.getValue();
        Assert.assertEquals(14, extensions.size());
        Assert.assertEquals(HOOK.property(), ((PropertyDefinition) extensions.get(0)).key());
        Assert.assertEquals(USER.property(), ((PropertyDefinition) extensions.get(1)).key());
        Assert.assertEquals(SONARQUBE_URL.property(), ((PropertyDefinition) extensions.get(2)).key());
        Assert.assertEquals(PROXY_IP.property(), ((PropertyDefinition) extensions.get(3)).key());
        Assert.assertEquals(PROXY_PORT.property(), ((PropertyDefinition) extensions.get(4)).key());
        Assert.assertEquals(PROXY_PROTOCOL.property(), ((PropertyDefinition) extensions.get(5)).key());
        Assert.assertEquals(ENABLED.property(), ((PropertyDefinition) extensions.get(6)).key());
        Assert.assertEquals(INCLUDE_BRANCH.property(), ((PropertyDefinition) extensions.get(7)).key());
        Assert.assertEquals(DEFAULT_CHANNEL.property(), ((PropertyDefinition) extensions.get(8)).key());
        Assert.assertEquals(NOTIFICATION_TEMPLATE.property(), ((PropertyDefinition) extensions.get(9)).key());
        Assert.assertEquals(SERVER_TOKEN.property(), ((PropertyDefinition) extensions.get(10)).key());
        Assert.assertEquals(INCLUDE_GATE.property(), ((PropertyDefinition) extensions.get(11)).key());
        Assert.assertEquals(CONFIG.property(), ((PropertyDefinition) extensions.get(12)).key());
        Assert.assertEquals(SlackPostProjectAnalysisTask.class, extensions.get(13));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void define_noDuplicateIndexes() {

        final Plugin.Context mockContext = mock(Plugin.Context.class);
        this.plugin.define(mockContext);
        final ArgumentCaptor<List> arg = ArgumentCaptor.forClass(List.class);
        verify(mockContext, times(1)).addExtensions(arg.capture());

        final List<Object> extensions = arg.getValue();

        final Set<Integer> indexes = extensions.stream().filter(PropertyDefinition.class::isInstance)
            .map(PropertyDefinition.class::cast).map(PropertyDefinition::index).
                collect(Collectors.toSet());
        Assert.assertEquals(12, indexes.size());

    }

}
