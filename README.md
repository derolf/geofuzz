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

## Example Query Flow

Start `./query.sh`

You will see:

```
Querying data/pois

Start typing. If you like the first result, hit 'ENTER' to move your location there:
```

Type `seattle`:

```
seattle

86% ferry_terminal              Seattle                                            Alaskan Way                              Seattle                                  6370km
70% ferry_terminal              Seattle                                                                                                                              6371km
70% ferry_terminal              Seattle                                                                                                                              6371km
40% cafe                        Seattle Grind                                      Harrison Street                          Seattle                                  6371km
40% event_space                 Seattle Cathedral                                  Ballard Avenue Northwest                 Seattle                                  6371km
33% pub                         Red Onion                                          East Madison Street                      Seattle                                  6371km
33% pub                         LTD                                                North 36th Street                        Seattle                                  6370km
33% pub                         Kate's Pub                                         Northeast 45th Street                    Seattle                                  6371km
33% fuel                        Shilshole Fuel Dock                                Seaview Avenue Northwest                 Seattle                                  6371km
33% spa                         Banya 5                                            9th Avenue North                         Seattle                                  6371km
```

Hit ENTER:

```
You are now located at Seattle. Start typing to explore the area and hit ENTER to move again:
```

Type `starbucks`:

```
starbucks

123% cafe                       Starbucks                                          Broadway East                            Seattle                                  3km
123% cafe                       Starbucks                                          Occidental Avenue South                  Seattle                                  2km
120% cafe                       Starbucks                                          Lake Street                              Kirkland                                 17km
120% cafe                       Starbucks                                          Strander Boulevard                       Tukwila                                  18km
106% cafe                       Starbucks                                          1st Avenue South                         Seattle                                  2km
100% cafe                       Starbucks                                          Alderwood Mall Parkway                   Lynnwood                                 21km
100% cafe                       Starbucks                                          196th Street Southwest                   Lynnwood                                 19km
96% cafe                        Starbucks                                          164th Street Southwest                   Lynnwood                                 23km
96% cafe                        Starbucks                                          Washington Avenue South                  Kent                                     24km
93% cafe                        Starbucks                                          Dawson Street                            Burnaby                                  171km
```

Erase (backspace) and type `fogo de chao`:

```
fogo de chao

106% restaurant                 Fogo de Chao                                       6th Avenue                               San Diego                                1544km
106% restaurant                 Fogo de Chao                                                                                Philadelphia                             5157km
106% restaurant                 Fogo de Chao                                       Park Place                               Rosemont                                 3820km
90% restaurant                  Fogo de Chao                                       Bellevue Way Northeast                                                            16km
76% restaurant                  Fogo de Chao                                                                                                                         3210km
73% restaurant                  Fogo De Chao                                                                                                                         3235km
73% restaurant                  Fogo de chao                                       West 53rd Street                                                                  5265km
73% restaurant                  Fogo de Chao                                                                                                                         3179km
73% restaurant                  fogo de chao                                                                                                                         5538km
73% restaurant                  Fogo de Chao                                                                                                                         4038km
```

Hit ENTER:

```
You are now located at Fogo de Chao. Start typing to explore the area and hit ENTER to move again:
```

Type `pharmacy`:

```
pharmacy

136% pharmacy                   Community Pharmacy                                 University Avenue                        San Diego                                3km
136% pharmacy                   CVS Pharmacy                                       El Cajon Boulevard                       San Diego                                11km
130% pharmacy                   Rite Aid                                           Robinson Avenue                          San Diego                                3km
126% pharmacy                   Walgreens                                          3rd Avenue                               Chula Vista                              14km
126% pharmacy                   CVS Pharmacy                                       Market Street                            San Diego                                0km
116% pharmacy                   CVS                                                El Cajon Boulevard                       San Diego                                7km
113% pharmacy                   CVS                                                West Valley Parkway                      Escondido                                39km
113% pharmacy                   Rite Aid                                           Jamacha Road                             El Cajon                                 25km
113% pharmacy                   CVS Pharmacy                                       Rosecrans Street                         San Diego                                6km
110% pharmacy                   Walgreens                                          Encinitas Boulevard                      Encinitas                                34km
```

Erase (backspace) and type `cafe toronto`:

```
cafe toronto

83% cafe                        Soban Cafe                                         Yonge Street                             Toronto                                  4278km
80% cafe                        Lido Caffe                                         St. Clair Avenue West                    Toronto                                  4271km
73% cafe                        Cafe Nuna                                          Queen Street West                        Toronto                                  4275km
73% cafe                        Cafe Piccolino                                     Bloor Street West                        Toronto                                  4272km
70% cafe                        Faema Cafe                                         Dupont Street                            Toronto                                  4275km
70% cafe                        Cactus                                             Bloor Street West                        Toronto                                  4275km
70% cafe                        Chatime                                            Yonge Street                             Toronto                                  4278km
70% cafe                        Caked Coffee                                       Bloor Street West                        Toronto                                  4271km
66% cafe                        Krave Coffee                                       St. Clair Avenue West                    Toronto                                  4274km
66% cafe                        Douce France                                       Yonge Street                             Toronto                                  4278km
```

Hit ENTER:

```
You are now located at Soban Cafe. Start typing to explore the area and hit ENTER to move again:
```

Type `fast_food`:

```
fast_food

110% fast_food                  Villa Fruit                                        Yonge Street                             North York                               1km
103% fast_food                  California Thai                                    Yonge Street                             North York                               1km
100% fast_food                  McDonald's                                         Yonge Street                             North York                               1km
96% fast_food                   Subway                                                                                                                               4km
93% fast_food                   Pizza Pizza                                                                                                                          4km
93% fast_food                   Wendy's                                            Yonge Street                             North York                               1km
93% fast_food                   Pizza Hut Express                                  Yonge Street                             North York                               1km
90% fast_food                   Subway                                             Finch Avenue West                        North York                               0km
90% fast_food                   Haida Sandwich                                     Northtown Way                            Toronto                                  0km
83% fast_food                   Pizza Pizza                                                                                                                          5km
```

That's it :-)...
