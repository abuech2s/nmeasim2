package simulator.data.ais.additionalFeatures.patrols;

public class P1SourceB extends AbstractPatrolSource {

	public P1SourceB() {
		super("RUS_Patrol1B", PatrolLines.reverse(PatrolLines.getP1Route()));
	}
	
}
