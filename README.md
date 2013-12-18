MTG-Engine
==========

An engine for executing the rules of Magic: The Gathering. (Magic: The Gathering is a trademark of Wizards of the Coast)


The implementation so far focuses on triggered events and conditional effect replacement. 
If there is a static effect on the Battlefield that replaces all Creature deaths with Exile, then the death should be fully replaced by an Exile effect - that is, nothing that triggers from "Enter the Graveyard" should trigger at that time. Since this seems to be one of the most fine-tuned aspects of MTG I'm focussing on a framework that can handle these kinds of effects first.

Eventually, I envision this being flexible enough to handle a huge variety of cards without much additional coding. The majority of card effects can be represented as an "expression tree" of simple effects. With a strong variety of common simple effects and the ability to group them together with conditions and complicated targeting rules, most of the cards in the game should be easy to implement.
