package magic.effect;

public abstract class EffectExecutor {
    protected abstract void execute(DamageCreatureEffect effect);
    protected abstract void execute(LeaveBattlefield effect);
    protected abstract void execute(EnterBattlefield effect);
    protected abstract void execute(EnterGraveyard effect);
    protected abstract void execute(ExilePermanent effect);
    protected abstract void execute(PlaceOneOneCounter effect);
    protected abstract void execute(MultiEffect effect);
}
