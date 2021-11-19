./gradlew build
bzip2 -dk data/pois.bz2
java -cp build/libs/geofuzz-fat-1.0-SNAPSHOT.jar Index data/pois