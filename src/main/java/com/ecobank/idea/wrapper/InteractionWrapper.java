package com.ecobank.idea.wrapper;

import com.ecobank.idea.constants.InteractionEnum;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.entity.Interaction;
import com.ecobank.idea.entity.User;

public final class InteractionWrapper {
    private InteractionWrapper() {

    }

    // Create an interaction
    public static Interaction createInteraction(User user, Idea idea, InteractionEnum interactionType) {
        Interaction interaction = new Interaction();
        interaction.setIdea(idea);
        interaction.setUser(user);
        interaction.setInteractionType(interactionType);
        return interaction;
    }

    public static int getInteractionValue(InteractionEnum interactionType) {
        return switch (interactionType) {
            case InteractionEnum.COMMENT -> 2;        // Example weight for a comment
            case InteractionEnum.LIKE -> 1;           // Example weight for a like
            case InteractionEnum.CREATE -> 3;         // Example weight for creating an idea
        };
    }
}
