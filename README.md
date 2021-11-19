# Geofuzz Demo

Demonstrates building a fuzzy geospatial-text index leveraging some fancy hashing techniques. Remember: This is just a prototype/demo!

## Data

`data/pois.bz2` contains about 900000 POIs extracted from OSM North America.

## Indexing

Run `./index.sh` to build the source code and start indexing the POIs. That will take about one minute.

## Querying

Run `./query.sh` and start typing. Search results will immediately appear.

To move your location hit ENTER. That will use the first result's location of the previous search as new location context.

PLEASE NOTE: I am using `stty -icanon min 1` to be able to read from the terminal character-by-character. So, search results should appear immediately after you type a letter.

## Example Query Flow:

Start `./query.sh`

You will see:

```
Querying data/pois

Start typing, if you like the first result, hit 'ENTER' to move your location there
```

Type `seattle`:

```
seattle

53% ferry_terminal              Seattle                                            Alaskan Way                              Seattle                                  6370km
43% ferry_terminal              Seattle                                                                                                                              6371km
43% ferry_terminal              Seattle                                                                                                                              6371km
23% cafe                        Seattle Drip                                                                                                                         6371km
23% restaurant                  Seaside Prime                                      Broadway Street                          Richmond                                 6371km
23% restaurant                  Sea Thai                                           North 45th Street                        Seattle                                  6371km
23% arts_centre                 Seattle Mosaic Arts                                North 46th Street                        Seattle                                  6371km
23% fuel                        Esso                                               Sims Avenue                              Weyburn                                  6371km
20% cafe                        Seattle Grind                                      Harrison Street                          Seattle                                  6371km
20% library                     Library                                            Holcott Drive                            Attleboro                                6371km
```

Hit ENTER:

```
You are now located at Seattle. Start typing to explore the area
```

Type `starbucks`:

```
starbucks

133% cafe                       Starbucks                                          Broadway East                            Seattle                                  3km
133% cafe                       Starbucks                                          Occidental Avenue South                  Seattle                                  2km
126% cafe                       Starbucks                                          1st Avenue South                         Seattle                                  2km
123% cafe                       Starbucks                                          12th Avenue                              Seattle                                  4km
120% cafe                       Starbucks                                          Strander Boulevard                       Tukwila                                  18km
113% cafe                       Starbucks                                          Lake Street                              Kirkland                                 17km
110% cafe                       Starbucks                                          Ackroyd Road                             Richmond                                 169km
110% cafe                       Starbucks                                          Sea Island Way                           Richmond                                 170km
110% cafe                       Starbucks                                          De Palma Road                            Corona                                   1438km
100% cafe                       Starbucks                                          Northwest 23rd Avenue                    Portland                                 198km
```

Erase (backspace) and type `fogo de chao`:

```
fogo de chao

106% restaurant                 Fogo de Chao                                       6th Avenue                               San Diego                                1544km
93% restaurant                  Fogo de Chao                                       Bellevue Way Northeast                                                            16km
76% restaurant                  Fogo de Chao                                       Park Place                               Rosemont                                 3820km
70% restaurant                  Fogo de Chao                                                                                Philadelphia                             5157km
60% restaurant                  Fogo De Chao                                                                                                                         3178km
56% restaurant                  Fogo de Chao                                                                                                                         3210km
56% restaurant                  Fogo De Chao                                                                                                                         3235km
56% restaurant                  Fogo de Chão                                       Santana Row                              San Jose                                 968km
53% restaurant                  Fogo de Chao                                                                                                                         3179km
53% restaurant                  Fogo de Chão                                       Southwest 6th Avenue                     Portland                                 199km
```

Hit ENTER:

```
You are now located at Fogo de Chao. Start typing to explore the area
```

Type `pharmacy`:

```
pharmacy

110% pharmacy                   CVS Pharmacy                                                                                                                         16km
110% pharmacy                   Rite Aid                                           Yucaipa Boulevard                        Yucaipa                                  131km
110% pharmacy                   CVS Pharmacy                                       El Cajon Boulevard                       San Diego                                11km
106% pharmacy                   CVS Pharmacy                                                                                                                         37km
106% pharmacy                   Walgreens                                          3rd Avenue                               Chula Vista                              14km
103% pharmacy                   Pharmaca                                                                                                                             757km
103% pharmacy                   Rite Aid                                           Del Mar Heights Road                     San Diego                                25km
100% pharmacy                   CVS Pharmacy                                                                                                                         134km
100% pharmacy                   Rite Aid                                                                                                                             14km
100% pharmacy                   Walgreens                                          Rosemead Boulevard                       San Gabriel                              171km
```

That's it :-)...
