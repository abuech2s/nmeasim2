package simulator.data.ais.additionalFeatures.patrols;

public class P2SourceB extends AbstractPatrolSource {

	public P2SourceB() {
		super("RUS_Patrol2B", PatrolLines.reverse(PatrolLines.getP2Route()));
	}
	
}
