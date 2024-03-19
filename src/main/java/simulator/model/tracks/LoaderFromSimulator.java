package simulator.model.tracks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.Constants;
import simulator.config.model.AppConfig;
import simulator.config.model.Config;
import simulator.data.adsb.ADSBSourceFactory;
import simulator.data.adsb.additionalFeatures.AirplanesOverBalticSea.AirplanesOverBalticSeaSourceFactory;
import simulator.data.ais.AISSourceFactory;
import simulator.data.ais.additionalFeatures.ferries.FerriesSourceFactory;
import simulator.data.ais.additionalFeatures.fishers.FishersSourceFactory;
import simulator.data.ais.additionalFeatures.patrols.PatrolSourceFactory;
import simulator.data.course.CourseSourceFactory;
import simulator.data.gps.GPSSourceFactory;
import simulator.data.radar.RadarSourceFactory;
import simulator.data.weather.WeatherSourceFactory;
import simulator.model.sinks.ISink;
import simulator.model.sinks.TCPSink;
import simulator.model.sinks.UDPSink;
import simulator.util.datagenerators.HexIdentGenerator;
import simulator.util.datagenerators.MMSIGenerator;

public class LoaderFromSimulator {
	
	private static final Logger log = LoggerFactory.getLogger(LoaderFromSimulator.class);

	protected static List<ITrack> loadFromSimulator(AppConfig appConfig) {
		List<ITrack> tracks = new ArrayList<>();
		
		for (Config config : appConfig.getDataStreams().getConfigs()) {
			String type = config.getType().toString().toLowerCase();
			switch (type) {
			case Constants.TOKEN_ADSB:
				if (config.isActive()) {
					Track track = new Track(Constants.TOKEN_ADSB, ADSBSourceFactory.get(), createSink(config));
					track.initSources(config.getNroftrack());
					if (appConfig.getAdditionalFeatures().isAirplanesOverBalticSea()) track.addAdditionalFeatureSource(new AirplanesOverBalticSeaSourceFactory());
					tracks.add(track);
				}
				Constants.DEFAULT_TRACK_SLEEP_TIME = config.getSleepTime();
				HexIdentGenerator.setInitTrackId(config.getInitTrackId());
				break;
			case Constants.TOKEN_AIS:
				if (config.isActive()) {
					Track track = new Track(Constants.TOKEN_AIS, AISSourceFactory.get(), createSink(config));
					track.initSources(config.getNroftrack());
					if (appConfig.getAdditionalFeatures().isPatrolVessels()) track.addAdditionalFeatureSources(new PatrolSourceFactory());
					if (appConfig.getAdditionalFeatures().isFishingVessels()) track.addAdditionalFeatureSources(new FishersSourceFactory());
					if (appConfig.getAdditionalFeatures().isFerryVessels()) track.addAdditionalFeatureSources(new FerriesSourceFactory());
					tracks.add(track);
				}
				Constants.DEFAULT_TRACK_SLEEP_TIME = config.getSleepTime();
				MMSIGenerator.setInitTrackId(config.getInitTrackId());
				break;
			case Constants.TOKEN_GPS:
				if (config.isActive()) {
					Track track = new Track(Constants.TOKEN_GPS, GPSSourceFactory.get(), createSink(config));
					track.initSources(1);
					tracks.add(track);
				}
				break;
			case Constants.TOKEN_RADAR:
				if (config.isActive()) {
					Track track = new Track(Constants.TOKEN_RADAR, RadarSourceFactory.get(), createSink(config));
					track.initSources(1);
					tracks.add(track);
				}
				Constants.MAX_RADAR_RADIUS = config.getRadius();
				break;
			case Constants.TOKEN_WEATHER:
				if (config.isActive()) {
					Track track = new Track(Constants.TOKEN_WEATHER, WeatherSourceFactory.get(), createSink(config));
					track.initSources(1);
					tracks.add(track);
				}
				break;	
			case Constants.TOKEN_COURSE:
				if (config.isActive()) {
					Track track = new Track(Constants.TOKEN_COURSE, CourseSourceFactory.get(), createSink(config));
					track.initSources(1);
					tracks.add(track);
				}
				break;
			default:
				log.warn("Unknown type: {}. Ignored.", type);
			}
		}
		return tracks;
	}
	
	private static ISink createSink(Config config) {
		switch (config.getSink().toLowerCase().trim()) {
		case Constants.TOKEN_TCP:
			return new TCPSink(config.getType().toString(), config.getPort(), Math.max(config.getNroftrack(), 50));
		case Constants.TOKEN_UDP:
			return new UDPSink(config.getType().toString(), config.getIp(), config.getPort(), Math.max(config.getNroftrack(), 50));
		default:
			log.warn("Unknown sink type: {}", config);
			return null;
		}
	}
	
}
