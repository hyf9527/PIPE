package pipe.actions.gui.animate;

import pipe.actions.gui.create.NoopAction;
import pipe.controllers.PetriNetController;
import pipe.controllers.PipeApplicationController;
import pipe.gui.Animator;
import pipe.gui.model.PipeApplicationModel;
import pipe.views.AbstractPetriNetViewComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This action is responsible for toggling the petri net in and out of animation mode
 */
public class ToggleAnimateAction extends AnimateAction {
    private final PipeApplicationModel applicationModel;

    private final PipeApplicationController applicationController;

    /**
     * Noop action to be used for toggling in and out of animation
     * Removes any spurious creates happening in animation mode
     */
    private final ActionListener noopAction;

    public ToggleAnimateAction(String name, String tooltip, String keystroke,
                               PipeApplicationModel applicationModel, PipeApplicationController applicationController) {
        super(name, tooltip, keystroke);
        this.applicationModel = applicationModel;
        this.applicationController = applicationController;
        noopAction = new NoopAction(applicationModel);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        PetriNetController petriNetController = applicationController.getActivePetriNetController();
        boolean animated = petriNetController.toggleAnimation();

        applicationModel.setInAnimationMode(animated);

        noopAction.actionPerformed(null);

        Animator animator = petriNetController.getAnimator();
        if (animated) {
            animator.startAnimation();
            AbstractPetriNetViewComponent.ignoreSelection(false);
        } else {
            animator.finish();
            AbstractPetriNetViewComponent.ignoreSelection(true);
        }
    }
}