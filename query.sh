set -e
./gradlew build
stty -icanon min 1
java -cp build/libs/geofuzz-fat-1.0-SNAPSHOT.jar Query data/pois
