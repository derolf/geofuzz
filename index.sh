set -e
./gradlew build
if [ ! -f "data/pois" ]; then
    bzip2 -dk data/pois.bz2
fi
java -cp build/libs/geofuzz-fat-1.0-SNAPSHOT.jar Index data/pois