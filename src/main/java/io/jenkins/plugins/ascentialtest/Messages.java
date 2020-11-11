// CHECKSTYLE:OFF

package io.jenkins.plugins.ascentialtest;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;
import org.kohsuke.accmod.Restricted;


/**
 * Generated localization support class.
 * 
 */
@SuppressWarnings({
    "",
    "PMD",
    "all"
})
@Restricted(org.kohsuke.accmod.restrictions.NoExternalUse.class)
public class Messages {

    /**
     * The resource bundle reference
     * 
     */
    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    /**
     * Key {@code AscentialTestBuilder.DescriptorImpl.errors.missingName}:

     * {@code Required field}.
     * 
     * @return
     *     {@code Required field}
     */
    public static String AscentialTestBuilder_DescriptorImpl_errors_missingName() {
        return "Please enter value for required field";
    }

    /**
     * Key {@code AscentialTestBuilder.DescriptorImpl.errors.missingName}:

     * {@code Required field}.
     * 
     * @return
     *     {@code Required field}
     */
    public static Localizable _HelloWorldBuilder_DescriptorImpl_errors_missingName() {
        return new Localizable(holder, "HelloWorldBuilder.DescriptorImpl.errors.missingName");
    }


    static class AscentialTestCmdLineBuilder {

        static String DescriptorImpl_DisplayName() {
            return "AscentialTest Command Line Builder";
        }

        public AscentialTestCmdLineBuilder() {
        }
    }

}
