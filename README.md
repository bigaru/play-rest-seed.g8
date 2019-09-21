# play-rest-seed.g8
[![Build Status](https://travis-ci.org/bigaru/play-rest-seed.g8.svg?branch=master)](https://travis-ci.org/bigaru/play-rest-seed.g8)

A fully-featured [Giter8](http://www.foundweekends.org/giter8/) template for building restful API with Play Framework in Scala and MongoDB! Further details can be found in the [quick start](https://github.com/bigaru/play-rest-seed.g8/blob/master/src/main/g8/README.md), which is located within the project seed.

## To create a project:
```
sbt new bigaru/play-rest-seed.g8
```

## Features
* flexible and compile-time dependency injection thanks to [MacWire](https://github.com/adamw/macwire)
* [ReactiveMongo](https://github.com/ReactiveMongo/Play-ReactiveMongo) is a non-blocking driver for MongoDB
* Swagger spec are generated based on comments in routes files by [iHeartRadio swagger plugin](https://github.com/iheartradio/play-swagger), in order to avoid code pollution (like java annotations)
* opinionated generic DAO implementation


## To test this template locally
* first install [g8](http://www.foundweekends.org/giter8/setup.html)
* then run following commands:
```
git clone git@github.com:bigaru/play-rest-seed.g8.git
g8 file://play-rest-seed.g8/ --name=my-seed-test --force
```

## Template license
Written in 2019 by bigaru

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.
