package st.seaside;

import java.io.IOException;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import st.seaside.PharoImageResizer.DescriptorImpl;

/**
 * {@link Builder} for <a href="http://www.pharo-project.org/">Pharo</a>
 * images.
 *
 * <p>
 * When the user configures the project and enables this builder,
 * {@link DescriptorImpl#newInstance(StaplerRequest)} is invoked
 * and a new {@link PharoImageResizer} is created. The created
 * instance is persisted to the project configuration XML by using
 * XStream, so this allows you to use instance fields (like {@link #name})
 * to remember the configuration.
 *
 * <p>
 * When a build is performed, the {@link #perform(AbstractBuild, Launcher, BuildListener)} method
 * will be invoked.
 *
 * @author Philippe Marschall
 */
public class PharoImageResizer extends Builder {

  private final String image;
  private final int width;
  private final int height;

  @DataBoundConstructor
  public PharoImageResizer(String image, int width, int height) {
    this.image = image;
    this.width = width;
    this.height = height;
  }

  public String getImage() {
    // needed by Jelly, don't remove
    return this.image;
  }

  public int getWidth() {
    // needed by Jelly, don't remove
    return this.width;
  }

  public int getHeight() {
    // needed by Jelly, don't remove
    return this.height;
  }

  byte[] getWidthInLittleEndian() {
    return toLittleEndian(this.width);
  }

  byte[] getHeightInLittleEndian() {
    return toLittleEndian(this.height);
  }

  private static byte[] toLittleEndian(int i) {
    return new byte[]{(byte) (i & 0xFF), (byte) (i >> 8)};
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
      throws InterruptedException, IOException {
    listener.getLogger().println("Not yet implemented");
    return false;
  }


  /**
   * Descriptor for {@link PharoImageResizer}. Used as a singleton.
   * The class is marked as public so that it can be accessed from views.
   *
   * <p>
   * See <tt>views/hudson/plugins/pharo-build/OneClickBuilder/*.jelly</tt>
   * for the actual HTML fragment for the configuration screen.
   */
  public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {


    /**
     * Default constructor, loads the defaults first and then the saved data.
     */
    public DescriptorImpl() {
      super(PharoImageResizer.class);
      load();
    }

    /**
     * Constructor, only loads the defaults and not the saved data.
     *
     * @param clazz the builder class
     */
    protected DescriptorImpl(Class<? extends PharoBuilder> clazz) {
      super(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isApplicable(Class<? extends AbstractProject> jobType) {
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
      return Messages.imageResizer_displayName();
    }

  }

}