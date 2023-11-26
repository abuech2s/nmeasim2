package simulator.data.ais.additionalFeatures.patrols;

public class P3Source extends AbstractPatrolSource {

	public P3Source() {
		super("SWE_Patrol", PatrolLines.getP3Route());
	}
	
}
