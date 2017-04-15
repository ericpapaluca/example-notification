package org.example;

import co.cask.cdap.api.annotation.Description;
import co.cask.cdap.api.annotation.Macro;
import co.cask.cdap.api.annotation.Name;
import co.cask.cdap.api.annotation.Plugin;
import co.cask.cdap.api.workflow.WorkflowNodeState;
import co.cask.cdap.api.workflow.WorkflowToken;
import co.cask.cdap.etl.api.PipelineConfigurer;
import co.cask.cdap.etl.api.batch.BatchActionContext;
import co.cask.cdap.etl.api.batch.PostAction;
import co.cask.hydrator.common.batch.action.ConditionConfig;

import java.util.Map;

/**
 * NotificationExample implementation of {@link PostAction}.
 *
 * <p>
 *   {@link PostAction} are type of plugins that are executed in the
 *   Batch pipeline at the end of execution. Irrespective of the status of
 *   the pipeline this plugin will be invoked.
 *
 *   This type of plugin can be used to send notifications to external
 *   system or notify other workflows.
 * </p>
 */
@Plugin(type = PostAction.PLUGIN_TYPE)
@Name("NotificationExample")
@Description("An example of notification plugin.")
public final class NotificationExample extends PostAction {
  private final Config config;

  public static class Config extends ConditionConfig {
    @Description("This is a configuration for this plugin.")
    @Name("name")
    @Macro
    private String name;
  }

  public NotificationExample(Config config) {
    this.config = config;
  }

  @Override
  public void configurePipeline(PipelineConfigurer configurer) {
    super.configurePipeline(configurer);
  }

  @Override
  public void run(BatchActionContext context) throws Exception {
    // Framework provides the ability to decide within this plugin
    // whether this should be run or no. This happens depending on
    // the status of pipeline selected -- COMPLETION, SUCCESS or FAILURE.
    if (!config.shouldRun(context)) {
      return;
    }

    // true if SUCCESS, false otherwise.
    boolean status = context.isSuccessful();

    // Extracts the status for each nodes in the dag.
    Map<String, WorkflowNodeState> nodeStates = context.getNodeStates();

    //
    WorkflowToken token = context.getToken();
  }
}
