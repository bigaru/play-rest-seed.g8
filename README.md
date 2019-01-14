A [Giter8](http://www.foundweekends.org/giter8/) template for summoning restful daemons!

# To summon
```
sbt new bigaru/play-rest-seed.g8
```

# Practice summoning locally
* install [g8](http://www.foundweekends.org/giter8/setup.html)
```
git clone git@github.com:bigaru/play-rest-seed.g8.git
g8 file://play-rest-seed.g8/ --name=my-seed-test --force
```

# Features
* macwire
    * compile-time dependency injection
* reactivemongo
    * non-blocking driver for MongoDB
* swagger spec
    * generator from iheartradio
    * to avoid code pollution

# Template license
Written in 2019 by bigaru

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.