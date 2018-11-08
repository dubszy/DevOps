package com.mwaltman.devops.framework;

import io.dropwizard.configuration.ConfigurationSourceProvider;

import java.io.*;

/**
 * An implementation of {@link ConfigurationSourceProvider} that reads two
 * configurations from the local file system. This works effectively the same
 * as DropWizard's {@link io.dropwizard.configuration.FileConfigurationSourceProvider}
 * except that this implementation reads two configurations and merges the
 * streams with {@link SequenceInputStream}. Note that the additional
 * configuration file must be a sibling of the main configuration file on the
 * file system.
 * <br/><br/>
 * How to use:
 *
 * <pre>
 * public class MyApplication extends {@link io.dropwizard.Application} {
 *     public void initialize({@link io.dropwizard.setup.Bootstrap} bootstrap) {
 *         bootstrap.setConfigurationSourceProvider(
 *           new MultipleFileConfigurationSourceProvider("my_additional_config.yml");
 *     }
 * }
 * </pre>
 *
 * @see io.dropwizard.setup.Bootstrap#setConfigurationSourceProvider(ConfigurationSourceProvider)
 *      Setting the configuration source provider
 */
public class MultipleFileConfigurationSourceProvider implements ConfigurationSourceProvider {

    private final String additionalConfigFileName;

    public MultipleFileConfigurationSourceProvider(String additionalConfigFileName) {
        this.additionalConfigFileName = additionalConfigFileName;
    }

    @Override
    public InputStream open(String path) throws IOException {
        final File mainConfigFile = new File(path);

        final File additionalConfigFile =
                new File(mainConfigFile
                        .getParent()
                        .concat("/")
                        .concat(additionalConfigFileName));

        if (!mainConfigFile.exists()) {
            throw new FileNotFoundException("Config file " + mainConfigFile + " not found");
        }
        if (!additionalConfigFile.exists()) {
            throw new FileNotFoundException("Config file " + additionalConfigFile + " not found");
        }

        return new SequenceInputStream(
                new FileInputStream(mainConfigFile),
                new FileInputStream(additionalConfigFile));
    }
}
