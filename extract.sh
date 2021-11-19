./gradlew build
echo IF FAILS, YOU NEED TO DOWNLOAD first!!!
java -cp build/libs/geofuzz-fat-1.0-SNAPSHOT.jar Index data/na.osm.bz2 data/pois.bz2