package simulator.data.course;

import simulator.model.ISourceFactory;
import simulator.model.source.ISource;

public class CourseSourceFactory implements ISourceFactory {

	@Override
	public ISource getSource() {
		return new CourseSource();
	}
	
	public static CourseSourceFactory get() {
		return new CourseSourceFactory();
	} 

}
