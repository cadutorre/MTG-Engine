package magic.effect;

public interface Effect {
    public void accept(EffectExecutor executor);

    public boolean someTargetsLegal();
}
